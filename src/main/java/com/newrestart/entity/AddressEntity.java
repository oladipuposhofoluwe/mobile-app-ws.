package com.newrestart.entity;

import com.newrestart.userDTO.UserDataTransferObject;
import com.newrestart.usersignupmodel.GetUserDetailFromClient;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "addresses")
public class AddressEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 30, nullable = false)
    private String addressId;

    @Column(length = 15, nullable = false)
    private String city;

    @Column(length = 100, nullable = false)
    private String streetName;

    @Column(length = 15, nullable = false)
    private String country;

    @Column(length = 7, nullable = false)
    private String postalCode;

    @Column(length = 10, nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity userDetails;
}
