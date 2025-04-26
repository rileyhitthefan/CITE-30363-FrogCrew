package edu.tcu.cs.frogcrewbackend.member;

import jakarta.transaction.Transactional;
import edu.tcu.cs.frogcrewbackend.system.exception.ObjectNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // Save new member
    public Member createMember(Member newMember) {
        return this.userRepository.save(newMember);
    }

    public List<Member> findAllMembers() {
        return this.userRepository.findAll();
    }

    public Member findMemberById(Integer id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("member", id));
    }

    public Member updateMember(Integer id, Member updatedMember) {
        Member oldMember = this.userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("member", id));
        oldMember.setFirstName(updatedMember.getFirstName());
        oldMember.setLastName(updatedMember.getLastName());
        oldMember.setEmail(updatedMember.getEmail());
        oldMember.setPhoneNumber(updatedMember.getPhoneNumber());
        oldMember.setRole(updatedMember.getRole());
        oldMember.setPositions(updatedMember.getPositions());
        return this.userRepository.save(oldMember);
    }

    public void deleteMember(Integer id) {
        this.userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("member", id));
        this.userRepository.deleteById(id);
    }

//    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email)
                .map(member -> new MyUserPrincipal(member))
                .orElseThrow(() -> new UsernameNotFoundException("user not found with email " + email));
    }
}
