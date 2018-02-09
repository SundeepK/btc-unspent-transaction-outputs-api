package com.blockchain.api.services;

import com.blockchain.api.domain.response.UnspentTransactionOutputs;
import com.blockchain.api.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);
    private final RestTemplate restTemplate;
    private final String blockChainApiBaseUrl;

    @Autowired
    public AddressService(final RestTemplate restTemplate,
                          @Value("${blockchain.api.base.url}") final String blockChainApiBaseUrl) {
        this.restTemplate = restTemplate;
        this.blockChainApiBaseUrl = blockChainApiBaseUrl;
    }

    public UnspentTransactionOutputs getUnspentTransactions(String address) throws NotFoundException {
        logger.info("url " + blockChainApiBaseUrl);
        String unspentOutputsUri = UriComponentsBuilder
                .fromUriString(blockChainApiBaseUrl)
                .path("unspent")
                .queryParam("active", address)
                .build()
                .toUriString();
        logger.info("Calling url " + unspentOutputsUri);

        try {
            return restTemplate.getForObject(unspentOutputsUri, UnspentTransactionOutputs.class);
        } catch (HttpStatusCodeException exception) {
            if (exception.getResponseBodyAsString().contains("Invalid Bitcoin Address")) {
                throw new NotFoundException("Bitcoin address not found");
            } else {
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getResponseBodyAsString());
            }
        }

    }

}
