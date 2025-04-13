package edu.tcu.cs.frogcrewbackend.system;

import edu.tcu.cs.frogcrewbackend.game.*;
import edu.tcu.cs.frogcrewbackend.member.Member;
import edu.tcu.cs.frogcrewbackend.member.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final GameScheduleRepository gameScheduleRepository;
    private final GameRepository gameRepository;

    public DBInitializer(UserRepository userRepository, GameScheduleRepository gameScheduleRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameScheduleRepository = gameScheduleRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Member mem1 = new Member();
//        mem1.setId(1);
        mem1.setFirstName("Bruce");
        mem1.setLastName("Wayne");
        mem1.setEmail("bw@gmail.com");
        mem1.setPhoneNumber("1234567890");
        mem1.setPassword("password1");
        mem1.setRole("MEMBER");
        mem1.setPositions("Director");

        Member mem2 = new Member();
//        mem2.setId(2);
        mem2.setFirstName("Clark");
        mem2.setLastName("Kent");
        mem2.setEmail("ck@gmail.com");
        mem2.setPhoneNumber("9876543210");
        mem2.setPassword("password2");
        mem2.setRole("ADMIN");
        mem2.setPositions("Videographer Planner");

        Member mem3 = new Member();
//        mem3.setId(3);
        mem3.setFirstName("Diana");
        mem3.setLastName("Prince");
        mem3.setEmail("dp@gmail.com");
        mem3.setPhoneNumber("2222555555");
        mem3.setPassword("password3");
        mem3.setRole("MEMBER");
        mem3.setPositions("Photographer Reporter");

        userRepository.save(mem1);
        userRepository.save(mem2);
        userRepository.save(mem3);

        GameSchedule schedule1 = new GameSchedule();
        schedule1.setSport("Baseball");
        schedule1.setSeason("2024-2025");

        GameSchedule schedule2 = new GameSchedule();
        schedule2.setSport("Football");
        schedule2.setSeason("2023-2024");

        gameScheduleRepository.save(schedule1);
        gameScheduleRepository.save(schedule2);
    }
}

