package banking;

import java.util.ArrayList;

public abstract class Account {
    protected String type;
    protected double depositLimit;
    protected double withdrawLimit;
    protected boolean monthlyLimitReached;
    protected String id;
    protected double apr;
    protected ArrayList<String> transactions;
    private double balance;
    private int time;

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void deposit(double amount) {
        setBalance(getBalance() + amount);
    }

    public void withdraw(double amount) {
        setBalance(getBalance() - amount);
        if (getBalance() < 0) {
            setBalance(0);
        }
    }

    public void accrue() {
        double monthly_apr = (apr / 100) / 12;
        double interest = getBalance() * monthly_apr;
        setBalance(getBalance() + interest);
    }
}
