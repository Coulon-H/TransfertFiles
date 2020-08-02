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

public class Client {
    // Envoi
    private int port;
    private Socket client = null;
    private FileInputStream fin;   
    private OutputStream out = null;
    private BufferedInputStream din = null;
    File fc ;
    String host;

    Client(String h, int p, File f){ //Constructor
        this.port = p;
        this.fc = f;
        this.host = h;
    }

    public void send() throws IOException{ //Function sending the file
        try {
            client = new Socket(host, port);
            fichename(fc);
            byte[] backup = new byte[(int) this.fc.length()];

            fin = new FileInputStream(fc);
            din = new BufferedInputStream(fin);
            this.out = client.getOutputStream();


            din.read(backup, 0, backup.length);
            out.write(backup, 0, backup.length);

            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (this.din != null) {
                try {
                    this.fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    this.din.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out != null) {
                out.close();
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void fichename(File f){ // Function sending the name and the length of the file
        try{
            String file = f.getName() + "/"+ f.length();
            byte[] b = file.getBytes();
            out = client.getOutputStream();

            out.write(b, 0, b.length);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null) {
                out = null;
            }
        }
    }


}
