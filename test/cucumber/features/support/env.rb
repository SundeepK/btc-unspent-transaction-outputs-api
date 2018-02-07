require 'rest-assured'
require 'pry'
require 'json_spec/cucumber'
require 'process-helper'

AfterConfiguration do |config|
  RestAssured::Server.start(database: ':memory:', port: 9000)
  @process = ProcessHelper::ProcessHelper.new()
  @process.start(['../../../../run.sh'], /(Tomcat started)/)
end

After do |scenario|
end

at_exit do
  @process.kill
  @process.wait_for_exit
end

