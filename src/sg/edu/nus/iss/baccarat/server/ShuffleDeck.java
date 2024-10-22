package sg.edu.nus.iss.baccarat.server;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShuffleDeck {
    private List<String> suit;
    
    //private List<String> suitNo = {"1","2","3","4","5","6","7","8","9","10","11","12","13"};
    //private List<String> suit;
    private List<String> suitNo;
    private List<String> deck;
    private List<List<String>> deckList;
    
    public List<List<String>> generateShuffledDeckList(int noOfDeck) {
        suit = new ArrayList<>(Arrays.asList("1", "2", "3", "4"));
    
        suitNo = new ArrayList<>(Arrays.asList("1","2","3","4","5","6","7","8","9","10","11","12","13"));
        deck = new ArrayList<>();
        String card;
        deckList = new ArrayList<>();
        //create 1 fixed deck list
        for (String suit : suit) {
            for (String suitNo : suitNo) {
                card = suitNo + "." + suit;
                deck.add(card);
            }
        }
        
        for ( int i = 0; i<noOfDeck;i++) {
            List<String> tempDeck = new ArrayList<>(deck); //creates a shallow copy
            //tempDeck = deck; points to the same reference. anything u do to the tempdeck will be done to deck

            Collections.shuffle(tempDeck);
    
            deckList.add(tempDeck);

            //deckList.add(new ArrayList<>(deck));

        }


        for (List<String> list : deckList) {
            for (String invCard : list) {
                
            }
        }
       

        return deckList;
    }
    public void writeFile (String outputFileName) throws IOException {
        
        Writer writer = new FileWriter(outputFileName,false);
        BufferedWriter bw = new BufferedWriter(writer);
        for (List<String> list : deckList) {
            for (String invCard : list) {
                bw.write(invCard);
                bw.newLine();
            }
        }
        bw.flush();
        bw.close();
        
        
    }

    
}   
