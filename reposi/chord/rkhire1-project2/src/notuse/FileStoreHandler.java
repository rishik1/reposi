
package notuse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.thrift.TException;
import org.omg.PortableInterceptor.SUCCESSFUL;

public class FileStoreHandler implements FileStore.Iface {
	private String filename;
	private BufferedReader buff;
	private HashMap<String, RFile> fileList;	
	File folder = new File("./serverdir");
	
	public FileStoreHandler()
	{
	         fileList = new HashMap<String,RFile>();
	         File file = new File("./serverdir");
	         for(File f: file.listFiles()){
	        	 if(f.isFile()){
	        		 f.delete();
	        	 }
	         }
	} 

    public List<RFileMetadata> listOwnedFiles(String user) throws SystemException, org.apache.thrift.TException
    {
    	List<RFileMetadata> subList = new ArrayList<RFileMetadata>();
    	
    	for (Map.Entry<String, RFile> entry : fileList.entrySet()) {
    	    String filename = entry.getKey();
    	    RFile value = entry.getValue();
    	    if(value.meta.owner.equals(user)&&(value.getMeta().getDeleted()==0))
    	    {
    	    	subList.add(value.meta);
    	    }
    	}
    	
    	if(subList.isEmpty())
    	{
    		SystemException s=new SystemException();
    		s.setMessage("NO List for the owner found");
    		throw s;
    	}
    	return subList;
   }

    public StatusReport writeFile(RFile rFile) throws SystemException, org.apache.thrift.TException{
    	File[] listOfFiles = folder.listFiles();
    	StatusReport state = new StatusReport();
    	state.setStatus(Status.FAILED);
    	RFileMetadata data = rFile.getMeta();
    	String name= rFile.getMeta().getFilename();
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
    	                state.setStatus(Status.SUCCESSFUL);             
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
                //rFile.meta.setFilename(filename);
                //rFile.meta.setOwner(rFile.getMeta().getOwner());
                rFile.meta.setVersion(0);
                rFile.meta.setUpdated(System.currentTimeMillis());
                rFile.meta.setContentLength(rFile.getContent().length());
                rFile.meta.setDeleted(0);
                fileList.put(rFile.meta.getFilename(), rFile);
                state.setStatus(Status.SUCCESSFUL);
                
    	    }
    	
    if(state.equals(Status.FAILED))
    {
    	SystemException s=new SystemException();
		s.setMessage("writing to server failed");
		throw s;
    }
    
return state;
}

    public RFile readFile(String filename, String owner) throws SystemException, org.apache.thrift.TException{

    	File[] listOfFiles = folder.listFiles();
    	    	
    	if(fileList.get(filename)!=null)
    	{
    	for (Map.Entry<String, RFile> entry : fileList.entrySet())
    	   {
    	    String fname = entry.getKey();
    	    RFile value = entry.getValue();
    	    if((value.getMeta().owner.equals(owner))&& (value.getMeta().deleted==0))
    	    {
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
    	
    	return null;
 	    	
}

    public StatusReport deleteFile(String filename, String owner) throws SystemException, org.apache.thrift.TException{
   
    	File[] listOfFiles = folder.listFiles();
    	StatusReport state = new StatusReport();
    	state.setStatus(Status.FAILED);
    	RFile rFile ;
    	if((rFile=fileList.get(filename))!=null){
    		File file= new File("./server/"+filename);
    		file.delete();
    		rFile.getMeta().setDeleted(System.currentTimeMillis());
            rFile.getMeta().setUpdated(System.currentTimeMillis());
            rFile.getMeta().setContentLength(rFile.getContent().length());
            rFile.setContent(null);
            state.setStatus(Status.SUCCESSFUL);
    	}
    	if(state.equals(Status.FAILED))
    	{
    		SystemException s=new SystemException();
    		s.setMessage("deleting from server failed");
    		throw s;
    	}
    	return state;
//    	if(fileList.get(filename)!=null)
//    	{
//    	for (Map.Entry<String, RFile> entry : fileList.entrySet())
//    	   {
//    	    String fname = entry.getKey();
//    	    RFile value = entry.getValue();
//    	    if(value.getMeta().owner.equals(owner))
//    	    {
//    	    	for(int i = 0; i < listOfFiles.length; i++)
//    	    	{
//    	    	    String f = listOfFiles[i].getName();
//    	    	    if(f.equals(filename))
//    	    	       {
//    	    	    	int ver =value.getMeta().getVersion();
//    	    	    	try{
//    	    	    	listOfFiles[i].delete();
//    	    	    	} 
//    	    	    	catch (Exception e) {
//							// TODO Auto-generated catch block
//    	    	    		System.err.println("error on deletion of non existsant file");
//							e.printStackTrace();
//						}
//    	    	    	
//    	                value.getMeta().setDeleted(System.currentTimeMillis());
//    	                value.getMeta().setUpdated(System.currentTimeMillis());
//    	                value.getMeta().setContentLength(value.getContent().length());
//    	                value.setContent(null);
//    	                state.setStatus(Status.SUCCESSFUL);   
//    	                break;
//    	    	        }
//   	            }
//    	    	
//    	     }
//    	    }
//    	}
   	
    	

}

}

