package com.glooneltharion.hospital.services;

import com.glooneltharion.hospital.mapper.UserMapper;
import com.glooneltharion.hospital.models.User;
import com.glooneltharion.hospital.models.dtos.UserDTO;
import com.glooneltharion.hospital.repositories.AppointmentRepository;
import com.glooneltharion.hospital.repositories.MedicalRecordRepository;
import com.glooneltharion.hospital.repositories.PrescriptionRepository;
import com.glooneltharion.hospital.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AppointmentRepository appointmentRepository, MedicalRecordRepository medicalRecordRepository, PrescriptionRepository prescriptionRepository,
                           UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDTO dto) {
        User user = userMapper.toEntity(dto);

        userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public List<UserDTO> getAllUsersSortedByParameter(String parameter) {
        return userRepository.findAll(Sort.by(parameter))
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userMapper.toDTO(
                userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );
    }


    @Override
    public void updateUser(Long id, UserDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userMapper.toDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow();

        appointmentRepository.clearDoctorFromAppointments(id);

        medicalRecordRepository.clearDoctorFromRecords(id);

        prescriptionRepository.clearDoctorFromPrescriptions(id);

        userRepository.delete(user);
    }

    @Override
    public List<User> getAllDoctors() {
        return userRepository.findDoctors();
    }
}
