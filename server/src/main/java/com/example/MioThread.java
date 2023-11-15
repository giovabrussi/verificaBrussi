package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class MioThread extends Thread{

    Socket s;

    public MioThread(Socket s){
        this.s = s;
    }

    public void run(){
        try {

            System.out.println("Un client si è collegato");

            String[] parole = {"gondole", "dvd", "francese", "vergogna", "lago", "originale"};
            ArrayList<String> caratteri = new ArrayList<>();

            Random random = new Random();
            String parola = parole[random.nextInt(6)+1];
            String sout = "";

            for (int i = 0; i < parola.length(); i++) {
                sout += "*";
            }

            System.out.println(parola);

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            
            int risposta = 0;
            int c = 0;

            do {
                out.writeBytes(sout+"\n");

                String stringaRicevuta = in.readLine();
                c++;
                if(stringaRicevuta.equals("0")){
                    out.writeBytes("5\n");
                    risposta = 5;
                }else if(stringaRicevuta.length() == 1){

                    if(parola.indexOf(stringaRicevuta) < 0){

                        out.writeBytes("1\n");
                        risposta = 1;

                    }else{

                        caratteri.add(stringaRicevuta);
                        sout = "";

                        for (int i = 0; i < parola.length(); i++) {

                            String p = String.valueOf(parola.charAt(i)) ;
                            if(caratteri.contains(p)){
                                sout += p;
                            }else{
                                sout += "*";
                            }

                        }


                        out.writeBytes("2\n");
                        risposta = 2; 
                    }

                }else{


                    if(!stringaRicevuta.equals(parola)){
                        out.writeBytes("3\n");
                        out.writeBytes(c+"\n");
                        risposta = 3; 
                    }else{
                        out.writeBytes("4\n");
                        out.writeBytes(c+"\n");

                        risposta = 4;
                    }

                }
                
            } while (risposta < 3);

            



            s.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }
    }
}