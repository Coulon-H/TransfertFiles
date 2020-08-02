package com.mycompany.firstguiprojects;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TextHandler{
    private String[] text = null;
    private Machine pc = null;
    private ArrayList<Machine> t;
    private ObjectOutputStream oos;
    private FileOutputStream fou;

    TextHandler(String machine){ // Constructeur 
        if(machine.contains("/")) { // Verification of User input
            this.text = machine.split("/");
            this.pc = new Machine(text[0], text[1]);
        }
        this.t = new ArrayList<>();
    }

    public void fileaction(){
        if(this.text != null){
            fileretrieve();
            fileregistration();
        }else{
            fileretrieve();
        }
    }

    private void fileregistration(){ // Writing the new list to the file
        File f = new File("Save.txt");
        if(this.pc != null)
            t.add(this.pc);
        try{
            fou = new FileOutputStream(f);
            oos = new ObjectOutputStream(fou);

            oos.writeObject(t);
            oos.flush();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String retrieveuser(String machine){// Retrieve tha address of the machine asked
        File f = new File("Save.txt");
        try{
            FileInputStream fin = new FileInputStream(f);
            ObjectInputStream oin = new ObjectInputStream(fin);

            this.t = (ArrayList<Machine>) oin.readObject();
            for(Machine m : t){
                String o = m.getNom();
                String l = m.getAddresse();
                if(o.equals(machine) || l.equals(machine)){
                    this.pc = m;
                    break;
                }
            }
            oin.close();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this.pc.getAddresse();
    }


    private void fileretrieve(){ // Take the list of machine registered
        File f = new File("Save.txt");
        try{
            FileInputStream fin = new FileInputStream(f);
            ObjectInputStream oin = new ObjectInputStream(fin);

            t = (ArrayList<Machine>) oin.readObject();
            oin.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class Machine implements Serializable{
    private String nom;
    private String addresse;

    Machine(String one, String two){
        this.nom = one;
        this.addresse = two;
    }
    public String getNom(){
        return this.nom;
    }
    public String getAddresse(){
        return this.addresse;
    }
}