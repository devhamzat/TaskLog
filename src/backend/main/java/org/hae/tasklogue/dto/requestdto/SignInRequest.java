package org.hae.tasklogue.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hae.tasklogue.utils.customvalidation.ValidPassword;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    @NotEmpty
    @NotBlank
    private String userName;
    @ValidPassword
    private String password;
}
