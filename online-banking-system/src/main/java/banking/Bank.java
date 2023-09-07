package banking;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts;

    Bank() {
        accounts = new LinkedHashMap<>();
    }

    public Map<String, Account> getAllAccounts() {
        return accounts;
    }

    public Account getAccount(String id) {
        return accounts.get(id);
    }

    public void addAccount(String id, Account account) {
        accounts.put(id, account);
    }

    private void removeAccount(String id) {
        accounts.remove(id);
    }

    public void deposit(String id, double amount) {
        Account account = accounts.get(id);
        account.deposit(amount);
    }

    public void withdraw(String id, double amount) {
        Account account = accounts.get(id);
        account.withdraw(amount);
    }

    public void transfer(String sender, String receiver, double amt) {
        Account s_acc = accounts.get(sender);
        Account r_acc = accounts.get(receiver);
        r_acc.deposit(Math.min(amt, s_acc.getBalance()));
        s_acc.withdraw(amt);
    }

    public boolean accountExists(String id) {
        return accounts.containsKey(id);
    }

    public boolean depositLimit(String id, double amt) {
        Account acc = accounts.get(id);
        return amt <= acc.depositLimit;
    }

    public boolean withdrawLimit(String id, double amt) {
        Account acc = accounts.get(id);
        return amt <= acc.withdrawLimit;
    }

    public void passTime(int time) {
        List<String> to_remove = new ArrayList<>();
        while (time > 0) {
            for (Map.Entry<String, Account> entry : accounts.entrySet()) {
                Account acc = entry.getValue();
                if (acc.getBalance() == 0) {
                    to_remove.add(acc.id);
                    continue;
                }
                if (acc.getBalance() < 100) {
                    collect_fee(acc);
                }
                acc.accrue();
                if (acc.type.equals("savings")) {
                    acc.monthlyLimitReached = false;
                }
                acc.setTime(acc.getTime() + time);
            }
            time--;
        }
        for (String id : to_remove) {
            removeAccount(id);
        }
    }

    private void collect_fee(Account acc) {
        acc.withdraw(25);
    }
}
