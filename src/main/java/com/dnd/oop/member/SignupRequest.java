package com.dnd.oop.member;

public record SignupRequest (
    String name,
    String email,
    String password
){
}
