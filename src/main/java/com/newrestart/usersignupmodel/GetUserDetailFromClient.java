package com.newrestart.usersignupmodel;


import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor


//This class is used for  User sign-up process. i.e to get user/client registration information.
public class GetUserDetailFromClient {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private List<AddressRequestModel> addresses;
}
