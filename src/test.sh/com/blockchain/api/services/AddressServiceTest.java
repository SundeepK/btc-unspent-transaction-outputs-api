package com.blockchain.api.services;

import com.blockchain.api.domain.response.UnspentTransaction;
import com.blockchain.api.domain.response.UnspentTransactionOutputs;
import org.bitcoinj.core.AddressFormatException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import validation.BitcoinAddress;

import java.net.MalformedURLException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceTest {

    private static final String VALID_BTC_ADDRESS = "1dice8EMZmqKvrGE4Qc9bUFf9PX3xaYDp";
    private static final String INVALID_BTC_ADDRESS = "!!!ZmqKvrGE4Qc9bUFf9PX3xaYDp";
    private static final String BLOCKCHAIN_INFO = "https://blockchain.info/";

    @Mock private RestTemplate restTemplate;
    @Mock private BitcoinAddress bitcoinAddress;

    private AddressService underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new AddressService(restTemplate, BLOCKCHAIN_INFO, bitcoinAddress);
    }

    @Test(expected = MalformedURLException.class)
    public void testItThrowsExceptionOnInvalidBaseApiUrl() throws Exception {
          new AddressService(restTemplate, "invalid", bitcoinAddress);
    }

    @Test
    public void testItFetchesUnspentTransactions(){
        when(restTemplate.getForObject("https://blockchain.info/unspent?active=" + VALID_BTC_ADDRESS,
                UnspentTransactionOutputs.class))
                .thenReturn(getUnspentTransactions());

        UnspentTransactionOutputs actualUnspentTransactions = underTest.getUnspentTransactions(VALID_BTC_ADDRESS);

        assertEquals(getUnspentTransactions(), actualUnspentTransactions);
        verify(bitcoinAddress).validateAddress(VALID_BTC_ADDRESS);
    }

    @Test
    public void testItThrowsAddressFormatException(){
        doThrow(new AddressFormatException("Invalid address")).when(bitcoinAddress).validateAddress(INVALID_BTC_ADDRESS);

        try {
            underTest.getUnspentTransactions(INVALID_BTC_ADDRESS);
            fail("AddressFormatException not thrown");
        } catch (AddressFormatException e) {
            assertEquals("Invalid address", e.getMessage());
            verifyNoMoreInteractions(restTemplate);
        }
    }

    private UnspentTransactionOutputs getUnspentTransactions() {
        return new UnspentTransactionOutputs(Collections.singletonList(new UnspentTransaction("hash", 0, 506509854L)));
    }

}