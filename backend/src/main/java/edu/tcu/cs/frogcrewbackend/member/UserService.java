package edu.tcu.cs.frogcrewbackend.member;

import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Member> findAllMembers() {
        return this.userRepository.findAll();
    }

    public Member findMemberById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Member ", id));
    }
}
