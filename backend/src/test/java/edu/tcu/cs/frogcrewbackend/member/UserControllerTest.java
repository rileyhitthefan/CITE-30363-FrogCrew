package edu.tcu.cs.frogcrewbackend.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.frogcrewbackend.system.StatusCode;
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

import java.awt.*;

import static org.mockito.BDDMockito.given;
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

    @BeforeEach
    void setUp(){

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
}
