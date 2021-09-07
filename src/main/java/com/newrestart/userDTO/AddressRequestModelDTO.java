package com.newrestart.userDTO;

import com.newrestart.usersignupmodel.AddressRequestModel;
import com.newrestart.usersignupmodel.GetUserDetailFromClient;
import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressRequestModelDTO {
    private Long id;
    private String addressId;

    private String city;
    private String streetName;
    private String postalCode;
    private String country;
    private String type;

    private UserDataTransferObject userDetails;
}
