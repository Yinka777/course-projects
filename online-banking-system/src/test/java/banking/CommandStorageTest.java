package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandStorageTest {
    CommandStorage commandStorage;
    Bank bank;

    @BeforeEach
    void setUp() {
        commandStorage = new CommandStorage(bank);
    }

    @Test
    void invalid_storage_is_initially_empty() {
        assertTrue(commandStorage.invalidCommands.isEmpty());
    }

    @Test
    void invalid_command_is_added_to_storage() {
        commandStorage.storeInvalidCommand("create current 12345678 0.97");
        assertEquals("create current 12345678 0.97", commandStorage.invalidCommands.get(0));
    }

    @Test
    void commands_are_returned_fifo() {
        commandStorage.storeInvalidCommand("create current 12345678 0.97");
        commandStorage.storeInvalidCommand("deposit 1234578 400");
        commandStorage.storeInvalidCommand("subtract 1234578 9000");
        assertEquals("create current 12345678 0.97", commandStorage.output().get(0));
        assertEquals("deposit 1234578 400", commandStorage.output().get(1));
        assertEquals("subtract 1234578 9000", commandStorage.output().get(2));
    }
}
