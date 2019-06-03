package com.hl7.firstPackage;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v24.message.ADT_A01;
import ca.uhn.hl7v2.parser.Parser;

public class MyTest {

    private static final int PORT_NUMBER = 16929;// change this to whatever your port number is

    // In HAPI, almost all things revolve around a context object
    private static HapiContext context = new DefaultHapiContext();
        
    public static void main(String[] args) throws Exception {

    	String HL7Message = "MSH|"+"^"+"~"+"\""+"&"+"|ECW|Shaila|RAP1100|BN|20190410151932||ORU^R01||P|2.1"
    			+"PID|1|AB13330||AB13330|TEST^TEST^||19900101|Male|||21 ANY STREET^APARTMENT 300B^JAMAICA PLAIN^MA^02130||5084445555|||||^LFMC|||"
    			+"PV1|1||||||1326018813^GRIEVESON^JOHN^|^^^|||||||||||137042|"
    			+"ORC||1234|||||||||||OrdProvider"
    			+"OBR|1|137042||SA_004^US abdomen Limited e-sch|||20190408145800|||||||||1326018813^GRIEVESON^JOHN||0||0|||||||^^^^^0|"
    			+"OBX|1||SA_004^US abdomen Limited e-sch||74160: CT ABDOMEN WITH CONTRAST|||||||||||"
    			+"NTE|1||The^uterus^is^anteverted,^appears^unremarkable^in^size,^shape,^and^contour.^The^endometrium^appears^homogeneous^and^unremarkable.^Unremarkable^vascularity^is^noted^within^the^endometrium^and^the^submucosa.^The^myometrium^and^endocervix^appear^homogeneous^and^unremarkable.The^right^ovary^appears^unremarkable;^an^11mm^simple^cyst^is^noted^in^the^para-adnexal^area.^The^left^ovary^contains^a^21mm^dominant^follicle/simple^cyst.^There^is^no^tenderness^or^free^fluid^in^the^para-adnexal^areas/cds";
        try {

            // create the HL7 message
            // this AdtMessageFactory class is not from HAPI but my own wrapper
            // check my GitHub page or see my earlier article for reference
            ADT_A01 adtMessage = (ADT_A01) AdtMessageFactory.createMessage("A01", HL7Message);
            System.out.print(adtMessage);
            // create a new MLLP client over the specified port
//            Connection connection = context.newClient("63.224.151.17", PORT_NUMBER, false);
//
//            // The initiator which will be used to transmit our message
//            Initiator initiator = connection.getInitiator();
//
//            // send the previously created HL7 message over the connection established
//            Parser parser = context.getPipeParser();
//            System.out.println("Sending message:" + "\n" + parser.encode(adtMessage));
//            Message response = initiator.sendAndReceive(adtMessage);
//            
//
//            // display the message response received from the remote party
//            String responseString = parser.encode(response);
//            System.out.println("Received response:\n" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}