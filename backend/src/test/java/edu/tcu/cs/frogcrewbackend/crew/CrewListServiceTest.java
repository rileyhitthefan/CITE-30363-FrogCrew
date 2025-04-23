package edu.tcu.cs.frogcrewbackend.crew;

import edu.tcu.cs.frogcrewbackend.crew.dto.CrewRequestDTO;
import edu.tcu.cs.frogcrewbackend.game.Game;
import edu.tcu.cs.frogcrewbackend.game.GameRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
public class CrewListServiceTest {
    @Mock
    CrewListRepository crewListRepository;

    @Mock
    GameRepository gameRepository;

    @Mock
    CrewedUserRepository crewedUserRepository;

    @Mock
    CrewListService crewListService;

    @BeforeEach
    void setup(){}

    @AfterEach
    void tearDown(){}


}
