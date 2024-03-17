package com.dnd.oop.member;

import java.net.URI;

import com.dnd.oop.auth.Auth;
import com.dnd.oop.auth.AuthCredentials;
import com.dnd.oop.member.dto.AuthResponse;
import com.dnd.oop.member.dto.LoginRequest;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid SignupRequest signupRequest) {
        Long id = memberService.signup(signupRequest);
        return ResponseEntity.created(URI.create("/member" + id))
            .build();
    }

    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid LoginRequest loginRequest) {
        AuthResponse response= memberService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<MemberDetailResponse> getMemberInfo(@Auth AuthCredentials authCredentials) {
        MemberDetailResponse response= memberService.getMemberInfo(authCredentials.memberId());
        return ResponseEntity.ok(response);
    }
}
