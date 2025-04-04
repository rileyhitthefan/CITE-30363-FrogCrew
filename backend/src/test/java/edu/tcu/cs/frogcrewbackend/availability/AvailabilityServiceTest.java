package edu.tcu.cs.frogcrewbackend.availability;

import jakarta.inject.Inject;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.logging.Logger;

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
}
