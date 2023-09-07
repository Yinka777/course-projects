package banking;

import java.util.List;

public class MasterControl {
    Bank bank;
    CommandValidator validator;
    CommandProcessor processor;
    CommandStorage storage;

    public MasterControl(Bank bank, CommandValidator validator, CommandProcessor processor, CommandStorage storage) {
        this.bank = bank;
        this.validator = validator;
        this.processor = processor;
        this.storage = storage;
    }

    public List<String> start(List<String> input) {
        for (String command : input) {
            if (validator.validate(command)) {
                processor.process(command);
            } else {
                storage.storeInvalidCommand(command);
                continue;
            }
            storage.storeCommand(command);
        }
        return storage.output();
    }
}
