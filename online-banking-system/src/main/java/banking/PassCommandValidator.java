package banking;

public class PassCommandValidator extends CommandValidator {
    public PassCommandValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String s) {
        s = s.toLowerCase();
        s = s.stripTrailing();
        String[] inputs = s.split(" ", 0);
        if (inputs.length == 2) {
            return "pass".equals(inputs[0]) && validateTime(inputs[1]);
        }
        return false;
    }

    private boolean validateTime(String s) {
        try {
            int time = Integer.parseInt(s);
            return time >= 1 && time <= 60;
        } catch (Exception e) {
            return false;
        }
    }
}
