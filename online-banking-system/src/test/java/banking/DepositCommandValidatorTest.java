package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepositCommandValidatorTest {
    public static final String CHECKING_ID = "12345678";
    public static final String SAVINGS_ID = "23456789";
    public static final String CD_ID = "34567890";

    CommandValidator depositCommandValidator;
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

        depositCommandValidator = new DepositCommandValidator(bank);
    }

    @Test
    void valid_deposit_command() {
        boolean actual = depositCommandValidator.validate("deposit " + CHECKING_ID + " 500");
        assertTrue(actual);
    }

    @Test
    void deposit_command_is_case_insensitive() {
        boolean actual = depositCommandValidator.validate("DEpoSiT " + SAVINGS_ID + " 1000");
        assertTrue(actual);
    }

    @Test
    void deposit_command_with_too_many_parameters_is_invalid() {
        boolean actual = depositCommandValidator.validate("deposit " + SAVINGS_ID + " 1000 0.9 banking.Savings DirectDeposit");
        assertFalse(actual);
    }

    @Test
    void deposit_command_with_leading_spaces_is_invalid() {
        boolean actual = depositCommandValidator.validate("   deposit " + CHECKING_ID + " 88.9");
        assertFalse(actual);
    }

    @Test
    void deposit_command_with_middle_double_spaces_is_invalid() {
        boolean actual = depositCommandValidator.validate("deposit  " + SAVINGS_ID + "  1111");
        assertFalse(actual);
    }

    @Test
    void deposit_command_with_trailing_spaces_is_valid() {
        boolean actual = depositCommandValidator.validate("deposit " + CHECKING_ID + " 0.6      ");
        assertTrue(actual);
    }

    @Test
    void typo_in_deposit_is_invalid() {
        boolean actual = depositCommandValidator.validate("depsit " + CHECKING_ID + " 888");
        assertFalse(actual);
    }

    @Test
    void deposit_missing_in_command_is_invalid() {
        boolean actual = depositCommandValidator.validate(SAVINGS_ID + " 1000");
        assertFalse(actual);
    }

    @Test
    void deposit_command_missing_id_is_invalid() {
        boolean actual = depositCommandValidator.validate("deposit 900");
        assertFalse(actual);
    }

    @Test
    void deposit_command_with_nonexistent_account_is_invalid() {
        boolean actual = depositCommandValidator.validate("deposit 00856789 1000");
        assertFalse(actual);
    }

    @Test
    void deposit_command_missing_amount_is_invalid() {
        boolean actual = depositCommandValidator.validate("deposit " + CHECKING_ID);
        assertFalse(actual);
    }

    @Test
    void deposit_0_into_checking_is_valid() {
        boolean actual = depositCommandValidator.validate("deposit " + CHECKING_ID + " 0");
        assertTrue(actual);
    }

    @Test
    void deposit_0_into_savings_is_valid() {
        boolean actual = depositCommandValidator.validate("deposit " + SAVINGS_ID + " 0");
        assertTrue(actual);
    }

    @Test
    void deposit_1000_into_checking_is_valid() {
        boolean actual = depositCommandValidator.validate("deposit " + CHECKING_ID + " 1000");
        assertTrue(actual);
    }

    @Test
    void deposit_2500_into_savings_is_valid() {
        boolean actual = depositCommandValidator.validate("deposit " + SAVINGS_ID + " 2500");
        assertTrue(actual);
    }

    @Test
    void deposit_more_than_1000_into_checking_is_invalid() {
        boolean actual = depositCommandValidator.validate("deposit " + CHECKING_ID + " 1000.50");
        assertFalse(actual);
    }

    @Test
    void deposit_more_than_2500_into_savings_is_invalid() {
        boolean actual = depositCommandValidator.validate("deposit " + SAVINGS_ID + " 3000");
        assertFalse(actual);
    }

    @Test
    void deposit_negative_amount_is_invalid() {
        boolean actual = depositCommandValidator.validate("deposit " + CHECKING_ID + " -600");
        assertFalse(actual);
    }

    @Test
    void deposit_amount_is_not_a_number_is_invalid() {
        boolean actual = depositCommandValidator.validate("deposit " + SAVINGS_ID + " SOO");
        assertFalse(actual);
    }

    @Test
    void deposit_into_cd_is_invalid() {
        boolean actual = depositCommandValidator.validate("deposit " + CD_ID + " 666");
        assertFalse(actual);
    }
}
