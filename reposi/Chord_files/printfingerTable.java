package com.rishi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import chord.DHTNode;
import chord.NodeID;
import chord.SystemException;
public class printfingerTable {

	/**
	 * @param args
	 * @throws SystemException 
	 */
	public static void main(String[] args) throws SystemException {
		
		
NodeID nodes = new NodeID();
		
		
		TTransport transport=null;
		TProtocol protocol=null;
		TIOStreamTransport trans =null;
		TProtocol tpro  =null;
				
		String temp_ip = null;
		String temp_id = null;

		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			SystemException s=new SystemException();
			s.setMessage(e.getMessage());
			throw s;
		}
		temp_ip = addr.getHostAddress();
		//nodes is node of start port
	
		String t_port = Integer.toString(9090);
		String t_ip = temp_ip;
		String t_id = genSHA(t_ip,t_port);
		nodes.setId(t_id);
		int j=0;
		while(j<3)
		{
			int temp_port = 9090+j;
			System.out.println("FOR PORT NUMBER"+temp_port);
			
			transport = new TSocket(temp_ip, temp_port);
			try {
			transport.open();	
			protocol = new  TBinaryProtocol(transport);
		    DHTNode.Client client = new DHTNode.Client(protocol);
		    	
		    trans = new TIOStreamTransport(System.out);
		  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
		  	 
			 try{	
					List<NodeID> plist=client.getFingertable(); 
					for(int i = 0; i < plist.size(); i++) {   
					    System.out.println(plist.get(i));
					}  
				 }
				 catch (TException e) {
						e.printStackTrace();
				 }					
			 transport.close();
			}
			catch (TTransportException e1) {
					e1.printStackTrace();
			} 
		j++;	
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
			e.printStackTrace();
		}
		return sb.toString();
		
	}
}
