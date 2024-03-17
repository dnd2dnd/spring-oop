package com.dnd.oop.member;

public record MemberDetailResponse (
	Long id,
	String name,
	String email
){
	public static MemberDetailResponse from(Member member) {
		return new MemberDetailResponse(member.getId(), member.getName(), member.getEmail());
	}
}
