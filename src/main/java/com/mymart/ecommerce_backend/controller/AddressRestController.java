package com.mymart.ecommerce_backend.controller;

import com.mymart.ecommerce_backend.dto.ApiResponse;
import com.mymart.ecommerce_backend.dto.ShippingAddressDto;
import com.mymart.ecommerce_backend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class AddressRestController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/{userId}/shipping-address")
    public ResponseEntity<ApiResponse<ShippingAddressDto>> saveShippingAddress(@PathVariable Integer userId,
                                                                               @RequestBody ShippingAddressDto shippingAddressDto) {
        ShippingAddressDto savedAddress = addressService.saveAddress(userId, shippingAddressDto);
        ApiResponse<ShippingAddressDto> response = new ApiResponse<>();

        if (savedAddress == null) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("User not found with id: " + userId);
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Shipping address saved successfully");
        response.setData(savedAddress);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{addrId}/shipping-address")
    public ResponseEntity<ApiResponse<ShippingAddressDto>> updateAddress(@PathVariable Integer addrId,
                                                                         @RequestBody ShippingAddressDto shippingAddressDto) {
        ShippingAddressDto savedAddress = addressService.updateAddress(addrId, shippingAddressDto);
        ApiResponse<ShippingAddressDto> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Shipping address updated successfully");
        response.setData(savedAddress);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/shipping-addresses/{userId}")
    public ResponseEntity<ApiResponse<List<ShippingAddressDto>>> getCustomerAddresses(@PathVariable Integer userId) {
        List<ShippingAddressDto> customerAddresses = addressService.getUserAddresses(userId);
        ApiResponse<List<ShippingAddressDto>> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Customer all addresses fetched successfully");
        response.setData(customerAddresses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/shipping-address/{addrId}")
    public ResponseEntity<ApiResponse<ShippingAddressDto>> deleteShippingAddress(@PathVariable Integer addrId) {
        ShippingAddressDto deletedAddress = addressService.deleteAddress(addrId);
        ApiResponse<ShippingAddressDto> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Shipping address deleted successfully");
        response.setData(deletedAddress);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

