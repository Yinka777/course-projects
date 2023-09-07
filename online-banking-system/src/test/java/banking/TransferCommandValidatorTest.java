package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferCommandValidatorTest {
    public static final String CHECKING_ID = "12345678";
    public static final String SAVINGS_ID = "23456789";
    public static final String CD_ID = "34567890";

    CommandValidator transferCommandValidator;
    Bank bank;
    Account checking;
    Account savings;
    Account cd;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking(CHECKING_ID, 1.3);
        bank.addAccount(CHECKING_ID, checking);
        bank.deposit(CHECKING_ID, 500);

        savings = new Savings(SAVINGS_ID, 1.8);
        bank.addAccount(SAVINGS_ID, savings);
        bank.deposit(SAVINGS_ID, 1000);

        cd = new Cd(CD_ID, 1.8, 5000);
        bank.addAccount(CD_ID, cd);

        transferCommandValidator = new TransferCommandValidator(bank);
    }

    @Test
    void transfer_between_checking_and_savings_is_valid() {
        boolean actual = transferCommandValidator.validate("transfer " + CHECKING_ID + " " + SAVINGS_ID + " 300");
        assertTrue(actual);
    }

    @Test
    void transfer_command_is_case_insensitive() {
        boolean actual = transferCommandValidator.validate("TRANsfer " + SAVINGS_ID + " " + CHECKING_ID + " 600");
        assertTrue(actual);
    }

    @Test
    void transfer_command_with_too_many_parameters_is_invalid() {
        boolean actual = transferCommandValidator.validate("transfer " + CHECKING_ID + " " + SAVINGS_ID +
                " 200 20 0.9 banking.Checking DirectDeposit");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_leading_spaces_is_invalid() {
        boolean actual = transferCommandValidator.validate("    transfer " + SAVINGS_ID + " " + CHECKING_ID + " 0.8");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_middle_double_spaces_is_invalid() {
        boolean actual = transferCommandValidator.validate("transfer   " + CHECKING_ID + "  " + SAVINGS_ID + "  150");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_trailing_spaces_is_valid() {
        boolean actual = transferCommandValidator.validate("transfer " + CHECKING_ID + " " + SAVINGS_ID + " 3    ");
        assertTrue(actual);
    }

    @Test
    void typo_in_transfer_command_is_invalid() {
        boolean actual = transferCommandValidator.validate("trnsfer " + CHECKING_ID + " " + SAVINGS_ID + " 1.21");
        assertFalse(actual);
    }

    @Test
    void transfer_missing_in_command_is_invalid() {
        boolean actual = transferCommandValidator.validate(CHECKING_ID + " " + SAVINGS_ID + " 999");
        assertFalse(actual);
    }

    @Test
    void transfer_command_missing_id_is_invalid() {
        boolean actual = transferCommandValidator.validate("transfer " + " " + SAVINGS_ID + " 30");
        assertFalse(actual);
    }

    @Test
    void transfer_command_with_nonexistent_account_is_invalid() {
        boolean actual = transferCommandValidator.validate("transfer " + CHECKING_ID + " 32940449 300");
        assertFalse(actual);
    }

    @Test
    void transfer_from_and_to_same_account_is_invalid() {
        boolean actual = transferCommandValidator.validate("transfer " + CHECKING_ID + " " + CHECKING_ID + " 377");
        assertFalse(actual);
    }

    @Test
    void transfer_missing_amount_is_invalid() {
        boolean actual = transferCommandValidator.validate("transfer " + CHECKING_ID + " " + SAVINGS_ID);
        assertFalse(actual);
    }

    @Test
    void transfer_0_between_accounts_is_valid() {
        boolean actual = transferCommandValidator.validate("transfer " + CHECKING_ID + " " + SAVINGS_ID + " 0");
        assertTrue(actual);
    }

    @Test
    void transfer_400_from_checking_is_valid() {
        boolean actual = transferCommandValidator.validate("transfer " + CHECKING_ID + " " + SAVINGS_ID + " 400");
        assertTrue(actual);
    }

    @Test
    void transfer_1000_from_savings_is_valid() {
        boolean actual = transferCommandValidator.validate("transfer " + SAVINGS_ID + " " + CHECKING_ID + " 1000");
        assertTrue(actual);
    }

    @Test
    void transfer_more_than_400_from_checking_is_invalid() {
        boolean actual = transferCommandValidator.validate("transfer " + CHECKING_ID + " " + SAVINGS_ID + " 401");
        assertFalse(actual);
    }

    @Test
    void transfer_1000_into_checking_is_valid() {
        boolean actual = transferCommandValidator.validate("transfer " + SAVINGS_ID + " " + CHECKING_ID + " 1000");
        assertTrue(actual);
    }

    @Test
    void transfer_negative_amount_is_invalid() {
        boolean actual = transferCommandValidator.validate("transfer " + SAVINGS_ID + " " + CHECKING_ID + " -1");
        assertFalse(actual);
    }

    @Test
    void transfer_amount_is_not_a_number_is_invalid() {
        boolean actual = transferCommandValidator.validate("transfer " + CHECKING_ID + " " + SAVINGS_ID + " SE0");
        assertFalse(actual);
    }

    @Test
    void multiple_transfers_in_a_month_from_checking_is_valid() {
        bank.transfer(CHECKING_ID, SAVINGS_ID, 40);
        boolean actual = transferCommandValidator.validate("transfer " + CHECKING_ID + " " + SAVINGS_ID + " 75");
        assertTrue(actual);
    }

    @Test
    void multiple_transfers_in_a_month_from_savings_is_invalid() {
        bank.transfer(SAVINGS_ID, CHECKING_ID, 370);
        boolean actual = transferCommandValidator.validate("transfer " + SAVINGS_ID + " " + CHECKING_ID + " 11");
        assertFalse(actual);
    }

    @Test
    void multiple_transfers_between_months_from_savings_is_valid() {
        bank.transfer(SAVINGS_ID, CHECKING_ID, 40);
        bank.getAccount(SAVINGS_ID).setTime(1);
        boolean actual = transferCommandValidator.validate("transfer " + SAVINGS_ID + " " + CHECKING_ID + " 75");
        assertFalse(actual);
    }

    @Test
    void transfer_from_cd_is_invalid() {
        boolean actual = transferCommandValidator.validate("transfer " + CD_ID + " " + CHECKING_ID + " 399");
        assertFalse(actual);
    }

    @Test
    void transfer_into_cd_is_invalid() {
        boolean actual = transferCommandValidator.validate("transfer " + CHECKING_ID + " " + CD_ID + " 99");
        assertFalse(actual);
    }
}
