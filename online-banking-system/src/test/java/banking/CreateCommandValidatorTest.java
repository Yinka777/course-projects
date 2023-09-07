package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCommandValidatorTest {
    CommandValidator createCommandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        createCommandValidator = new CreateCommandValidator(bank);
    }

    @Test
    void valid_creation_command() {
        boolean actual = createCommandValidator.validate("create checking 12345678 0.6");
        assertTrue(actual);
    }

    @Test
    void creation_command_is_case_insensitive() {
        boolean actual = createCommandValidator.validate("cReAtE checking 12345678 0.97");
        assertTrue(actual);
    }

    @Test
    void creation_command_with_too_many_parameters_is_invalid() {
        boolean actual = createCommandValidator.validate("create checking 12345678 0.6 CONFIDENTIAL months");
        assertFalse(actual);
    }

    @Test
    void creation_command_with_leading_spaces_is_invalid() {
        boolean actual = createCommandValidator.validate("   create checking 12345678 0.6");
        assertFalse(actual);
    }

    @Test
    void creation_command_with_middle_double_spaces_is_invalid() {
        boolean actual = createCommandValidator.validate("create  checking  12345678  0.6");
        assertFalse(actual);
    }

    @Test
    void creation_command_with_trailing_spaces_is_valid() {
        boolean actual = createCommandValidator.validate("create checking 12345678 0.6      ");
        assertTrue(actual);
    }

    @Test
    void typo_in_create_is_invalid() {
        boolean actual = createCommandValidator.validate("creat savings 12345678 1.2");
        assertFalse(actual);
    }

    @Test
    void creation_command_with_create_missing_is_invalid() {
        boolean actual = createCommandValidator.validate("savings 12345678 7.2");
        assertFalse(actual);
    }

    @Test
    void creation_command_with_wrong_type_is_invalid() {
        boolean actual = createCommandValidator.validate("create ira 12345678 1.2");
        assertFalse(actual);
    }

    @Test
    void creation_command_with_type_missing_is_invalid() {
        boolean actual = createCommandValidator.validate("create 12345678 0.12");
        assertFalse(actual);
    }

    @Test
    void creation_command_with_id_missing_is_invalid() {
        boolean actual = createCommandValidator.validate("create cd 5.4321 0.9");
        assertFalse(actual);
    }

    @Test
    void creation_command_with_duplicate_id_is_invalid() {
        Account cd = new Cd("12345678", 1.23, 2300);
        bank.addAccount("12345678", cd);
        boolean actual = createCommandValidator.validate("create savings 12345678 1.2");
        assertFalse(actual);
    }

    @Test
    void creation_command_with_id_containing_string_is_invalid() {
        boolean actual = createCommandValidator.validate("create checking 876S4E21 5.55");
        assertFalse(actual);
    }

    @Test
    void creation_command_without_8numberid_is_invalid() {
        boolean actual = createCommandValidator.validate("create savings 123490 9.999");
        assertFalse(actual);
    }

    @Test
    void creation_command_with_apr_missing_is_invalid() {
        boolean actual = createCommandValidator.validate("create savings 12345678");
        assertFalse(actual);
    }

    @Test
    void creation_command_with_apr_greater_than_10_is_invalid() {
        boolean actual = createCommandValidator.validate("create checking 87654321 10.001");
        assertFalse(actual);
    }

    @Test
    void creation_command_with_apr_less_than_0_is_invalid() {
        boolean actual = createCommandValidator.validate("create checking 87654321 -20");
        assertFalse(actual);
    }

    @Test
    void creation_command_with_apr_of_0_is_valid() {
        boolean actual = createCommandValidator.validate("create cd 12345678 0 4500");
        assertTrue(actual);
    }

    @Test
    void creation_command_with_apr_of_10_is_valid() {
        boolean actual = createCommandValidator.validate("create cd 12345678 10 4500");
        assertTrue(actual);
    }

    @Test
    void creation_command_with_string_in_apr_is_invalid() {
        boolean actual = createCommandValidator.validate("create savings 12345678 8?7");
        assertFalse(actual);
    }

    @Test
    void creation_command_for_savings_account_with_amount_is_invalid() {
        boolean actual = createCommandValidator.validate("create savings 12345678 1.2 1500");
        assertFalse(actual);
    }

    @Test
    void creation_command_for_checking_account_with_amount_is_invalid() {
        boolean actual = createCommandValidator.validate("create checking 65433906 0.2 1500");
        assertFalse(actual);
    }

    @Test
    void creation_command_for_cd_account_with_amount_is_valid() {
        boolean actual = createCommandValidator.validate("create cd 65433906 0.2 1500");
        assertTrue(actual);
    }

    @Test
    void creation_command_for_cd_account_with_amount_not_between_1000_and_10000_is_invalid() {
        boolean actual = createCommandValidator.validate("create cd 65433906 8.7 100");
        assertFalse(actual);
    }

    @Test
    void creation_command_for_cd_account_with_negative_amount_is_invalid() {
        boolean actual = createCommandValidator.validate("create cd 87654321 0.006 -5000");
        assertFalse(actual);
    }

    @Test
    void creation_command_for_cd_account_without_amount_is_invalid() {
        boolean actual = createCommandValidator.validate("create cd 12345678 0.666");
        assertFalse(actual);
    }
}
