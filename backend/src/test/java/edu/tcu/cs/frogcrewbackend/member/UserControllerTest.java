package edu.tcu.cs.frogcrewbackend.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.frogcrewbackend.member.dto.UserDTO;
import edu.tcu.cs.frogcrewbackend.system.StatusCode;
import edu.tcu.cs.frogcrewbackend.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    UserService userService;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    ArrayList<Member> members;

    @BeforeEach
    void setUp(){
        Member mem1 = new Member();
        mem1.setId(1);
        mem1.setFirstName("Jack");
        mem1.setLastName("Smith");
        mem1.setEmail("jsmith@gmail.com");
        mem1.setPhoneNumber("1234567890");
        mem1.setPassword("password1");
        mem1.setRole("MEMBER");
        mem1.setPositions("Director");

        Member mem2 = new Member();
        mem2.setId(2);
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

    @Test
    void testCreateMemberSuccess() throws Exception {
        Member member = new Member();
        member.setId(1);
        member.setFirstName("John");
        member.setLastName("Smith");
        member.setEmail("john.smith@gmail.com");
        member.setPhoneNumber("1234567890");
        member.setPassword("password");
        member.setRole("ADMIN");
        member.setPositions("Director");

        String json = this.objectMapper.writeValueAsString(member);

        member.setId(1);
        // Given
        given(this.userService.createMember(Mockito.any(Member.class))).willReturn(member);

        // When and Then
        this.mockMvc.perform(
                post(this.baseUrl + "/crewMember")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Member created"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.firstName").value("John"))
                .andExpect(jsonPath("$.data.lastName").value("Smith"));
    }

    @Test
    void testFindAllMembersSuccess() throws Exception {
        // Given
        given(this.userService.findAllMembers()).willReturn(this.members);

        // When and Then
        this.mockMvc.perform(get(this.baseUrl + "/crewMember")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Found all members"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].firstName").value("Jack"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].firstName").value("Jane"));
    }

    @Test
    void testFindMemberByIdSuccess() throws Exception {
        // Given
        given(this.userService.findMemberById(1)).willReturn(this.members.get(0));

        // When and Then
        this.mockMvc.perform(get(this.baseUrl + "/crewMember/1")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Found member with id: 1"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.firstName").value("Jack"));
    }

    @Test
    void testFindMemberByIdNotFound() throws Exception {
        // Given
        given(this.userService.findMemberById(3)).willThrow(new ObjectNotFoundException("member", 3));

        // When and Then
        this.mockMvc.perform(get(this.baseUrl + "/crewMember/3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find member with Id 3"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}
