package BSP;

import java.util.*;
import org.json.JSONObject;
import static BSP.BSPConstant.*;

/**
 * Created by nathan on 13/5/17.
 */
public class SystemController {
    private List<Customer> customers = new ArrayList<Customer>();
    private Customer customer;
    private DataManager dataManager = new DataManager();
    public SystemController() {
    }

    public void main()
    {
        List<JSONObject> jsonCustomers = dataManager.readFile();
        for (JSONObject j: jsonCustomers)
        {
            customers.add(new Customer(j));
        }
    }

    public boolean checkLoginInfo(String username, String password)
    {
        for (Customer c: customers)
        {
            if (String.valueOf(c.getCustomerId()).equals(username))
            {
                if (c.getPassword().equals(password))
                {
                    customer = c;
                    customer.updateLastLogin();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAdmin(String username, String password)
    {
        return username.equals(SUPER_ID) && password.equals(SUEPR_PASSWORD);
    }

    public int[] createCustomer(String password, String fname, String lname, Date dob, String state, String city, String district, String street, String unit, String postcode, String emailAddress, String pin) {
        int customerId = getAvailableCustomerId();
        Customer newCustomer = new Customer(customerId, password, fname, lname, dob, state, city, district, street, unit, postcode, emailAddress, pin, getAvailableAccountId(0));
        if (customers.add(newCustomer))
        {
            return new int[] {newCustomer.getCustomerId(), newCustomer.getSavingAccount().getAccountId()};
        }
        else
        {
            return null;
        }
    }

    public boolean deleteCustomer(Customer customer) {
        return customers.remove(customer);
    }

    public int getAvailableCustomerId()
    {
        if (customers.isEmpty())
        {
            return 100001;
        }
        else
        {
            return customers.get(customers.size() - 1).getCustomerId() + 1;
        }
    }

    public void close()
    {
        List<String> jsons = new ArrayList<String>();
        for (Customer c: customers)
        {
            jsons.add(c.toString());
        }
        dataManager.writeFile(jsons);
    }
    
    public int getAvailableAccountId(int index)
    {
        int maxNumber = INITIAL_ACCONT_ID[index];
        for (Customer c: customers)
        {
            if (maxNumber < c.getAccounts()[index].getAccountId())
            {
                maxNumber = c.getAccounts()[index].getAccountId();
            }
        }
        return maxNumber + 1;
    }
    
    public int getAccountIndex(String accountType)
    {
        int index = 0;
        for (int i = 0; i < ACCOUNT_TYPES.length; i++)
        {
            if (accountType.equalsIgnoreCase(ACCOUNT_TYPES[i]))
            {
                index = i;
                break;
            }
        }
        return index;
    }
    
    public String getCustomerName()
    {
        return customer.getFname() + " " + customer.getLname();
    }
    
    public String[] withdraw(String accountType, double amount)
    {
        if (accountType.equalsIgnoreCase(ACCOUNT_TYPES[2]) && !customer.canCreateHomeLoan(amount))
        {
            return new String[] {"", "Not enough fund in saving account"};
        }
        ArrayList<String[]> list = new ArrayList<>();
        String[] array = new String[] {"", ""};
        String string = "";
        Transaction transaction = customer.getAccounts()[getAccountIndex(accountType)].withdraw(amount, 0);
        if (transaction.getError().isEmpty())
        {
            for (String[] s: transaction.toArrayList())
            {
                string += "\n" + s[0] + ": " + s[1];
            }
            array[0] = string;
        }
        else 
            array[1] = transaction.getError();
        return array;
    }
    
    public String[] deposit(String accountType, double amount)
    {
        String[] array = new String[] {"", ""};
        Transaction transaction = customer.getAccounts()[getAccountIndex(accountType)].deposit(amount, 0);
        if (transaction.getError().isEmpty())
        {
            String string = "";
            for (String[] s: transaction.toArrayList())
            {
                string += "\n" + s[0] + ": " + s[1];
            }
            array[0] = string;
        }
        else 
            array[1] = transaction.getError();
        return array;
    }
    
    public String[] termDeposit(double amount, int term)
    {
        String[] array = new String[] {"", ""};
        Transaction transaction = customer.getAccounts()[3].deposit(amount, 0);
        if (transaction.getError().isEmpty())
        {
            String string = "Term:" + TD_TERMS_ARRAY[term];
            for (String[] s: transaction.toArrayList())
            {
                string += "\n" + s[0] + ": " + s[1];
            }
            array[0] = string;
        }
        else 
            array[1] = transaction.getError();
        return array;
    }
    
    public String[] transfer(String outputAccountType, String  inputAccountType, double amount)
    {
        String[] array = new String[] {"", ""};
        Transaction transaction = customer.transfer(getAccountIndex(outputAccountType), getAccountIndex(inputAccountType), amount);
        if (transaction.getError().isEmpty())
        {
            String string = "";
            for (String[] s: transaction.toArrayList())
            {
                string += "\n" + s[0] + ": " + s[1];
            }
            array[0] = string;
        }
        else 
            array[1] = transaction.getError();
        return array;
    }
    
    public String createAccount(String accountType)
    {
        int index = getAccountIndex(accountType);
        return String.valueOf(customer.createAccount(index, getAvailableAccountId(index)).getAccountId());
    }
    
    public ArrayList<String> getExistAccountList()
    {
        return customer.getExistAccounts();
    }
    
    public ArrayList<String> getNonExistAccountList()
    {
        return customer.getNonExistAccounts();
    }
    
    public void countWrongPin()
    {
        customer.countWrongPin();
    }
    
    public boolean isCustomerCanTransfer()
    {
        return customer.canTransfer();
    }
    
    public boolean isPin(String pin)
    {
        return customer.getPin().equals(pin);
    }
    
    public List<Double> getBalances()
    {
        return customer.getBalances();
    }
}
