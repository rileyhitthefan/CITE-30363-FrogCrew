package edu.tcu.cs.frogcrewbackend.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, Integer> {
}
