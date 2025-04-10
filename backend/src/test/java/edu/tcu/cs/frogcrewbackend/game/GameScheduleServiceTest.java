package edu.tcu.cs.frogcrewbackend.game;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
public class GameScheduleServiceTest {
    private static final Logger log = LoggerFactory.getLogger(GameScheduleServiceTest.class);

    @Mock
    GameScheduleRepository gameScheduleRepository;

    @InjectMocks
    GameScheduleService gameScheduleService;

    @BeforeEach
    void setUp() {
        GameSchedule schedule1 = new GameSchedule();
        schedule1.setId(1);
        schedule1.setSport("Baseball");
        schedule1.setSeason("2024-2025");

        GameSchedule schedule2 = new GameSchedule();
        schedule2.setId(2);
        schedule2.setSport("Football");
        schedule2.setSeason("2023-2024");
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testAddGameScheduleSuccess(){
        // Given
        GameSchedule gameSchedule = new GameSchedule();
        gameSchedule.setId(1);
        gameSchedule.setSport("Baseball");
        gameSchedule.setSeason("2024-2025");

        given(this.gameScheduleRepository.save(gameSchedule)).willReturn(gameSchedule);

        // When
        GameSchedule returnedSchedule = this.gameScheduleService.addGameSchedule(gameSchedule);

        // Then
        assertThat(returnedSchedule.getId()).isEqualTo(gameSchedule.getId());
        assertThat(returnedSchedule.getSport()).isEqualTo(gameSchedule.getSport());
        assertThat(returnedSchedule.getSeason()).isEqualTo(gameSchedule.getSeason());
        verify(this.gameScheduleRepository, times(1)).save(gameSchedule);
    }
}
