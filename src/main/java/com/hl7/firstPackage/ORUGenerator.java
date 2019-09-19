package com.hl7.firstPackage;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.Parser;


public class ORUGenerator {

	//private static int PORT_NUMBER = 16929;
	
	// In HAPI, almost all things revolve around a context object
    private static HapiContext context = new DefaultHapiContext();
    
	//public static void main(String[] args) {

		public ORUGenerator(String hl7Message, String observation){
        
		try {
            // create the HL7 message
            // this OruMessageFactory class is not from NHAPI but my own wrapper class
            // check my GitHub page or see my earlier article for reference
            Message oruMessage = OruMessageFactory.CreateMessage(hl7Message, observation);

            // create a new MLLP client over the specified port
            //Connection connection = context.newClient("63.224.151.17", PORT_NUMBER, false);
            Connection connection = null;
            try {
            	 connection = context.newClient("63.224.151.17", 16929, false);
            } catch (Exception e) {
            	e.printStackTrace();
            }
            

            // The initiator which will be used to transmit our message
            Initiator initiator = connection.getInitiator();

            // send the previously created HL7 message over the connection established
            Parser parser = context.getPipeParser();
            System.out.println("Sending message:" + "\n" + parser.encode(oruMessage));
            Message response = initiator.sendAndReceive(oruMessage);

            // display the message response received from the remote party
            String responseString = parser.encode(response);
            System.out.println("Received response:\n" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

	}

}
