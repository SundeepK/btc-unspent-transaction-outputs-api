# Unspent transaction outputs api

This api allows consumers to fetcher unspent transaction outputs for a particular address given in `Base58`. An example get request is below:
  ```
    http://localhost:8080/address/1N52wHoVR79PMDishab2XmRHsbekCdGquK
  ```
  
  Which will return the following json:
  ```
{
  "outputs": [
    {
      "tx_hash": "eb3796ccde7f0421f443f6d1d520d89679611842741a73e096ee1f6cc58bf880",
      "value": 2000,
      "output_idx": 0
    }
  ]
}
```

## Dependencies
The project requires, Java 8, Ruby 2.3.1 and Bundler.

## Building
To build the project run:
```sh
    ./gradlew build
```
This will execute the Java unit tests and also run the cucumber acceptance tests. To just build a jar file and run the unit tests only run:
```sh
    $ ./gradlew build -x acceptanceTest
```

## Running api

To run the api execute the `run` script in the root of the project:
```sh 
    $ ./run.sh
```

It is also possible to run the api directly through gradle:
```sh
    $ ./gradlew bootRun -Dspring.profiles.active=prod
```