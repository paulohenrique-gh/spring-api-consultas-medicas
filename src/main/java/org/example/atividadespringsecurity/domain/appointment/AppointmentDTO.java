package org.example.atividadespringsecurity.domain.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record AppointmentDTO(
        @NotBlank String doctorName,
        @NotBlank String patientName,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime appointmentDate
) {
}
