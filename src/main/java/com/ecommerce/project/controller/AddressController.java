package com.ecommerce.project.controller;


import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.service.AddressService;
import com.ecommerce.project.util.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {
    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private AddressService addressService;

    @PostMapping("/public/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO){
        User user = authUtil.loggedInUser();
        AddressDTO savedAddressDTO = addressService.createAddress(addressDTO, user);
        return new ResponseEntity<>(savedAddressDTO, HttpStatus.CREATED);
    }

    @GetMapping("/public/user/addresses")
    public ResponseEntity<List<AddressDTO>> getAddresses(){
        List<AddressDTO> addressList = addressService.getAddresses();
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    @GetMapping("/public/address/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId){
        AddressDTO addressDTO = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @GetMapping("/public/useraddress")
    public ResponseEntity<List<AddressDTO>> getAddressByUser(){
        User user = authUtil.loggedInUser();
        List<AddressDTO> addressDTOS = addressService.getAddressByUser(user);
        return new ResponseEntity<>(addressDTOS, HttpStatus.OK);
    }

    @PutMapping("/public/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId, @RequestBody AddressDTO addressDTO){
        AddressDTO addressDTO1 = addressService.updateAddress(addressId, addressDTO);
        return new ResponseEntity<>(addressDTO1, HttpStatus.OK);
    }

    @DeleteMapping("/public/addresses/{addressId}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Long addressId){
        String address = addressService.deleteAddressById(addressId);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }
}
