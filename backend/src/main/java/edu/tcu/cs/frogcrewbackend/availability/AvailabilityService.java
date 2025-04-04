package edu.tcu.cs.frogcrewbackend.availability;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AvailabilityService {
    private AvailabilityRepository availRepository;

    public AvailabilityService(AvailabilityRepository availRepository) {
        this.availRepository = availRepository;
    }
}
