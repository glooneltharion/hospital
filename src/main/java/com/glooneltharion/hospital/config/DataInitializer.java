package com.glooneltharion.hospital.config;

import com.glooneltharion.hospital.models.*;
import com.glooneltharion.hospital.models.enums.AppointmentStatus;
import com.glooneltharion.hospital.models.enums.Gender;
import com.glooneltharion.hospital.models.enums.InsuranceCompany;
import com.glooneltharion.hospital.models.enums.Role;
import com.glooneltharion.hospital.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            UserRepository userRepository,
            PatientRepository patientRepository,
            AppointmentRepository appointmentRepository,
            MedicalRecordRepository medicalRecordRepository,
            PrescriptionRepository prescriptionRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (userRepository.count() > 0) {
            System.out.println("=================================");
            System.out.println("Database already contains data.");
            System.out.println("Demo data will not be initialized.");
            System.out.println("=================================");
            return;
        }

        // =========================
        // USERS
        // =========================

        User admin = User.builder()
                .firstName("Admin")
                .lastName("System")
                .email("admin@hospital.com")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .build();

        User doctor = User.builder()
                .firstName("John")
                .lastName("Smith")
                .email("doctor@hospital.com")
                .password(passwordEncoder.encode("doctor123"))
                .role(Role.DOCTOR)
                .build();

        User nurse = User.builder()
                .firstName("Anna")
                .lastName("Wilson")
                .email("nurse@hospital.com")
                .password(passwordEncoder.encode("nurse123"))
                .role(Role.NURSE)
                .build();

        User receptionist = User.builder()
                .firstName("Emma")
                .lastName("Brown")
                .email("reception@hospital.com")
                .password(passwordEncoder.encode("reception123"))
                .role(Role.RECEPTIONIST)
                .build();

        userRepository.saveAll(List.of(
                admin,
                doctor,
                nurse,
                receptionist
        ));

        // =========================
        // PATIENTS
        // =========================

        Patient patient1 = Patient.builder()
                .firstName("Michael")
                .lastName("Johnson")
                .birthDate(LocalDate.of(1995, 5, 12))
                .gender(Gender.MALE)
                .phoneNumber("+421900111222")
                .email("michael@example.com")
                .address("Bratislava")
                .insuranceCompany(InsuranceCompany.DOVERA)
                .build();

        Patient patient2 = Patient.builder()
                .firstName("Sarah")
                .lastName("Connor")
                .birthDate(LocalDate.of(1988, 9, 20))
                .gender(Gender.FEMALE)
                .phoneNumber("+421900333444")
                .email("sarah@example.com")
                .address("Kosice")
                .insuranceCompany(InsuranceCompany.UNION)
                .build();

        patientRepository.saveAll(List.of(patient1, patient2));

        // =========================
        // APPOINTMENTS
        // =========================

        Appointment appointment1 = Appointment.builder()
                .patient(patient1)
                .doctor(doctor)
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusMinutes(30))
                .status(AppointmentStatus.SCHEDULED)
                .reason("Regular checkup")
                .build();

        Appointment appointment2 = Appointment.builder()
                .patient(patient2)
                .doctor(doctor)
                .startTime(LocalDateTime.now().plusDays(2))
                .endTime(LocalDateTime.now().plusDays(2).plusMinutes(45))
                .status(AppointmentStatus.SCHEDULED)
                .reason("Headache and fever")
                .build();

        appointmentRepository.saveAll(List.of(
                appointment1,
                appointment2
        ));

        // =========================
        // MEDICAL RECORDS
        // =========================

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .patient(patient1)
                .doctor(doctor)
                .diagnosis("Seasonal flu")
                .symptoms("Fever, cough, fatigue")
                .treatment("Rest and hydration")
                .notes("Patient should rest for 7 days")
                .build();

        medicalRecordRepository.save(medicalRecord);

        // =========================
        // PRESCRIPTIONS
        // =========================

        Prescription prescription = Prescription.builder()
                .patient(patient1)
                .doctor(doctor)
                .medication("Paracetamol")
                .dosage("500mg")
                .frequency("3 times daily")
                .duration("5 days")
                .build();

        prescriptionRepository.save(prescription);

        System.out.println("=================================");
        System.out.println("Hospital demo data initialized!");
        System.out.println("=================================");
    }
}
