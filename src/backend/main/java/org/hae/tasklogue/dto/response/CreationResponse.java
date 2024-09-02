package org.hae.tasklogue.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreationResponse {
    private Integer statusCode;
    private HttpStatus status;
}
