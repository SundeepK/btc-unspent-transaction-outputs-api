Feature:
  AS a consumer of Blockchain unspent transactions api
  I want to get unspent transaction outputs
  SO that I can make applications based around unspent transaction outputs

  Scenario: Blockchain api returns unspent transaction outputs
    When I get address/unspent/1H1aQ4bZC1Pr9etkbUzebJkh5K98MALmes
    Then the JSON response should be:
      """
      {
        "outputs": [
          {
            "tx_hash": "1236dd2e9494fd9a93c6b6c57e141520e29b4600c56abbdc295d4858ef182e48",
            "value": "9000",
            "output_idx": "0"
          },
          {
            "tx_hash": "123d715823d17ad879cf29ba7fb58e0308e564f8f1109f15297446d732daf35a",
            "value": "925000",
            "output_idx": "0"
          },
          {
            "tx_hash": "1237c883be6c2d839ceb64ccac71d81954fc8ee436159cf9688be3ac954a5291",
            "value": "22790000",
            "output_idx": "1"
          }
        ]
      }
      """
