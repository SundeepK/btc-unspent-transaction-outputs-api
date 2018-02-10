package com.blockchain.api.bitcoin;

import org.bitcoinj.core.Address;
import org.springframework.stereotype.Component;

@Component
public class BitcoinAddress {

    public void validateAddress(String base58Address){
        Address.fromBase58(null, base58Address);
    }

}
