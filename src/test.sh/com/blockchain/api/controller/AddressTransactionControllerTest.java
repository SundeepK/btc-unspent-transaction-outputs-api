package com.blockchain.api.controller;

import com.blockchain.api.domain.response.UnspentTransaction;
import com.blockchain.api.domain.response.UnspentTransactionOutputs;
import com.blockchain.api.services.AddressService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class AddressTransactionControllerTest {

    private static final String ADDRESS_REQUESTS_URL = "/address";
    private static final String VALID_BTC_ADDRESS = "1dice8EMZmqKvrGE4Qc9bUFf9PX3xaYDp";


    @Mock
    private AddressService addressService;

    private AddressTransactionController underTest;


    @Before
    public void setUp() {
        underTest = new AddressTransactionController(addressService);
    }

    @Test
    public void testItGetsUnspentTransactionOutputs() throws Exception {
        MockMvc mockMvc = standaloneSetup(underTest).build();

        when(addressService.getUnspentTransactions(VALID_BTC_ADDRESS)).thenReturn(getUnspentTransactions());

        MockHttpServletRequestBuilder param = MockMvcRequestBuilders.get(ADDRESS_REQUESTS_URL + "/" + VALID_BTC_ADDRESS);

        mockMvc.perform(param)
                .andExpect((MockMvcResultMatchers.jsonPath("$.outputs", Matchers.hasSize(2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.outputs[0].value").value(506509854L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.outputs[0].output_idx").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.outputs[0].tx_hash").value("hash"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.outputs[1].value").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.outputs[1].output_idx").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.outputs[1].tx_hash").value("hash2"))
                .andReturn();

    }


    private UnspentTransactionOutputs getUnspentTransactions() {
        List<UnspentTransaction> outputs = Arrays.asList(
                new UnspentTransaction("hash", 0, 506509854L),
                new UnspentTransaction("hash2", 0, 1L));
        return new UnspentTransactionOutputs(outputs);
    }


}