package com.ra.ss9.model.dto.request;

import com.ra.ss9.validator.ConfirmPasswordMatching;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfirmPasswordMatching(password = "password", confirmPassword = "confirmPassword")
public class AccountRequestDTO {
    @NotBlank(message = "Username is empty!")
    private String username;
    @NotBlank(message = "Password is empty!")
    @Length(min = 6, message = "Password has greater or equals than 6 characters!")
    private String password;
    @NotBlank(message = "Full name is empty!")
    private String fullName;
    @NotNull(message = "Gender is empty!")
    private Boolean gender;
    @NotBlank(message = "Address is empty!")
    private String address;
    @NotBlank(message = "Email is empty!")
    @Email(regexp = "^[a-zA-Z]*[@]([a-zA-Z]){3,7}[.]([a-zA-Z]){2,5}$", message = "Email not valid!")
    private String email;
    @NotBlank(message = "Phone is empty!")
    private String phone;
    private String confirmPassword;
    @NotNull(message = "Image is null!")
    private MultipartFile imageFile;
}
