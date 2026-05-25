package com.mymart.ecommerce_backend.service.Imp;

import com.mymart.ecommerce_backend.constants.AppConstants;
import com.mymart.ecommerce_backend.dto.ShippingAddressDto;
import com.mymart.ecommerce_backend.entities.ShippingAddressEntity;
import com.mymart.ecommerce_backend.entities.UserEntity;
import com.mymart.ecommerce_backend.mapper.AddressMapper;
import com.mymart.ecommerce_backend.repos.AddressRepo;
import com.mymart.ecommerce_backend.repos.UserRepo;
import com.mymart.ecommerce_backend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddresServiceImpl implements AddressService {

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public ShippingAddressDto saveAddress(Integer userId, ShippingAddressDto shippingAddressDto){

        ShippingAddressEntity addressEntity = AddressMapper.doToEntity(shippingAddressDto);
        Optional<UserEntity> user = userRepo.findById(userId);
        if(user.isPresent()){
            UserEntity userEntity = user.get();
            addressEntity.setUser(userEntity);
            addressEntity.setActiveSw(AppConstants.YES);
            ShippingAddressEntity savedAddrEntity = addressRepo.save(addressEntity);
            return AddressMapper.doToDto(savedAddrEntity);
        }
        return  null;
    }

    @Override
    public ShippingAddressDto deleteAddress(Integer shippingAddressId){

        Optional<ShippingAddressEntity> address = addressRepo.findById(shippingAddressId);
        if(address.isPresent()){
            ShippingAddressEntity addressEntity = address.get();
            addressEntity.setActiveSw(AppConstants.NO);
            ShippingAddressEntity deleteAddr = addressRepo.save(addressEntity);
            return AddressMapper.doToDto(deleteAddr);
        }
        return null;
    }

    @Override
    public List<ShippingAddressDto>  getUserAddresses(Integer userId) {

        List<ShippingAddressEntity> entities = addressRepo.findByUserUserId(userId);

        return entities.stream()
                .map(AddressMapper::doToDto)
                .collect(Collectors.toList());
    }

    @Override
    public  ShippingAddressDto updateAddress(Integer addrId, ShippingAddressDto shippingAddressDto) {

        Optional<ShippingAddressEntity> byId= addressRepo.findById(addrId);

        if(byId.isPresent()) {
            ShippingAddressEntity addressEntity = byId.get();
            addressEntity.setHouseNum(shippingAddressDto.getHouseNum());
            addressEntity.setCity(shippingAddressDto.getCity());
            addressEntity.setState(shippingAddressDto.getState());
            addressEntity.setZipcode(shippingAddressDto.getZipcode());
            addressEntity.setCountry(shippingAddressDto.getCountry());
            addressEntity.setAddrType(shippingAddressDto.getAddrType());
            addressEntity.setActiveSw(shippingAddressDto.getActiveSw());

            ShippingAddressEntity updateAddr = addressRepo.save(addressEntity);
            return AddressMapper.doToDto(updateAddr);
        }
        return null;
    }

}
