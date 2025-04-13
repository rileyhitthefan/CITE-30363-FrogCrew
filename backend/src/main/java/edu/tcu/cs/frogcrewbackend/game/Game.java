package edu.tcu.cs.frogcrewbackend.game;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer gameId;

    @ManyToOne
    @JoinColumn(name = "scheduleId")
    private GameSchedule schedule;

    private Integer scheduleId;
    private String gameDate;
    private String venue;
    private String opponent;
    private Boolean isFinalized;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
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
