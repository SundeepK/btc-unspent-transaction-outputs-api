require 'json'
require 'rest-client'

When(/^I get ([^"]*)$/) do |path|
  get("http://localhost:8080/#{path}")
end

def get(url)
  begin
    @response = RestClient.get url
  rescue RestClient::ExceptionWithResponse => e
    @response = e.response
  end
end

Then(/^response is a "([^"]*)"$/) do |response_code|
  expect(@response.code.to_s).to eq(response_code)
end

Given(/^([^"]*) has following unspent output transactions$/) do |address, table|
  RestAssured::Double.create(
      :verb => 'GET',
      :fullpath => "/unspent?active=#{address}",
      :status => 200,
      :response_headers => { 'Content-Type' => 'application/json' },
      :content => {unspent_outputs: table.symbolic_hashes.map }.to_json
  )
end

def last_json
  @response
end

Given(/^([^"]*) is unkown$/) do |address|
  RestAssured::Double.create(
      :verb => 'GET',
      :fullpath => "/unspent?active=#{address}",
      :status => 500,
      :response_headers => { 'Content-Type' => 'text/plain' },
      :content => 'Invalid Bitcoin Address'
  )
end