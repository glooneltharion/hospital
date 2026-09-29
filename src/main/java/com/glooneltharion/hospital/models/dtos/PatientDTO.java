package com.glooneltharion.hospital.models.dtos;

import com.glooneltharion.hospital.models.enums.Gender;
import com.glooneltharion.hospital.models.enums.InsuranceCompany;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private Gender gender;
    @Pattern(
            regexp = "^\\+4219\\d{8}$",
            message = "Phone number must be in format +4219XXXXXXXX"
    )
    private String phoneNumber;

    private String email;

    private String address;

    private InsuranceCompany insuranceCompany;
}
