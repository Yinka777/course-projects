package banking;

public class DepositCommandProcessor extends CommandProcessor {
    public DepositCommandProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public void process(String s) {
        s = s.stripTrailing();
        String[] inputs = s.split(" ", 0);
        String id = inputs[1];
        double amount = Double.parseDouble(inputs[2]);
        bank.deposit(id, amount);
    }
}
