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
    private FileInputStream fin;   //Composants pour l'envoi
    private BufferedInputStream din = null;
    private OutputStream out = null;
    DataOutputStream dout;
    File fc ;
    String host;

    Client(String h, int p, File f){
        this.port = p;
        this.fc = f;
        this.host = h;
    }

    public void send() throws IOException{
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
            System.out.println("File send !!!");

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

    public void fichename(File f){ // Fonction envoyant le nom et la taille du fichier
        try{
            String file = f.getName() + "/"+ f.length();
            out = client.getOutputStream();
            dout = new DataOutputStream(out);

            dout.writeUTF(file);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null) {
                out = null;
                dout = null;
            }
        }
    }

}
