package progetti.restapitest.dto;
import progetti.restapitest.entities.Wallet;

public class WalletResponse {

    private Long id;
    private Long balance;
    private UserResponse user;

    public static WalletResponse from (Wallet wallet){
        WalletResponse dto = new WalletResponse();
        dto.id = wallet.getId();
        dto.balance = wallet.getBalance();
        dto.user = UserResponse.from(wallet.getUser());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
    

    
}
