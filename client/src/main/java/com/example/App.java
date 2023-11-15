package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {
            System.out.println("Il client è partito");
            Socket s = new Socket("localhost", 3000);

            Scanner sin = new Scanner(System.in);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            System.out.println("Connessione effettuata, premere 0 per uscire");
            System.out.println("Digita una lettera per vedere se c'è oppure inserisci la parola per indovinare");

            int risposta = 0;

            do {

                String parola = in.readLine();
                System.out.println("Parola da indovinare: " + parola);

                String n = sin.nextLine();
                out.writeBytes(n + "\n");

                String confronto = in.readLine();
                risposta = Integer.parseInt(confronto);

                switch (risposta) {
                    case 1:
                        System.out.println("La lettera non è nella parola");
                        break;
                    case 2:
                        System.out.println("La lettera è nella parola");
                        break;
                    case 3:
                        System.out.println("La parola non è quella giusta, hai perso dopo "
                                + Integer.parseInt(in.readLine()) + " tentativi");
                        s.close();
                        break;
                    case 4:
                        System.out.println("Hai indovinato la parola in " + Integer.parseInt(in.readLine())
                                + " tentativi, complimenti");
                        s.close();
                        break;
                    case 5:
                        System.out.println("Uscita");
                        s.close();
                        break;
                }

            } while (risposta < 3);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }
    }
}