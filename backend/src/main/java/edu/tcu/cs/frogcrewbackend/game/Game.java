package edu.tcu.cs.frogcrewbackend.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.tcu.cs.frogcrewbackend.game.dto.GameScheduleDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer gameId;

    @ManyToOne
    @JoinColumn(name = "scheduleId")
    private GameSchedule schedule;

//    private Integer scheduleId;
    @NotEmpty(message =  "date required")
    private String gameDate;

    @NotEmpty(message =  "venue required")
    private String venue;

    @NotEmpty(message =  "opponent required")
    private String opponent;

    @JsonProperty("isFinalized")
    @NotNull(message =  "finalized required")
    private Boolean isFinalized;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public Boolean getFinalized() {
        return isFinalized;
    }

    public void setFinalized(Boolean finalized) {
        isFinalized = finalized;
    }

    public GameSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(GameSchedule schedule) {
        this.schedule = schedule;
    }
}
