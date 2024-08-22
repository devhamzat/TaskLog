package org.hae.tasklogue.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApplicationError {

    private int code;
    private String description;
    private String message;
}
