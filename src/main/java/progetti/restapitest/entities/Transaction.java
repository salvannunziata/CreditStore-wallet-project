package progetti.restapitest.entities;
import java.time.Instant;
import jakarta.persistence.*;
import progetti.restapitest.enums.TransactionType;

@Entity
@Table(name="transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="wallet_id",nullable = false)
    private Wallet wallet;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private TransactionType type;

    @Column(name="amount", nullable=false)
    private Long amount;

    @Column(name="new_balance", nullable=false)
    private Long newBalance;

    @Column(name="description")
    private String description;

    @Column(name="created_at",nullable=false)
    private Instant createdAt = Instant.now();


    public Transaction(){}

    public Transaction(Wallet wallet, TransactionType type, Long amount, 
    Long newBalance, String description){
        this.wallet=wallet;
        this.type=type;
        this.amount=amount;
        this.newBalance=newBalance;
        this.description=description;
        this.createdAt=Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(Long newBalance) {
        this.newBalance = newBalance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    
}
