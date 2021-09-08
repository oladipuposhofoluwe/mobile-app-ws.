package com.newrestart.usersignupmodel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.transaction.support.ResourceHolderSupport;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReturnedAddressDetails{
    private String addressId;
    private String city;
    private String streetName;
    private String postalCode;
    private String country;
    private String type;

}
