package sg.edu.nus.iss.baccarat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        List<String> gameHistory = engine.getResultHistory();

        while (true){
            Socket conn = server.accept();
            System.out.println("Got a connection");
            while (true) { 
                InputStream is = conn.getInputStream();
                // Reader reader = new InputStreamReader(is);
                // BufferedReader br = new BufferedReader(reader);
                DataInputStream dis = new DataInputStream(is);
                OutputStream os = conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);

                String clientInput = dis.readUTF();
                System.out.println(clientInput);
                

                if (clientInput.startsWith("login")) {
                    System.out.println(clientInput.length());
                    String[] tokens = clientInput.split("\\|"); // important to add escape
                    String userName  = tokens[1];
                    engine.setUserName(userName);
                    String amount = tokens[2];
                    engine.setStartingAmount(amount);
                    
                    engine.createDatabase(userName,amount);
                    dos.writeUTF("Hello " + engine.getUserName()); // must have a write because client is reading
                    dos.flush();
                    


                } else if (clientInput.equals("quit")) {
                    System.out.println("Client has closed connecton. Server closing...");
                    for (String history : gameHistory) {
                        System.out.println(history);
                    }
                    engine.writeGameHistory(gameHistory);
                    conn.close();
                    System.exit(-1);

                } else if (clientInput.startsWith("bet")) {
                    String[] tokens = clientInput.split("\\|");
                    String betSession = tokens[1];
                    if (Integer.parseInt(engine.getStartingAmount()) < Integer.parseInt(betSession)) {
                        String response = "You can only bet amounts lower than your starting amount. Start amount: " + engine.getStartingAmount();
                        System.out.println(response);
                        dos.writeUTF(response);
                        dos.flush();
                       
                        
                    } else {
                        engine.setBetSession(betSession);
                        String response = "Bet session is: " + engine.getBetSession();
                        System.out.println(response);
                        dos.writeUTF(response);
                        dos.flush();
            
                    }
                    
                } else if (clientInput.startsWith("deal")) {
                    String[] tokens = clientInput.split("\\|");
                    String betWinner = tokens[1];
                    
                    String outcome = engine.initialise(); // i will know whether player or banker wins. if betWinner = P && winner>0 {add bet amount}
                    //else;subtract bet amount from kenneth db
                    engine.getWinner(); // if >0, player wins. if <0 banker wins.
                    String result = engine.displayOutcome(engine.getWinner());
                    String serverOutput = outcome + "\n" + result;
                    dos.writeUTF(serverOutput);
                    dos.flush();

                    if (betWinner.toLowerCase().equals("p")) {
                        if (engine.getWinner() > 0) {
                            engine.winBet();
                            gameHistory.add("P");
                        } else if (engine.getWinner() <0) {
                            if (engine.getIntSumB() == 6) {
                                System.out.println("Banker won with 6 points");
                                int currentBetSession = Integer.parseInt(engine.getBetSession());
                                currentBetSession = currentBetSession/2;
                                engine.setBetSession(String.valueOf(currentBetSession));
                                engine.loseBet();
                                gameHistory.add("B");

                            } else {
                                engine.loseBet();
                                gameHistory.add("B");
                            }
                            
                        } else {
                            gameHistory.add("D");
                        }
                        
                    }  else if ((betWinner.toLowerCase().equals("b"))) {
                        if (engine.getWinner() < 0) {
                            if (engine.getIntSumB()== 6) {
                                System.out.println("Banker won with 6 points");
                                int currentBetSession = Integer.parseInt(engine.getBetSession());
                                currentBetSession = currentBetSession/2;
                                engine.setBetSession(String.valueOf(currentBetSession));
                                engine.winBet();
                                gameHistory.add("B");

                            }else {
                                engine.winBet();
                                gameHistory.add("B");
                            }
                        } else if (engine.getWinner() > 0) {
                            engine.loseBet();
                            gameHistory.add("P");
                        } else {
                            gameHistory.add("D");
                        }
                    }
                
                }
        
            }
            
        }
        
        
    }
}