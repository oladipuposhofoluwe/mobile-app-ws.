package com.newrestart.usersignupmodel;


import lombok.*;

import javax.persistence.Column;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestModel {
    private String city;
    private String streetName;
    private String postalCode;
    private String country;
    private String type;
}
