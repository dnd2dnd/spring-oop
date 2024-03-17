package com.dnd.oop.member;

import com.dnd.oop.member.dto.AuthResponse;
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
    public ResponseEntity<AuthResponse> signup(SignupRequest signupRequest) {
        AuthResponse response = memberService.signup(signupRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/login")
    public ResponseEntity<Void> login() {
        return null;
    }
}
