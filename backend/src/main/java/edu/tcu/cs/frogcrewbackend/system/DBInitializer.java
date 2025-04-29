package edu.tcu.cs.frogcrewbackend.system;

import edu.tcu.cs.frogcrewbackend.crew.CrewList;
import edu.tcu.cs.frogcrewbackend.crew.CrewListRepository;
import edu.tcu.cs.frogcrewbackend.crew.CrewedUser;
import edu.tcu.cs.frogcrewbackend.crew.CrewedUserRepository;
import edu.tcu.cs.frogcrewbackend.game.*;
import edu.tcu.cs.frogcrewbackend.member.Member;
import edu.tcu.cs.frogcrewbackend.member.UserRepository;
import edu.tcu.cs.frogcrewbackend.member.UserService;
import edu.tcu.cs.frogcrewbackend.notification.Notification;
import edu.tcu.cs.frogcrewbackend.notification.NotificationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
public class DBInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final UserService userService;
    private final GameScheduleRepository gameScheduleRepository;
    private final GameRepository gameRepository;
    private final CrewedUserRepository crewedUserRepository;
    private final CrewListRepository crewListRepository;
    private final NotificationRepository notificationRepository;

    public DBInitializer(UserRepository userRepository, UserService userService, GameScheduleRepository gameScheduleRepository, GameRepository gameRepository, CrewedUserRepository crewedUserRepository, CrewListRepository crewListRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.gameScheduleRepository = gameScheduleRepository;
        this.gameRepository = gameRepository;
        this.crewedUserRepository = crewedUserRepository;
        this.crewListRepository = crewListRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Member mem1 = new Member();
//        mem1.setId(1);
        mem1.setFirstName("Bruce");
        mem1.setLastName("Wayne");
        mem1.setEmail("bw@gmail.com");
        mem1.setPhoneNumber("123-456-7890");
        mem1.setPassword("password1");
        mem1.setRole("ADMIN MEMBER");
        mem1.setPositions("Director");

        Member mem2 = new Member();
//        mem2.setId(2);
        mem2.setFirstName("Clark");
        mem2.setLastName("Kent");
        mem2.setEmail("ck@gmail.com");
        mem2.setPhoneNumber("987-654-3210");
        mem2.setPassword("password2");
        mem2.setRole("ADMIN MEMBER");
        mem2.setPositions("Videographer Planner");

        Member mem3 = new Member();
        mem3.setFirstName("Diana");
        mem3.setLastName("Prince");
        mem3.setEmail("dp@gmail.com");
        mem3.setPhoneNumber("222-255-5555");
        mem3.setPassword("password3");
        mem3.setRole("MEMBER");
        mem3.setPositions("Photographer Reporter");

        Member mem4 = new Member();
        mem4.setFirstName("Hal");
        mem4.setLastName("Jordan");
        mem4.setEmail("hj@gmail.com");
        mem4.setPhoneNumber("135-792-4680");
        mem4.setPassword("password4");
        mem4.setRole("MEMBER");
        mem4.setPositions("Reporter Supervisor");

        userService.createMember(mem1);
        userService.createMember(mem2);
        userService.createMember(mem3);
        userService.createMember(mem4);

        GameSchedule schedule1 = new GameSchedule();
        schedule1.setSport("Baseball");
        schedule1.setSeason("2024-2025");

        GameSchedule schedule2 = new GameSchedule();
        schedule2.setSport("Football");
        schedule2.setSeason("2023-2024");

        Game game1 = new Game();
        game1.setGameDate("2024-10-10");
        game1.setSchedule(schedule1);
        game1.setVenue("Amon G. Carter");
        game1.setOpponent("Texas Longhorn");
        game1.setFinalized(Boolean.FALSE);

        Game game2 = new Game();
        game2.setGameDate("2022-01-10");
        game2.setSchedule(schedule1);
        game2.setVenue("Amon G. Carter");
        game2.setOpponent("Baylor");
        game2.setFinalized(Boolean.TRUE);

        schedule1.addGame(game1);
        schedule1.addGame(game2);

        gameScheduleRepository.save(schedule1);
        gameScheduleRepository.save(schedule2);

        gameRepository.save(game1);
        gameRepository.save(game2);

        CrewedUser cu1 = new CrewedUser();
        cu1.setCrewedUserId(0);
        cu1.setUser(mem1);
        cu1.setGame(game1);
        cu1.setPosition("DIRECTOR");
        cu1.setReportTime("12:00");
        cu1.setReportLocation("CONTROL ROOM");

        CrewedUser cu2 = new CrewedUser();
        cu2.setCrewedUserId(1);
        cu2.setUser(mem2);
        cu2.setGame(game1);
        cu2.setPosition("CAMERA");
        cu2.setReportTime("10:00");
        cu2.setReportLocation("FIELD");

        crewedUserRepository.save(cu1);
        crewedUserRepository.save(cu2);

        // crew list
        CrewList clist1 = new CrewList();
        clist1.setGame(game1);
        clist1.setCrewedUsers(List.of(cu1, cu2));

        game1.setCrewList(clist1);

        crewListRepository.save(clist1);
        gameRepository.save(game1);

        // notifications
        Notification noti1 = new Notification();
        noti1.setMessage("Need to be at all games this season");
        noti1.setRead(Boolean.FALSE);
        noti1.setDate("11/10/2024 14:00:00");

        mem3.addNotification(noti1);
        noti1.setUser(mem3);
        this.userRepository.save(mem3);
        this.notificationRepository.save(noti1);
    }
}

