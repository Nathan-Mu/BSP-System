package BSP;

import java.util.Date;
import java.util.ArrayList;
import org.json.*;
import static BSP.JSONReader.*;
import static BSP.Time.*;

/**
 * Created by nathan on 13/5/17.
 */
public class Transaction {
    private Date issueDate;
    private int outputAccount = 0;
    private int inputAccount = 0;
    private double amount = 0;
    private String description = null;
    private Date expireDate = null;
    private String error = "";

    public Transaction() {
        this.issueDate = getCurrentDate();
    }
    
    public Transaction(Date issueDate, int inputAccount, int outputAccount, double amount, String description, Date expireDate) 
    {
        this.issueDate = getCurrentDate();
        this.inputAccount = inputAccount;
        this.outputAccount = outputAccount;
        this.amount = amount;
        this.description = description;
        this.expireDate = expireDate;
    }
    
    public Transaction(JSONObject json) 
    {
        this.issueDate = toDate(getStringFromJSONObject(json, "issueDate"),JSON_DATE_FORMAT);
        this.inputAccount = getIntegerFromJSONObject(json, "inputAccount");
        this.outputAccount = getIntegerFromJSONObject(json, "outputAccount");
        this.amount = getDoubleFromJSONObject(json, "amount");
        this.description = getStringFromJSONObject(json, "description");
        this.expireDate = toDate(getStringFromJSONObject(json, "expireDate"),JSON_DATE_FORMAT);
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public int getInputAccount() {
        return inputAccount;
    }

    public void setInputAccount(int inputAccount) {
        this.inputAccount = inputAccount;
    }

    public int getOutputAccount() {
        return outputAccount;
    }

    public void setOutputAccount(int outputAccount) {
        this.outputAccount = outputAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
    
    public ArrayList<String[]> toArrayList()
    {
        ArrayList<String[]> list = new ArrayList<String[]>();
        list.add(new String[] {"Issue date", toText(issueDate, "yyyy-MM-dd HH:mm:ss")});
        if (outputAccount > 0)
            list.add(new String[] {"Output account", String.valueOf(outputAccount)});
        if (inputAccount > 0)
            list.add(new String[] {"Input account", String.valueOf(inputAccount)});
        list.add(new String[] {"Amount", String.valueOf(amount)});
        if (description != null)
            list.add(new String[] {"Description", description});
        if (expireDate != null)
            list.add(new String[] {"Expire date", toText(expireDate, "yyyy-MM-dd HH:mm:ss")});
        return list;
    }
   
    public String getError()
    {
        return error;
    }
   
    public void setError(String error)
    {
        this.error = error;
    }
}
