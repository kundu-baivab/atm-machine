package com.hellocode.atm;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AppController {

    @FXML
    private TextArea displayTextArea;

    @FXML
    private Button zeroButton;

    @FXML
    private Button oneButton;

    @FXML
    private Button twoButton;

    @FXML
    private Button threeButton;

    @FXML
    private Button fourButton;

    @FXML
    private Button fiveButton;

    @FXML
    private Button sixButton;

    @FXML
    private Button sevenButton;

    @FXML
    private Button eightButton;

    @FXML
    private Button nineButton;

    @FXML
    private ImageView cashImageView;

    private String numbersEntered = "";

    private int selectedAccountId;

    private int optionSelected;


    private EventHandler<ActionEvent> keyboardListener = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Button sourceComponent = (Button) actionEvent.getSource();
            String number = sourceComponent.getText();
            boolean wasShowBalanceOptionSelected = number.equals("1") && selectedAccountId!=0 && optionSelected==0;
            boolean wasWitdrawOptionSelected = number.equals("2") && selectedAccountId!=0;
            if(wasShowBalanceOptionSelected){
                float balance = ATM.getInstance().showBalance(selectedAccountId);
                displayTextArea.clear();
                displayTextArea.setText("YOUR ACCOUNT BALANCE IS: "+balance);
                numbersEntered ="";

            }else if(wasWitdrawOptionSelected){
                displayTextArea.clear();
                displayTextArea.setText("PLEASE ENTER THE AMOUNT: \n");
                optionSelected =2;
            }else{
                numbersEntered = numbersEntered+number;
                displayTextArea.appendText(number);
            }


        }
    };

    @FXML
    public void initialize(){
        displayWelcomeMessage();
        zeroButton.setOnAction(keyboardListener);
        oneButton.setOnAction(keyboardListener);
        twoButton.setOnAction(keyboardListener);
        threeButton.setOnAction(keyboardListener);
        fourButton.setOnAction(keyboardListener);
        fiveButton.setOnAction(keyboardListener);
        sixButton.setOnAction(keyboardListener);
        sevenButton.setOnAction(keyboardListener);
        eightButton.setOnAction(keyboardListener);
        nineButton.setOnAction(keyboardListener);

    }

    public void okButtonListener() throws FileNotFoundException {
        if(numbersEntered.equals("")){
            showMoney(true);
            displayMenu();
            return;
        }
        if(optionSelected == 2 ){
            float amount = Float.parseFloat(numbersEntered);
            ATM.getInstance().withDraw(selectedAccountId,amount);
            numbersEntered="";
            showMoney(false);
            displayTextArea.setText("PRESS OK TO GO BACK TO MENU \n");
            optionSelected = 0;
            return;
        }
        int accountId = Integer.parseInt(numbersEntered);
        boolean isAccountValid = ATM.getInstance().isAccountValid(accountId);
        if(isAccountValid){
            selectedAccountId = accountId;
            displayMenu();
        }else{
            displayErrorInvalidAccount(numbersEntered);
        }
        numbersEntered="";

    }

    private void displayErrorInvalidAccount(String numbersEntered) {
        displayTextArea.clear();
        displayTextArea.setText(" ERROR : ACCOUNT INVALID, PLEASE TRY AGAIN :");
    }

    private void displayMenu() {
        displayTextArea.clear();
        displayTextArea.setText("       MENU          \n"+
                "    1. SHOW BALANCE  \n"+
                "    2. WITHDRAW      \n");

    }

    private void displayWelcomeMessage() {
        displayTextArea.setText("     WELCOME TO THE HELLOCODECLUB ATM!! \n"+
                "   PLEASE ENTER YOUR ACCOUNT ID: \n");

    }

    private void showMoney(boolean empty) throws FileNotFoundException {
        FileInputStream imageLocation = null;
        if(empty){
            imageLocation = new FileInputStream("E:/atm-machine/src/main/resources/atm-empty.png");
        }else {
            imageLocation = new FileInputStream("E:/atm-machine/src/main/resources/atm-with-cash.png");
        }
        cashImageView.setImage(new Image(imageLocation));
    }
}