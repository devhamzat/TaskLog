package org.hae.tasklogue.service.userService;

import org.hae.tasklogue.dto.requestdto.ApplicationUserSignUp;
import org.hae.tasklogue.dto.response.CreationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<CreationResponse> createUser(ApplicationUserSignUp applicationUserSignUp);
}
