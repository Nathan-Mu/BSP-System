package BSP;

import org.json.*;
/**
 * Created by nathan on 13/5/17.
 */
public class HomeLoanAccount extends Account {
    public HomeLoanAccount()
    {
        super.accountType = "Home loan account";
    }
    
    public HomeLoanAccount(JSONObject json)
    {
        super(json);
    }
    
    public HomeLoanAccount(int accountId)
    {
        super(accountId, "Home loan account");
    }
    
    @Override
    public Transaction withdraw(double amount, int inputAccountId)
    {
        Transaction newTransaction = new Transaction();
        this.balance -= amount;
        newTransaction.setOutputAccount(accountId);
        newTransaction.setInputAccount(accountId);
        newTransaction.setAmount(amount);
        transactionList.add(newTransaction);
        return newTransaction;
    }
}

