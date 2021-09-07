package com.newrestart.UserServiceImpl;

import com.newrestart.repository.AddressRepository;
import com.newrestart.entity.AddressEntity;
import com.newrestart.entity.UserEntity;
import com.newrestart.repository.UserEntityRepository;
import com.newrestart.service.AddressService;
import com.newrestart.userDTO.AddressRequestModelDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressRequestModelDTO> getUserAddresses(String userId) {
        List<AddressRequestModelDTO> returnValue = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userEntityRepository.findByUserId(userId);
        if(userEntity == null) return returnValue;

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for (AddressEntity addressEntity: addresses ) {
            returnValue.add(modelMapper.map(addressEntity , AddressRequestModelDTO.class));
        }

        return returnValue;
    }

    @Override
    public AddressRequestModelDTO getAddress(String addressId) {

        AddressRequestModelDTO returnValue = new AddressRequestModelDTO();
        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
        if(addressEntity != null) {
            returnValue = new ModelMapper().map(addressEntity, AddressRequestModelDTO.class);
        }
        return  returnValue;
    }

}
