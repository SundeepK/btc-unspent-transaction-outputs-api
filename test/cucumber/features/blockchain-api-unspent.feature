Feature:
  AS a consumer of Blockchain unspent transactions api
  I want to get unspent transaction outputs
  SO that I can make applications based around unspent transaction outputs

  Scenario: Api returns unspent transaction outputs
    Given 1H8TqpBY4V9fWQyKBUtWPWxRcgy1Qxcb1U has following unspent output transactions
      | tx_hash                                                          | value    | output_idx | confirmations | tx_index |
      | 1236dd2e9494fd9a93c6b6c57e141520e29b4600c56abbdc295d4858ef182e48 | 9000     | 0          | 12000000000   | 1        |
      | 123d715823d17ad879cf29ba7fb58e0308e564f8f1109f15297446d732daf35a | 925000   | 0          | 11000000000   | 2        |
      | 1237c883be6c2d839ceb64ccac71d81954fc8ee436159cf9688be3ac954a5291 | 22790000 | 1          | 10000000000   | 3        |
    When I get address/unspent/1H8TqpBY4V9fWQyKBUtWPWxRcgy1Qxcb1U
    Then response is a "200"
    And the JSON should be:
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

  Scenario: Api returns 400 for unknown address
    When I get address/unspent/1H1aQ4bZC1Pr9etkbUzebJkh5K98
    Then response is a "400"
    And the JSON should be:
      """
      {
        "error": "Checksum does not validate"
      }
      """

  Scenario: Api returns 404 for unknown path
    When I get address/unspent/unkown/1H1aQ4bZC1Pr9etkbUzebJkh5K98
    Then response is a "404"
    And the JSON should be:
      """
      {
        "error": "Not found"
      }
      """

  Scenario: Api returns 500 when error fetching unspent outputs
    Given 1H8TqpBY4V9fWQyKBUtWPWxRcgy1Qxcb1U returns an error response
    When I get address/unspent/1H8TqpBY4V9fWQyKBUtWPWxRcgy1Qxcb1U
    Then response is a "500"
    And the JSON should be:
      """
      {
        "error": "Blockchain Internal Server Error"
      }
      """
