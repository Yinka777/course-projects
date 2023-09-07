package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassCommandValidatorTest {
    CommandValidator passCommandValidator;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        passCommandValidator = new PassCommandValidator(bank);
    }

    @Test
    void pass_command_is_case_insensitive() {
        boolean actual = passCommandValidator.validate("pass 5");
        assertTrue(actual);
    }

    @Test
    void typo_in_pass_command_is_invalid() {
        boolean actual = passCommandValidator.validate("pss 9");
        assertFalse(actual);
    }

    @Test
    void missing_parameters_in_pass_command_is_invalid() {
        boolean actual = passCommandValidator.validate("pass");
        assertFalse(actual);
    }

    @Test
    void pass_command_with_leading_spaces_is_invalid() {
        boolean actual = passCommandValidator.validate("    pass 12");
        assertFalse(actual);
    }

    @Test
    void pass_command_with_middle_double_spaces_is_invalid() {
        boolean actual = passCommandValidator.validate("pass    50");
        assertFalse(actual);
    }

    @Test
    void pass_command_with_trailing_spaces_is_valid() {
        boolean actual = passCommandValidator.validate("pass 3     ");
        assertTrue(actual);
    }

    @Test
    void pass_command_with_zero_time_is_invalid() {
        boolean actual = passCommandValidator.validate("pass 0");
        assertFalse(actual);
    }

    @Test
    void pass_command_with_negative_time_is_invalid() {
        boolean actual = passCommandValidator.validate("pass -10");
        assertFalse(actual);
    }

    @Test
    void pass_time_between_1_and_60_is_valid() {
        boolean actual = passCommandValidator.validate("pass 30");
        assertTrue(actual);
    }

    @Test
    void pass_time_more_than_60_is_invalid() {
        boolean actual = passCommandValidator.validate("pass 61");
        assertFalse(actual);
    }

    @Test
    void pass_time_that_is_not_an_integer_is_invalid() {
        boolean actual = passCommandValidator.validate("pass 5.5");
        assertFalse(actual);
    }
}
