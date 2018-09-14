package BSP;

import java.util.*;
import org.json.*;
import static BSP.JSONReader.*;

public class Account {
    protected int accountId;
    protected double balance = 0;
    protected String accountType;
    protected List<Transaction> transactionList = new ArrayList<>();
    public Account() {
        
    }
    
    protected Account(JSONObject json) {
        this.accountId = getIntegerFromJSONObject(json,"accountId");
        this.balance = getDoubleFromJSONObject(json, "balance");
        this.accountType = getStringFromJSONObject(json, "accountType");
        JSONArray transactionArray = getJSONArrayFromJSONObject(json, "transactionList");
        for (int i = 0; i < transactionArray.length(); i++)
        {
            Transaction transaction = new Transaction(getJSONObjectFromArray(transactionArray, i));
            this.transactionList.add(transaction);
        }
    }
    
    public Account(int accountId, String accountType)
    {
        this.accountId = accountId;
        this.accountType = accountType;
    }
    
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
    
    public Transaction withdraw(double amount, int inputAccountId)
    {
        Transaction newTransaction = new Transaction();
        if (this.balance >= amount)
        {
            this.balance -= amount;
            newTransaction.setOutputAccount(accountId);
            newTransaction.setInputAccount(inputAccountId);
            newTransaction.setAmount(amount);
            transactionList.add(newTransaction);
        }
        else
        {
            newTransaction.setError("Not enough fund.");
        }
        return newTransaction;
    }
    
    public Transaction deposit(double amount, int outputAccountId)
    {
        this.balance += amount;
        Transaction newTransaction = new Transaction();
        newTransaction.setInputAccount(accountId);
        newTransaction.setOutputAccount(outputAccountId);
        newTransaction.setAmount(amount);
        transactionList.add(newTransaction);
        return newTransaction;
    }
    
    public void rollbackWithdraw(Transaction rollbackTransaction)
    {
        this.balance += rollbackTransaction.getAmount();
        transactionList.remove(rollbackTransaction);
    }
}