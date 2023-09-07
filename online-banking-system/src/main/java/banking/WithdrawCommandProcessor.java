package banking;

public class WithdrawCommandProcessor extends CommandProcessor {
    public WithdrawCommandProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public void process(String s) {
        s = s.stripTrailing();
        String[] inputs = s.split(" ", 0);
        String id = inputs[1];
        double amount = Double.parseDouble(inputs[2]);
        bank.withdraw(id, amount);
    }
}
