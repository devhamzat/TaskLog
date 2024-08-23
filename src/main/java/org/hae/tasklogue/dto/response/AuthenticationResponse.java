package org.hae.tasklogue.dto.response;

import lombok.*;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse  {
    private String token;
}