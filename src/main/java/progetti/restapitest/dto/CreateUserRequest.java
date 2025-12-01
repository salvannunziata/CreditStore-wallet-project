package progetti.restapitest.dto;
import progetti.restapitest.entities.User;
public class CreateUserRequest {

    private String name;
    private String email;
    private String password;

    public static CreateUserRequest from (User user){
        CreateUserRequest dto = new CreateUserRequest();
        dto.name = user.getName();
        dto.email = user.getEmail();
        dto.password = user.getPassword();
        return dto;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
