package progetti.restapitest.dto;
public class WithdrawRequest {

    private Long amount;
    private String description;

    public static WithdrawRequest from( Long amount,String description){
        WithdrawRequest dto = new WithdrawRequest();
        dto.amount = amount;
        return dto;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    

}
