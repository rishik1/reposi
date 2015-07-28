	package com.rishi;
	
	
	
	import java.io.BufferedReader;
	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.math.BigDecimal;
	import java.math.BigInteger;
	import java.security.MessageDigest;
	import java.security.NoSuchAlgorithmException;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.Iterator;
	import java.util.List;
	import java.util.Map;
	import java.util.Map.Entry;
	
	import org.apache.thrift.TException;
	import org.apache.thrift.protocol.TBinaryProtocol;
	import org.apache.thrift.protocol.TJSONProtocol;
	import org.apache.thrift.protocol.TProtocol;
	import org.apache.thrift.transport.TIOStreamTransport;
	import org.apache.thrift.transport.TSocket;
	import org.apache.thrift.transport.TTransport;
	import org.apache.thrift.transport.TTransportException;
	import org.w3c.dom.Node;
	
	import chord.FileStore;
	import chord.NodeID;
	import chord.RFile;
	import chord.RFileMetadata;
	import chord.SystemException;
	
	public class FileStoreImpl implements FileStore.Iface {
		private String filename;
		private String current_key;
		private BufferedReader buff;
		private HashMap<String, RFile> fileList;
		private List<NodeID> fingerTable;
		private int caseFlag=-1;
		File folder = new File("./serverdir");
	private NodeID current_node = null;
	private NodeID predecessor_node =null;
	int portno;
	public FileStoreImpl()
	{
		fingerTable = new ArrayList<NodeID>();
			 
	         fileList = new HashMap<String,RFile>();
	         File file = new File("./serverdir");
	         for(File f: file.listFiles()){
	        	 if(f.isFile()){
	        		 f.delete();
	        	 }
	         }
	         
	        
	         
	} 
	
	
	
	
	public FileStoreImpl(String hostname, String port) {
		// TODO Auto-generated constructor stub
		//System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		//System.out.println("ON the Node ::::::::::");
		fingerTable = new ArrayList<NodeID>();
		 
	    fileList = new HashMap<String,RFile>();
	    current_node= new NodeID();
	    current_node.setIp(hostname);
	    portno = Integer.parseInt(port);
	    current_node.setPort(portno);
	    current_key = genSHA(hostname, port);
	    current_node.setId(current_key);
	    NodeID pred_node = new NodeID();
	    
	   
	   // System.out.println("portno			 :"+ portno);
	   // System.out.println("current_key		::"+current_key);
	    
	    //System.out.println("pred_node key::"+pred_node.getId());
	    File file = new File("./serverdir");
	    for(File f: file.listFiles()){
	   	 if(f.isFile()){
	   		 f.delete();
	   	 }
	    }
	    for(int i=0;i<256;i++)
        {
        	fingerTable.add(current_node);
        //	System.out.println("fingertable["+i+"] = "+fingerTable.get(i).getPort());
        }
	    try {
			setNodePred(current_node);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	    if(portno==9090)
        {
            RFile rt=new RFile();
            rt.meta=new RFileMetadata();
            rt.meta.setFilename("temp.txt");
            rt.meta.setOwner("shrikant");
            fileList.put("temp.txt",rt);

            rt=new RFile();
            rt.meta=new RFileMetadata();
            rt.meta.setFilename("tutorial.txt");
            rt.meta.setOwner("shrikant");
            fileList.put("tutorial.txt",rt);


            rt=new RFile();
            rt.meta=new RFileMetadata();
            rt.meta.setFilename("tesFile.txt");
            rt.meta.setOwner("shrikant");
            fileList.put("tesFile.txt",rt);

            rt=new RFile();
            rt.meta=new RFileMetadata();
            rt.meta.setFilename("test.txt");
            rt.meta.setOwner("shrikant");
            fileList.put("test.txt",rt);

            rt=new RFile();
            rt.meta=new RFileMetadata();
            rt.meta.setFilename("example.txt");
            rt.meta.setOwner("shrikant");
            fileList.put("example.txt",rt);


            rt=new RFile();
            rt.meta=new RFileMetadata();
            rt.meta.setFilename("homework.txt");
            rt.meta.setOwner("shrikant");
            fileList.put("homework.txt",rt);

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
		 //      System.out.println("Hex format : " + sb.toString());
		return sb.toString();
		
	}
	private int ownedBy(String key)
	{
		int result=-1;
		NodeID predecessorNode =this.predecessor_node;
	//	System.out.println("the predecessor node of this is"+ predecessorNode);
		String predecessor_key = predecessorNode.getId();
		String currentKey = current_node.getId();
		if(key.compareToIgnoreCase(currentKey)<=0 && key.compareToIgnoreCase(predecessor_key)>0)
		{
			result =1;
		}
		if(currentKey.compareToIgnoreCase(predecessor_key)<0)
		{
			if(key.compareToIgnoreCase(currentKey)<=0 && key.compareToIgnoreCase(predecessor_key)<0)
			{
				result =1;
			}
			if(key.compareToIgnoreCase(currentKey)>=0 && key.compareToIgnoreCase(predecessor_key)>0)
			{
				result =1;
			}
		}
		
		return result;
	}
	public void writeFile(RFile rFile) throws SystemException, org.apache.thrift.TException{
	 	File[] listOfFiles = folder.listFiles();
	//	StatusReport state = new StatusReport();
		//state.setStatus(Status.FAILED);
		RFileMetadata data = rFile.getMeta();
		String name= rFile.getMeta().getFilename();
		//System.out.println("in write fi/le at server.....................");
		int flag =-1;
		String Owner = rFile.getMeta().getOwner();
		String fKey = genSHA(Owner,name);
		if(ownedBy(fKey)!=1)
		{
			SystemException s=new SystemException();
			s.setMessage("writing to wrong node");
	    		throw s;
		}
		else{
		if(fileList.containsKey(name))
		{
		for (Map.Entry<String, RFile> entry : fileList.entrySet())
		   {
		    String filename = entry.getKey();
		    RFile value = entry.getValue();
		    if(value.getMeta().owner.equals(rFile.getMeta().owner))
		    {
		    	for(int i = 0; i < listOfFiles.length; i++)
		    	{
		    	    String f = listOfFiles[i].getName();
		    	    if(f.equals(filename))
		    	       {
		    	    	int ver =value.getMeta().getVersion();
		    	    	listOfFiles[i].delete();
		    	    	File updatedOne = new File(folder,rFile.meta.filename);
		    	    	FileWriter fw;
						try {
							fw = new FileWriter(updatedOne.getAbsoluteFile());
						
		                BufferedWriter bw = new BufferedWriter(fw);
		                bw.write(rFile.content);
		                bw.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                rFile.getMeta().setCreated(System.currentTimeMillis());
		                rFile.getMeta().setVersion(ver+1);
		                rFile.getMeta().setUpdated(System.currentTimeMillis());
		                rFile.getMeta().setContentLength(rFile.getContent().length());
		                rFile.getMeta().setDeleted(0);
		                fileList.put(filename, rFile);
		                       flag=1;   
		    	        }
	            }
		    	
		     }
		    }
		}
		    else{
		    	File newOne = new File("./serverdir/"+name);
		    	
		    	FileWriter fw;
				try {
					fw = new FileWriter(newOne.getAbsoluteFile());
	            BufferedWriter bw = new BufferedWriter(fw);
	            bw.write(rFile.content);
	            bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            rFile.meta.setCreated(System.currentTimeMillis());
	            rFile.meta.setVersion(0);
	            rFile.meta.setUpdated(System.currentTimeMillis());
	            rFile.meta.setContentLength(rFile.getContent().length());
	            rFile.meta.setDeleted(0);
	            fileList.put(rFile.meta.getFilename(), rFile);
	            
	            System.out.println("written successfully");
	            flag=1;
		    }
		
	if(flag!=1)
	{
		SystemException s=new SystemException();
		s.setMessage("writing to server failed");
			throw s;
	    }
		}
	
	}
	
	    public RFile readFile(String filename, String owner) throws SystemException, org.apache.thrift.TException{
			
		File[] listOfFiles = folder.listFiles();
		//System.out.println("IN the server reading file:::::::::::");
		String Owner = owner;
		String fKey = genSHA(Owner,filename);
		if(ownedBy(fKey)!=1)
		{
			SystemException s=new SystemException();
			s.setMessage("writing to wrong node");
		    		throw s;
			}
			else{
					if(fileList.get(filename)!=null)
					{
					for (Map.Entry<String, RFile> entry : fileList.entrySet())
					   {
					    String fname = entry.getKey();
					    RFile value = entry.getValue();
					    if((value.getMeta().owner.equals(owner))&& (value.getMeta().deleted==0))
					    {
					    	//System.out.println("read from file ::::"+value.content);
					    	return value;
					    }
					    	
					   }
					}
					else
					{
						SystemException s=new SystemException();
						s.setMessage("reading from server failed");
						throw s;
					}
		}	
	    	return null;
	 	    	
	
	}
	
	    public void deleteFile(String filename, String owner) throws SystemException, org.apache.thrift.TException{
	    	File[] listOfFiles = folder.listFiles();
	    	int flag=-1;
	    	RFile rFile ;
	    	//System.out.println("in delete++++++++++++");
	    	String Owner = owner;
			String fKey = genSHA(Owner,filename);
			if(ownedBy(fKey)!=1)
			{
				SystemException s=new SystemException();
				s.setMessage("writing to wrong node");
		    		throw s;
			}
			else{	
	    	
				if((rFile=fileList.get(filename))!=null){
					File file= new File("./server/"+filename);
					file.delete();
					rFile.getMeta().setDeleted(System.currentTimeMillis());
			        rFile.getMeta().setUpdated(System.currentTimeMillis());
			        rFile.getMeta().setContentLength(rFile.getContent().length());
			        rFile.setContent(null);
			      //  System.out.println("file deleted+++++++++++++++");
			        flag=1;
				}
				if(flag!=1)
				{
					SystemException s=new SystemException();
					s.setMessage("deleting from server failed");
			    		throw s;
			    	}
			}
	    }	
	
		@Override
		public void setNodePred(NodeID nodeId) throws SystemException, TException {
			// TODO Auto-generated method stub
		
		predecessor_node =nodeId;
		
	}
	
	@Override
	public void updateFinger(int idx, NodeID nodeId) throws SystemException,
			TException {
		// TODO Auto-generated method stub
		//System.out.println(" value update i->"+idx);
		//System.out.println("cureent port :"+current_node.getPort());
		//System.out.println(" nodeid updated entry::"+nodeId.getPort());
		this.fingerTable.set(idx, nodeId);
	}
	
	@Override
	public List<NodeID> getFingertable() throws SystemException, TException {
		// TODO Auto-generated method stub
		return fingerTable;
	}
	
	private int checkFileOwned(String key)
	{
		int result=-1;
		NodeID predNode = this.predecessor_node;
		String predkey = predNode.getId();
		String curkey = current_node.getId();
	//	System.out.println("predecessor    "+predecessor_node);
	//	System.out.println("current key    "+ current_node);
	//	System.out.println("filekey        "+ key);
		if(predkey.compareToIgnoreCase(curkey)<0)  //noremal case
		{
			if(key.compareToIgnoreCase(predkey)>0 && key.compareToIgnoreCase(curkey)<=0)
			{
				result =1;
				return result; 
			}
		}
		if(predkey.compareToIgnoreCase(curkey)>0) //exceptional case
		{
			if(key.compareToIgnoreCase(predkey)>0 && key.compareToIgnoreCase(curkey)>=0)
			{
				result =1;
			}
			if(key.compareToIgnoreCase(predkey)<0 && key.compareToIgnoreCase(curkey)<=0)
			{
				result =1;
			}
		}
			
		return result;
	}
	@Override
	public List<RFile> pullUnownedFiles() throws SystemException, TException {
		// TODO Auto-generated method stub
		String filename=null;
		RFile rfile=null;
		RFileMetadata rfmeta = null;
		NodeID predNode =null;
		predNode = this.predecessor_node;
		String predkey = predNode.getId();
		String curkey = current_node.getId();
		String file_key=null;
		String uname =null;
		List<String> pull_list_name = new ArrayList<String>();
		List<RFile> pull_list = new ArrayList<RFile>();
		Iterator<Entry<String, RFile>> entries = fileList.entrySet().iterator();
		while (entries.hasNext()) {
		    Entry<String, RFile> entry = entries.next();
		     filename =entry.getKey();
		     rfile=entry.getValue();
		     rfmeta =rfile.getMeta();
		     uname =rfmeta.getOwner();
		     
		     file_key = genSHA(uname,filename);
		     if(checkFileOwned(file_key)!=1)
		     {
		    	 pull_list_name.add(filename);
		    	 pull_list.add(rfile);
		    	 
		     }
		     
		}
		for(String k:pull_list_name)
	     {
	    	   	 fileList.remove(k);
	     }
		return pull_list;
	}
	@Override
	public void pushUnownedFiles(List<RFile> files) throws SystemException,
			TException {
		// TODO Auto-generated method stub
		String filename=null;
		RFile rfile=null;
		RFileMetadata rfmeta = null;
		NodeID succNode =null;
		String uname =null;
		int i=0;
		//System.out.println("All files in push files "+files);
		while(i<files.size())
		{
			rfile = files.get(i);
			if(rfile!=null)
			{	
				rfmeta = rfile.getMeta();
				uname = rfmeta.getFilename();
				System.out.println("file name got from pushed  ::::::::::  "+uname);
				fileList.put(uname, rfile);
			}
			i++;
		}
		
	}
	
	@Override
	public void join(NodeID nodeId) throws SystemException, TException {
		// TODO Auto-generated method stub
		
		int i;
		String filename;
		RFile rfile;
		RFileMetadata rfmeta = new RFileMetadata();
		ArrayList newfingerTable = new ArrayList<NodeID>(256);
		BigInteger t = BigInteger.valueOf(2);
		NodeID thispred=null;
		TTransport transport = null;
				
		TProtocol protocol = null;
		FileStore.Client client = null;
		TIOStreamTransport trans = null;
		TProtocol tpro = null;
		String temp_ip =null;
		int temp_port;
		int flag=-100;
		List<RFile> pulled_list = new ArrayList<RFile>();
	//	System.out.println(" Strting join");
		
		
		if(nodeId ==null)
		{
			//System.out.println("updating node when null");
		 for(i=0;i<256;i++)
	        {
	        	fingerTable.add(current_node);
	        //	System.out.println("fingertable["+i+"] = "+fingerTable.get(i).getPort());
	        }
		 setNodePred(current_node);
		}
		else
		{
			String nkey = nodeId.getId();
			String nip = nodeId.getIp();
		//	System.out.println("NIP:"+nip);
			int nport=nodeId.getPort();
			BigInteger p = new BigInteger(current_node.getId(),16);
			NodeID pred_node_succ=null;
			 transport = new TSocket(nip, nport);
			 transport.open();
			
			 protocol = new  TBinaryProtocol(transport);
		     client = new FileStore.Client(protocol);
		     trans = new TIOStreamTransport(System.out);
		  	 tpro = new TJSONProtocol.Factory().getProtocol(trans);
		     System.out.println("connecting to ::"+nport);
		  	 try{
			  	 for(i=0;i<256;i++)
			  	 {
			  		NodeID node =null;
			  		BigInteger two_power = t.pow(i);
			  		BigInteger sum = p.add(two_power);
			  		String nextkey = sum.toString(16);
			  		//System.out.println("here");
			  	//	System.out.println(current_node.getPort());
			  		//System.out.println("Find succ index:"+i);
			  		if(nport !=current_node.getPort())
			  		{
			  			//	System.out.println("yetoy");
			  		//	System.out.println("finding succ of nextkey"+nextkey);
			  			node = client.findSucc(nextkey); //////////////////////////////
			  		}
			  		else
			  		{
			  			node =this.findSucc(nextkey);
			  		}
			  	//	System.out.println("out of here with succ and pred");
			  	//System.out.println("next key ::::"+nextkey);
			  		fingerTable.set(i,node);			
			  	 }
		  	 }catch(SystemException e)
		  	 {
		  		 e.write(tpro);
		  	 }
		  	 transport.close();
		  	// System.out.println("------------------setting node predecessor----------");
			 thispred=findPred(current_node.getId());
			 this.setNodePred(thispred);
			 
			    NodeID sNode =fingerTable.get(0);
			 	temp_ip = sNode.getIp();
				temp_port =sNode.getPort();
				
					transport = new TSocket(temp_ip, temp_port);
					transport.open();
					protocol = new  TBinaryProtocol(transport);
				    client = new FileStore.Client(protocol);
				    trans = new TIOStreamTransport(System.out);
				  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
				  	 
				  	 try{
				  		 	if(temp_port != current_node.getPort())
				  		 	{		  		 		
				  		 		client.setNodePred(current_node);
				  		 	}
				  		 	transport.close();
				  	 	}
				  	 catch(SystemException e)
					  	{
					  		 e.write(tpro);
					  	 }
			 		 
		  	// System.out.println("AALOy");
		  	// System.out.println("the fingerTable");
		  	 //for(i=0;i<256;i++)
		  	// {
		  		// System.out.println("fingetTable[i]  ::"+fingerTable.get(i).getPort());
		  	 //}
		  	 // findind current node pred thispred
			 //	System.out.println(":::::::::::port"+thispred.getPort());
			 	p = new BigInteger(current_node.getId(),16);	
		  	 
		  	 for(i=0;i<256;i++)
		  	 {
		  		 BigInteger two_power = t.pow(i);
		  		 BigInteger new_p=p.subtract(two_power);
		  		 int sign =new_p.signum();
		  		 		  		 
		  		 if(sign<0)
		  		 {
		  			 BigInteger two_power256 = (t.pow(255)).subtract(BigInteger.valueOf(1));
		  			 new_p=new_p.add(two_power256);
		  		 }
		  		String key = new_p.toString(16);
		  		//System.out.println("secrch pred of key"+key);
		  		NodeID prednode = this.findPred(key);
		  		temp_ip = prednode.getIp();
				temp_port = prednode.getPort();
				transport = new TSocket(temp_ip, temp_port);
				transport.open();
				protocol = new  TBinaryProtocol(transport);
			    client = new FileStore.Client(protocol);
			    trans = new TIOStreamTransport(System.out);
			  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
			  	 
			  	 try{
			  		 if(temp_port != current_node.getPort())
			  		 {
				  	 pred_node_succ = client.getNodeSucc();
			  		 }
			  		 else
			  		 {
			  			 pred_node_succ = getNodeSucc();
			  		 }
				  	 }catch(SystemException e)
				  	 {
				  		 e.write(tpro);
				  	 }
			  	 	transport.close();	
			  	 		if((pred_node_succ.getId()).equals(key))
			  	 		{
			  	 			prednode =pred_node_succ;
			  	 		}
		  		 do{
		  				flag=-100;	 
		  			//	System.out.println("flag ="+flag);
		  				BigInteger p_pred = new BigInteger(prednode.getId(),16);
		  				BigInteger p_pred_plus = p_pred.add(two_power);
		  				String p_pred_plus_key = p_pred_plus.toString(16);
		  			//	System.out.println("final key:"+p_pred_plus_key);
		  				//System.out.println("pred key:"+thispred.getId());
		  				//System.out.println("current key:"+current_node.getId());
		  				if((p_pred_plus_key.compareToIgnoreCase(current_node.getId())<=0) &&(p_pred_plus_key.compareToIgnoreCase(thispred.getId())>0))
		  				{
		  					NodeID tempNode;
		  					//System.out.println("Condition true");
		  					flag=1;
		  					//System.out.println(flag);
		  					temp_ip = prednode.getIp();
			  				temp_port = prednode.getPort();
			  				if(current_node.getPort()!=temp_port)
			  				{
			  			//	System.out.println("port id::"+temp_port);
			  				transport = new TSocket(temp_ip, temp_port);
			  				transport.open();
			  				protocol = new  TBinaryProtocol(transport);
			  			    client = new FileStore.Client(protocol);
			  			    trans = new TIOStreamTransport(System.out);
			  			  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
			  			  //	System.out.println("updateing");
		  					client.updateFinger(i, current_node);
		  					//tempNode =client.findPred(prednode.getId());
		  					transport.close();	
			  				}
			  				
			  				else{
			  					updateFinger(i, current_node);
			  					tempNode=findPred(prednode.getId());
			  					
			  				}
			  				tempNode =findPred(prednode.getId());
		  					prednode =tempNode;
		  					 
			  			//	System.out.println("i :"+i+"port:: "+prednode.getPort());
			  			 // 	 System.out.println("                  key :"+prednode.getId());
			  			  	 
			  			     
			  			  	 //key =prednode.getId();
		  				}
		  				if((current_node.getId().compareToIgnoreCase(thispred.getId())<0))
		  				{
		  					if((p_pred_plus_key.compareToIgnoreCase(current_node.getId())<=0) &&(p_pred_plus_key.compareToIgnoreCase(thispred.getId())<0))
		  					{
		  						NodeID tempNode;
			  				//	System.out.println("Condition true");
			  					flag=1;
			  					//System.out.println(flag);
			  					temp_ip = prednode.getIp();
				  				temp_port = prednode.getPort();
				  				if(current_node.getPort()!=temp_port)
				  				{
				  			//	System.out.println("port id::"+temp_port);
				  				transport = new TSocket(temp_ip, temp_port);
				  				transport.open();
				  				protocol = new  TBinaryProtocol(transport);
				  			    client = new FileStore.Client(protocol);
				  			    trans = new TIOStreamTransport(System.out);
				  			  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
				  			  //	System.out.println("updateing");
			  					client.updateFinger(i, current_node);
			  					//tempNode =client.findPred(prednode.getId());
			  					transport.close();	
				  				}
				  				
				  				else{
				  					updateFinger(i, current_node);
				  					tempNode=findPred(prednode.getId());
				  					
				  				}
				  				tempNode =findPred(prednode.getId());
			  					prednode =tempNode;
			  					 
				  			//	System.out.println("i :"+i+"port:: "+prednode.getPort());
				  			  //	 System.out.println("                  key :"+prednode.getId());
				  			  	 
				  			     
		  						
		  					}
		  					if((p_pred_plus_key.compareToIgnoreCase(current_node.getId())>=0) &&(p_pred_plus_key.compareToIgnoreCase(thispred.getId())>0))
		  					{
		  						NodeID tempNode;
			  			//		System.out.println("Condition true");
			  					flag=1;
			  				//	System.out.println(flag);
			  					temp_ip = prednode.getIp();
				  				temp_port = prednode.getPort();
				  				if(current_node.getPort()!=temp_port)
				  				{
				  			//	System.out.println("port id::"+temp_port);
				  				transport = new TSocket(temp_ip, temp_port);
				  				transport.open();
				  				protocol = new  TBinaryProtocol(transport);
				  			    client = new FileStore.Client(protocol);
				  			    trans = new TIOStreamTransport(System.out);
				  			  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
				  			  //	System.out.println("updateing");
			  					client.updateFinger(i, current_node);
			  					//tempNode =client.findPred(prednode.getId());
			  					transport.close();	
				  				}
				  				
				  				else{
				  					updateFinger(i, current_node);
				  					tempNode=findPred(prednode.getId());
				  					
				  				}
				  				tempNode =findPred(prednode.getId());
			  					prednode =tempNode;
			  					 
				  			//	System.out.println("i :"+i+"port:: "+prednode.getPort());
				  			  //	 System.out.println("                  key :"+prednode.getId());
				  			  	 
				  			     
		  					}
		  				}
		  				else
		  				{
		  					flag=-100;
		  				}
		  				
		  			//}
		  		 }while(flag==1);
		  		 
		  		 
		  	 }
		  	 //--------------------------------------------------------
		   	
		}//end else
		NodeID pd =findPred(current_node.getId());
		//System.out.println("current node :::pred is   ::"+pd);
		setNodePred(pd);
		NodeID successorNode = getNodeSucc();
	//	System.out.println("setting the cureent node succ to :"+successorNode);
		temp_ip = successorNode.getIp();
		temp_port =successorNode.getPort();
		
			transport = new TSocket(temp_ip, temp_port);
			transport.open();
			protocol = new  TBinaryProtocol(transport);
		    client = new FileStore.Client(protocol);
		    trans = new TIOStreamTransport(System.out);
		  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
		  	 
		  	 try{
		  		 	if(temp_port != current_node.getPort())
		  		 	{		  		 		
		  		 		pulled_list =client.pullUnownedFiles();
		  		 	}
		  		 	transport.close();
		  	 	}
		  	 catch(SystemException e)
			  	{
			  		 e.write(tpro);
			  	 }
		  	 i=0;
		  	 for(i=0;i<pulled_list.size();i++)
		  	 {
		  		 RFile r = pulled_list.get(i);
		  		 RFileMetadata meta_data= r.getMeta();
		  		 String file_name = meta_data.getFilename();
		  		 fileList.put(file_name, r);
		  		// System.out.println("putting file name ::::::::  "+meta_data.getFilename());
		  	 }
		
		// System.out.println("printing");
		 // 	for(i=0;i<256;i++)
		  	// {
		  	// System.out.println("fingetTable[i]  ::"+fingerTable.get(i).getPort() + fingerTable.get(i).getId());
		  	// }
		  	 System.out.println("join complete");
	}
	
	
	@Override
	public void remove() throws SystemException, TException {
		// TODO Auto-generated method stub
	
		int i;
		String filename;
		RFile rfile;
		RFileMetadata rfmeta = new RFileMetadata();
		ArrayList newfingerTable = new ArrayList<NodeID>(256);
		BigInteger t = BigInteger.valueOf(2);
		NodeID thispred=null;
		int flag=-100;
		System.out.println(" Strating join");
		NodeID nodeId = current_node;
		 TTransport transport = null;
		 TProtocol protocol = null;
	     FileStore.Client client = null;
	     TIOStreamTransport trans = null;
	  	 TProtocol tpro = null;
	
		String nkey = nodeId.getId();
		String nip = nodeId.getIp();
		String temp_ip =null;
		int temp_port;
		//System.out.println("NIP:"+nip);
		int nport=nodeId.getPort();
		BigInteger p = new BigInteger(current_node.getId(),16);
		NodeID pred_node_succ=null;
		NodeID predecessor=this.predecessor_node;
		NodeID successorNode = getNodeSucc();
		//System.out.println("getting the cureent node succ to :"+successorNode);
		try{
			
			if((successorNode.getId()).compareToIgnoreCase(current_node.getId())==0)
			{
	 			SystemException e = new SystemException();
	 			e.setMessage("only one node in chord cannot delete");
	 			throw e;
			}
		}catch(SystemException e)
		{
			throw e;
		}
			
		
	     System.out.println("connecting to ::"+nport);
	
	     List<String> remove_file_name = new ArrayList<String>();
	     List<RFile> remove_list =new ArrayList<RFile>();
	     String file_key=null;
	     String uname =null;
	     String f=null;
			Iterator<Entry<String, RFile>> entries = fileList.entrySet().iterator();
			while (entries.hasNext()) {
			    Entry<String, RFile> entry = entries.next();
			     filename =entry.getKey();
			     rfile=entry.getValue();
			     if(rfile!=null)
			     {
				     rfmeta =rfile.getMeta();
				     uname =rfmeta.getOwner();
				     f =rfmeta.getFilename();
				     //file_key = genSHA(uname, filename);
				     remove_list.add(rfile);
				     remove_file_name.add(f);
				    
			     }
			}
			     /*	for(i=0;i<remove_file_name.size();i++)
			     	{
			     		f= remove_file_name.get(i);
			     		 fileList.remove(f);
			     		System.out.println("file list deletd ::::::::::::"+remove_list.get(i));
			     	}
			     	*/
			System.out.println("Removing:::::"+remove_list.size());
			     	successorNode =fingerTable.get(0);
			  		temp_ip = successorNode.getIp();
			  		temp_port =successorNode.getPort();
			  		
			  			transport = new TSocket(temp_ip, temp_port);
						transport.open();
						protocol = new  TBinaryProtocol(transport);
					    client = new FileStore.Client(protocol);
					    trans = new TIOStreamTransport(System.out);
					  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
					  	 
					  	 try{
					  		 	if(temp_port != current_node.getPort())
					  		 	{
					  		 		client.pushUnownedFiles(remove_list);
					  		 	}
					  		 
					  	 	}
					  	 catch(SystemException e)
						  	{
						  		 e.write(tpro);
						  	 }
					 	transport.close();
					  	 
	     
		BigInteger two_power;
		BigInteger sum;
		String nextkey=null;
	//--------------------------------------------------------------------------------
	  
	 //------------------------------------------------------------------------------ 		
		
		 // 	 System.out.println("AALOy");
			thispred=findPred(current_node.getId());
			  	
			this.setNodePred(thispred);
		 // 	System.out.println("AALOy");
		 // 	System.out.println("the fingerTable");
		  	// for(i=0;i<256;i++)
		  //	 {
		  		// System.out.println("fingetTable[i]  ::"+fingerTable.get(i).getPort());
		  	// }
		  	 // findind current node pred thispred
			// 	System.out.println(":::::::::::port"+thispred.getPort());
			 	p = new BigInteger(current_node.getId(),16);	
		  	 
		  	 for(i=0;i<256;i++)
		  	 {
		  		 two_power = t.pow(i);
		  		 BigInteger new_p=p.subtract(two_power);
		  		 int sign =new_p.signum();
		  		 		  		 
		  		 if(sign<0)
		  		 {
		  			 BigInteger two_power256 = (t.pow(255)).subtract(BigInteger.valueOf(1));
		  			 new_p=new_p.add(two_power256);
		  		 }
		  		String key = new_p.toString(16);
		  	//	System.out.println("secrch pred of key"+key);
		  		NodeID prednode = this.findPred(key);
		  		temp_ip = prednode.getIp();
				temp_port = prednode.getPort();
				transport = new TSocket(temp_ip, temp_port);
				transport.open();
				protocol = new  TBinaryProtocol(transport);
			    client = new FileStore.Client(protocol);
			    trans = new TIOStreamTransport(System.out);
			  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
			  	 
		  	 try{
		  		 if(temp_port != current_node.getPort())
		  		 {
			  	 pred_node_succ = client.getNodeSucc();
		  		 }
		  		 else
		  		 {
		  			 pred_node_succ = getNodeSucc();
		  		 }
			  	 }catch(SystemException e)
			  	 {
			  		 e.write(tpro);
			  	 }
		  	 	transport.close();	
		  	 		if((pred_node_succ.getId()).equals(key))
		  	 		{
		  	 			prednode =pred_node_succ;
		  	 		}
		  		 do{
		  				flag=-100;	 
		  				//System.out.println("flag ="+flag);
		  				BigInteger p_pred = new BigInteger(prednode.getId(),16);
		  				BigInteger p_pred_plus = p_pred.add(two_power);
		  				String p_pred_plus_key = p_pred_plus.toString(16);
		  			//	System.out.println("final key:"+p_pred_plus_key);
		  			//	System.out.println("pred key:"+thispred.getId());
		  			//	System.out.println("current key:"+current_node.getId());
		  				NodeID setsuccNode = getNodeSucc();
		  			//	System.out.println("succ of current node"+setsuccNode.getId());
		  				if((p_pred_plus_key.compareToIgnoreCase(current_node.getId())<=0) &&(p_pred_plus_key.compareToIgnoreCase(thispred.getId())>0))
		  				{
		  					NodeID tempNode;
		  					//System.out.println("Condition true");
		  					flag=1;
		  					//System.out.println(flag);
		  					temp_ip = prednode.getIp();
			  				temp_port = prednode.getPort();
			  				if(current_node.getPort()!=temp_port)
			  				{
			  				//System.out.println("port id::"+temp_port);
			  				transport = new TSocket(temp_ip, temp_port);
			  				transport.open();
			  				protocol = new  TBinaryProtocol(transport);
			  			    client = new FileStore.Client(protocol);
			  			    trans = new TIOStreamTransport(System.out);
			  			  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
			  			 // 	System.out.println("updateing");
			  			  	
		  					client.updateFinger(i, setsuccNode);
		  					
		  					transport.close();	
			  				}
			  				else{
			  					updateFinger(i, setsuccNode);
			  					tempNode=findPred(prednode.getId());
			  					
			  				}
			  				tempNode =findPred(prednode.getId());
		  					prednode =tempNode;
		  					 
			  				// System.out.println("i :"+i+"port:: "+prednode.getPort());
			  			  	 //System.out.println("                  key :"+prednode.getId());
			  			  	 	  			     
			  			  	 //key =prednode.getId();
		  				}
		  				if((current_node.getId().compareToIgnoreCase(thispred.getId())<0))
		  				{
		  					if((p_pred_plus_key.compareToIgnoreCase(current_node.getId())<=0) &&(p_pred_plus_key.compareToIgnoreCase(thispred.getId())<0))
		  					{
		  						NodeID tempNode;
			  				//	System.out.println("Condition true");
			  					flag=1;
			  					//System.out.println(flag);
			  					temp_ip = prednode.getIp();
				  				temp_port = prednode.getPort();
				  				if(current_node.getPort()!=temp_port)
				  				{
				  				//System.out.println("port id::"+temp_port);
				  				transport = new TSocket(temp_ip, temp_port);
				  				transport.open();
				  				protocol = new  TBinaryProtocol(transport);
				  			    client = new FileStore.Client(protocol);
				  			    trans = new TIOStreamTransport(System.out);
				  			  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
				  			  //	System.out.println("updateing");
				  			  	
			  					client.updateFinger(i, setsuccNode);
			  					
			  					transport.close();	
				  				}
				  				else{
				  					updateFinger(i, setsuccNode);
				  					tempNode=findPred(prednode.getId());
				  					
				  				}
				  				tempNode =findPred(prednode.getId());
			  					prednode =tempNode;
			  					 
				  				// System.out.println("i :"+i+"port:: "+prednode.getPort());
				  			  	 //System.out.println("                  key :"+prednode.getId());
				  			  	 	  			     
		  					}
		  					if((p_pred_plus_key.compareToIgnoreCase(current_node.getId())>=0) &&(p_pred_plus_key.compareToIgnoreCase(thispred.getId())>0))
		  					{
		  						NodeID tempNode;
			  					//System.out.println("Condition true");
			  					flag=1;
			  					//System.out.println(flag);
			  					temp_ip = prednode.getIp();
				  				temp_port = prednode.getPort();
				  				if(current_node.getPort()!=temp_port)
				  				{
				  				//System.out.println("port id::"+temp_port);
				  				transport = new TSocket(temp_ip, temp_port);
				  				transport.open();
				  				protocol = new  TBinaryProtocol(transport);
				  			    client = new FileStore.Client(protocol);
				  			    trans = new TIOStreamTransport(System.out);
				  			  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
				  			  //	System.out.println("updateing");
				  			  	
			  					client.updateFinger(i, setsuccNode);
			  					
			  					transport.close();	
				  				}
				  				else{
				  					updateFinger(i, setsuccNode);
				  					tempNode=findPred(prednode.getId());
				  					
				  				}
				  				tempNode =findPred(prednode.getId());
			  					prednode =tempNode;
			  					 
				  				// System.out.println("i :"+i+"port:: "+prednode.getPort());
				  			  	// System.out.println("                  key :"+prednode.getId());
				  			  	 	  			     
		  					}
		  					  					
		  				}
		  				else
		  				{
		  					flag=-100;
		  				}
		  		 }while(flag==1); 
		  	 }
		  	//--------------------------setting predecessor of successor-------------
				NodeID sucessor = fingerTable.get(0);
		  		temp_ip =sucessor.getIp();
		  		temp_port = sucessor.getPort();
		  		transport = new TSocket(temp_ip, temp_port);
					transport.open();
					protocol = new  TBinaryProtocol(transport);
				    client = new FileStore.Client(protocol);
				    trans = new TIOStreamTransport(System.out);
				  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
				  	 
				  	 try{
				  		 if(temp_port != current_node.getPort())
				  		 {
					  	 client.setNodePred(predecessor_node);
				  		 }
					  	 }catch(SystemException e)
					  	 {
					  		 e.write(tpro);
					  	 }
				  	 	transport.close();	
				  	 		  		
		  		
	  	
		// System.out.println("printing");
		  //	for(i=0;i<256;i++)
		  //	 {
		  //	 System.out.println("fingetTable[i]  ::"+fingerTable.get(i).getPort() + fingerTable.get(i).getId());
		 // 	 }
		  	 System.out.println("deleted");
		 // 	 System.out.println("printing file list--------");
		  	 //System.out.println(fileList);
		  	 
	}
	
	/*
	    @Override
		public void setFingertable(List<NodeID> node_list) throws TException {
			// TODO Auto-generated method stub
			fingerTable = node_list;
		
			//for(NodeID f_table :fingerTable)
			//System.out.println(f_table);
		}
	*/
	
	private NodeID checkFingerRange(String Key)throws SystemException, TException{
		TTransport transport=null;
		TProtocol protocol=null;
		TIOStreamTransport trans =null;
		TProtocol tpro  =null;
		NodeID return_node=null; 
		NodeID temp = null;
		String temp_ip = null;
		String temp_id = null;
		int temp_port;
		NodeID firstnode = fingerTable.get(0);
		String firstkey = firstnode.getId();
		
		NodeID secondnode = fingerTable.get(1);
		String secondkey = secondnode.getId();
		String curkey = current_node.getId();
		int i=0;
		int j=i+1;
		
		for(i=0;i<fingerTable.size()-1;i++)
		{
			j=i+1;
			firstnode = fingerTable.get(i);
			firstkey = firstnode.getId();
			secondnode = fingerTable.get(j);
			secondkey = secondnode.getId();
			if(j==fingerTable.size())
			{
				break;
			}
			if(Key.equals(firstkey))
			{
			}	
			if((firstkey.compareToIgnoreCase(secondkey)>0) && (secondkey.compareToIgnoreCase(Key)>=0))
			{
				temp = fingerTable.get(i);
				temp_ip = temp.getIp();
				temp_id = temp.getId();
				temp_port = temp.getPort();
			//	System.out.println("in finger exceptional case smaller case");
			/*
			if(temp_port!=current_node.port)
			{
			transport = new TSocket(temp_ip, temp_port);
			transport.open();
			
			protocol = new  TBinaryProtocol(transport);
		     FileStore.Client client = new FileStore.Client(protocol);
		    	
		    trans = new TIOStreamTransport(System.out);
		  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
		  	 
		  	 try{
		  		 
			  	 return_node = client.findPred(Key);
		  		 
			  	 }catch(SystemException e)
			  	 {
			  		 e.write(tpro);
			  	 }
		  	 transport.close();
		
			return return_node;
			}
			else{
				return_node=findPred(Key);
			}*/
				return firstnode;
		}	
		if(firstkey.compareToIgnoreCase(secondkey)>0 && firstkey.compareToIgnoreCase(Key)<=0)
		{
		//	System.out.println("in finger exceptional case greater");
		/*	temp = fingerTable.get(i);
			temp_ip = temp.getIp();
			temp_id = temp.getId();
			temp_port = temp.getPort();
			
			if(temp_port!=current_node.port)
			{
			transport = new TSocket(temp_ip, temp_port);
			transport.open();
			
			protocol = new  TBinaryProtocol(transport);
		     FileStore.Client client = new FileStore.Client(protocol);
		    	
		    trans = new TIOStreamTransport(System.out);
		  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
		  	 
		  	 try{
		  	  	 return_node = client.findPred(Key);
		  	  	 }catch(SystemException e)
			  	 {
			  		 e.write(tpro);
			  	 }
		  	 transport.close();
		  	 return return_node;
		   }
		   else{
				return_node =findPred(Key);
			 }
			 */
			return_node = firstnode;
			return firstnode;
		}
		if (Key.compareToIgnoreCase(firstkey)>0 && Key.compareToIgnoreCase(secondkey)<=0)
		{
			temp = fingerTable.get(i);
			temp_ip = temp.getIp();
			temp_id = temp.getId();
			temp_port = temp.getPort();
			
				if(temp_port!=current_node.port)
				{
				transport = new TSocket(temp_ip, temp_port);
				transport.open();
				
				protocol = new  TBinaryProtocol(transport);
			     FileStore.Client client = new FileStore.Client(protocol);
			    	
			    trans = new TIOStreamTransport(System.out);
			  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
		  	 
		  	 	try{
		  		 	return_node = client.findPred(Key);
			  	 	}
		  	 		catch(SystemException e)
		  	 		{
			  		 e.write(tpro);
			  	 	}
		  	 		
				}
				else
					{
					return_node = this.findPred(Key);
					}
				transport.close();
				return return_node;
		}
	   
		}
	//System.out.println("no case last node finger table");
			temp = fingerTable.get(fingerTable.size()-1);
			temp_ip = temp.getIp();
			temp_id = temp.getId();
			temp_port = temp.getPort();
		//	System.out.println("last node ::"+ temp_id);
				
				if(temp_port!=current_node.port)
				{
				transport = new TSocket(temp_ip, temp_port);
				transport.open();
				
				protocol = new  TBinaryProtocol(transport);
			     FileStore.Client client = new FileStore.Client(protocol);
			    	
			    trans = new TIOStreamTransport(System.out);
			  	tpro = new TJSONProtocol.Factory().getProtocol(trans);
			  	 
			  	 try{
			  		 	return_node = client.findPred(Key);
				  	 }
			  	 catch(SystemException e)
				  	 {
				  		 e.write(tpro);
				  	 }
				transport.close();
				}
				else{
					return_node =findPred(Key);
				}
				
		return(return_node);
	}
				
		@Override
		public NodeID findPred(String key) throws SystemException, TException {
			// TODO Auto-generated method stub
		
		//System.out.println("Check o/p :prede");
		NodeID temp ;
		NodeID node = null;
		String temp_ip;
		String temp_id;
		int temp_port;
		String firstKey = fingerTable.get(0).getId();
		//System.out.println(firstKey);
		//System.out.println("caseFlag::."+ caseFlag);
		//System.out.println("---------------------------------------------------//////");
		//System.out.println("finding pred of "+key);
		
		if((firstKey.compareToIgnoreCase(key)==0))
				{
					return current_node;
				}
		if((current_node.getId()).compareToIgnoreCase(firstKey)==0)
		{
			//System.out.println("here in fin pred");
			return current_node;
		}
		 if((current_node.getId()).compareToIgnoreCase(firstKey)>=0)
		{
			if((current_node.getId()).compareToIgnoreCase(key)<0)
			{
				node =current_node;
			    return current_node;
			}
			else if(firstKey.compareToIgnoreCase(key)>=0)
			{
				//System.out.println("here..............");
				node=current_node;
			   return current_node;
			}
		}
		 if(current_node.getId().compareToIgnoreCase(firstKey)<0)
		{		
				if(key.compareToIgnoreCase(current_node.getId())>0 && key.compareToIgnoreCase(firstKey)<=0)
				{
				node =current_node;
			//	System.out.println("noremal..........case");
				return current_node;
				}
		}
		
		 node = checkFingerRange(key);
		// System.out.println(" nothing::::::::::::::");
		 return node;
		
		 	
	}
	
	@Override
	public NodeID findSucc(String key) throws SystemException, TException {
		// TODO Auto-generated method stub
		NodeID node ;
		NodeID node_succ = null;
		if(current_node.id.equals(key))
		{
			//System.out.println("current node = key..........");
			return current_node;
		}
		else
		{
			//System.out.println("in the find succ :: fin suuc of key"+key);
			node = findPred(key);
			//System.out.println("here in the find succ:retured"+node.getId());
		//	System.out.println("Returned:"+node.getPort());
			NodeID succNode =fingerTable.get(0);
			String succkey =succNode.getId();
			String curkey = current_node.getId();
			
			if(node.getId().equals(key))
			{
				return node;
			}
			
		/*	if(current_node.getId().compareToIgnoreCase(succkey)>0)
			{
				if(curkey.compareTo(key)<0 && succkey.compareTo(key)<0)
				{    
					return succNode;
				}
				if(curkey.compareTo(key)>0 && succkey.compareTo(key)>0)
				{
					return succNode;
				}
			}*/
			if(node.getPort() == this.portno)
			{
			//	System.out.println("port of peredecessor = port of client request node");
				node_succ = this.getNodeSucc();
			}
			else
			{
				String temp_ip = node.getIp();
				String temp_id = node.getId();
				int temp_port = node.getPort();
				//System.out.println("hopping to new clientimpl:"+ temp_ip);
			//	System.out.println(" portno :"+ temp_port);
				//System.out.println("node id:"+ temp_id);
				try{
				TTransport transport = new TSocket(temp_ip, temp_port);
				transport.open();
				
				 TProtocol protocol = new  TBinaryProtocol(transport);
			     FileStore.Client client = new FileStore.Client(protocol);
			    	
			     TIOStreamTransport trans = new TIOStreamTransport(System.out);
			  	 TProtocol tpro = new TJSONProtocol.Factory().getProtocol(trans);
			  	 
				if(temp_port != current_node.port)
				{
				node_succ = client.getNodeSucc();
				}
				else
				{
				node_succ = this.getNodeSucc();	
				}
				//System.out.println("succ node returned ::::::::"+node_succ.id);
			  	//System.out.println("  and ip port ::"+node.ip +": "+ node_succ.port);
				transport.close();
				}    catch(SystemException e)
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
		}
		return node_succ;
	}
	
	@Override
	public NodeID getNodeSucc() throws SystemException, TException {
		// TODO Auto-generated method stub
			NodeID node =null;
			node =this.fingerTable.get(0);
			return node;
		}
	
	}
	
