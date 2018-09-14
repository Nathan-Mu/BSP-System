package BSP;

import org.json.*;

/**
 * Created by nathan on 13/5/17.
 */
public class SavingAccount extends Account{
    public SavingAccount()
    {
        super.accountType = "Saving account";
    }
    
    public SavingAccount(JSONObject json)
    {
        super(json);
    }
    
    public SavingAccount(int accountId)
    {
        super(accountId, "Saving account");
    }
}
