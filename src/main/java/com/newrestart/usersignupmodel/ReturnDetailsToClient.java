package com.newrestart.usersignupmodel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

//This class represents the information we want to return after the User/Client is done with registration
public class ReturnDetailsToClient {
    private String userId; // this is not auto generated from the database
    private String firstname;
    private String lastname;
    private String email;
    private List<ReturnedAddressDetails> addresses;
}
