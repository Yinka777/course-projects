package banking;

public class Cd extends Account {
    public Cd(String id, double apr, double open_amount) {
        type = "cd";
        this.id = id;
        this.apr = apr;
        setBalance(open_amount);
        setTime(0);
    }

    @Override
    public void accrue() {
        double monthly_apr = (apr / 100) / 12;
        for (int i = 0; i < 4; i++) {
            double interest = getBalance() * monthly_apr;
            setBalance(getBalance() + interest);
        }
    }
}
