package com.dnd.oop.auth;

import static com.dnd.oop.auth.AuthErrorCode.*;
import static org.springframework.http.HttpHeaders.*;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtArgumentResolver implements HandlerMethodArgumentResolver {
	private final JwtProvider jwtProvider;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Auth.class)
			&& parameter.getParameterType().equals(AuthCredentials.class);
	}

	@Override
	public Object resolveArgument(
		MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		if (request.getHeader(AUTHORIZATION) == null) {
			throw new TokenException(TOKEN_NOT_FOUND);
		}
		String accessToken = BearerTokenExtractor.extract(request);
		String id = jwtProvider.getPayload(accessToken);

		return new AuthCredentials(Long.valueOf(id));
	}
}
