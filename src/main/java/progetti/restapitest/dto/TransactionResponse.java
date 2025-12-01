package progetti.restapitest.dto;
import java.time.Instant;
import progetti.restapitest.entities.Transaction;


public class TransactionResponse {

    private String type;
    private Long amount;
    private Long newBalance;
    private String description;
    private Instant createdAt;

    public static TransactionResponse from(Transaction transaction){
        TransactionResponse dto = new TransactionResponse();
        dto.type = transaction.getType().name();
        dto.amount = transaction.getAmount();
        dto.newBalance = transaction.getNewBalance();
        dto.description = transaction.getDescription();
        dto.createdAt = transaction.getCreatedAt();
        return dto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
