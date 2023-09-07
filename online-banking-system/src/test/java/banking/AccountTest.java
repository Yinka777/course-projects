package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    public static final String ID = "12345678";
    public static final double APR = 0.02;
    Account checking;
    Account savings;
    Account cd;

    @BeforeEach
    void setUp() {
        checking = new Checking(ID, APR);
        savings = new Savings(ID, APR);
        cd = new Cd(ID, APR, 2000);
    }

    @Test
    void account_has_no_money_initially() {
        assertEquals(0, checking.getBalance());
    }
}
