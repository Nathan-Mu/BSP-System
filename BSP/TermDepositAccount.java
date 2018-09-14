package BSP;

import org.json.*;
import static BSP.BSPConstant.*;
/**
 * Created by nathan on 13/5/17.
 */
public class TermDepositAccount extends Account {
    private int term;
    public TermDepositAccount()
    {
        super.accountType = "Term deposit account";
    }
    
    public TermDepositAccount(JSONObject json)
    {
        super(json);
    }
    
    public TermDepositAccount(int accountId)
    {
        super(accountId, "Term deposit account");
    }
    
    public int getTerm()
    {
        return term;
    }
    
    public void setTerm(int term)
    {
        this.term = term;
    }
    
    public Transaction deposit(double amount, int outputAccount, int term)
    {
        double time = amount/TD_FIX_DEPOSIT_AMOUNT;
        if (time == (int)time)
        {
            this.term = TD_TERMS_ARRAY[term];
            return super.deposit(amount, outputAccount);
        }
        else
        {
            Transaction errorTransaction = new Transaction();
            errorTransaction.setError("Amount must be integer multiple of " + TD_FIX_DEPOSIT_AMOUNT);
            return errorTransaction;
        }
    }
    
    @Override
    public Transaction withdraw(double amount, int inputAccount)
    {
        double time = amount/TD_FIX_DEPOSIT_AMOUNT;
        if (time == (int)time)
            return super.withdraw(amount, inputAccount);
        else
        {
            Transaction errorTransaction = new Transaction();
            errorTransaction.setError("Amount must be integer multiple of " + TD_FIX_DEPOSIT_AMOUNT);
            return errorTransaction;
        }
    }
}
