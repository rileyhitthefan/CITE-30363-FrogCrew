package edu.tcu.cs.frogcrewbackend.crew;

import edu.tcu.cs.frogcrewbackend.game.Game;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Entity
public class CrewList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @NotNull(message = "game Id required")
    private Game game;

    @OneToMany
    @NotNull(message = "crew required")
    private List<CrewedUser> crewedUsers = new ArrayList<>();

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

    public List<CrewedUser> getCrewedUsers() {
        return crewedUsers;
    }

    public void setCrewedUsers(List<CrewedUser> crewedUsers) {
        this.crewedUsers = crewedUsers;
    }
}
