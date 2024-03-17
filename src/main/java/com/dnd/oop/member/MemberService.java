package com.dnd.oop.member;

import com.dnd.oop.auth.JwtProvider;
import com.dnd.oop.member.dto.AuthResponse;
import com.dnd.oop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public AuthResponse signup(SignupRequest signupRequest) {
        Member member = Member.builder()
            .name(signupRequest.name())
            .password(passwordEncoder.encode(signupRequest.password()))
            .email(signupRequest.email())
            .build();
        memberRepository.save(member);

        String accessToken = jwtProvider.createToken(member.getId());
        return new AuthResponse(accessToken);
    }

}
