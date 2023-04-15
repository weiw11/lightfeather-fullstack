package dev.weiwang.backend;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FormGroup {
    @NotBlank(message = "First name cannot be blank or null")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")

    private String firstname;
    @NotBlank(message = "Last name  cannot be blank or null")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")

    private String lastname;
    @Nullable
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$", message = "Invalid email address")
    private String email;
    @Nullable
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Phone number must contain only numbers")
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")

    private String phone;
    @NotBlank(message = "Supervisor cannot be blank or null")
    private String supervisor;
}
