package edu.tcu.cs.frogcrewbackend.availability;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.tcu.cs.frogcrewbackend.availability.converter.AvailabilityToAvailabilityDTOConverter;
import edu.tcu.cs.frogcrewbackend.availability.dto.AvailabilityDTO;
import edu.tcu.cs.frogcrewbackend.game.Game;
import edu.tcu.cs.frogcrewbackend.member.Member;
import edu.tcu.cs.frogcrewbackend.system.StatusCode;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AvailabilityControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    AvailabilityService availabilityService;

    @MockitoBean
    AvailabilityToAvailabilityDTOConverter availabilityToAvailabilityDTOConverter;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    ArrayList<Availability> availList;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testAddAvailability() throws Exception{
        Game game = new Game();
        game.setGameId(1);

        Member mem1 = new Member();
        mem1.setId(1);

        Availability availability = new Availability();
        availability.setUser(mem1);
        availability.setGame(game);
        availability.setAvailability(true);
        availability.setComment("alo");

        AvailabilityDTO availabilityDTO = new AvailabilityDTO(1, 1, Boolean.TRUE, "alo");

        String json = this.objectMapper.writeValueAsString(availabilityDTO);

        // Given
        given(this.availabilityService.addAvailability(Mockito.any(Availability.class))).willReturn(availability);
        given(this.availabilityToAvailabilityDTOConverter.convert(availability)).willReturn(availabilityDTO);

        // When and Then
        this.mockMvc.perform(
                post(this.baseUrl + "/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("New availability added"))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }
}
