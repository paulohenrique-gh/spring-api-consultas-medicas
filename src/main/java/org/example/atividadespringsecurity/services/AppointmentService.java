package org.example.atividadespringsecurity.services;

import org.example.atividadespringsecurity.domain.appointment.Appointment;
import org.example.atividadespringsecurity.domain.appointment.AppointmentDTO;
import org.example.atividadespringsecurity.repositories.AppointmentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Appointment findById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    public List<Appointment> findByPatientName(String patientName) {
        return appointmentRepository.findByPatientName(patientName);
    }

    public Appointment saveAppointment(AppointmentDTO appointmentDTO) {
        return appointmentRepository.save(this.convertToModel(appointmentDTO));
    }

    private Appointment convertToModel(AppointmentDTO appointmentDTO) {
        Appointment appointmentModel = new Appointment();
        BeanUtils.copyProperties(appointmentDTO, appointmentModel);
        return appointmentModel;
    }
}
