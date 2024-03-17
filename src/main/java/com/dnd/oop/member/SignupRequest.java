package com.dnd.oop.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequest (
	@NotBlank
    String name,
	@Email
	@NotBlank
    String email,
	@NotBlank
    String password
){
}
