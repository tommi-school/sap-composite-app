require 'rubygems'
require 'savon'

client = Savon::Client.new do
  wsdl.document = File.read("CreateSalesOrderV1.wsdl")
  http.auth.basic "cslew", "handsome1"
end

#print all the operations in the service
#client.wsdl.soap_actions.each { |i| puts i }

response = client.request :yq1, :sales_order_erp_create_request_confirmation_in_v1 do
  soap.namespaces["xmlns:SOAP-ENV"] = "http://schemas.xmlsoap.org/soap/envelope/"
  soap.namespaces["xmlns:xs"] = "http://www.w3.org/2001/XMLSchema"
  soap.namespaces["xmlns:xsi"] = "http://www.w3.org/2001/XMLSchema-instance"
  
  soap.xml = '<?xml version="1.0" encoding="utf-8"?><SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><SOAP-ENV:Body><yq1:SalesOrderERPCreateRequest_sync_V1 xmlns:yq1="http://sap.com/xi/APPL/Global2"><SalesOrder><ProcessingTypeCode>OR</ProcessingTypeCode><BuyerDocument><ID>ICB-0200</ID><Date>2012-03-27</Date></BuyerDocument><SalesAndServiceBusinessArea><SalesOrganisationID>40A1</SalesOrganisationID><DistributionChannelCode>WH</DistributionChannelCode><DivisionCode>03</DivisionCode></SalesAndServiceBusinessArea><BuyerParty><InternalID>1</InternalID></BuyerParty><DateTerms><RequestDate>2012-04-06</RequestDate></DateTerms><DeliveryTerms><DeliveryPlantID>P100</DeliveryPlantID></DeliveryTerms><Item><Product><InternalID>5</InternalID></Product><TotalValues><RequestedQuantity>10</RequestedQuantity></TotalValues></Item></SalesOrder></yq1:SalesOrderERPCreateRequest_sync_V1></SOAP-ENV:Body></SOAP-ENV:Envelope>'
end

#Reference: http://fagiani.github.com/savon/
#Note: The XML is hardcoded for now.
