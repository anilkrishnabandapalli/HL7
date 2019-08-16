package com.hl7.firstPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hl7.service.Hl7MessageHandler;

public class HL7TCPServer {

 
	public void startServer(ServerSocket serverSocket, Hl7MessageHandler hl7MessageHandler) {
        final ExecutorService clientRequestPool = Executors.newFixedThreadPool(10);

        Runnable hl7ProcessingTask = new Runnable() {
            @Override
            public void run() {
                try {
                    
                    System.out.println("Waiting for eCW Client to connect...");
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        clientRequestPool.submit(new HL7Runnable(clientSocket, hl7MessageHandler));
                    }
                } catch (IOException e) {
                    System.err.println("Unable to process ceCW Client request");
                    e.printStackTrace();
                }
            }
        };
        Thread hl7ServerThread = new Thread(hl7ProcessingTask);
        hl7ServerThread.start();

    }
    
    public static void main (String [] args) {
    	
    	//new HL7TCPServer().startServer();
    }

}
