package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferCommandProcessorTest {
    public static final String CHECKING_ID = "12345678";
    public static final String SAVINGS_ID = "23456789";

    CommandProcessor transferCommandProcessor;
    Bank bank;
    Account checking;
    Account savings;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(CHECKING_ID, 1.3);
        bank.addAccount(CHECKING_ID, checking);
        bank.deposit(CHECKING_ID, 100);

        savings = new Savings(SAVINGS_ID, 1.8);
        bank.addAccount(SAVINGS_ID, savings);
        bank.deposit(SAVINGS_ID, 500);

        transferCommandProcessor = new TransferCommandProcessor(bank);
    }

    @Test
    void transfer_command_should_affect_sender_and_receiver_balance() {
        transferCommandProcessor.process("transfer " + CHECKING_ID + " " + SAVINGS_ID + " 30");
        assertEquals(70, bank.getAccount(CHECKING_ID).getBalance());
        assertEquals(530, bank.getAccount(SAVINGS_ID).getBalance());
    }

    @Test
    void transferring_more_than_sender_balance_deposits_to_receiver_correct_amount() {
        transferCommandProcessor.process("transfer " + CHECKING_ID + " " + SAVINGS_ID + " 1000");
        assertEquals(600, bank.getAccount(SAVINGS_ID).getBalance());
    }
}
