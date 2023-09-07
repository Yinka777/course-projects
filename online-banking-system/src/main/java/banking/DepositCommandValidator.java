package banking;

public class DepositCommandValidator extends CommandValidator {
    public DepositCommandValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String s) {
        s = s.toLowerCase();
        s = s.stripTrailing();
        String[] inputs = s.split(" ", 0);
        if (inputs.length == 3) {
            return validateCommand(inputs[0]) && validateID(inputs[1]) && validateAmount(inputs[1], inputs[2]);
        }
        return false;
    }

    private boolean validateCommand(String s) {
        return s.equals("deposit");
    }

    private boolean validateID(String s) {
        if (s.length() != 8) {
            return false;
        }
        try {
            int id = Integer.parseInt(s);
            if (accounts.containsKey(s)) {
                Account acc = accounts.get(s);
                return !acc.type.equals("cd");
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validateAmount(String id, String amount) {
        try {
            double d_amt = Double.parseDouble(amount);
            if (d_amt >= 0) {
                return bank.depositLimit(id, d_amt);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
