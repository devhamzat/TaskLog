package org.hae.tasklogue.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApplicationError {

    private int code;
    private String description;
    private String message;
    private Set<String> validationErrors;
}
