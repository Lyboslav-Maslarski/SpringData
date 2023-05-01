package example.services;

import example.entities.Account;
import example.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void registerAccount(Account account) {
        Optional<Account> found = accountRepository.findById(account.getId());
        if (found.isEmpty()) {
            accountRepository.save(account);
        }
    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            BigDecimal balance = account.getBalance();
            if (balance.compareTo(money) > 0) {
                account.setBalance(balance.subtract(money));
                accountRepository.save(account);
            }
        }
    }

    @Override
    public void transferMoney(BigDecimal money, Long id) {

    }
}
