package com.newrestart.service;

import com.newrestart.userDTO.UserDataTransferObject;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDataTransferObject createUser(UserDataTransferObject userDTO);
    UserDataTransferObject getUser(String email);
    UserDataTransferObject findByUserId(String id);
    UserDataTransferObject updateUser(String userId, UserDataTransferObject userDTO);
    void deleteUser(String userId);
    List<UserDataTransferObject> getUsers(int page, int limit);
}
