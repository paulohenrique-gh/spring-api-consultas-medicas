package org.example.atividadespringsecurity.controllers;

import jakarta.validation.Valid;
import org.example.atividadespringsecurity.domain.appointment.Appointment;
import org.example.atividadespringsecurity.domain.appointment.AppointmentDTO;
import org.example.atividadespringsecurity.domain.user.User;
import org.example.atividadespringsecurity.services.AppointmentService;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var authorities = user.getAuthorities();
        if (authorities.size() == 1 && authorities.contains(new SimpleGrantedAuthority("ROLE_PATIENT"))) {
           return ResponseEntity.ok(appointmentService.findByPatientName(user.getUsername()));
        }
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable long id) {
        return ResponseEntity.ok(appointmentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody @Valid AppointmentDTO appointmentDTO) {
        Appointment savedAppointment = appointmentService.saveAppointment(appointmentDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAppointment.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedAppointment);
    }
}
