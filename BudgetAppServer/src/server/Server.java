/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import thread.ProcessClientsRequests;


/**
 *
 * @author EMA
 */
public class Server extends Thread{
    
    private ServerSocket serverSocket;

    @Override
    public void run() {
       try {
           if (serverSocket == null || serverSocket.isClosed()) {
               serverSocket = new ServerSocket(9000);
                System.out.println("Waiting for connection...");
           }
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("Connected!");
                handleClient(socket);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
     
    public void stopServer() {
        try {
            serverSocket.close();
            System.out.println("closed");
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private void handleClient(Socket socket) throws Exception {
        ProcessClientsRequests processRequests = new ProcessClientsRequests(socket, serverSocket);
        processRequests.start();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    
}
