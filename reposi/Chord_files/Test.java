package com.rishi;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.rishi.TestServer;
import chord.DHTNode;
import chord.NodeID;
import chord.SystemException;

public class Test {
	
	static int totalCorrectSucc;
	static int avgCorrSucc;
	static int totalfindSuccCalls;
	public static void main(String[] args){
		if(args.length!=3){
			System.out.println("Please enter valid arguments");
			System.exit(0);
		}
		Map<Integer,FingerTable> compareFingers = null;
		List<Integer> portList = null;
		FingerCheck fingerCheck = null;
		int startPort = Integer.parseInt(args[0]);
		int intervalR = Integer.parseInt(args[1]);
		int noOfServers = Integer.parseInt(args[2]);
		
		portList = new ArrayList<Integer>();
		for(int i = 0; i<noOfServers; i++){
			portList.add(startPort+i);
		}
		compareFingers = new HashMap<Integer,FingerTable>();
		fingerCheck = new FingerCheck(portList);
		compareFingers = fingerCheck.createFingerTables();
		
		TestServer server = null;
		//Map<Integer,List<NodeID>> fingerTable = new HashMap<Integer,List<NodeID>>();
		for(int i = 0;i<noOfServers;i++){
			server  = new TestServer(startPort+i,intervalR);
			server.startServer();
			System.out.println("Started server:"+(startPort+i));
		}
		
		try {
			TimeUnit.SECONDS.sleep(10);
			ConcurrentJoin concurrJoin = new ConcurrentJoin();
			
			for(int i = 1;i<noOfServers;i++){
				concurrJoin.join(startPort,startPort+i);
				System.out.println("Node joined:"+(startPort+i));
			}
			TimeUnit.MILLISECONDS.sleep(700);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		testResults(noOfServers,portList,compareFingers);
				
	}

	private static void testResults(int noOfServers, List<Integer> portList,
			Map<Integer, FingerTable> compareFingers) {
		
		int portNumber = 0;
		TTransport transport = null;
		TProtocol protocol = null;
		DHTNode.Client client = null;
	
		Map<Integer, FingerTable> currentMap = new HashMap<Integer,FingerTable>();
		FingerTable currentNode = null;
		List<NodeID> currFingerList = null;
		NodeID succNode = null;
		NodeID predNode = null;
		
		String ip=null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		for(int j=0;j<7000;j++){
			
			for(int i = 0;i<noOfServers;i++){
				
				try {
					portNumber = portList.get(i);
					System.out.println("Connecting to port:"+portNumber);
					
					transport = new TSocket(ip,portNumber);
					protocol = new TBinaryProtocol(transport);
					transport.open();
					client = new DHTNode.Client(protocol);
					currFingerList = client.getFingertable();
					predNode = client.getNodePred();
					succNode= client.getNodeSucc();
					currentNode= new FingerTable();
					currentNode.setFingers(currFingerList);
					currentNode.setIndex(portNumber);
					currentNode.setPredecessor(predNode);
					currentNode.setSuccessor(succNode);
					currentMap.put(portNumber, currentNode);
					System.out.println("got fingertable for port:"+(portNumber));
					transport.close();
					
					
				} catch (TTransportException e) {
					e.printStackTrace();
				} catch (SystemException e) {
					e.printStackTrace();
				} catch (TException e) {
					e.printStackTrace();
				} 
			}//loop end
			computeResults(currentMap,compareFingers,portList);
			try {
				TimeUnit.MILLISECONDS.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//outer loop end

		
	}

	private static void computeResults(Map<Integer, FingerTable> currentMap,
			Map<Integer, FingerTable> compareFingers, List<Integer> portList) {
		int numberOfCorrectSucc = 0;
		int numberOfCorrectPred = 0;
		int numberOfCorrectEntries = 0;
		int numberOfInCorrectEntries= 0;
		NodeID checkSuccNode = null;
		NodeID checkPredNode = null;
		NodeID correctSuccNode = null;
		NodeID correctPredNode = null;
		int correct = 0;
		FingerTable checkNode = null; // current finger to compute results
		FingerTable correctNode = null; //correct finger table
		List<Integer> fingerEntries = new ArrayList<Integer>();
		List<NodeID> currentFingerTable = null;
		List<NodeID> correctFingerTable = null;
		int i=0;
		for(i=0;i<currentMap.size();i++){
			checkNode = currentMap.get(portList.get(i));
			correctNode = compareFingers.get(portList.get(i));
			
			checkSuccNode = checkNode.getSuccessor();
			correctSuccNode = correctNode.getSuccessor();
			//Check Successor node
			correct = checkSuccessor(checkSuccNode,correctSuccNode);
			//If same increment number of correct successors
			if(correct==1){
				numberOfCorrectSucc++;
			}
			correct=0;
			checkPredNode = checkNode.getPredecessor();
			correctPredNode = correctNode.getPredecessor();
			correct = checkPredecessor(checkPredNode,correctPredNode);
			//If same increment number of correct predecessors
			if(correct==1){
				numberOfCorrectPred++;
			}
			
			currentFingerTable = checkNode.getFingers();
			correctFingerTable = correctNode.getFingers();
			fingerEntries = checkFingerTable(currentFingerTable,correctFingerTable);
			numberOfCorrectEntries = numberOfCorrectEntries+fingerEntries.get(0);
			numberOfInCorrectEntries = numberOfInCorrectEntries + fingerEntries.get(1);
		}
		
		System.out.println("Correct Successors:"+numberOfCorrectSucc);
		System.out.println("Correct Predecessors:"+numberOfCorrectPred);
		System.out.println("Correct Finger Entries:"+numberOfCorrectEntries);
		System.out.println("InCorrect Finger Entries:"+numberOfInCorrectEntries);
	}

	private static List<Integer> checkFingerTable(
			List<NodeID> currentFingerTable, List<NodeID> correctFingerTable) {
		int i=0;
		int inCorrectEntries =0;
		int correctEntries = 0;
		NodeID currentNode = null;
		NodeID correctNode = null;
		List<Integer> fingerEntries = new ArrayList<Integer>();
		int currentPort = 0;
		int correctPort = 0;
		int counter =0;
		for(i=0;i<currentFingerTable.size();i++){
			counter++;
			currentNode = currentFingerTable.get(i);
			currentPort = currentNode.getPort();
			correctNode = correctFingerTable.get(i);
			correctPort = correctNode.getPort();
			
			if((currentPort!=0)&& (currentPort==correctPort)){
				correctEntries++;
			}
			else if((currentPort!=0)&& (currentPort!=correctPort)){
				inCorrectEntries++;
			}
		}
		System.out.println("Counter:"+counter);
		fingerEntries.add(correctEntries);
		fingerEntries.add(inCorrectEntries);
		
		return fingerEntries;
	}

	private static int checkSuccessor(NodeID checkSuccNode,
			NodeID correctSuccNode) {
		System.out.println("Check Node in check Successor+:"+checkSuccNode.getPort());
		if(checkSuccNode.getPort()==correctSuccNode.getPort()){
			System.out.println("Suceessor:"+checkSuccNode.getPort()+":"+correctSuccNode.getPort());
			return 1;
		}
		return 0;
	}
	private static int checkPredecessor(NodeID checkPredNode,
			NodeID correctPredNode) {
		System.out.println("Check Node in check Predecessor+:"+checkPredNode.getPort());
		if(checkPredNode.getPort()==correctPredNode.getPort()){
			System.out.println("Predecessor:"+checkPredNode.getPort()+":"+correctPredNode.getPort());
			return 1;
		}
		return 0;
	}
}
