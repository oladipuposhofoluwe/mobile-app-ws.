package com.newrestart.UserServiceImpl;


import com.newrestart.entity.UserEntity;
import com.newrestart.errormessages.ErrorMessages;
import com.newrestart.userDTO.AddressRequestModelDTO;
import com.newrestart.userDTO.UserDataTransferObject;
import com.newrestart.repository.UserEntityRepository;
import com.newrestart.service.UserService;
import com.newrestart.shared.utils;
import com.newrestart.usersignupmodel.AddressRequestModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

        @Autowired
        UserEntityRepository userEntityRepository;

        @Autowired
        utils utils;

        @Autowired
        BCryptPasswordEncoder bCryptPasswordEncoder;


        @Override
        public UserDataTransferObject createUser(UserDataTransferObject userDTO) {

            if(userEntityRepository.findByEmail(userDTO.getEmail()) != null) throw new RuntimeException("Record Already Exist");

            for (int i = 0; i < userDTO.getAddresses().size(); i++){
                AddressRequestModelDTO address = userDTO.getAddresses().get(i);
                address.setUserDetails(userDTO);
                address.setAddressId(utils.generateAddressId(30));
                userDTO.getAddresses().set(i, address);
            }


            ModelMapper modelMapper = new ModelMapper();
            UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);  // This line allows us to store the information in the data entity

            String publicUserId = utils.generateUserId(30);
            userEntity.setUserId(publicUserId);
            userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword())); // This line encrypts the password that user provide while signing up

            UserEntity storeUserDetails =  userEntityRepository.save(userEntity);
            UserDataTransferObject returnValue = modelMapper.map(storeUserDetails, UserDataTransferObject.class);
            return returnValue ;
        }



        @Override
        public UserDataTransferObject getUser(String email) {
            UserEntity userEntity = userEntityRepository.findByEmail(email);
            if(userEntity == null) throw new UsernameNotFoundException(email);
            UserDataTransferObject returnValue = new UserDataTransferObject();
            BeanUtils.copyProperties(userEntity, returnValue);
            return returnValue;
        }

    @Override
    public UserDataTransferObject findByUserId(String userId) {
        UserDataTransferObject returnValue = new UserDataTransferObject();
        UserEntity userEntity = userEntityRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException(userId);
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }



    @Override
    public UserDataTransferObject updateUser(String userId, UserDataTransferObject userDTO) {
        UserDataTransferObject returnValue = new UserDataTransferObject();
        UserEntity userEntity = userEntityRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        userEntity.setFirstname(userDTO.getFirstname());
        userEntity.setLastname(userDTO.getLastname());
        userEntity.setEncryptedPassword(userDTO.getPassword());
        UserEntity updatedUserDetails = userEntityRepository.save(userEntity);
        BeanUtils.copyProperties(updatedUserDetails, returnValue);
        return returnValue;
    }

    @Transactional
    @Override
    public void deleteUser(String userId) {
            UserEntity userEntity = userEntityRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        userEntityRepository.delete(userEntity);
    }

    @Override
    public List<UserDataTransferObject> getUsers(int page, int limit) {
        List<UserDataTransferObject> returnValue = new ArrayList<>();
        if(page > 0) page -= 1;
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<UserEntity> userPage = userEntityRepository.findAll(pageableRequest); //this returns PAGE and so need to convert to LIST
        List<UserEntity> users = userPage.getContent();
        for (UserEntity userEntity: users) {
            UserDataTransferObject userDTO = new UserDataTransferObject();
            BeanUtils.copyProperties(userEntity, userDTO);
            returnValue.add(userDTO);
        }
        return returnValue;
    }

    //This method is triggered at the point of user sign in in other to load the user details from the database
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityRepository.findByEmail(email);
        if(userEntity == null) throw new UsernameNotFoundException(email); // This checks if user is exist in the database
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());// This method needs to return an object of UserDetails
        // and new User() here is an object of UserDetails. and the it will automatically trigger the attemptAuthentication() method in the AuthenticationFilter class in other to authenticate user
    }
}
