package banking;

public class CreateCommandProcessor extends CommandProcessor {
    public CreateCommandProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public void process(String s) {
        Account account;
        s = s.stripTrailing();
        String[] inputs = s.split(" ", 0);
        String type = inputs[1];
        String id = inputs[2];
        double apr = Double.parseDouble(inputs[3]);
        switch (type) {
            case "checking":
                account = new Checking(id, apr);
                break;
            case "savings":
                account = new Savings(id, apr);
                break;
            case "cd":
                double amount = Double.parseDouble(inputs[4]);
                account = new Cd(id, apr, amount);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        bank.addAccount(id, account);
    }
}
