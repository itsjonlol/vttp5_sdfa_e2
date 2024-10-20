package sg.edu.nus.iss.baccarat.server;

import java.util.List;

public class ServerApp {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Ensure that arguments are <port> and <no.of.deck.cards> respectively");
        }
        int port = Integer.parseInt(args[0]);
        int noOfDeck = Integer.parseInt(args[1]);
        ShuffleDeck shuffleDeck = new ShuffleDeck();
        List<List<String>> shuffledDeckList = shuffleDeck.generateShuffledDeckList(noOfDeck);
        //Collections.shuffle(deckCard);
        
        for (List deck : shuffledDeckList) {
            //Collections.shuffle(deck);
            System.out.println(deck.toString());
        }
        System.out.println(shuffledDeckList.get(1).size());
        System.out.println(shuffledDeckList.size());
        
    }
}