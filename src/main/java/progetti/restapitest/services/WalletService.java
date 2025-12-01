package progetti.restapitest.services;

import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import progetti.restapitest.entities.Transaction;
import progetti.restapitest.entities.User;
import progetti.restapitest.entities.Wallet;
import progetti.restapitest.enums.RoleType;
import progetti.restapitest.enums.TransactionType;
import progetti.restapitest.repository.TransactionRepository;
import progetti.restapitest.repository.UserRepository;
import progetti.restapitest.repository.WalletRepository;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public WalletService(WalletRepository walletRepository,
                         TransactionRepository transactionRepository,
                         UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    private Wallet findByIdWithAuthorization(Long walletId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet non trovato"));

        if (!currentUser.getRoleType().equals(RoleType.ADMIN) &&
            !wallet.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Non puoi accedere a un wallet che non è tuo");
        }

        return wallet;
    }

    public List<Wallet> findAll() {
        return walletRepository.findAll();
    }

    public Wallet findById(Long id) {
        return findByIdWithAuthorization(id);
    }

    public Wallet findByUserId(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet non trovato"));
        return findByIdWithAuthorization(wallet.getId());
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactions(Long walletId) {
        Wallet wallet = findByIdWithAuthorization(walletId);
        return transactionRepository.findByWalletOrderByCreatedAtDesc(wallet);
    }

    public Long getBalance(Long walletId) {
        Wallet wallet = findByIdWithAuthorization(walletId);
        return wallet.getBalance();
    }

    public Transaction deposit(Long walletId, Long amount, String description) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("L'importo deve essere maggiore di zero");
        }
        Wallet wallet = findByIdWithAuthorization(walletId);
        Long newBalance = wallet.getBalance() + amount;
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);

        Transaction tx = new Transaction(wallet, TransactionType.DEPOSIT, amount, newBalance, description);
        return transactionRepository.save(tx);
    }

    public Transaction withdraw(Long walletId, Long amount, String description) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("L'importo deve essere maggiore di zero");
        }
        Wallet wallet = findByIdWithAuthorization(walletId);
        if (wallet.getBalance() < amount) {
            throw new IllegalArgumentException("Saldo insufficiente");
        }
        Long newBalance = wallet.getBalance() - amount;
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);

        Transaction tx = new Transaction(wallet, TransactionType.WITHDRAW, amount, newBalance, description);
        return transactionRepository.save(tx);
    }
}
