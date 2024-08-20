package org.hae.tasklogue.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUserSignUp {

    private String userName;
    private String email;
    private String password;
}
