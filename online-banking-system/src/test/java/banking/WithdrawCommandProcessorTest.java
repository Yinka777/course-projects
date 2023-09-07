package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithdrawCommandProcessorTest {
    public static final String CHECKING_ID = "12345678";
    public static final String SAVINGS_ID = "23456789";
    public static final String CD_ID = "34567890";

    CommandProcessor withdrawCommandProcessor;
    Bank bank;
    Account checking;
    Account savings;
    Account cd;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(CHECKING_ID, 1.3);
        bank.addAccount(CHECKING_ID, checking);
        bank.deposit(CHECKING_ID, 400);

        savings = new Savings(SAVINGS_ID, 1.8);
        bank.addAccount(SAVINGS_ID, savings);
        bank.deposit(SAVINGS_ID, 1000);

        cd = new Cd(CD_ID, 2.33, 2300.00);
        bank.addAccount(CD_ID, cd);

        withdrawCommandProcessor = new WithdrawCommandProcessor(bank);
    }

    @Test
    void withdraw_zero_from_account() {
        withdrawCommandProcessor.process("withdraw " + CHECKING_ID + " 0");
        assertEquals(400, bank.getAccount(CHECKING_ID).getBalance());
    }

    @Test
    void withdraw_from_checking_account() {
        withdrawCommandProcessor.process("withdraw " + CHECKING_ID + " 300");
        assertEquals(100, bank.getAccount(CHECKING_ID).getBalance());
    }

    @Test
    void withdraw_from_savings_account() {
        withdrawCommandProcessor.process("withdraw " + SAVINGS_ID + " 900");
        assertEquals(100, bank.getAccount(SAVINGS_ID).getBalance());
    }

    @Test
    void withdraw_from_cd_account() {
        withdrawCommandProcessor.process("withdraw " + CD_ID + " 2300");
        assertEquals(0, bank.getAccount(CD_ID).getBalance());
    }

    @Test
    void multiple_withdraw_from_account() {
        bank.withdraw(CHECKING_ID, 20);
        withdrawCommandProcessor.process("withdraw " + CHECKING_ID + " 10");
        withdrawCommandProcessor.process("withdraw " + CHECKING_ID + " 300");
        withdrawCommandProcessor.process("withdraw " + SAVINGS_ID + " 75.99");
        assertEquals(70, bank.getAccount(CHECKING_ID).getBalance());
    }

    @Test
    void withdraw_more_than_checking_balance_is_valid() {
        withdrawCommandProcessor.process("withdraw " + CHECKING_ID + " 50000");
        assertEquals(0, bank.getAccount(CHECKING_ID).getBalance());
    }

    @Test
    void withdraw_more_than_savings_balance_is_valid() {
        withdrawCommandProcessor.process("withdraw " + SAVINGS_ID + " 1000000.99");
        assertEquals(0, bank.getAccount(SAVINGS_ID).getBalance());
    }

    @Test
    void withdraw_more_than_cd_balance_is_valid() {
        withdrawCommandProcessor.process("withdraw " + CD_ID + " 10007400.99");
        assertEquals(0, bank.getAccount(CD_ID).getBalance());
    }
}
