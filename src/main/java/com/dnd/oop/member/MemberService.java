package com.dnd.oop.member;

import static com.dnd.oop.member.MemberErrorCode.*;

import java.util.Optional;

import com.dnd.oop.auth.JwtProvider;
import com.dnd.oop.member.dto.AuthResponse;
import com.dnd.oop.member.dto.LoginRequest;
import com.dnd.oop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public Long signup(SignupRequest signupRequest) {
        Member member = Member.builder()
            .name(signupRequest.name())
            .password(passwordEncoder.encode(signupRequest.password()))
            .email(signupRequest.email())
            .build();
        return  memberRepository.save(member).getId();
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Optional<Member> optionalMember = memberRepository.findByEmail(loginRequest.email());
        if(optionalMember.isEmpty()) {
            throw new MemberException(MEMBER_NOT_FOUND);
        }

        Member member = optionalMember.get();
        validatePassword(loginRequest.password(), member.getPassword());

        String accessToken = jwtProvider.createToken(member.getId());
        return new AuthResponse(accessToken);
    }

    public MemberDetailResponse getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
        return MemberDetailResponse.from(member);
    }

    private void validatePassword(String checkPassword, String savedPassword) {
        if(!passwordEncoder.matches(checkPassword, savedPassword)) {
            throw new MemberException(PASSWORD_NOT_MATCH);
        }
    }
}
