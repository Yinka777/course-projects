package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankTest {
    public static final String QUICK_ID = "12345678";
    public static final double QUICK_APR = 1.35;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    void bank_has_no_accounts_initially() {
        assertTrue(bank.getAllAccounts().isEmpty());
    }

    @Test
    void add_account_to_bank() {
        Account account = new Checking(QUICK_ID, QUICK_APR);
        bank.addAccount(QUICK_ID, account);
        assertTrue(bank.getAllAccounts().containsKey(QUICK_ID));
    }

    @Test
    void add_two_unique_accounts_to_bank() {
        Account account1 = new Savings(QUICK_ID, QUICK_APR);
        Account account2 = new Cd("12873402", QUICK_APR, 2000);
        bank.addAccount(QUICK_ID, account1);
        bank.addAccount("12873402", account2);
        assertTrue(bank.getAllAccounts().containsKey(QUICK_ID));
        assertTrue(bank.getAllAccounts().containsKey("12873402"));
    }

    @Test
    void deposit_to_account() {
        Account account = new Cd(QUICK_ID, QUICK_APR, 2000);
        bank.addAccount(QUICK_ID, account);
        bank.deposit(QUICK_ID, 3000);
        assertEquals(3000 + 2000, account.getBalance());
    }

    @Test
    void deposit_to_account_multiple_times() {
        Account account = new Savings(QUICK_ID, QUICK_APR);
        bank.addAccount(QUICK_ID, account);
        for (int i = 0; i < 10; i++) {
            bank.deposit(QUICK_ID, 3000);
        }
        assertEquals(3000 * 10, account.getBalance());
    }

    @Test
    void withdraw_from_account() {
        Account account = new Cd(QUICK_ID, QUICK_APR, 15000);
        bank.addAccount(QUICK_ID, account);
        bank.withdraw(QUICK_ID, 3330);
        assertEquals(15000 - 3330, account.getBalance());
    }

    @Test
    void withdraw_from_account_multiple_times() {
        Account account = new Checking(QUICK_ID, QUICK_APR);
        bank.addAccount(QUICK_ID, account);
        bank.deposit(QUICK_ID, 3000);
        for (int i = 0; i < 10; i++) {
            bank.withdraw(QUICK_ID, 200);
        }
        assertEquals(3000 - (200 * 10), account.getBalance());
    }

    @Test
    void withdraw_more_than_account_balance() {
        Account account = new Cd(QUICK_ID, QUICK_APR, 200);
        bank.addAccount(QUICK_ID, account);
        bank.withdraw(QUICK_ID, 1500);
        assertEquals(0, account.getBalance());
    }

    @Test
    void withdraw_more_than_account_balance_multiple_times() {
        Account account = new Cd(QUICK_ID, QUICK_APR, 200);
        bank.addAccount(QUICK_ID, account);
        bank.withdraw(QUICK_ID, 300);
        bank.withdraw(QUICK_ID, 5000);
        assertEquals(0, account.getBalance());
    }
}
