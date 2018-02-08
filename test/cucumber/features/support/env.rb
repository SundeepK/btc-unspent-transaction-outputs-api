require 'rest-assured'
require 'pry'
require 'json_spec/cucumber'
require 'process-helper'
require_relative 'under_test'

AfterConfiguration do |config|
  RestAssured::Server.start(database: ':memory:', port: REST_ASSURED_PORT)
  until RestAssured::Server.up?
    puts "Waiting for RestAssured::Server to come up on port #{REST_ASSURED_PORT}..."
    sleep 1
  end
  UnderTest.instance.start
end

After do |scenario|
  RestClient.delete "#{RestAssured::Server.address}/doubles/all"
end

at_exit do
  UnderTest.instance.kill
end

