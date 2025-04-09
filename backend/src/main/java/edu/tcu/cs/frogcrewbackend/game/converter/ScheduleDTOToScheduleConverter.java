package edu.tcu.cs.frogcrewbackend.schedule.converter;

import edu.tcu.cs.frogcrewbackend.schedule.GameSchedule;
import edu.tcu.cs.frogcrewbackend.schedule.dto.GameScheduleDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ScheduleDTOToScheduleConverter implements Converter<GameScheduleDTO, GameSchedule> {
    @Override
    public GameSchedule convert(GameScheduleDTO source) {
        GameSchedule gameSchedule = new GameSchedule();
        gameSchedule.setId(source.id());
        gameSchedule.setSport(source.sport());
        gameSchedule.setSeason(source.season());
        return gameSchedule;
    }
}
