package banking;

public class WithdrawCommandValidator extends CommandValidator {
    public WithdrawCommandValidator(Bank bank) {
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
        return s.equals("withdraw");
    }

    private boolean validateID(String s) {
        if (s.length() != 8) {
            return false;
        }
        try {
            int id = Integer.parseInt(s);
            if (!accounts.containsKey(s)) {
                return false;
            }
            Account acc = accounts.get(s);
            if ("cd".equals(acc.type)) {
                return acc.getTime() >= 12;
            } else if ("savings".equals(acc.type)) {
                return !acc.monthlyLimitReached;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validateAmount(String id, String amount) {
        try {
            double w_amt = Double.parseDouble(amount);
            Account acc = accounts.get(id);
            if ("cd".equals(acc.type)) {
                return acc.getBalance() <= w_amt;
            }
            if (w_amt >= 0) {
                return bank.withdrawLimit(id, w_amt);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
