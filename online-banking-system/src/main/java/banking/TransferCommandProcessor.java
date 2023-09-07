package banking;

public class TransferCommandProcessor extends CommandProcessor {
    public TransferCommandProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public void process(String s) {
        s = s.stripTrailing();
        String[] inputs = s.split(" ", 0);
        String sender = inputs[1];
        String receiver = inputs[2];
        double amount = Double.parseDouble(inputs[3]);
        bank.transfer(sender, receiver, amount);
    }
}
