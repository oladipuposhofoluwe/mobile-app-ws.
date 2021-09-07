package com.newrestart.userDTO;

import com.newrestart.usersignupmodel.AddressRequestModel;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

// This is class (DTO) stands between client request and the response from the serve.
// it carries the User/Client info to the server and also bring back the return information
public class UserDataTransferObject implements Serializable {

    private long Id;
    private  String userId;

    private String firstname;
    private String lastname;
    private String email;
    private String password;

    private String encryptedPassword;

    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false;

    private List<AddressRequestModelDTO> addresses;

    //   private List<AddressDTO> address;




}
