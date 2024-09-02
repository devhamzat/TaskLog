package org.hae.tasklogue.service.userService;

import org.hae.tasklogue.dto.requestdto.EditProfileRequest;
import org.hae.tasklogue.dto.response.EditProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<EditProfileResponse> editProfile(EditProfileRequest editProfileRequest);
}
