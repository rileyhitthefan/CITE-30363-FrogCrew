package edu.tcu.cs.frogcrewbackend.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    List<Member> members;

    @BeforeEach
    void setUp(){
        Member mem1 = new Member();
        mem1.setFirstName("Jack");
        mem1.setLastName("Smith");
        mem1.setEmail("jsmith@gmail.com");
        mem1.setPhoneNumber("1234567890");
        mem1.setPassword("password1");
        mem1.setRole("MEMBER");
        mem1.setPositions("Director");

        Member mem2 = new Member();
        mem2.setFirstName("Jane");
        mem2.setLastName("Smith");
        mem2.setEmail("jane.smith@gmail.com");
        mem2.setPhoneNumber("0123456789");
        mem2.setPassword("password2");
        mem2.setRole("MEMBER");
        mem2.setPositions("Videographer Planner");

        this.members = new ArrayList<>();
        this.members.add(mem1);
        this.members.add(mem2);
    }

    @AfterEach
    void tearDown(){

    }

    @Test
    void testCreateMemberSuccess(){
        // Given
        Member newMember = new Member();
        newMember.setFirstName("Ben");
        newMember.setLastName("Ten");
        newMember.setEmail("b10@gmail.com");
        newMember.setPhoneNumber("0001112222");
        newMember.setPassword("password");
        newMember.setRole("MEMBER");
        newMember.setPositions("Crew");

        given(this.userRepository.save(newMember)).willReturn(newMember);

        // When
        Member returnedMember = this.userService.createMember(newMember);

        // Then
        assertThat(returnedMember.getFirstName()).isEqualTo(newMember.getFirstName());
        assertThat(returnedMember.getLastName()).isEqualTo(newMember.getLastName());
        assertThat(returnedMember.getEmail()).isEqualTo(newMember.getEmail());
        assertThat(returnedMember.getPhoneNumber()).isEqualTo(newMember.getPhoneNumber());
        assertThat(returnedMember.getRole()).isEqualTo(newMember.getRole());
        assertThat(returnedMember.getPositions()).isEqualTo(newMember.getPositions());
        verify(this.userRepository, times(1)).save(newMember);
    }
}
