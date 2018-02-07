package com.blockchain.api.services;

import com.blockchain.api.domain.UnspentTransactionOutputs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AddressService {

    private final RestTemplate restTemplate;
    private final String blockChainApiBaseUrl;

    public AddressService(final RestTemplate restTemplate,
                          @Value("${blockchain.api.base.url}") final String blockChainApiBaseUrl) {
        this.restTemplate = restTemplate;
        this.blockChainApiBaseUrl = blockChainApiBaseUrl;
    }

    public UnspentTransactionOutputs getUnspentTransactions(String address) {
        String unspentOutputsUri = UriComponentsBuilder
                .fromUriString(blockChainApiBaseUrl)
                .path("unspent")
                .queryParam("active", address)
                .build()
                .toUriString();
        return restTemplate.getForObject(unspentOutputsUri, UnspentTransactionOutputs.class);
    }

}
