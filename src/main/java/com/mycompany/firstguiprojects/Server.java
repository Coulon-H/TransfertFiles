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

    public Server() throws IOException {
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
    private BufferedOutputStream dout = null;
    private InputStream ins = null;
    DataInputStream din;
    private File f;
    private Socket client;
    
    ClientHandlerReceive(Socket client){
        this.client = client;
    }

    public void receive() throws IOException {
        try{
            String r = null;
            ins = this.client.getInputStream();
            din = new DataInputStream(ins);

            r = din.readUTF();
            System.out.println(r);
            s = r.split("/");

        } catch (IOException ex) {
            Logger.getLogger(ClientHandlerReceive.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            if(ins != null){
                ins = null;
                din = null;
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
             ins = this.client.getInputStream();

             long taille = Long.parseLong(s[1]);
             byte[] receiver = new byte[(int) taille];
             int bytesRead = 0;

             while((bytesRead = ins.read(receiver)) != -1)
                 dout.write(receiver, 0, bytesRead);

             dout.flush();
             client.close();
             System.out.println("File received");
            
        } catch (IOException ex) {
            Logger.getLogger(ClientHandlerReceive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
