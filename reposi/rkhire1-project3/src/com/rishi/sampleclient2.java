package com.rishi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import chord.FileStore;
import chord.NodeID;
import chord.SystemException;

public class sampleclient2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  
				NodeID node_id = new NodeID();	  
					  
			        TTransport transport;
			        
			        
			        String n_id =null;
			        
			    
			    transport = new TSocket("127.0.1.1",9092);//////////////////////
			    try {
			    transport.open();    
			    TProtocol protocol = new  TBinaryProtocol(transport);
			    FileStore.Client client = new FileStore.Client(protocol);
				
			    TIOStreamTransport trans = new TIOStreamTransport(System.out);
				TProtocol tpro = new TJSONProtocol.Factory().getProtocol(trans);
				//client.join(null);
				
				n_id = genSHA("127.0.1.1","9090");
				NodeID n = new NodeID(n_id,"127.0.1.1",9090);
			
				client.join(n);
				//client.remove();
				 transport.close();
			//*/
			    }
			    catch(SystemException e)
			    {
			    	try {
			    		e.printStackTrace();
						e.write(new TJSONProtocol.Factory().getProtocol(new TIOStreamTransport(System.out)));
					} catch (TException s)
					{
						s.printStackTrace();
						System.exit(0);	
			    }
			    }  
			    catch (TTransportException e1) {
			   	 SystemException s = new SystemException();
					s.setMessage(e1.getMessage());
					try {
						s.write(new TJSONProtocol.Factory().getProtocol(new TIOStreamTransport(System.out)));
					} 
				catch(TException m)
					{
					}
					System.exit(0);
				}
				  catch (TException e) {
			    	SystemException s = new SystemException();
					s.setMessage(e.getMessage());
					try {
						s.write(new TJSONProtocol.Factory().getProtocol(new TIOStreamTransport(System.out)));
					} 
					catch(TException m)
					{
					}
					System.exit(0);
				}
			    
			   
			    
			    
	}
	 private static String genSHA (String username,String filename)
	    {

	    	 MessageDigest md = null;
	    	  byte[] digest = null;
	    	  StringBuffer sb = new StringBuffer();
	    	try {
	    		md = MessageDigest.getInstance("SHA-256");
	    		
	    	    String text = username +":"+filename;
	    	    md = MessageDigest.getInstance("SHA-256");
	    		md.update(text.getBytes());
	    	   
	    		for (byte byt : md.digest())
	    			sb.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
	    	    
	    	} catch (NoSuchAlgorithmException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    	     //   System.out.println("Hex format : " + sb.toString());
	    	return sb.toString();
	    }
}
