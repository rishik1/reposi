package com.rishi;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import chord.RFile;
import chord.RFileMetadata;
import chord.SystemException;

public class FileStoreClient {
  public static void main(String [] args) {
	  
	String host;
	int port;
	String operation = null;
	String filename = null;
	String path =null;
	String user = null;
	NodeID node_id = new NodeID();	  
		  if (args.length != 8) {
			  System.out.println("Please enter proper command line argument");
			  System.exit(0);  
		  		}
	
        TTransport transport;
        host = args[0];
        port = Integer.parseInt(args[1]);
        
        
        String n_id = genSHA(host,args[1]);
        node_id.id= n_id;
        node_id.ip = host;
        node_id.port = port;
    

    if(args[2].equals("--operation") && args[4].equals("--filename") && args[6].equals("--user") )
      {
    	  operation= args[3];
    	  path = args[5];
    	  user = args[7];
    	   	  
      }
      else if(args[2].equals("--operation") && args[6].equals("--filename") && args[4].equals("--user") )
      {
    	  operation= args[3];
    	  path = args[7];
    	  user = args[5];
      }
      else if(args[6].equals("--operation") && args[4].equals("--filename") && args[2].equals("--user") )
      {
    	  operation= args[7];
    	  path = args[5];
    	  user = args[3];
    	  
    	  
      }else if(args[6].equals("--operation") && args[2].equals("--filename") && args[4].equals("--user") )
      {
    	  operation= args[7];
    	  path = args[3];
    	  user = args[5];
    	  
      }
      else if(args[4].equals("--operation") && args[2].equals("--filename") && args[6].equals("--user") )
      {
    	  operation= args[5];
    	  path = args[3];
    	  user = args[7];
    	     	  
      }
      else if(args[4].equals("--operation") && args[6].equals("--filename") && args[2].equals("--user") )
      {
    	  operation= args[5];
    	  path = args[7];
    	  user = args[2];
       	  
      }
     else {
    	  System.err.println("improper command line arguments");
    	  System.exit(0);
      }
    
    transport = new TSocket(host, port);
    try {
    transport.open();    
    TProtocol protocol = new  TBinaryProtocol(transport);
    FileStore.Client client = new FileStore.Client(protocol);
	
    TIOStreamTransport trans = new TIOStreamTransport(System.out);
	TProtocol tpro = new TJSONProtocol.Factory().getProtocol(trans);
	String[] fileP = path.split("/");
	filename = fileP[fileP.length-1]; 
	  
      //System.out.println("sha of file");
	  String key = genSHA(user,filename);
	  NodeID file_id;
	  
	  
	 // System.out.println("Key of the file to be found is::"+key);
	// key = "823ab5776ac5dd1c1161351bcd0c7cd4241e9e717c39676cdd0f8d978a38564a";
	 
		file_id = client.findSucc(key);
		//System.out.println("writing to file id::" + file_id );
		transport.close();
		
		perform(operation,filename,user,file_id,key,path);
    }
    catch(SystemException e)
    {
    	try {
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
   
  

  private static void perform(String operation,String filename,String user, NodeID file_id, String key,String path) throws TException,SystemException
  {
	
	  	 
	  if(operation.equals("write"))
	  {	  
		   write(user,filename,key,file_id,path);

	  }		
	  else if(operation.equals("read"))
	  {
		String temp_ip =file_id.getIp();
		int temp_port = file_id.getPort();
		TTransport transport = new TSocket(temp_ip, temp_port);
		transport.open();
		TProtocol protocol = new  TBinaryProtocol(transport);	
		FileStore.Client client = new FileStore.Client(protocol);
		System.out.println("READING THE FILE::::");
		TIOStreamTransport trans = new TIOStreamTransport(System.out);
		 TProtocol tpro = new TJSONProtocol.Factory().getProtocol(trans);
		
		 try{
			 
			  RFile returnFile = client.readFile(filename, user);
			//  System.out.println("read contecnt"+returnFile.getContent());
			  System.out.println("file read successfully");
			   returnFile.write(tpro);
			  }
			  catch(SystemException e)
			  {
				  e.write(tpro);
			  }
			transport.close();
	  }
	  
	  else if(operation.equals("delete"))
	  {
		  String temp_ip =file_id.getIp();
			int temp_port = file_id.getPort();
			TTransport transport = new TSocket(temp_ip, temp_port);
			transport.open();
			TProtocol protocol = new  TBinaryProtocol(transport);
			FileStore.Client client = new FileStore.Client(protocol);
			
			TIOStreamTransport trans = new TIOStreamTransport(System.out);
			TProtocol tpro = new TJSONProtocol.Factory().getProtocol(trans);
			try{
			client.deleteFile(filename, user);
			System.out.println("file deleted");
			}catch(SystemException e)
			{
				e.write(tpro);
				System.exit(0);
			}
			transport.close();
		
	  }
	  else{
		  System.err.println("no such operation found!!!!");
	  }
	
  }
  
 

private static void write(String user, String filename, String key, NodeID nid, String path) {
	
	// TODO Auto-generated method stub
	
	String[] fileP = path.split("/");
	filename = fileP[fileP.length-1]; 
  	File clientdir = new File(path);
      //	System.out.println("path::::"+path);
      	for(int i=0;i< fileP.length;i++)
      	{
      		
      	}
    String temp_ip =nid.getIp();
	  int temp_port = nid.getPort();
	  TTransport transport = new TSocket(temp_ip, temp_port);

		try {
			transport.open();
		} catch (TTransportException e1) {
			SystemException s = new SystemException();
			s.setMessage(e1.getMessage());
			try {
				s.write(new TJSONProtocol.Factory().getProtocol(new TIOStreamTransport(System.out)));
			} 
			catch(TException m)
			{
				System.out.println("error in opening port");
			}
			
		}
		 TProtocol protocol = new  TBinaryProtocol(transport);
	     FileStore.Client client = new FileStore.Client(protocol);
	    	
	     TIOStreamTransport trans = new TIOStreamTransport(System.out);
	  	 TProtocol tpro = new TJSONProtocol.Factory().getProtocol(trans);

  //	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
  	File[] listOfFiles = clientdir.listFiles();
	int flag=0;
	String sCurrentLine;
	 
	String content ="";
	    	File readOne = new File(clientdir,filename);
	    	try{
	    			try{
	    			BufferedReader br = new BufferedReader(new FileReader(path));
	    			
					while ((sCurrentLine = br.readLine()) != null) {
					content = content + sCurrentLine;
				     }
					br.close();
	    			}
	    			catch(FileNotFoundException e){
	    				SystemException s = new SystemException();
	     				s.setMessage("file not found !!");
	     				s.write(tpro);
	     				System.exit(0);
	    			}
					
				//	System.out.println("Content:"+content);
					
   					RFileMetadata rfmd = new RFileMetadata();
   					rfmd.setFilename(filename);
   					rfmd.setOwner(user);
					rfmd.setContentLength(content.length());
					rfmd.setDeleted(0);
					
					rfmd.setVersion(0);
					rfmd.setUpdated(0);
					
					String original = content;
					rfmd.setContentHash(key.toString());
					
					RFile rFile = new RFile();
					rFile.setContent(content);
					rFile.setMeta(rfmd);
				//	System.out.println("Yetoy");
					try{
						//System.out.println("Yetoy11");
						//System.out.println("Writnig to client ::"+temp_ip+"::"+temp_port);
						client.writeFile(rFile);
				    	}
				    	catch(SystemException e)
				    	{
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
	    	catch (SystemException e)
	    	{
	    		try {
					e.write(new TJSONProtocol.Factory().getProtocol(new TIOStreamTransport(System.out)));
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		
	    	}
			  catch (Exception e) {
				  
 				SystemException s = new SystemException();
 				s.setMessage("error in writing to file");
 				try {
 					s.write(new TJSONProtocol.Factory().getProtocol(new TIOStreamTransport(System.out)));
 				} 
 				catch(TException m)
 				{
 				}
 				System.exit(0);
	    	    }    	
	    
	    
System.out.println("written");

	
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