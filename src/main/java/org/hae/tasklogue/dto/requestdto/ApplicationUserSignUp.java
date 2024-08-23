package org.hae.tasklogue.dto.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hae.tasklogue.utils.customvalidation.ValidPassword;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ApplicationUserSignUp {
    @NotEmpty(message = "username field is required ")
    @NotBlank(message = "username is required")
    private String userName;
    @NotEmpty(message = "username field is required ")
    @NotBlank(message = "username is required")
    @Email(message = "email is invalid")
    private String email;
    @ValidPassword(message = "invalid password")
    private String password;
}
