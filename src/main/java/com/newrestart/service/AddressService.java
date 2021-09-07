package com.newrestart.service;

import com.newrestart.userDTO.AddressRequestModelDTO;
import com.newrestart.userDTO.UserDataTransferObject;

import java.util.List;

public interface AddressService {
    List<AddressRequestModelDTO> getUserAddresses(String userid);

    AddressRequestModelDTO getAddress(String addressId);

}
