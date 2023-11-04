package com.hellocode.atm;

public class Account {
    private int accountId;
    private float balance;

    public Account(int accountId, float balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public float getBalance() {
        return balance;
    }

    public void withDrawal(float amount){
        if(balance>amount){
            balance = balance - amount;
        }
    }
}