package progetti.restapitest.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import progetti.restapitest.enums.RoleType;


@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String email;

    @JsonIgnore
    @Column(nullable=false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private RoleType roleType = RoleType.USER;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"})  
    private Wallet wallet;

    public User(){}

    public User(String name, String email,String password, Wallet wallet){
        this.name=name;
        this.email=email;
        this.password=password;
        this.wallet=wallet;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() {return password; }
    public void setPassword(String password) {this.password = password;}

    public Wallet getWallet() { return wallet; }
    public void setWallet(Wallet wallet) { this.wallet = wallet; }

    public RoleType getRoleType() {return roleType; }
    public void setRoleType(RoleType roleType) {this.roleType = roleType; }



    

}
