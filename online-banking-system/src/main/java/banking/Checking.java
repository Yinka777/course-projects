package banking;

public class Checking extends Account {
    public Checking(String id, double apr) {
        type = "checking";
        this.id = id;
        this.apr = apr;
        setBalance(0);
        depositLimit = 1000;
        withdrawLimit = 400;
    }
}