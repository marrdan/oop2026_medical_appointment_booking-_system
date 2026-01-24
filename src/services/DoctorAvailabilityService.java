package services;

import exceptions.DoctorUnavailableException;
import exceptions.TimeSlotAlreadyBookedException;
import repositories.AppointmentRepository;

import java.time.LocalDateTime;

public class DoctorAvailabilityService {

    private final AppointmentRepository repo;

    public DoctorAvailabilityService(AppointmentRepository repo) {
        this.repo = repo;
    }

    public void check(int doctorId, LocalDateTime time) {
        try {
            repo.findByDoctor(doctorId);
        }catch (DoctorUnavailableException e) {
            throw new DoctorUnavailableException("Doctor with id " + doctorId + " dose not exist or has no appointments");
        }

        if (repo.exists(doctorId, time))
            throw new TimeSlotAlreadyBookedException("Time slot already booked");
    }
}
