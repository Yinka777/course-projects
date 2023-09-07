package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositCommandProcessorTest {
    public static final String CHECKING_ID = "12345678";
    public static final String SAVINGS_ID = "23456789";

    CommandProcessor depositCommandProcessor;
    Bank bank;
    Account checking;
    Account savings;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(CHECKING_ID, 1.3);
        bank.addAccount(CHECKING_ID, checking);
        savings = new Savings(SAVINGS_ID, 1.8);
        bank.addAccount(SAVINGS_ID, savings);

        depositCommandProcessor = new DepositCommandProcessor(bank);
    }

    @Test
    void deposit_zero_to_account() {
        depositCommandProcessor.process("deposit " + CHECKING_ID + " 0");
        assertEquals(0, bank.getAccount(CHECKING_ID).getBalance());
    }

    @Test
    void deposit_to_checking_account() {
        depositCommandProcessor.process("deposit " + CHECKING_ID + " 900");
        assertEquals(900, bank.getAccount(CHECKING_ID).getBalance());
    }

    @Test
    void deposit_to_savings_account() {
        depositCommandProcessor.process("deposit " + SAVINGS_ID + " 1300");
        assertEquals(1300, bank.getAccount(SAVINGS_ID).getBalance());
    }

    @Test
    void multiple_deposits_to_account() {
        bank.deposit(SAVINGS_ID, 1000.01);
        depositCommandProcessor.process("deposit " + SAVINGS_ID + " 1300");
        depositCommandProcessor.process("deposit " + CHECKING_ID + " 300");
        depositCommandProcessor.process("deposit " + SAVINGS_ID + " 75.99");
        assertEquals(2376, bank.getAccount(SAVINGS_ID).getBalance());
    }
}
