package progetti.restapitest.entities; 
import jakarta.persistence.*;

@Entity
@Table(name="wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="balance", nullable = false)
    private Long balance;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id", unique = true)
    private User user;

    public Wallet(){}

    public Wallet(Long balance, User user){
        this.balance=balance;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    

}
