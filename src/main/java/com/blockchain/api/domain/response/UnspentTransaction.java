package com.blockchain.api.domain.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UnspentTransaction {
    
    private String txHash;
    private int outputIdx;
    private long value;

    @JsonCreator
    public UnspentTransaction(@JsonProperty("tx_hash") String txHash,
                              @JsonProperty("tx_output_n")  int outputIdx,
                              @JsonProperty("value")  long value) {
        this.txHash = txHash;
        this.outputIdx = outputIdx;
        this.value = value;
    }

    public String getTxHash() {
        return txHash;
    }

    public int getOutputIdx() {
        return outputIdx;
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnspentTransaction that = (UnspentTransaction) o;
        return Objects.equals(txHash, that.txHash) &&
                Objects.equals(outputIdx, that.outputIdx) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(txHash, outputIdx, value);
    }

    @Override
    public String toString() {
        return "UnspentTransaction{" +
                "txHash='" + txHash + '\'' +
                ", outputIdx='" + outputIdx + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
