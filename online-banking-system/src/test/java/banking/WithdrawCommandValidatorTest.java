package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WithdrawCommandValidatorTest {
    public static final String CHECKING_ID = "12345678";
    public static final String SAVINGS_ID = "23456789";
    public static final String CD_ID = "34567890";

    CommandValidator withdrawCommandValidator;
    Bank bank;
    Account checking;
    Account savings;
    Account cd;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(CHECKING_ID, 1.3);
        bank.addAccount(CHECKING_ID, checking);
        savings = new Savings(SAVINGS_ID, 1.8);
        bank.addAccount(SAVINGS_ID, savings);
        cd = new Cd(CD_ID, 1.8, 4000.90);
        bank.addAccount(CD_ID, cd);

        withdrawCommandValidator = new WithdrawCommandValidator(bank);
    }

    @Test
    void valid_withdraw_command() {
        boolean actual = withdrawCommandValidator.validate("withdraw " + CHECKING_ID + " 100");
        assertTrue(actual);
    }

    @Test
    void withdraw_command_is_case_insensitive() {
        boolean actual = withdrawCommandValidator.validate("wiTHdrAw " + SAVINGS_ID + " 600");
        assertTrue(actual);
    }

    @Test
    void withdraw_command_with_too_many_parameters_is_invalid() {
        boolean actual = withdrawCommandValidator.validate("withdraw " + SAVINGS_ID + " 20 0.9 banking.Savings DirectDeposit");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_with_leading_spaces_is_invalid() {
        boolean actual = withdrawCommandValidator.validate("   withdraw " + CHECKING_ID + " 88.9");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_with_middle_double_spaces_is_invalid() {
        boolean actual = withdrawCommandValidator.validate("withdraw  " + SAVINGS_ID + "  111");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_with_trailing_spaces_is_valid() {
        boolean actual = withdrawCommandValidator.validate("withdraw " + CHECKING_ID + " 0.6      ");
        assertTrue(actual);
    }

    @Test
    void typo_in_withdraw_is_invalid() {
        boolean actual = withdrawCommandValidator.validate("withdrew " + CHECKING_ID + " 8");
        assertFalse(actual);
    }

    @Test
    void withdraw_missing_in_command_is_invalid() {
        boolean actual = withdrawCommandValidator.validate(SAVINGS_ID + " 999");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_missing_id_is_invalid() {
        boolean actual = withdrawCommandValidator.validate("withdraw 90");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_with_nonexistent_account_is_invalid() {
        boolean actual = withdrawCommandValidator.validate("withdraw 00856789 1000");
        assertFalse(actual);
    }

    @Test
    void withdraw_command_missing_amount_is_invalid() {
        boolean actual = withdrawCommandValidator.validate("withdraw " + CHECKING_ID);
        assertFalse(actual);
    }

    @Test
    void withdraw_0_from_checking_is_valid() {
        boolean actual = withdrawCommandValidator.validate("withdraw " + CHECKING_ID + " 0");
        assertTrue(actual);
    }

    @Test
    void withdraw_0_from_savings_is_valid() {
        boolean actual = withdrawCommandValidator.validate("withdraw " + SAVINGS_ID + " 0");
        assertTrue(actual);
    }

    @Test
    void withdraw_400_from_checking_is_valid() {
        boolean actual = withdrawCommandValidator.validate("withdraw " + CHECKING_ID + " 400");
        assertTrue(actual);
    }

    @Test
    void withdraw_1000_from_savings_is_valid() {
        boolean actual = withdrawCommandValidator.validate("withdraw " + SAVINGS_ID + " 1000");
        assertTrue(actual);
    }

    @Test
    void withdraw_more_than_400_from_checking_is_invalid() {
        boolean actual = withdrawCommandValidator.validate("withdraw " + CHECKING_ID + " 400.02");
        assertFalse(actual);
    }

    @Test
    void withdraw_more_than_1000_from_savings_is_invalid() {
        boolean actual = withdrawCommandValidator.validate("withdraw " + SAVINGS_ID + " 2000");
        assertFalse(actual);
    }

    @Test
    void withdraw_negative_amount_is_invalid() {
        boolean actual = withdrawCommandValidator.validate("withdraw " + CHECKING_ID + " -600");
        assertFalse(actual);
    }

    @Test
    void withdraw_amount_is_not_a_number_is_invalid() {
        boolean actual = withdrawCommandValidator.validate("withdraw " + SAVINGS_ID + " GO");
        assertFalse(actual);
    }

    @Test
    void multiple_withdraws_in_a_month_from_savings_is_invalid() {
        bank.deposit(SAVINGS_ID, 1000);
        bank.withdraw(SAVINGS_ID, 300);
        boolean actual = withdrawCommandValidator.validate("withdraw " + SAVINGS_ID + " 300");
        assertFalse(actual);
    }

    @Test
    void multiple_withdraws_between_months_from_savings_is_valid() {
        bank.deposit(SAVINGS_ID, 1000);
        bank.withdraw(SAVINGS_ID, 300);
        bank.getAccount(SAVINGS_ID).setTime(1);
        boolean actual = withdrawCommandValidator.validate("withdraw " + SAVINGS_ID + " 250");
        assertFalse(actual);
    }

    @Test
    void withdraw_from_cd_before_12_months_is_invalid() {
        boolean actual = withdrawCommandValidator.validate("withdraw " + CD_ID + " 4500");
        assertFalse(actual);
    }

    @Test
    void withdraw_from_cd_after_12_months_is_valid() {
        bank.getAccount(CD_ID).setTime(15);
        boolean actual = withdrawCommandValidator.validate("withdraw " + CD_ID + " 4500");
        assertTrue(actual);
    }

    @Test
    void withdraw_less_than_cd_account_balance_is_invalid() {
        bank.getAccount(CD_ID).setTime(30);
        boolean actual = withdrawCommandValidator.validate("withdraw " + CD_ID + " 4000");
        assertFalse(actual);
    }
}
