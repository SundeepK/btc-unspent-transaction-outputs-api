package com.blockchain.api.controller;

import com.blockchain.api.domain.response.UnspentTransactionOutputs;
import com.blockchain.api.services.AddressService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Component
@RestController
@RequestMapping("/address")
public class AddressTransactionController {

    private final AddressService addressService;

    public AddressTransactionController(final AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(value = "/{bitcoinAddr}", method = GET)
    @ResponseBody
    public UnspentTransactionOutputs getUnspentTransactionOutputs(@PathVariable String bitcoinAddr) {
        return addressService.getUnspentTransactions(bitcoinAddr);
    }

}
