package com.glooneltharion.hospital.controllers;

import com.glooneltharion.hospital.models.dtos.UserDTO;
import com.glooneltharion.hospital.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String page(Model model,
                       @RequestParam(value = "sortBy", required = false) String sortBy) {

        if (Objects.nonNull(sortBy)) {
            model.addAttribute("users", userService.getAllUsersSortedByParameter(sortBy));
        } else {
            model.addAttribute("users", userService.getAllUsers());
        }

        model.addAttribute("user", new UserDTO());
        model.addAttribute("page", "users");
        return "users";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute("user") UserDTO dto) {

        if (dto.getId() != null) {
            userService.updateUser(dto.getId(), dto);
        } else {
            userService.createUser(dto);
        }

        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        UserDTO user = userService.getUserById(id);

        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        model.addAttribute("user", dto);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("page", "users");

        return "users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
