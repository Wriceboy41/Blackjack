import acm.util.RandomGenerator;

public class Deck {

    private  Card[] cards;
    private int top;

    public Deck(){
        //initialize the array
        cards = new Card[52];
        //use enhanced for! to intantiate all cards
        //for each suit suit in suit.values
        int pos = 0;
        for(Card.Suit suit : Card.Suit.values()){
            for (Card.Face face : Card.Face.values()){
                Card card = new Card(face, suit); //make new card
                cards[pos++] = card; // put card in deck
            }
        }
        // call shuffle
        shuffle();
    }

    public void shuffle(){
        for (int i = 0; i < cards.length; i++) {
            int randomPos = RandomGenerator.getInstance().nextInt(0, cards.length-1);

            Card temp = cards[i];
            cards[i] = cards[randomPos];
            cards[randomPos] = temp;
        }

        top = 0;
    }

    public Card deal(){
        if(top == cards.length){
            shuffle();
        }

        return cards[top++];
    }
}
