package com.glooneltharion.hospital.services;

import com.glooneltharion.hospital.kafka.AppointmentCreatedEvent;
import com.glooneltharion.hospital.kafka.HospitalEventProducer;
import com.glooneltharion.hospital.mapper.AppointmentMapper;
import com.glooneltharion.hospital.models.Appointment;
import com.glooneltharion.hospital.models.Patient;
import com.glooneltharion.hospital.models.User;
import com.glooneltharion.hospital.models.dtos.AppointmentRequestDTO;
import com.glooneltharion.hospital.models.dtos.AppointmentResponseDTO;
import com.glooneltharion.hospital.models.enums.AppointmentStatus;
import com.glooneltharion.hospital.repositories.AppointmentRepository;
import com.glooneltharion.hospital.repositories.PatientRepository;
import com.glooneltharion.hospital.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final AppointmentMapper mapper;
    private final HospitalEventProducer eventProducer;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  UserRepository userRepository,
                                  PatientRepository patientRepository,
                                  AppointmentMapper mapper, HospitalEventProducer eventProducer) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.mapper = mapper;
        this.eventProducer = eventProducer;
    }

    @Override
    public void createAppointment(AppointmentRequestDTO dto) {

        User doctor = userRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        List<Appointment> conflicts =
                appointmentRepository.findConflictingAppointments(
                        doctor,
                        dto.getStartTime(),
                        dto.getEndTime()
                );

        if (!conflicts.isEmpty()) {
            throw new IllegalStateException("Time slot taken");
        }

        Appointment appointment = new Appointment();

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setStartTime(dto.getStartTime());
        appointment.setEndTime(dto.getEndTime());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        AppointmentCreatedEvent event = new AppointmentCreatedEvent(
                savedAppointment.getId(),
                savedAppointment.getPatient().getId(),
                savedAppointment.getDoctor().getId(),
                savedAppointment.getStartTime()
        );

        eventProducer.sendAppointmentCreatedEvent(event);

        mapper.toDTO(savedAppointment);
    }

    @Override
    public List<AppointmentResponseDTO> getAll() {
        return appointmentRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public AppointmentRequestDTO getById(Long id) {
        Appointment a = appointmentRepository.findById(id)
                .orElseThrow();

        AppointmentRequestDTO dto = new AppointmentRequestDTO();

        dto.setId(a.getId());

        if (a.getDoctor() != null) {
            dto.setDoctorId(a.getDoctor().getId());
        }

        dto.setPatientId(a.getPatient().getId());

        dto.setStartTime(a.getStartTime());
        dto.setEndTime(a.getEndTime());

        return dto;
    }

    @Override
    public void update(Long id, AppointmentRequestDTO dto) {
        Appointment appointment = appointmentRepository
                .findById(id)
                .orElseThrow();

        User doctor = userRepository.findById(dto.getDoctorId())
                .orElseThrow();

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow();

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        appointment.setStartTime(dto.getStartTime());
        appointment.setEndTime(dto.getEndTime());

        appointmentRepository.save(appointment);
    }



    @Override
    public void cancel(Long id) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        appt.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appt);
    }
}
