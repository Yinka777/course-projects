package banking;

public class PassCommandProcessor extends CommandProcessor {
    public PassCommandProcessor(Bank bank) {
        super(bank);
    }

    @Override
    public void process(String s) {
        s = s.stripTrailing();
        String[] inputs = s.split(" ", 0);
        int time = Integer.parseInt(inputs[1]);
        bank.passTime(time);
    }
}
