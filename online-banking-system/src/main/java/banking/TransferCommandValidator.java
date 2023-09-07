package banking;

public class TransferCommandValidator extends CommandValidator {
    public TransferCommandValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String s) {
        s = s.toLowerCase();
        s = s.stripTrailing();
        String[] inputs = s.split(" ", 0);
        if (inputs.length == 4) {
            return validateCommand(inputs[0]) && validateIDs(inputs[1], inputs[2]) && validateAmount(inputs[1], inputs[2], inputs[3]);
        }
        return false;
    }

    private boolean validateCommand(String s) {
        return s.equals("transfer");
    }

    private boolean validateIDs(String from, String to) {
        if (from.length() != 8 || to.length() != 8) {
            return false;
        }
        if (from.equals(to)) {
            return false;
        }
        try {
            int fid = Integer.parseInt(from);
            int tid = Integer.parseInt(to);
            if (!accounts.containsKey(from) || !accounts.containsKey(to)) {
                return false;
            }
            Account sender = accounts.get(from);
            Account receiver = accounts.get(to);
            if ("cd".equals(sender.type) || "cd".equals(receiver.type)) {
                return false;
            } else if ("savings".equals(sender.type)) {
                return !sender.monthlyLimitReached;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validateAmount(String from, String to, String amount) {
        try {
            double t_amt = Double.parseDouble(amount);
            if (t_amt >= 0) {
                return bank.withdrawLimit(from, t_amt) && bank.depositLimit(to, t_amt);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
