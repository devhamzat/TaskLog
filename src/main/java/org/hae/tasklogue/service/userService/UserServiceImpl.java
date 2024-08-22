//package org.hae.tasklogue.service.userService;
//
//import org.hae.tasklogue.dto.requestdto.ApplicationUserSignUp;
//import org.hae.tasklogue.dto.response.CreationResponse;
//import org.hae.tasklogue.entity.applicationUser.ApplicationUser;
//import org.hae.tasklogue.exceptions.AccountExist;
//import org.hae.tasklogue.exceptions.EmptyRequiredFields;
//import org.hae.tasklogue.repository.ApplicationUserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private ApplicationUserRepository applicationUserRepository;
//
//    @Override
//    public ResponseEntity<CreationResponse> createUser(ApplicationUserSignUp applicationUserSignUp) {
//        try {
//            validateInput(applicationUserSignUp);
//            Optional<ApplicationUser> existingUser = applicationUserRepository.findApplicationUserByUserNameOrEmail(applicationUserSignUp.getUserName(), applicationUserSignUp.getEmail());
//            if (existingUser.isPresent()) {
//                throw new AccountExist("An account with this username already exists");
//            }
//            CreationResponse creationResponse = getResponse(applicationUserSignUp);
//            return ResponseEntity.ok(creationResponse);
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage(), e);
//        }
//    }
//
//
////    public ResponseEntity<ApplicationUserResponse> getApplicationUserDetails(LoginRequest loginRequest){
////
////           Optional<ApplicationUser> user = applicationUserRepository.findApplicationUserByUserNameOrEmail(loginRequest.getUserName());
////           if (!user.isPresent()){
////               ApplicationUserResponse applicationUserResponse = new ApplicationUserResponse();
////               ApplicationUser  userInfo = user.get();
////               applicationUserResponse.setPhotoUrl(userInfo.getPhotoUrl());
////               applicationUserResponse.setUserName(userInfo.getUserName());
////               applicationUserResponse.setDisplayName(userInfo.getDisplayName());
////               applicationUserResponse.setBio(userInfo.getBio());
////               applicationUserResponse.setUserTasks(userInfo.getUserTasks());
////       }else {
////               throw new RuntimeException("err");
////           }
////
////        return null;
////    }
//
//
//    private CreationResponse getResponse(ApplicationUserSignUp applicationUserSignUp) {
//        ApplicationUser user = new ApplicationUser();
//        user.setUserName(applicationUserSignUp.getUserName());
//        user.setEmail(applicationUserSignUp.getEmail());
//        user.setSecretPassword(applicationUserSignUp.getPassword());
//
//
//        applicationUserRepository.save(user);
//        CreationResponse creationResponse = new CreationResponse();
//        creationResponse.setStatusCode(201);
//        creationResponse.setStatus(HttpStatus.CREATED);
//        creationResponse.setMessage(user.getUserName() + " successfully created");
//        return creationResponse;
//    }
//
//    private void validateInput(ApplicationUserSignUp applicationUserSignUp) {
//        if (applicationUserSignUp.getUserName().trim().isEmpty() || applicationUserSignUp.getEmail().trim().isEmpty() || applicationUserSignUp.getPassword().isEmpty()) {
//            throw new EmptyRequiredFields("required fields are empty");
//        }
//    }
//}
