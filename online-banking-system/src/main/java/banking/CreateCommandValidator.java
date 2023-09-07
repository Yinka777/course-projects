package banking;

public class CreateCommandValidator extends CommandValidator {
    public CreateCommandValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String s) {
        s = s.toLowerCase();
        s = s.stripTrailing();
        String[] inputs = s.split(" ", 0);
        if (inputs.length == 4) {
            return validateCommand(inputs[0]) && validateType(inputs[1])
                    && validateID(inputs[2]) && validateApr(inputs[3]);
        } else if (inputs.length == 5) {
            String str = inputs[1];
            return validateCommand(inputs[0]) && str.equals("cd")
                    && validateID(inputs[2]) && validateApr(inputs[3]) && validateAmount(inputs[4]);
        }
        return false;
    }

    private boolean validateCommand(String s) {
        return s.equals("create");
    }

    private boolean validateType(String s) {
        return s.equals("checking") || s.equals("savings");
    }

    private boolean validateID(String s) {
        if (s.length() != 8) {
            return false;
        }
        try {
            int id = Integer.parseInt(s);
            return !accounts.containsKey(s);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validateApr(String s) {
        try {
            double apr = Double.parseDouble(s);
            return (apr >= 0 && apr <= 10);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validateAmount(String s) {
        try {
            double amount = Double.parseDouble(s);
            return (amount >= 1000 && amount <= 10000);
        } catch (Exception e) {
            return false;
        }
    }
}
