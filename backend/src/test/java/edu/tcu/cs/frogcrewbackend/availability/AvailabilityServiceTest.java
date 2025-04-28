package edu.tcu.cs.frogcrewbackend.availability;

import edu.tcu.cs.frogcrewbackend.game.Game;
import edu.tcu.cs.frogcrewbackend.member.Member;
import jakarta.inject.Inject;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
public class AvailabilityServiceTest {

    @Mock
    AvailabilityRepository availabilityRepository;

    @InjectMocks
    AvailabilityService availabilityService;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testAddAvailability() {
        // Given
        Game game = new Game();
        game.setGameId(1);

        Member mem1 = new Member();
        mem1.setId(1);

        Availability availability = new Availability();
        availability.setUser(mem1);
        availability.setGame(game);
        availability.setAvailability(true);
        availability.setComment("comment");

        given(this.availabilityRepository.save(availability)).willReturn(availability);

        // When
        Availability newAvailability = this.availabilityService.addAvailability(availability);

        // Then
        assertThat(newAvailability.getUser().getId()).isEqualTo(1);
        assertThat(newAvailability.getGame().getGameId()).isEqualTo(1);
        assertThat(newAvailability.getAvailability()).isEqualTo(true);
        assertThat(newAvailability.getComment()).isEqualTo("comment");
        verify(this.availabilityRepository, times(1)).save(availability);
    }
}
