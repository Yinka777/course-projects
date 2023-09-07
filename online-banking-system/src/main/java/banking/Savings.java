package banking;

public class Savings extends Account {
    public Savings(String id, double apr) {
        type = "savings";
        this.id = id;
        this.apr = apr;
        setBalance(0);
        depositLimit = 2500;
        withdrawLimit = 1000;
        monthlyLimitReached = false;
    }

    @Override
    public void withdraw(double amount) {
        super.withdraw(amount);
        monthlyLimitReached = true;
    }
}
