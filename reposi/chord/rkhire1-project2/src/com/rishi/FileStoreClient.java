package com.rishi;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.MessageDigest;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;

import chord.FileStore;
import chord.RFile;
import chord.RFileMetadata;
import chord.Status;
import chord.StatusReport;
import chord.SystemException;

public class FileStoreClient {
  public static void main(String [] args) {
	  
	String host;
	int port;
	String operation = null;
	String filename = null;
	String user = null;
		  
		  if (args.length != 8) {
			  System.out.println("Please enter proper command line argument");
			  System.exit(0);  
		  		}
		  
    try {
        TTransport transport;
        host = args[0];
        port = Integer.parseInt(args[1]);
        transport = new TSocket(host, port);
        transport.open();
        
        TProtocol protocol = new  TBinaryProtocol(transport);
        FileStore.Client client = new FileStore.Client(protocol);
    	
        TIOStreamTransport trans = new TIOStreamTransport(System.out);
  	    TProtocol tpro = new TJSONProtocol.Factory().getProtocol(trans);
    
    try{
    if(args[2].equals("--operation") && args[4].equals("--filename") && args[6].equals("--user") )
      {
    	  operation= args[3];
    	  filename = args[5];
    	  user = args[7];
    	   	  
      }
      else if(args[2].equals("--operation") && args[6].equals("--filename") && args[4].equals("--user") )
      {
    	  operation= args[3];
    	  filename = args[7];
    	  user = args[5];
      }
      else if(args[6].equals("--operation") && args[4].equals("--filename") && args[2].equals("--user") )
      {
    	  operation= args[7];
    	  filename = args[5];
    	  user = args[3];
    	  
    	  
      }else if(args[6].equals("--operation") && args[2].equals("--filename") && args[4].equals("--user") )
      {
    	  operation= args[7];
    	  filename = args[3];
    	  user = args[5];
    	  
      }
      else if(args[4].equals("--operation") && args[2].equals("--filename") && args[6].equals("--user") )
      {
    	  operation= args[5];
    	  filename = args[3];
    	  user = args[7];
    	     	  
      }
      else if(args[4].equals("--operation") && args[6].equals("--filename") && args[2].equals("--user") )
      {
    	  operation= args[5];
    	  filename = args[7];
    	  user = args[2];
       	  
      }
     else {
    	  SystemException a = new SystemException();
		  a.setMessage("Please enter proper command line argument");
		  throw a;
      }
    }catch(SystemException e)
    {
    	e.write(tpro);
    }
             
        try{
        perform(client,operation,filename,user);
       // perform(client,"read",filename,user);
        }
        catch(SystemException e)
        {
        	e.write(tpro);
        }
      
        transport.close();
    }
    catch (TException x) {
        x.printStackTrace();
      } 
    
    
	
  }

  private static void perform(FileStore.Client client,String operation,String filename,String user) throws TException,SystemException
  {
	  RFile rFile = new RFile();
	  RFileMetadata rfmd = new RFileMetadata();
	  rfmd.setFilename(filename);
	  rfmd.setOwner(user);
	  String content = "";
	  String contentHash = "";
	  TIOStreamTransport trans1 = new TIOStreamTransport(System.out);
	  TProtocol tpro = new TJSONProtocol.Factory().getProtocol(trans1);
	 
	  if(operation.equals("write"))
	  {
		  
		  	String path = "./clientdir/";
		  	File clientdir = new File(path);
			File[] listOfFiles = clientdir.listFiles();
			int flag=0;
			for(int i = 0; i < listOfFiles.length; i++)
	    	{
	    	    String f = listOfFiles[i].getName();
	    	    
	    	    if(f.equals(filename))
	    	    {
	    	    	flag=1;
	    	    	File readOne = new File(clientdir,filename);
	    	    	try{
	    	    	 BufferedReader br = new BufferedReader(new FileReader(path+filename));
	    	    	    			
	    				String sCurrentLine;
	    	 
	    				while ((sCurrentLine = br.readLine()) != null) {
	    					content = content + sCurrentLine;
	    				}
	    				    
	    				    
	    					
	       					rfmd.setContentLength(content.length());
	    					rfmd.setDeleted(0);
	    					
	    					rfmd.setVersion(0);
	    					rfmd.setUpdated(0);
	    					
	    					String original = content;
	    					MessageDigest md = MessageDigest.getInstance("MD5");
	    					md.update(original.getBytes());
	    					byte[] digest = md.digest();
	    					StringBuffer sb = new StringBuffer();
	    					for (byte b : digest) {
	    						sb.append(String.format("%02x", b & 0xff));
	    					}
	    					
	    					rfmd.setContentHash(sb.toString());	 
	    					rFile.setContent(content);
	    					rFile.setMeta(rfmd);
	    					try{
	    					//	StatusReport st = client.writeFile(rFile);
	    						client.writeFile(rFile);
	    					//	st.write(tpro);
	    						
	    					//	System.out.println("Status Report"+st.toString());
	    					/*	if(st.equals(Status.SUCCESSFUL))
	    						{
	    							System.out.println("written to server");
	    						}
	    						*/
	    				    	}
	    				    	catch(SystemException e)
	    				    	{
	    				    		e.write(tpro);
	    				    	}
	    			} 
	    			  catch (Exception e) {
	     				System.out.println("error in reading from file");
	 	    	    }    	
	    	    	break;
	    	    }
	    	
		
	  }
			if(flag==0){
				System.out.println("No file found on local directory");
				System.exit(0);
			}
	  }
			
	  else if(operation.equals("read"))
	  {
		  try{
		  RFile returnFile = client.readFile(filename, user);
		    returnFile.write(tpro);
		  }
		  catch(SystemException e)
		  {
			  e.write(tpro);
		  }		  
	  }
	  
	  else if(operation.equals("delete"))
	  {
		try{
		 // StatusReport st = client.deleteFile(filename, user);
			client.deleteFile(filename, user);
		 // System.out.println(st.toString());
		 
		 /* if(st.equals(Status.SUCCESSFUL))
			{
				System.out.println("deletedr");
			}*/
		}
		catch(SystemException e){
				e.write(tpro);		
			}
		
	  }
	  
	  else if(operation.equals("list"))
	  {
		/*  try{
		  List<RFileMetadata> listown =  client.listOwnedFiles(user);
		  
			  System.out.println("file by owner " +user);
			  
			  for(RFileMetadata value : listown) {
		            //System.out.println(value.getFilename());
		           value.write(tpro);
		         }
		  }catch(SystemException e)
		  {
			  e.write(tpro);
		  }*/
		  
	  }
	  else{
		  System.err.println("no such operation found!!!!");
	  }
  }
  
 

}

