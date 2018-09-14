package BSP;

import java.util.*;
import static BSP.Time.*;
import static BSP.BSPConstant.*;
/**
 * Write a description of class UIController here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class UIController
{
    int tmp = 0;
    private SystemController controller = new SystemController();
    public UIController()
    {
    }
    
    public void main()
    {
        controller.main();
        boolean optionInvalid = false;
        while (!optionInvalid)
        {
            showRoleOption();
            String option = getUserInput();
            if (option.equals("1"))
            {
                if (loginAsCustomer())
                    showCustomerHomePage();
            }
            else if (option.equals("2"))
            {
                if (loginAsAdmin())
                    showAdminHomePage();
            }
            else if (option.equals("3"))
            {
                controller.close();
                System.exit(0);
            }
            else
            {
                showOptionInvalid();
                waitReading();
            }
        }
    }
    
    public void showRoleOption()
    {
        cleanPage();
        System.out.println("========================================");
        System.out.println("Welcome to the Monash Bank System");
        System.out.println("========================================");
        System.out.println("(1) Login as customer");
        System.out.println("(2) Login as administrator");
        System.out.println("(3) Exit System");
        System.out.println("==============================");
        System.out.println("Choose an option: ");
    }
    
    public String getUserInput()
    {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        return input;
    }
    
    public boolean showCustomerLoginPage()
    {
        cleanPage();
        System.out.println("========================================");
        System.out.println("Welcome to the Monash Bank System");
        System.out.println("========================================");
        System.out.println("Please enter customer ID:");
        String customerId = getUserInput();
        System.out.println("Please enter password:");
        String password = getUserInput();
        boolean loginInfoMatched = controller.checkLoginInfo(customerId, password);
        if (!loginInfoMatched)
        {
            System.out.println("");
            System.out.println(">> Warning: Customer ID and password not matched.");
            System.out.println(">> Please enter again.");
            waitReading();
        }
        return loginInfoMatched;
    }
    
    public void cleanPage()
    {
        System.out.println('\f');
    }
    
    public void waitReading()
    {
        try
        {
            Thread.sleep(1500);
        } 
        catch (Exception e)
        {
        }
    }
    
    public boolean loginAsCustomer()
    {
        boolean loginInfoMatched = false;
        while (!loginInfoMatched)
        {
            cleanPage();
            System.out.println("========================================");
            System.out.println("Welcome to the Monash Bank System");
            System.out.println("========================================");
            System.out.println("Please enter customer ID:");
            String customerId = getUserInput();
            if (customerId.equals("-b"))
                return false;
            System.out.println("Please enter password:");
            String password = getUserInput();
            if (password.equals("-b"))
                return false;
            loginInfoMatched = controller.checkLoginInfo(customerId, password);
            if (!loginInfoMatched)
            {
                System.out.println("");
                System.out.println(">> Warning: Customer ID and password not matched.");
                System.out.println(">> Please enter again.");
                waitReading();
            }
        }
        return true;
    }
    
    public void showCustomerHomePage()
    {
        boolean exit = false;
        while (!exit)
        {
            cleanPage();
            System.out.println("========================================");
            System.out.println("Hello, " + controller.getCustomerName() + "!");
            System.out.println("========================================");
            System.out.println("(1) Withdraw/Lend");
            System.out.println("(2) Deposit/Repay");
            System.out.println("(3) Transfer");
            System.out.println("(4) View accounts");
            System.out.println("(5) Create an account");
            System.out.println("(6) Go back");
            System.out.println("========================================");
            System.out.println("Choose an option: ");
            String option = getUserInput();
            switch (option)
            {
                case "1":
                    showWithdrawPage();
                    break;
                case "2":
                    showDepositPage();
                    break;
                case "3":
                    if (showInputPinPage())
                        showTransferPage();
                    break;
                case "4":
                    showViewAccountsPage();
                    break;
                case "5":
                    showCreateAccountPage();
                    break;
                case "6":
                    exit = true;
                    break;
                default:
                    showError("Invalid option.");
                    break;
            }
        }
    }
    
    public boolean loginAsAdmin()
    {
        boolean isLogin = false;
        while (!isLogin)
        {
            cleanPage();
            System.out.println("========================================");
            System.out.println("Hi, administrator!");
            System.out.println("========================================");
            System.out.println("Please enter administrator ID:");
            String adminId = getUserInput();
            if (adminId.equals("-b"))
                return false;
            System.out.println("Please enter password:");
            String password = getUserInput();
            if (password.equals("-b"))
                return false;
            Boolean loginInfoMatched = controller.isAdmin(adminId, password);
            if (!loginInfoMatched)
            {
                System.out.println("");
                System.out.println(">> Warning: Administrator ID and password not matched.");
                System.out.println(">> Please enter again.");
                waitReading();
            }
            else
            {
                isLogin = true;
            }
        }
        return true;
    }
    
    public void showOptionInvalid()
    {
        System.out.println("");
        System.out.println(">> Warning: Invalid option.");
        System.out.println(">> Please enter again.");
    }
    
    public void showAdminHomePage()
    {
        boolean exit = false;
        while (!exit)
        {
            cleanPage();
            System.out.println("========================================");
            System.out.println("Hi, administrator!");
            System.out.println("========================================");
            System.out.println("(1) Create a customer");
            System.out.println("(2) Search a customer");
            System.out.println("(3) Go back");
            System.out.println("========================================");
            System.out.println("Choose an option: ");
            String option = getUserInput();
            if (option.equals("1"))
            {
                showCreateCustomerPage();
            }
            else if (option.equals("2"))
            {
            }
            else if (option.equals("3"))
            {
                exit = true;
            }
            else
            {
            }
        }
    }
    
    public void showCreateCustomerPage()
    {
        String fname = null;
        String lname = null;
        String state = null;
        String city = null;
        String district = null;
        String street = null;
        String unit = null;
        String postcode = null;
        String emailAddress = null;
        String pin = null;
        String password = null;
        String confirmPassword;
        Date dob = null;
        cleanPage();
        System.out.println("========================================");
        System.out.println("Create a customer");
        System.out.println("========================================");
        boolean goNext = false;
        while (!goNext)
        {
            System.out.println("Customer first name:");
            fname = getUserInput();
            goNext = validateFieldNotNull(fname);
        }
        goNext = false;
        while (!goNext)
        {
            System.out.println("Customer last name: ");
            lname = getUserInput();
            goNext = validateFieldNotNull(lname);
        }
        goNext = false;
        while (!goNext)
        {
            System.out.println("Customer date of birth: ");
            String dobString = getUserInput();
            dob = toDate(dobString, "yyyy-MM-dd");
            if (dob == null)
            {
                System.out.println("");
                System.out.println(">> Warning: Invalid date Format.");
                System.out.println("Accepted format: yyyy-MM-dd");
                System.out.println("Please enter again.");
                System.out.println("");
            }
            else
            {
                goNext = true;
            }
        }
        goNext = false;
        while (!goNext)
        {
            System.out.println("State:");
            state = getUserInput();
            goNext = validateFieldNotNull(state);
        }
        goNext = false;
        while (!goNext)
        {
            System.out.println("City: ");
            city = getUserInput();
            goNext = validateFieldNotNull(city);
        }
        goNext = false;
        while (!goNext)
        {
            System.out.println("District: ");
            district = getUserInput();
            goNext = validateFieldNotNull(district);
        }
        goNext = false;
        while (!goNext)
        {
            System.out.println("Street: ");
            street = getUserInput();
            goNext = validateFieldNotNull(street);
        }
        goNext = false;
        while (!goNext)
        {
            System.out.println("Unit (enter 0 to skip): ");
            unit = getUserInput();
            goNext = validateFieldNotNull(unit);
        }
        goNext = false;
        while (!goNext)
        {
            System.out.println("Postcode: ");
            postcode = getUserInput();
            goNext = validateFieldNotNull(postcode);
        }
        goNext = false;
        while (!goNext)
        {
            System.out.println("Email address:");
            emailAddress = getUserInput();
            goNext = validateFieldNotNull(emailAddress);
        }
        goNext = false;
        while (!goNext)
        {
            System.out.println("PIN: ");
            pin = getUserInput();
            goNext = validateFieldNotNull(pin) && validatePin(pin);
        }
        goNext = false;
        while (!goNext)
        {
            System.out.println("Password: ");
            password = getUserInput();
            if (!validateFieldNotNull(password))
                continue;
            System.out.println("Confirm Password: ");
            confirmPassword = getUserInput();
            goNext = validatePasswordConfirmation(password, confirmPassword);
        }
        int[] array = controller.createCustomer(password, fname, lname, dob, state, city, district, street, unit, postcode, emailAddress, pin);  
        if (array.length > 0)
        {
            System.out.println("Create customer successfully");
            System.out.println("Please remember Customer ID: " + array[0]);
            System.out.println("Please remember Saving Account ID: " + array[1]);
            System.out.println("Press enter to go back...");
            getUserInput();
        }
    }
    
    public void showNotNullWarning()
    {
        System.out.println("");
        System.out.println(">> Warning: This field cannot be empty.");
        System.out.println("Please enter again");
        System.out.println("");
    }
    
    public boolean validateFieldNotNull(String input)
    {
        if (input.isEmpty())
        {
            showNotNullWarning();
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public boolean validatePin(String pin)
    {
        if (pin.length() != 4)
        {
            showError("PIN number must be a 4-digit number.");
            return false;
        }
        else if (!isNumeric(pin))
        {
            showError("PIN number must be a 4-digit number.");
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public boolean validatePasswordConfirmation(String password, String confirmPassword)
    {
        if (password.equals(confirmPassword))
        {
            System.out.println("");
            System.out.println(">> Warning: Different password.");
            System.out.println("Please enter again");
            System.out.println("");
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean showWithdrawPage()
    {
        boolean exit = false;
        while (!exit)
        {
            cleanPage();
            System.out.println("========================================");
            System.out.println("Withdraw/Lend");
            System.out.println("========================================");
            System.out.println("Account List");
            ArrayList<String> list = showAccounts();
            int option = showAccountChoosePart(list.size());
            if (option == -1)
            {
                return false;
            }
            double amount = showAmountInputPart();
            if (amount == -1)
                return false;
            String[] result = controller.withdraw(list.get(option - 1), amount);
            if (result[1].isEmpty())
            {
                showSuccess();
                showTransaction(result[0]);
                pressBack();
            }
            else
            {
                showError(result[1]);
                pressBack();
            }
            exit = true;
        }
        return true;
    }
    
    public ArrayList<String> showAccounts()
    {
        ArrayList<String> accountList = controller.getExistAccountList();
        List<Double> balanceList = controller.getBalances();
        for (int i = 0; i < accountList.size(); i++)
        {
                System.out.println("(" + (i + 1) + ") " + accountList.get(i) + " (" + balanceList.get(i) + ")");
        }
        System.out.println("");
        return accountList;
    }
    
    public void showSuccess()
    {
        System.out.println("");
        System.out.println("=> Success");
    }
    
    public void showError(String error)
    {
        System.out.println("");
        System.out.println(">> Warning: " + error);
        //System.out.println("Please enter again");
        System.out.println("");
    }
    
    public void pressBack()
    {
        System.out.println("Press enter to go back...");
        getUserInput();
    }
    
    public boolean showDepositPage()
    {
        boolean exit = false;
        while (!exit)
        {
            cleanPage();
            System.out.println("========================================");
            System.out.println("Deposit/Repay");
            System.out.println("========================================");
            System.out.println("Account List");
            ArrayList<String> list = showAccounts();
            int option = showAccountChoosePart(list.size());
            if (option == -1)
                return false;
            if (list.get(option).equalsIgnoreCase(ACCOUNT_TYPES[3]))
                showChooseTermOption();
            else
                tmp = 0;
            double amount = showAmountInputPart();
            if (amount == -1)
                return false;
            String[] result;
            if (tmp == 0)
            {
                result = controller.deposit(list.get(option - 1), amount);
            }
            else
            {
                result = controller.termDeposit(amount, tmp);
            }
            if (result[1].isEmpty())
            {
                showSuccess();
                showTransaction(result[0]);
                pressBack();
            }
            else
            {
                showError(result[1]);
                pressBack();
            }
            exit = true;
        }
        return true;
    }
    
    public int showAccountChoosePart(int index)
    {
        int option = 0;
        boolean accountChosen = false;
        while (!accountChosen)
        {
            System.out.println("Please choose an account:");
            String stringOption = getUserInput();
            if (stringOption.equals("-b"))
                return -1;
            try
            {
                option = Integer.valueOf(stringOption);
            }
            catch (Exception e)
            {
            }
            if (option > 0 && option < index + 1)
            {
                accountChosen = true;
            }
            else
            {
                showError("Invalid input.");
                waitReading();
            }
        }
        return option;
    }
    
    public double showAmountInputPart()
    {
        double amount = 0;
        boolean amountInput = false;
        while (!amountInput)
        {
            System.out.println("Amount of withdraw/lend:");
            String stringAmount = getUserInput();
            if (stringAmount.equals("-b"))
                return -1;
            try
            {
                amount = Double.valueOf(stringAmount);
            }
            catch (Exception e)
            {
            }
            if (amount > 0)
            {
                amountInput = true;
            }
            else
            {
                showError("Invalid input.");
                waitReading();
            }
        }
        return amount;
    }
    
    public void showTransaction(String string)
    {
        System.out.println(string);
    }
    
    public void showViewAccountsPage()
    {
        cleanPage();
        System.out.println("========================================");
        System.out.println("View accounts");
        System.out.println("========================================");
        showAccounts();
        pressBack();
    }
    
    public boolean showTransferPage()
    {
        boolean exit = false;
        while (!exit)
        {
            cleanPage();
            System.out.println("========================================");
            System.out.println("Transfer");
            System.out.println("========================================");
            System.out.println("Account List");
            ArrayList<String> list = showAccounts();
            if (list.size() < 2)
            {
                showError("Only one account exist.");
                pressBack();
                break;
            }
            System.out.println("");
            System.out.println("-> Transfer from?");
            System.out.println("");
            int optionFrom = showAccountChoosePart(list.size());
            if (optionFrom == -1)
                return false;
            System.out.println("");
            System.out.println("-> Transfer to?");
            System.out.println("");
            int optionTo = showAccountChoosePart(list.size());
            if (optionTo == -1)
                return false;
            if (optionFrom == optionTo)
            {
                showError("Input and output account cannot be same.");
                pressBack();
                break;
            }
            double amount = showAmountInputPart();
            if (amount == -1)
                return false;
            String[] result = controller.transfer(list.get(optionFrom - 1), list.get(optionTo - 1), amount);
            if (result[1].isEmpty())
            {
                showSuccess();
                showTransaction(result[0]);
                pressBack();
            }
            else
            {
                showError(result[1]);
                pressBack();
            }
            exit = true;
        }
        return true;
    }
    
    public boolean showCreateAccountPage()
    {
        cleanPage();
        System.out.println("========================================");
        System.out.println("Create an account");
        System.out.println("========================================");
        System.out.println("Account able to be created");
        ArrayList<String> nonExistAccounts = showNonExistAccountsPage();
        if (nonExistAccounts.size() < 1)
        {
            showError("No accounts available to create.");
            pressBack();
            return false;
        }
        int option = showAccountChoosePart(nonExistAccounts.size());
        if (option == -1)
            return false;
        String accountId = controller.createAccount(nonExistAccounts.get(option - 1));
        showSuccess();
        System.out.println("Please remember your accountId: " + accountId);
        pressBack();
        return true;
    }
    
    public ArrayList<String> showNonExistAccountsPage()
    {
        ArrayList<String> accountList = controller.getNonExistAccountList();
        int i = 0;
        for (String s: accountList)
        {
            i++;
            System.out.println("(" + i + ") " + s);
        }
        System.out.println("");
        return accountList;
    }
    
    public boolean showInputPinPage()
    {
        cleanPage();
        boolean exit = false;
        while (!exit)
        {
            if (controller.isCustomerCanTransfer())
            {
                System.out.println("Please input 4-digial PIN number:");
                System.out.println("Enter -b to go back");
                String pinNumber = getUserInput();
                exit = controller.isPin(pinNumber);
                if(pinNumber.equals("-b"))
                {
                    return false;
                }
                if (!exit)
                {
                    controller.countWrongPin();
                    showError("PIN number not matched.");
                }
            }
            else
            {
                showError("Input wrong PIN number three times a day.\nYour transfer function has been locked.\nPlease contact administrator to unlock.");
                pressBack();
                return false;
            }
        }
        return true;
    }
    
    public static boolean isNumeric(String numberString){  
        for (int i = 0; i < numberString.length(); i++)
        {    
            if (!Character.isDigit(numberString.charAt(i)))
            {  
                return false;  
            }  
        }  
        return true;  
    }  
    
    public int showChooseTermOption()
    {
        System.out.println("Which term do you want to choose?");
        System.out.println("(1) 3-month");
        System.out.println("(2) 6-month");
        System.out.println("(3) 12-month");
        boolean exit = false;
        while (!exit)
        {
            System.out.println("Please choose an option:");
            int option = Integer.valueOf(getUserInput());
            if (option > 0 && option < 4)
            {
                tmp = option;
                return option;
            }
        }
        return 0;
    }
}
