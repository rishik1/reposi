package taskmanager.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import taskmanager.worker.Performace_Observer;
import taskmanager.worker.Process_Observer;
import taskmanager.worker.Subject;
import taskmanager.worker.User_Observer;

/**
 * FReader class is use to open the file 
 * and read the file line by line
 * @author aashay-Rishikesh
 *
 */
public class FReader {

	private String filename;
	private BufferedReader buff;
	/**
	 * FReader constructor takes in the name of the file to be opened
	 * set the file name to private string varibale 
	 * @param name
	 */
	public FReader(String name)
	{
		Logger.dump(2, "IN FileReader constructor");
		this.filename=name;
		this.buff=null;
	}

/**
 * OpenFile method is used to open the file
 * create BufferedReader and FileReader to create stream to  read from file
 */
	public void Openfile()
	{
		try{
			this.buff=new BufferedReader(new FileReader(this.filename)); 
		}
		catch(FileNotFoundException e){
			Logger.dump(0, "FILE dosen't exist");
			System.out.println("File dosen't exist");
			System.exit(1);	
		}
		finally{
		}
	}


/**
 * read function used to read from the file send by the Driver
 * It create the object of the subject i.e taskManager
 * It also creates the object of 3 observers Process_Observer,Performance_Observer and User_Observer
 * and send the object of the subject to them
 * Then we will read the file till end of the file line by line
 * line read will be splitted by space to get the different tab's structure
 * Then each tab structure has the tab index as the first character which is splitted from rest of tab stucture
 * Choice is set and respective tab update_String method callled and subject is notified
 */
	public void read()
	{

		String s=null;
		String[] s1=new String[5];
		String[] s2 = new String[5];
		String s3=null;
		int choice;
		int cnt=0;
		Subject sub=new Subject();
		Process_Observer pro_tab1=new Process_Observer(sub);
		Performace_Observer perf_tab2=new  Performace_Observer(sub);
		User_Observer user_tab3=new  User_Observer(sub);


		try {

			while(buff.ready())
			{

				try {
					s=buff.readLine();


				} catch (IOException e) {
					Logger.dump(0, "Exception in eading File");
					System.err.println("Exception while reading file");
					System.exit(0);
				}

				s2=s.split(" ");
				int count = s.length() - s.replace(" ", "").length();
				

				for(int i=0;i<count+1;i++)
				{
					s1 = s2[i].split("-");
					choice=Integer.parseInt(s1[0]);

					switch(choice)
					{
					case 1:
						sub.setTab_Name("tab1");
						sub.setUpdate_String(s1[1]);
						break;

					case 2:
						sub.setTab_Name("tab2");	
						sub.setUpdate_String(s1[1]);
						break;

					case 3:
						sub.setTab_Name("tab3");
						sub.setUpdate_String(s1[1]);
						break;

					}//switch
				}//for

			}//while	


			Logger.dump(1," Result is:");

			//try


		} catch (IOException e) {
			Logger.dump(0, "Buffer not ready exception");
			System.out.println("Buffer not ready exception");
			System.exit(0);
		}


		


	}


}

