package com.rishi;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

import chord.FileStore;
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
      handler = new FileStoreImpl();
      processor = new FileStore.Processor<FileStoreImpl>(handler);

      Runnable simple = new Runnable() {
        public void run() {
          simple(processor,port);
        }
      };      
     
      new Thread(simple).start();
     
    } catch (Exception x) {
      x.printStackTrace();
    }
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