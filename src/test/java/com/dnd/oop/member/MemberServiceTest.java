package com.dnd.oop.member;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.datafaker.Faker;

import com.dnd.oop.member.dto.AuthResponse;
import com.dnd.oop.member.dto.LoginRequest;
import com.dnd.oop.member.repository.MemberRepository;

@SpringBootTest
class MemberServiceTest {
	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final Faker FAKER = new Faker();
	String name;
	String email;
	String password;

	@BeforeEach
	void setUp() {
		name = FAKER.text().text(2, 10);
		email = FAKER.internet().emailAddress();
		password = FAKER.text().text(10, 20);
	}

	@Test
	void 회원가입에_성공한다() {
		// given
		SignupRequest signupRequest = new SignupRequest(name, email, password);

		// when
		memberService.signup(signupRequest);

		// then
		assertThat(status().isOk());
	}

	@Test
	void 회원가입에_실패한다() {
		// given
		SignupRequest signupRequest = new SignupRequest(null, email, password);

		// when
		memberService.signup(signupRequest);

		// then
		assertThat(status().isBadRequest());
	}

	@Test
	void 로그인에_성공한다() {
		// given
		SignupRequest signupRequest = new SignupRequest(name, email, password);
		memberService.signup(signupRequest);
		LoginRequest loginRequest = new LoginRequest(email, password);

		// when
		AuthResponse authResponse = memberService.login(loginRequest);

		// then
		assertThat(authResponse.accessToken()).isNotNull();
	}

	@Test
	void 존재하지않는아이디로_로그인에_실패한다() {
		// given
		LoginRequest loginRequest = new LoginRequest(email, password);

		// when
		ThrowingCallable throwingCallable = () -> memberService.login(loginRequest);

		// then
		assertThatExceptionOfType(MemberException.class).isThrownBy(throwingCallable)
			.withMessage(MemberErrorCode.MEMBER_NOT_FOUND.getMessage());
	}

	@Test
	void 비밀번호가_틀려_로그인에_실패한다() {
		// given
		SignupRequest signupRequest = new SignupRequest(name, email, password);
		memberService.signup(signupRequest);
		LoginRequest loginRequest = new LoginRequest(email, "invalid-password");

		// when
		ThrowingCallable throwingCallable = () -> memberService.login(loginRequest);

		// then
		assertThatExceptionOfType(MemberException.class).isThrownBy(throwingCallable)
			.withMessage(MemberErrorCode.PASSWORD_NOT_MATCH.getMessage());
	}

	@Test
	void 나의_정보를_가져온다() {
		// given
		SignupRequest signupRequest = new SignupRequest(name, email, password);
		Long id = memberService.signup(signupRequest);

		// when
		MemberDetailResponse expected = memberService.getMemberInfo(id);

		// then
		assertThat(expected.id()).isEqualTo(id);
		assertThat(expected.name()).isEqualTo(name);
		assertThat(expected.email()).isEqualTo(email);
	}
}