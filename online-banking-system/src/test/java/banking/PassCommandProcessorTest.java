package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PassCommandProcessorTest {
    public static final String CHECKING_ID = "12345678";
    public static final String SAVINGS_ID = "23456789";
    public static final String CD_ID = "34567890";

    CommandProcessor passCommandProcessor;
    Bank bank;
    Account checking;
    Account savings;
    Account cd;

    @BeforeEach
    void setUp() {
        bank = new Bank();

        checking = new Checking(CHECKING_ID, 0.12);
        bank.addAccount(CHECKING_ID, checking);

        savings = new Savings(SAVINGS_ID, 1.8);
        bank.addAccount(SAVINGS_ID, savings);

        cd = new Cd(CD_ID, 2.9, 5000);
        bank.addAccount(CD_ID, cd);

        passCommandProcessor = new PassCommandProcessor(bank);
    }

    @Test
    void pass_time_accrues_interest_on_all_open_accounts() {
        bank.deposit(CHECKING_ID, 1000);
        bank.deposit(SAVINGS_ID, 2500);
        passCommandProcessor.process("pass 10");
        assertEquals(1001, Math.round(bank.getAccount(CHECKING_ID).getBalance() * 100) / 100d);
        assertEquals(2537.75, Math.round(bank.getAccount(SAVINGS_ID).getBalance() * 100) / 100d);
        assertEquals(5506.82, Math.round(bank.getAccount(CD_ID).getBalance() * 100) / 100d);
    }

    @Test
    void pass_time_deletes_accounts_with_zero_balance() {
        bank.deposit(CHECKING_ID, 300);
        passCommandProcessor.process("pass 1");
        assertFalse(bank.accountExists(SAVINGS_ID));
    }

    @Test
    void pass_time_does_not_remove_accounts_with_new_zero_balance_after_fee_has_been_collected() {
        bank.deposit(CHECKING_ID, 25);
        assertTrue(bank.accountExists(CHECKING_ID));
    }

    @Test
    void pass_time_deducts_a_25_fee_from_accounts_with_balance_less_than_100() {
        bank.deposit(SAVINGS_ID, 80);
        passCommandProcessor.process("pass 1");
        assertEquals(55.08, Math.round(bank.getAccount(SAVINGS_ID).getBalance() * 100) / 100d);
    }
}
