package models;

import lombok.Getter;
import lombok.Setter;

public class AccountRequest {
    @Getter @Setter
    private int customerId;
    @Getter @Setter
    private int newAccountType;
    @Getter @Setter
    private int fromAccountId;

    public AccountRequest(){

    }

    public AccountRequest(int customerId,int newAccountType,int fromAccountId){
        this.customerId = customerId;
        this.newAccountType = newAccountType;
        this.fromAccountId = fromAccountId;
    }
}

