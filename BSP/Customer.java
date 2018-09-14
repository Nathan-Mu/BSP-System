package BSP;

import java.util.Date;
import java.util.List;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import static BSP.JSONReader.*;
import static BSP.Time.*;

/**
 * Created by nathan on 13/5/17.
 */
public class Customer {
    private int customerId = -1;
    private String password;
    private String fname;
    private String lname;
    private Date dob;
    private String state;
    private String city;
    private String district;
    private String street;
    private String unit;
    private String postcode;
    private String emailAddress;
    private String pin;
    private String tfn;
    private int timesInputWrongPin = 0;
    private Date lastLogin;
    private boolean isTransferLocked = false;
    private Account[] accounts = {new SavingAccount(), new CreditAccount(), new HomeLoanAccount(), new TermDepositAccount()};

    public Customer() {
    }

    public Customer(int customerId, String password, String fname, String lname, Date dob, String state, String city, 
                    String district, String street, String unit, String postcode, String emailAddress, String pin,
                    int savingAccountId) 
    {
        this.customerId = customerId;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.state = state;
        this.city = city;
        this.district = district;
        this.street = street;
        this.unit = unit;
        this.postcode = postcode;
        this.emailAddress = emailAddress;
        this.pin = pin;
        this.accounts[0].setAccountId(savingAccountId);
    }
    
    public Customer(JSONObject json)
    {
        this.customerId = getIntegerFromJSONObject(json,"customerId");
        this.password = getStringFromJSONObject(json, "password");
        this.fname = getStringFromJSONObject(json, "fname");
        this.lname = getStringFromJSONObject(json, "lname");
        this.dob = toDate(getStringFromJSONObject(json, "dob"), JSON_DATE_FORMAT);
        this.state = getStringFromJSONObject(json, "state");
        this.city = getStringFromJSONObject(json, "city");
        this.district = getStringFromJSONObject(json, "district");
        this.street = getStringFromJSONObject(json, "street");
        this.unit = getStringFromJSONObject(json, "unit");
        this.postcode = getStringFromJSONObject(json, "postcode");
        this.emailAddress = getStringFromJSONObject(json, "emailAddress");
        this.pin = getStringFromJSONObject(json, "pin");
        JSONArray accountArray = getJSONArrayFromJSONObject(json, "accounts");
        this.accounts[0] = new SavingAccount(getJSONObjectFromArray(accountArray, 0));
        this.accounts[1] = new CreditAccount(getJSONObjectFromArray(accountArray, 1));
        this.accounts[2] = new HomeLoanAccount(getJSONObjectFromArray(accountArray, 2));
        this.accounts[3] = new TermDepositAccount(getJSONObjectFromArray(accountArray, 3));
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getPassword() {
        return password;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public Date getDob() {
        return dob;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getStreet() {
        return street;
    }

    public String getUnit() {
        return unit;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPin() {
        return pin;
    }

    public String getTfn() {
        return tfn;
    }
    
    public Account[] getAccounts()
    {
        return accounts;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setTfn(String tfn) {
        this.tfn = tfn;
    }

    public boolean isNull() {
        return customerId == -1;
    }
    
    public String toString()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    
    public SavingAccount getSavingAccount() {
        return (SavingAccount)accounts[0];
    }

    public void setSavingAccount(SavingAccount savingAccount) {
        this.accounts[0] = savingAccount;
    }

    public CreditAccount getCreditAccount() {
        return (CreditAccount)accounts[1];
    }

    public void setCreditAccount(CreditAccount creditAccount) {
        this.accounts[1] = creditAccount;
    }

    public HomeLoanAccount getHomeLoanAccount() {
        return (HomeLoanAccount)accounts[2];
    }

    public void setHomeLoanAccount(HomeLoanAccount homeLoanAccount) {
        this.accounts[2] = homeLoanAccount;
    }

    public TermDepositAccount getTermDepositAccount() {
        return (TermDepositAccount)accounts[3];
    }

    public void setTermDepositAccount(TermDepositAccount termDepositAccount) {
        this.accounts[3] = termDepositAccount;
    }
    
    public Transaction transfer(int outputAccountIndex, int inputAccountIndex, double amount)
    {
        Account outputAccount = getAccounts()[outputAccountIndex];
        Account inputAccount = getAccounts()[inputAccountIndex];
        Transaction mainTransaction = outputAccount.withdraw(amount, inputAccount.getAccountId());
        if (mainTransaction.getError().isEmpty())
        {
            Transaction secondaryTransaction = inputAccount.deposit(amount, outputAccount.getAccountId());
            if (secondaryTransaction.getError().isEmpty())
            {
                return mainTransaction;
            }
            else
            {
                outputAccount.rollbackWithdraw(mainTransaction);
                return secondaryTransaction;
            }
        }
        else
        {
            return mainTransaction;
        }
    }
    
    public Account createAccount(int index, int accountId)
    {
        getAccounts()[index].setAccountId(accountId);
        return getAccounts()[index];
    }
    
    public ArrayList<String> getExistAccounts()
    {
        ArrayList<String> list = new ArrayList<>();
        for (Account a: accounts)
        {
            if (a.getAccountId() > 0)
                list.add(a.getAccountType());
        }
        return list;
    }
    
    public ArrayList<Double> getBalances()
    {
        ArrayList<Double> list = new ArrayList<>();
        for (Account a: accounts)
        {
            if (a.getAccountId() > 0)
                list.add(a.getBalance());
        }
        return list;
    }
    
    public ArrayList<String> getNonExistAccounts()
    {
        ArrayList<String> list = new ArrayList<>();
        for (Account a: accounts)
        {
            if (a.getAccountId() <= 0)
                list.add(a.getAccountType());
        }
        return list;
    }
    
    public void refreshTimesInputWrongPin()
    {
        Date currentDate = getCurrentDate();
        if (!isSameDay(currentDate, lastLogin))
        {
            timesInputWrongPin = 0;
        }
        lastLogin = currentDate;
    }
    
    public void countWrongPin()
    {
        timesInputWrongPin++;
        refreshTimesInputWrongPin();
        if (timesInputWrongPin >= 3)
        {
            isTransferLocked = true;
        }
    }
    
    public boolean canTransfer()
    {
        return !isTransferLocked;
    }
    
    public void updateLastLogin()
    {
        this.lastLogin = getCurrentDate();
    }
    
    public boolean canCreateHomeLoan(double amount)
    {
        return accounts[0].getBalance() - amount * 0.01 >= 0;
    }
}
