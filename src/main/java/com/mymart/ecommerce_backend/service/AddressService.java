package com.mymart.ecommerce_backend.service;

import com.mymart.ecommerce_backend.dto.ShippingAddressDto;

import java.util.List;

public interface AddressService {

    public ShippingAddressDto saveAddress(Integer userId,ShippingAddressDto addressDto);

    public ShippingAddressDto updateAddress(Integer addrId, ShippingAddressDto addressDto);

    public List<ShippingAddressDto> getUserAddresses(Integer userId);

    public ShippingAddressDto deleteAddress(Integer addrId);
}
