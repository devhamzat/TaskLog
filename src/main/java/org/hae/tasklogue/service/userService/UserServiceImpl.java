package org.hae.tasklogue.service.userService;

import jakarta.transaction.Transactional;
import org.hae.tasklogue.dto.requestdto.EditProfileRequest;
import org.hae.tasklogue.dto.response.EditProfileResponse;
import org.hae.tasklogue.entity.applicationUser.ApplicationUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Override
    @Transactional
    public ResponseEntity<EditProfileResponse> editProfile(EditProfileRequest editProfileRequest) {
        ApplicationUser applicationUser = new ApplicationUser();

        return null;

    }
}
