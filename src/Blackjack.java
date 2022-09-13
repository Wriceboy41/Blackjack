import acm.graphics.GLabel;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Blackjack extends GraphicsProgram {

    //data about the game
    private double wager = 0;
    private double balance = 10000;
    private double bank = 1000000;

    //labels to display info
    private GLabel bankLabel;
    private GLabel wagerLabel;
    private GLabel balanceLabel;
    private GLabel blackjack;

    //buttons for controls
    private JButton wagerButton, playButton, hitButton, stayButton, quitButton;

    //objects we are playing with
    private Deck deck;
    private GHand gHand;
    private GHand dealer;

    @Override
    public void init(){
       this.setBackground(Color.GRAY);

        deck = new Deck();

        //set up buttons
        wagerButton = new JButton("Wager");
        playButton = new JButton("Play");
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        quitButton = new JButton("Quit");

        //add buttons to the screen
        add(wagerButton, SOUTH);
        add(playButton, SOUTH);
        add(hitButton, SOUTH);
        add(stayButton, SOUTH);
        add(quitButton, SOUTH);

        // TODO: handle initial button visibility
        hitButton.setVisible(false);
        stayButton.setVisible(false);

        addActionListeners();
        //TODO: set up your GLabels
        bankLabel = new GLabel("bank:" +bank);
        balanceLabel = new GLabel("player:" +balance);
        wagerLabel = new GLabel("wager:" +wager);
        add(bankLabel, 1, 10);
        add(balanceLabel, 95, 10);
        add(wagerLabel, 200,10);
    }

    public void actionPerformed(ActionEvent ae){
        switch (ae.getActionCommand()){
            case "Wager":
                wager();
                break;

            case "Play":
                if(wager == 0){
                    Dialog.showMessage("must have wager");
                } else {
                    play();}
                break;

            case "Hit":
                hit();
                break;

            case "Stay":
                stay();
                break;

            case"Quit":
                System.exit(0);
                break;

            default:
                System.out.println("you wrong");
                break;
        }
    }

    private void wager(){
        wager = Dialog.getDouble("enter wager");
        if (wager > balance){
            Dialog.showMessage("cannot wage more than current balance");
            wager = 0;
        }
        if (wager<100){
            Dialog.showMessage("minimum wager is 100");
            wager = 0;
        }
        wagerLabel.setLabel("wager:" +wager);
    }

    private void play(){
        gHand = new GHand(new Hand(deck, false));
        add(gHand, 50, 100);
        dealer = new GHand(new Hand(deck, true));
        add(dealer, 50, 250);
        hitButton.setVisible(true);
        stayButton.setVisible(true);
    }

    private void hit(){
        if(gHand.getTotal()<=21){
            gHand.hit();
        } else{
            Dialog.showMessage("bust");
        }
    }

    private void stay(){
        dealer.flipCard(0);
        while(dealer.getTotal()<17){
            dealer.hit();
        }
        checkWinner();
        remove(gHand);
        remove(dealer);
        hitButton.setVisible(false);
        stayButton.setVisible(false);
    }

    private void checkWinner(){
        if(dealer.getTotal() > gHand.getTotal() && dealer.getTotal() <= 21 || gHand.getTotal() > 21){
            balance -= wager;
            bank += wager;
            Dialog.showMessage("you lost");
        } else if(gHand.getTotal() > dealer.getTotal() && gHand.getTotal() <=21 || dealer.getTotal() > 21){
            balance += wager;
            bank -= wager;
            Dialog.showMessage("you won");
        } else{
            Dialog.showMessage("you drew");
        }
        wager = 0;
        bankLabel.setLabel("bank:" +bank);
        balanceLabel.setLabel("player:" +balance);
        wagerLabel.setLabel("wager:" +wager);
        checkUltimateVictory();
    }

    private void checkUltimateVictory(){
        if (balance == 0){
            Dialog.showMessage("you have ultimately lost");
            pause(1000);
            System.exit(0);
        }
        if (bank == 0){
            Dialog.showMessage("you have ultimately won");
            pause(1000);
            System.exit(0);
        }
    }
    @Override
    public void run(){
        System.out.println("balls!");
    }


    public static void main(String[] args) {
        new Blackjack().start();
    }
}
