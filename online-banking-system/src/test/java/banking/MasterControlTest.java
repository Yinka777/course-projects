package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterControlTest {
    MasterControl masterControl;
    List<String> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>();
        Bank bank = new Bank();
        CommandValidator validator = new CommandValidator(bank);
        CommandProcessor processor = new CommandProcessor(bank);
        CommandStorage storage = new CommandStorage(bank);
        masterControl = new MasterControl(bank, validator, processor, storage);
    }

    private void assertSingleCommand(String command, List<String> actual) {
        assertEquals(1, actual.size());
        assertEquals(command, actual.get(0));
    }

    @Test
    void typo_in_create_command_is_invalid() {
        input.add("creat checking 12345678 1.0");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("creat checking 12345678 1.0", actual);
    }

    @Test
    void typo_in_deposit_command_is_invalid() {
        input.add("deposit 46579 900");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("deposit 46579 900", actual);
    }

    @Test
    void two_typo_commands_are_both_invalid() {
        input.add("creat checking 12345678 1.0");
        input.add("deposit 46579 900");

        List<String> actual = masterControl.start(input);

        assertEquals(2, actual.size());
        assertEquals("creat checking 12345678 1.0", actual.get(0));
        assertEquals("deposit 46579 900", actual.get(1));
    }

    @Test
    void invalid_to_create_accounts_with_same_ID() {
        input.add("create checking 12345678 0.875");
        input.add("create checking 12345678 0.875");

        List<String> actual = masterControl.start(input);

        assertSingleCommand("create checking 12345678 0.875", actual);
    }


}
