import javax.xml.namespace.QName;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class ClientDrunk {

    public static void main(String[] args) throws AxisFault {

        RPCServiceClient serviceClient = new RPCServiceClient();
        Options options = serviceClient.getOptions();

        // Setting the endpoint resource
        EndpointReference targetEPR = new EndpointReference
			("http://ec2-175-41-168-146.ap-southeast-1.compute.amazonaws.com:8080/axis2/services/listServices:8080/axis2/services/Drunk.DrunkHttpSoap12Endpoint/");
		//EndpointReference targetEPR = new EndpointReference
		//	("http://localhost:8080/axis2/services/Drunk.DrunkHttpSoap12Endpoint/");
		
			
		options.setTo(targetEPR);

        // Getting the operation based on the targetnamespace
        QName opGetExchange = new QName                            
              ("http://ws.apache.org/axis2", "getOrderedQtyByItemId");

        // preparing the arguments
        Object[] opGetExchangeArgs = new Object[] { 1 };
        
        // preparing the return type
        Class[] returnTypes = new Class[] { Integer.class };

        // invoking the service passing in the arguments and getting the
        // response
        Object[] response = serviceClient.invokeBlocking(opGetExchange,
                opGetExchangeArgs, returnTypes);
        // obtaining the data from the response
        int result = (Integer) response[0];
        
        // Displaying the result
        System.out.println("Result : " + result);
   } 
}