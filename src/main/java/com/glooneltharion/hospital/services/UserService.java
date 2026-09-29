package com.glooneltharion.hospital.services;

import com.glooneltharion.hospital.models.User;
import com.glooneltharion.hospital.models.dtos.UserDTO;

import java.util.List;

public interface UserService {

    void createUser(UserDTO dto);

    List<UserDTO> getAllUsers();

    List<UserDTO> getAllUsersSortedByParameter(String parameter);

    UserDTO getUserById(Long id);

    void updateUser(Long id, UserDTO dto);

    void deleteUser(Long id);
    List<User> getAllDoctors();
}