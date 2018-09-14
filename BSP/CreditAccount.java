package BSP;

import org.json.*;
import java.lang.Math;
import static BSP.BSPConstant.*;
/**
 * Created by nathan on 13/5/17.
 */
public class CreditAccount extends Account {
    private double maxLimit = 1000;
    private double minLimit = 20;
    public CreditAccount()
    {
        super.accountType = "Credit card account";
    }
    
    public CreditAccount(JSONObject json)
    {
        super(json);
    }
    
    public CreditAccount(int accountId)
    {
        super(accountId, "Credit card account");
    }
    
    @Override
    public Transaction withdraw(double amount, int inputAccountId)
    {
        Transaction newTransaction = new Transaction();
        if (Math.abs(super.balance - amount) > C_LINE_OF_CREDIT)
        {
            newTransaction.setError("Over line of credit.");
        }
        else if (amount < minLimit)
        {
            newTransaction.setError("Below minimum limit.");
        }
        else if (amount > maxLimit)
        {
            newTransaction.setError("Over maximum limit.");
        }
        else 
        {
            this.balance -= amount;
            newTransaction.setOutputAccount(accountId);
            newTransaction.setInputAccount(accountId);
            newTransaction.setAmount(amount);
            transactionList.add(newTransaction);
        }
        return newTransaction;
    }
}
