package sg.edu.nus.iss.baccarat.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class BaccaratEngine {
    
    List<String> betPlayer;
    List<String> betBanker;
    String betSession;
    List<String> cardsPlayerInitial;
    List<String> cardsBankerInitial;
    int winner; // if winner > 0, it means player wins. if < 0, it means banker wins.
    
    public void createDatabase(String userName,String amount) throws IOException {
        String outputFileName = userName + ".db";
        File file = new File(outputFileName);
        FileWriter writer = new FileWriter(outputFileName,false);
        BufferedWriter bw = new BufferedWriter(writer);
        bw.write(amount);
        bw.flush();
        bw.close();

    }
    public String dealCard() throws IOException {
        File tempFile = new File("cardstemp.db");
        File file = new File("cards.db");
        Reader reader = new FileReader("cards.db");
        BufferedReader br = new BufferedReader(reader);
        FileWriter writer = new FileWriter(tempFile);
        BufferedWriter bw = new BufferedWriter(writer);
        int lineToRemove = 1;
        String line  = "";
        int count = 0;
        String card = br.readLine();
        String[] tokens = card.split("\\.");
        String cardNumber = tokens[0];
        if (Integer.parseInt(cardNumber)>10) {
            cardNumber = "10";
        }
        while ((line = br.readLine())!=null) {
            // count++;
        
            // if(count == lineToRemove) {
    

            //     continue;
            // }
            bw.write(line);
            bw.newLine();
        }
        bw.flush();
        bw.close();
        file.delete();
        tempFile.renameTo(file);
        //System.out.println(cardNumber);
        return cardNumber;

        
    }

    public void initialise() throws IOException { // initialise player and bankers with 2 cards at the start
        cardsPlayerInitial = new ArrayList<>();
        cardsBankerInitial = new ArrayList<>();
        
        for (int i = 0; i<2; i++) {
            String cardDealtBanker = this.dealCard();
            
            cardsBankerInitial.add(cardDealtBanker);

            String cardDealtPlayer = this.dealCard();
            cardsPlayerInitial.add(cardDealtPlayer);
        
        }
        List<String> cardsPlayerFinal = new ArrayList<>(cardsPlayerInitial);
        List<String> cardsBankerFinal = new ArrayList<>(cardsBankerInitial);

        

        int intSumP = 0;
        for (String cardP : cardsPlayerInitial) {
            //System.out.println("initial card P: " + cardP);
            String pointP;
            if (Integer.parseInt(cardP)== 10) {
                pointP = "0";
            } else {
                pointP = cardP;
            }
        
            //System.out.println("Point p: " + pointP);
            intSumP += Integer.parseInt(pointP);
        }
        // System.out.println("initialSumPointsP: " + intSumP);
        
        if (intSumP <= 15) {
            String cardDealtPlayer = this.dealCard();
            String newCardDealtPlayer;
            if (cardDealtPlayer.equals("10")) {
                newCardDealtPlayer = "0";
            } else {
                newCardDealtPlayer = cardDealtPlayer;
            }
            intSumP += Integer.parseInt(newCardDealtPlayer);
            cardsPlayerFinal.add(cardDealtPlayer);

        }
        for (String test : cardsPlayerFinal) {
            //System.out.println("NewlistP: " + test);
        }
        // System.out.println("Final List player sum: " + intSumP);

        int intSumB = 0;
        for (String cardB : cardsBankerInitial) {
            //System.out.println("initial card B: " + cardB);
            String pointB;
            if (Integer.parseInt(cardB)== 10) {
                pointB = "0";
            } else {
                pointB = cardB;
            }
        
            //System.out.println("Point B: " + pointB);
            intSumB += Integer.parseInt(pointB);
        }
        // System.out.println("initialSumPointsB: " + intSumB);
        
        if (intSumB <= 15) {
            String cardDealtBanker = this.dealCard();
            String newCardDealtBanker;
            if (cardDealtBanker.equals("10")) {
                newCardDealtBanker = "0";
            } else {
                newCardDealtBanker = cardDealtBanker;
            }
            intSumB += Integer.parseInt(newCardDealtBanker);
            cardsBankerFinal.add(cardDealtBanker);

        }
        String strSumB = String.valueOf(intSumB);
        if (strSumB.length()>1) {
            strSumB = strSumB.substring(1);
            intSumB = Integer.parseInt(strSumB);

        }
        String strSumP = String.valueOf(intSumP);
        if (strSumP.length()>1) {
            strSumP = strSumP.substring(1);
            intSumP = Integer.parseInt(strSumP);

        }
        

        for (String test : cardsBankerFinal) {
            //System.out.println("NewlistB: " + test);
        }
        // System.out.println("Final List banker sum: " + intSumB);

        String player ="P";
        String banker = "B";

        for (String playerCards : cardsPlayerFinal) {
            player += "|" + playerCards;

        }
        for (String bankerCards: cardsBankerFinal) {
            banker += "|"  + bankerCards;
        }
        String outcome = player + "," + banker;
        if (intSumP>intSumB) {
            System.out.println("Player wins with " + (intSumP)+ " points, while banker has " + intSumB + " points");
            System.out.println(outcome);

        } else if (intSumB >intSumP) {
            System.out.println("Banker wins with " + intSumB + " points,while player has " + intSumP + " points");
            System.out.println(outcome);
        } else {
            System.out.println("Draw: " + intSumB + " points");
            System.out.println(outcome);
        }
        winner = intSumP - intSumB;


    }

   

    public List<String> getBetPlayer() {
        return betPlayer;
    }

    public void setBetPlayer(List<String> betPlayer) {
        this.betPlayer = betPlayer;
    }

    public List<String> getBetBanker() {
        return betBanker;
    }

    public void setBetBanker(List<String> betBanker) {
        this.betBanker = betBanker;
    }

    public String getBetSession() {
        return betSession;
    }

    public void setBetSession(String betSession) {
        this.betSession = betSession;
    }


    
}
