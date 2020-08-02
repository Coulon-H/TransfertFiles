/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.firstguiprojects;

/**
 *
 * @author USER
 */

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static int port = 82;
    Socket client = null;
    public Server() throws IOException {//Server Constructor
        try (ServerSocket server = new ServerSocket(port)) {
            this.client = server.accept();
            Thread t = new ClientHandlerReceive(client);
            t.start();
        }
    }
}


class ClientHandlerReceive extends Thread {
    private String[] s = null;
    private FileOutputStream fout;
    private InputStream ins = null;
    private BufferedOutputStream dout = null;
    private File f;
    private Socket client;
    
    ClientHandlerReceive(Socket client){//Constructor
        this.client = client;
    }

    public void receive() throws IOException {//Function receiving the name and the length of the file
        try{
            String r = null;
            ins = client.getInputStream();
            byte[] receiver = new byte[1000];

            ins.read(receiver);

            r = Arrays.toString(receiver);
            s = r.split(" ");

        } catch (IOException ex) {
            Logger.getLogger(ClientHandlerReceive.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            if(ins != null){
                ins = null;
            }
        }
    }
    
    @Override
    public void run(){
         try{
             receive();
             f = new File(s[0]);
             fout = new FileOutputStream(f);
             dout = new BufferedOutputStream(fout);
             ins = client.getInputStream();

             int taille = Integer.parseInt(s[1]);
             byte[] receiver = new byte[taille];
             int bytesRead = 0;

             while((bytesRead = ins.read(receiver)) != -1)
                 dout.write(receiver, 0, bytesRead);

             dout.flush();
             client.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandlerReceive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
