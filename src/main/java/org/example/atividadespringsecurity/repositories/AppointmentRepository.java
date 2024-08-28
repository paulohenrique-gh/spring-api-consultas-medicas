package org.example.atividadespringsecurity.repositories;

import org.example.atividadespringsecurity.domain.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
