package sg.edu.nus.iss.baccarat.client;

import java.io.BufferedWriter;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    
    public static void main(String[] args) throws IOException {

        String[] tokens = args[0].split(":");
        String host = tokens[0];

        int port = Integer.parseInt(tokens[1]);
        Socket sock = new Socket(host,port);
        System.out.printf("Connected to server at %s, %d\n",host,port);

        Console console = System.console();
    
        //k
        OutputStream os = sock.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        Writer writer = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(writer);
        InputStream is = sock.getInputStream();
        DataInputStream dis = new DataInputStream(is);

        String keyboardInput  ="";
        
        // String serverResponse = dis.readUTF();
        while (!keyboardInput.equals("quit")) {
            keyboardInput = console.readLine("Please Login, bet, or deal|B or deal|p\n");
            System.out.println(keyboardInput);
            String serverResponse;
            if (keyboardInput.toLowerCase().equals("quit")) {
                dos.writeUTF(keyboardInput);

            } else  {
                Scanner scan = new Scanner(keyboardInput);
                
                String clientOutput = "";
                while (scan.hasNext()) {
                    String word =scan.next();
                    clientOutput += word + "|";
                }
                clientOutput = clientOutput.substring(0,clientOutput.length()-1);
                dos.writeUTF(clientOutput.toLowerCase());
                serverResponse = dis.readUTF();
                System.out.println(serverResponse);
                
                
            }
        

        

            } 
        dos.flush();
        dos.close();
        os.close();
        sock.close();
            

            
        }
        // dos.flush();
        // dos.close();
        // os.close();
        // sock.close();

        
        
        
    }

    


    
