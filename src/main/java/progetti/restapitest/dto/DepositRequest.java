package progetti.restapitest.dto;


public class DepositRequest {

    private Long amount;
    private String description;

    public static DepositRequest from(Long amount, String description){
        DepositRequest dto = new DepositRequest();
        dto.amount = amount;
        dto.description = description;
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
