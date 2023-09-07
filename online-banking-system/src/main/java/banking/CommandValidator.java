package banking;

import java.util.Map;

public class CommandValidator {
    Bank bank;
    Map<String, Account> accounts;

    public CommandValidator(Bank bank) {
        this.bank = bank;
        this.accounts = bank.getAllAccounts();
    }

    public boolean validate(String s) {
        s = s.toLowerCase();
        CreateCommandValidator createValidator = new CreateCommandValidator(bank);
        DepositCommandValidator depositValidator = new DepositCommandValidator(bank);
        WithdrawCommandValidator withdrawValidator = new WithdrawCommandValidator(bank);
        TransferCommandValidator transferValidator = new TransferCommandValidator(bank);

        if (s.startsWith("create")) {
            return createValidator.validate(s);
        } else if (s.startsWith("deposit")) {
            return depositValidator.validate(s);
        } else if (s.startsWith("withdraw")) {
            return withdrawValidator.validate(s);
        } else if (s.startsWith("transfer")) {
            return transferValidator.validate(s);
        }
        return false;
    }
}
