package banking;

import java.util.ArrayList;

public class CommandStorage {
    Bank bank;
    ArrayList<String> invalidCommands;
    ArrayList<String> outputList;

    public CommandStorage(Bank bank) {
        this.bank = bank;
        invalidCommands = new ArrayList<>();
        outputList = new ArrayList<>();
    }

    public void storeInvalidCommand(String s) {
        invalidCommands.add(s);
    }

    public ArrayList<String> output() {
        return invalidCommands;
    }
}
