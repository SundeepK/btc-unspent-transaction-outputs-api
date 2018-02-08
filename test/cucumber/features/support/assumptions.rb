
unless defined? SET_ASSUMPTIONS
  BASE_DIR = File.expand_path('../../../..', File.dirname(__FILE__))
  REST_ASSURED_PORT = 9000
  ENV['BLOCKCHAIN_API_BASE_URL'] = "http://localhost:#{REST_ASSURED_PORT}"
  SET_ASSUMPTIONS = true
end

