package edu.tcu.cs.frogcrewbackend.member;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Member createMember(Member newMember) {
        return this.userRepository.save(newMember);
    }
}
