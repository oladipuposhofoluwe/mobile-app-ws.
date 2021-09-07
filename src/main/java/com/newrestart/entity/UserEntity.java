package com.newrestart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long Id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, length = 25, unique = true)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
    private List<AddressEntity> addresses;

    private String emailVerificationToken;

    @Column(nullable = true)
    private Boolean emailVerificationStatus;

    //@OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
    //private List<AddressEntity> addresses;
}
