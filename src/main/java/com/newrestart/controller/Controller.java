package com.newrestart.controller;

import com.newrestart.errormessages.ErrorMessages;
import com.newrestart.errormessages.OperationStatusModel;
import com.newrestart.errormessages.RequestOperationName;
import com.newrestart.errormessages.RequestOperationStatus;
import com.newrestart.exceptions.UserServiceException;
import com.newrestart.service.AddressService;
import com.newrestart.userDTO.AddressRequestModelDTO;
import com.newrestart.userDTO.UserDataTransferObject;
import com.newrestart.usersignupmodel.GetUserDetailFromClient;
import com.newrestart.usersignupmodel.ReturnDetailsToClient;
import com.newrestart.service.UserService;
import com.newrestart.usersignupmodel.ReturnedAddressDetails;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.bytebuddy.description.method.MethodDescription;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class Controller {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

//     @Autowired
//    private UserEntityRepository user;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ReturnDetailsToClient getUser(@PathVariable String id){
        ReturnDetailsToClient returnValue = new ReturnDetailsToClient();
        UserDataTransferObject userDTO = userService.findByUserId(id);
        BeanUtils.copyProperties(userDTO, returnValue);

        return returnValue;
    }

    //This is user Sign Up process... from Controller (createUser()) -> service later and then to -> Repo
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public @ResponseBody ReturnDetailsToClient createUser(@RequestBody GetUserDetailFromClient getUserDetails){

        if(getUserDetails.getFirstname().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        ModelMapper modelMapper = new ModelMapper();
        UserDataTransferObject userDTO = modelMapper.map(getUserDetails, UserDataTransferObject.class);

        UserDataTransferObject createUser = userService.createUser(userDTO);
        ReturnDetailsToClient returnValue = modelMapper.map(createUser, ReturnDetailsToClient.class);// this line Copies User registration information (getUserDetails) into the UserDataTransferObject class object (userDTO)

        return returnValue;
    }

    @PutMapping(value = "/{userId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ReturnDetailsToClient updateUser(@PathVariable String userId, @RequestBody GetUserDetailFromClient getUserDetails){
        ReturnDetailsToClient returnValue = new ReturnDetailsToClient();
        UserDataTransferObject userDTO = new UserDataTransferObject();
        BeanUtils.copyProperties(getUserDetails, userDTO);
        UserDataTransferObject updateUser  = userService.updateUser(userId, userDTO);
        BeanUtils.copyProperties(updateUser, returnValue);

        return returnValue;
    }


    @DeleteMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String userId){
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        userService.deleteUser(userId);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
        public List<ReturnDetailsToClient> getUsers(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "2") int limit) {
            List<ReturnDetailsToClient> returnValue = new ArrayList<>();
            List<UserDataTransferObject> users = userService.getUsers(page, limit);
            for(UserDataTransferObject userDTO : users ){
                ReturnDetailsToClient userModel = new ReturnDetailsToClient();
                BeanUtils.copyProperties(userDTO, userModel);
                returnValue.add(userModel);
            }

        return returnValue;
    }

    @GetMapping(value = "/{id}/addresses", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<ReturnedAddressDetails> getUserAddresses(@PathVariable String id){
        List<ReturnedAddressDetails> returnValue = new ArrayList<>();

       List<AddressRequestModelDTO> addressesDTO = addressService.getUserAddresses(id);

       if(addressesDTO != null && !addressesDTO.isEmpty()){

           Type listType = new TypeToken<List<ReturnedAddressDetails>>() {}.getType();
           returnValue = new ModelMapper().map(addressesDTO, listType);
       }

        return returnValue;
    }


    @GetMapping(value = "/{userId}/addresses/{addressId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ReturnedAddressDetails getUserAddress(@PathVariable String addressId){

        AddressRequestModelDTO addressDTO = addressService.getAddress(addressId);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(addressDTO, ReturnedAddressDetails.class);
    }

}
