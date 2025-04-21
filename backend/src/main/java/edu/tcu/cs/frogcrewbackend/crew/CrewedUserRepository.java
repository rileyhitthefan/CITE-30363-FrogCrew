package edu.tcu.cs.frogcrewbackend.crew;

import edu.tcu.cs.frogcrewbackend.crew.dto.CrewedUserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewedUserRepository extends JpaRepository<CrewedUser, Integer> {
}
