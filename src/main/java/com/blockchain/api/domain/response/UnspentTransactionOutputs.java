package com.blockchain.api.domain.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UnspentTransactionOutputs {

    private List<UnspentTransaction> outputs;

    @JsonCreator
    public UnspentTransactionOutputs(@JsonProperty("unspent_outputs") List<UnspentTransaction> outputs) {
        this.outputs = outputs;
    }

    public List<UnspentTransaction> getOutputs() {
        return outputs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnspentTransactionOutputs that = (UnspentTransactionOutputs) o;
        return Objects.equals(outputs, that.outputs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outputs);
    }

    @Override
    public String toString() {
        return "UnspentTransactionOutputs{" +
                "outputs=" + outputs +
                '}';
    }
}
