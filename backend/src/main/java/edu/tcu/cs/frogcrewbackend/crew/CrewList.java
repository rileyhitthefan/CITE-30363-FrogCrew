package edu.tcu.cs.frogcrewbackend.crew;

import edu.tcu.cs.frogcrewbackend.game.Game;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.io.Serializable;

@Transactional
@Entity
public class CrewList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "gameId", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "crewedId", nullable = false)
    private CrewedUser crewedUsers;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public CrewedUser getCrewedUser() {
        return crewedUsers;
    }

    public void setCrewedUser(CrewedUser crewedUser) {
        this.crewedUsers = crewedUser;
    }
}
