package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCommandProcessorTest {
    public static final String CHECKING_ID = "12345678";
    public static final String SAVINGS_ID = "23456789";
    public static final String CD_ID = "34567890";

    CommandProcessor createCommandProcessor;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        createCommandProcessor = new CreateCommandProcessor(bank);
    }

    @Test
    void create_checking_account() {
        createCommandProcessor.process("create checking " + CHECKING_ID + " 0.6");
        assertTrue(bank.accountExists(CHECKING_ID));
    }

    @Test
    void create_savings_account() {
        createCommandProcessor.process("create savings " + SAVINGS_ID + " 0.6");
        assertTrue(bank.accountExists(SAVINGS_ID));
    }

    @Test
    void create_cd_account() {
        createCommandProcessor.process("create cd " + CD_ID + " 0.6 1000");
        assertTrue(bank.accountExists(CD_ID));
    }

    @Test
    void create_multiple_accounts() {
        createCommandProcessor.process("create checking " + CHECKING_ID + " 0.6");
        createCommandProcessor.process("create checking 56347678 0.6");
        createCommandProcessor.process("create savings " + SAVINGS_ID + " 0.6");
        createCommandProcessor.process("create savings 77596433 0.6");
        createCommandProcessor.process("create cd " + CD_ID + " 0.6 1000");
        createCommandProcessor.process("create cd 09853788 0.6 1000");
        assertEquals(6, bank.getAllAccounts().size());
    }

    @Test
    void created_account_has_correct_type() {
        createCommandProcessor.process("create checking " + CHECKING_ID + " 0.5");
        assertEquals("checking", bank.getAccount(CHECKING_ID).type);
    }

    @Test
    void created_account_has_correct_apr() {
        createCommandProcessor.process("create savings " + SAVINGS_ID + " 0.45");
        assertEquals(0.45, bank.getAccount(SAVINGS_ID).apr);
    }

    @Test
    void checking_account_has_zero_balance() {
        createCommandProcessor.process("create checking " + CHECKING_ID + " 0.6");
        assertEquals(0, bank.getAccount(CHECKING_ID).getBalance());
    }

    @Test
    void savings_account_has_zero_balance() {
        createCommandProcessor.process("create savings " + SAVINGS_ID + " 0.6");
        assertEquals(0, bank.getAccount(SAVINGS_ID).getBalance());
    }

    @Test
    void cd_account_has_nonzero_balance() {
        createCommandProcessor.process("create cd " + CD_ID + " 0.6 2500");
        assertEquals(2500, bank.getAccount(CD_ID).getBalance());
    }
}
