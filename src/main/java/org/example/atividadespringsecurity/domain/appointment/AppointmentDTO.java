package org.example.atividadespringsecurity.domain.appointment;

import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record AppointmentDTO(
        @NotBlank String doctorName,
        @NotBlank String patientName,
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime appointmentDate
) {
}
