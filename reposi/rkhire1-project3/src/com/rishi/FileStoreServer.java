package com.rishi;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

import chord.FileStore;
import chord.NodeID;
public class FileStoreServer {

  public static FileStoreImpl handler;

  public static FileStore.Processor<FileStoreImpl> processor;

  public static void main(String [] args) {
	  if(args.length!= 1)
	  {
		  System.out.println("improper argument");
		  System.exit(0);
	  }
	  final int port = Integer.parseInt(args[0]);
	  
    try {
    	String hostname = InetAddress.getLocalHost().getHostAddress();
    	System.out.println("hostname"+hostname);
    	System.out.println("portno"+args[0]);
        handler = new FileStoreImpl(hostname,args[0]);
        processor = new FileStore.Processor<FileStoreImpl>(handler);
        
         simple(processor,port);
       
    } catch (Exception x) {
      x.printStackTrace();
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
  	     System.out.println("key format : " + sb.toString());
  	return sb.toString();
  	
  }
  public static void simple(FileStore.Processor<FileStoreImpl> processor,int port) {
    try {
      TServerTransport serverTransport = new TServerSocket(port);
      TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

      // Use this for a multithreaded server
      // TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

      System.out.println("Starting the simple server...");
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}