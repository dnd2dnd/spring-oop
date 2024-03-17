package com.dnd.oop.member.repository;

import com.dnd.oop.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
