package com.blockchain.api.services;

import com.blockchain.api.domain.response.UnspentTransactionOutputs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import validation.BitcoinAddress;

import java.net.MalformedURLException;
import java.net.URL;

@Component
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);
    private final RestTemplate restTemplate;
    private final URL blockChainApiBaseUrl;
    private final BitcoinAddress bitcoinAddress;

    @Autowired
    public AddressService(final RestTemplate restTemplate,
                          @Value("${blockchain.api.base.url}") final String blockChainApiBaseUrl,
                          final BitcoinAddress bitcoinAddress) throws MalformedURLException {
        this.restTemplate = restTemplate;
        this.blockChainApiBaseUrl = new URL(blockChainApiBaseUrl);
        this.bitcoinAddress = bitcoinAddress;
        logger.info("url " + blockChainApiBaseUrl);
    }

    public UnspentTransactionOutputs getUnspentTransactions(String address) {
        bitcoinAddress.validateAddress(address);
        String unspentOutputsUri = UriComponentsBuilder
                .fromUriString(blockChainApiBaseUrl.toString())
                .path("unspent")
                .queryParam("active", address)
                .build()
                .toUriString();
        logger.info("Calling url " + unspentOutputsUri);
        return restTemplate.getForObject(unspentOutputsUri, UnspentTransactionOutputs.class);
    }

}
