package banking;

import java.util.Map;

public class CommandProcessor {
    Bank bank;
    Map<String, Account> accounts;

    public CommandProcessor(Bank bank) {
        this.bank = bank;
        this.accounts = bank.getAllAccounts();
    }

    public void process(String s) {
        s = s.toLowerCase();
        CreateCommandProcessor createProcessor = new CreateCommandProcessor(bank);
        DepositCommandProcessor depositProcessor = new DepositCommandProcessor(bank);

        if (s.startsWith("create")) {
            createProcessor.process(s);
        } else if (s.startsWith("deposit")) {
            depositProcessor.process(s);
        }
    }
}
