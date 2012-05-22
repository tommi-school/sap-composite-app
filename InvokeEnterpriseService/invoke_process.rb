require 'rubygems'
require 'savon'

client = Savon::Client.new do
  wsdl.document = File.read("InvokeProcess.wsdl")
  http.auth.basic "cslew", "handsome1"
end

#print all the operations in the service
client.wsdl.soap_actions.each { |i| puts i }

response = client.request :yq1, :start_process do
  soap.namespaces["xmlns:SOAP-ENV"] = "http://schemas.xmlsoap.org/soap/envelope/"
  soap.namespaces["xmlns:xs"] = "http://www.w3.org/2001/XMLSchema"
  soap.namespaces["xmlns:xsi"] = "http://www.w3.org/2001/XMLSchema-instance"
  
  soap.xml = '<?xml version="1.0" encoding="utf-8"?><SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><SOAP-ENV:Body><yq1:StartProcessRequestMessage xmlns:yq1="http://www.example.org/StartProcess/"><in/><Item xmlns:pns="http://www.demo.sap.com/createso/pr/context"><pns:Id>5</pns:Id><pns:Quantity>1</pns:Quantity></Item><BuyerId>1</BuyerId><POId>Tommi is awesome</POId><PODate>2012-03-31</PODate><RequestDate>2012-04-14</RequestDate></yq1:StartProcessRequestMessage></SOAP-ENV:Body></SOAP-ENV:Envelope>'
  
end

#Reference: http://fagiani.github.com/savon/
#Note: The XML is hardcoded for now.
