package edu.tcu.cs.frogcrewbackend.crew.converter;

import edu.tcu.cs.frogcrewbackend.system.exception.ObjectNotFoundException;
import edu.tcu.cs.frogcrewbackend.crew.CrewedUser;
import edu.tcu.cs.frogcrewbackend.crew.dto.CrewedUserDTO;
import edu.tcu.cs.frogcrewbackend.game.Game;
import edu.tcu.cs.frogcrewbackend.game.GameRepository;
import edu.tcu.cs.frogcrewbackend.member.Member;
import edu.tcu.cs.frogcrewbackend.member.UserRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CrewedUserDTOToCrewedUserConverter implements Converter<CrewedUserDTO, CrewedUser> {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public CrewedUserDTOToCrewedUserConverter(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public CrewedUser convert(CrewedUserDTO source) {
        CrewedUser cu = new CrewedUser();
        cu.setCrewedUserId(source.crewedUserId());

        Member member = userRepository.findById(source.userId())
                .orElseThrow(() -> new ObjectNotFoundException("Member", source.userId()));
        cu.setUser(member);

        Game game = gameRepository.findById(source.gameId())
                .orElseThrow(() -> new ObjectNotFoundException("Game", source.gameId()));
        cu.setGame(game);

        cu.setPosition(source.position());
        cu.setReportTime(source.reportTime());
        cu.setReportLocation(source.reportLocation());

        return cu;
    }
}
