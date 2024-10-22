package sg.edu.nus.iss.baccarat.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Ensure that arguments are <port> and <no.of.deck.cards> respectively");
        }
        int port = Integer.parseInt(args[0]);
        int noOfDeck = Integer.parseInt(args[1]);
        ShuffleDeck shuffleDeck = new ShuffleDeck();
        List<List<String>> shuffledDeckList = shuffleDeck.generateShuffledDeckList(noOfDeck);


       

        
        try {
            shuffleDeck.writeFile("cards.db");
        } catch (IOException e) {
        
            e.printStackTrace();
        }

        
        
        ServerSocket server = new ServerSocket(port);
        System.out.printf("Server listening on port %d\n",port);
        BaccaratEngine engine = new BaccaratEngine();

        while (true){
            Socket conn = server.accept();
            System.out.println("Got a connection");
            while (true) { 
                InputStream is = conn.getInputStream();
                // Reader reader = new InputStreamReader(is);
                // BufferedReader br = new BufferedReader(reader);
                DataInputStream dis = new DataInputStream(is);

                String clientInput = dis.readUTF();
                System.out.println(clientInput);

                if (clientInput.startsWith("login")) {
                    System.out.println(clientInput.length());
                    String[] tokens = clientInput.split("\\|"); // important to add escape
                    String userName  = tokens[1];
                    String amount = tokens[2];
                    for (String token : tokens) {
                        System.out.println(token);
                    }
                    engine.createDatabase(userName,amount);

                } else if (clientInput.equals("quit")) {
                    System.out.println("Client has closed connecton. Server closing...");
                    conn.close();
                    System.exit(-1);

                } else if (clientInput.startsWith("bet")) {
                    String[] tokens = clientInput.split("\\|");
                    String betSession = tokens[1];
                    engine.setBetSession(betSession);
                    System.out.println("Bet session is: " + engine.getBetSession());

                } else if (clientInput.startsWith("deal")) {
                    String[] tokens = clientInput.split("\\|");
                    
                    engine.initialise();
                    
                }

                



            
                
            }
            

        }
        
            
            
            
        
       
        
    }
}