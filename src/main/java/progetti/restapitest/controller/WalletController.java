package progetti.restapitest.controller;

import progetti.restapitest.dto.DepositRequest;
import progetti.restapitest.dto.TransactionResponse;
import progetti.restapitest.dto.WalletResponse;
import progetti.restapitest.dto.WithdrawRequest;
import progetti.restapitest.entities.Transaction;
import progetti.restapitest.entities.User;
import progetti.restapitest.entities.Wallet;
import progetti.restapitest.security.CustomUserDetails;
import progetti.restapitest.services.WalletService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService){
        this.walletService = walletService;
    }

    private User getLoggedUser() {
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();
        return userDetails.getUser();
    }

    @GetMapping
    public ResponseEntity<List<WalletResponse>> getWalletList() {
        User logged = getLoggedUser();
        if (!logged.getRoleType().name().equals("ADMIN")) {
            return ResponseEntity.status(403).build();
        }
        List<WalletResponse> dto = walletService.findAll()
                .stream()
                .map(WalletResponse::from)
                .toList();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        User logged = getLoggedUser();
        if (!logged.getRoleType().name().equals("ADMIN")) {
            return ResponseEntity.status(403).build();
        }
        List<TransactionResponse> dto = walletService.getAllTransactions()
                .stream()
                .map(TransactionResponse::from)
                .toList();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/me")
    public ResponseEntity<WalletResponse> getMyWallet() {
        User logged = getLoggedUser();
        Wallet wallet = walletService.findByUserId(logged.getId());
        return ResponseEntity.ok(WalletResponse.from(wallet));
    }

    @GetMapping("/me/balance")
    public ResponseEntity<Long> getMyBalance() {
        User logged = getLoggedUser();
        Wallet wallet = walletService.findByUserId(logged.getId());
        Long balance = walletService.getBalance(wallet.getId());
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/me/transactions")
    public ResponseEntity<List<TransactionResponse>> getMyTransactions() {
        User logged = getLoggedUser();
        Wallet wallet = walletService.findByUserId(logged.getId());
        List<TransactionResponse> dto = walletService.getTransactions(wallet.getId())
                .stream()
                .map(TransactionResponse::from)
                .toList();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponse> getWallet(@PathVariable Long walletId) {
        Wallet wallet = walletService.findById(walletId);
        return ResponseEntity.ok(WalletResponse.from(wallet));
    }

    @GetMapping("/{walletId}/balance")
    public ResponseEntity<Long> getBalance(@PathVariable Long walletId) {
        Long balance = walletService.getBalance(walletId);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<Transaction> deposit(
            @PathVariable Long walletId,
            @RequestBody DepositRequest request) {

        Transaction transaction = walletService.deposit(walletId, request.getAmount(), request.getDescription());
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/{walletId}/withdraw")
    public ResponseEntity<Transaction> withdraw(
            @PathVariable Long walletId,
            @RequestBody WithdrawRequest request) {

        Transaction transaction = walletService.withdraw(walletId, request.getAmount(), request.getDescription());
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/{walletId}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable Long walletId) {
        List<TransactionResponse> dto = walletService.getTransactions(walletId)
                .stream()
                .map(TransactionResponse::from)
                .toList();
        return ResponseEntity.ok(dto);
    }
}
