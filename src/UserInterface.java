import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
// This section represents ATS's User Interface that main panel of Güncel Programlama Dilleri Final Project
public abstract class UserInterface{
    protected static JMenuItem menu_log;
    protected static JMenuItem menu_general;
    protected static JMenuItem menu_admin;
    protected static JMenuItem menu_logout;
    protected static JMenuItem menu_login;
    protected static JMenuItem menu_resident;
    protected static JTextArea log_area;
    protected static JMenuBar mb;
    protected static final JLabel balanceInformationLabel = new JLabel("Waiting for action...");
    protected static final JLabel residentInformationLabel = new JLabel("Waiting for action...");
    public static void main(String[] args) {
        // ATS FRAME
        JFrame frame = new JFrame("ATS User Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(922,485);
        frame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        ClearFrameContext(frame);
    }
    private static void ClearFrameContext(JFrame frame){
        frame.getContentPane().removeAll();
        frame.repaint();
        // ATS MENUBAR & MENU ITEMS
        mb = new JMenuBar();
        mb.setLayout(new GridLayout(1,0));
        menu_log = new JMenuItem("LOG");
        menu_admin = new JMenuItem("ADMIN");
        menu_general = new JMenuItem("GENERAL");
        menu_login = new JMenuItem("LOGIN");
        menu_resident = new JMenuItem("RESIDENT");
        menu_logout = new JMenuItem("LOG OUT");
        if (Residents.current_resident == null)
            mb.add(menu_login);
        else {
            if(Residents.current_resident.getSuperUser()){
                mb.add(menu_log);
                mb.add(menu_general);
                mb.add(menu_admin);
            }
            else {
                mb.add(menu_resident);
            }
            mb.add(menu_logout);
        }
        // ATS LISTENERS
        menu_log.addActionListener(e -> PrintLogArea(frame));
        menu_general.addActionListener(e -> PrintGeneral(frame));
        menu_admin.addActionListener(e -> PrintAdmin(frame));
        menu_login.addActionListener(e -> PrintLogin(frame));
        menu_logout.addActionListener(e -> PrintLogout(frame));
        menu_resident.addActionListener(e -> PrintResident(frame));
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.setVisible(true);
    }
    private static void PrintLogArea(JFrame frame){
        ClearFrameContext(frame);
        log_area = new JTextArea();
        Scanner myReader;
        try {
            File myObj = new File("log.txt");
            myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                log_area.append(data + "\n");
            }
            myReader.close();
            JScrollPane scroll = new JScrollPane(log_area,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            frame.getContentPane().add(scroll, BorderLayout.CENTER);
        } catch (IOException ignored) {}
        frame.setVisible(true);
    }

    private static TableModel toTableModel(Map <String,ArrayList<String>> map) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[] { "DESCRIPTION", "DATE", "AMOUNT" }, 0);
        for (Map.Entry<String, ArrayList<String>> hashmap : map.entrySet()) {
            ArrayList<String> description = hashmap.getValue();
            ArrayList<String> dateList = new ArrayList<>(Arrays.asList(description.get(0).split("DM")));
            ArrayList<String> amountList = new ArrayList<>(Arrays.asList(description.get(1).split("DM")));
            for (int i = 0; i <= Math.min(dateList.size(), amountList.size()) - 1; i++) {
                model.addRow(new Object[]{hashmap.getKey(), dateList.get(i), amountList.get(i)});
            }
        }
        return model;
    }
    private static void PrintGeneral(JFrame frame) {

        ClearFrameContext(frame);
        JLabel incomesLabel = new JLabel("INCOMES");
        JLabel expensesLabel = new JLabel("EXPENSES");
        JTable incomeTable = new JTable(toTableModel(Analysis.incomes));
        JTable expenseTable = new JTable(toTableModel(Analysis.expenses));
        JTextField net = new JTextField("TOTAL INCOME: " + Analysis.getTotal_incomes() + " TL\tTOTAL EXPENSE: " + Analysis.getTotal_expenses() + " TL\tNET: " + Apartment.balance + " TL");
        net.setEditable(false);
        JScrollPane incomePane = new JScrollPane(incomeTable);
        JScrollPane expensePane = new JScrollPane(expenseTable);
        JScrollPane netPane = new JScrollPane(net);
        frame.add(incomesLabel, BorderLayout.WEST);
        frame.add(expensesLabel, BorderLayout.EAST);
        frame.add(incomePane, BorderLayout.WEST);
        frame.add(expensePane, BorderLayout.EAST);
        frame.add(netPane, BorderLayout.SOUTH);
        frame.validate();
        frame.pack();
    }
    private static void PrintAdmin(JFrame frame){
        ClearFrameContext(frame);
        //ATS ADMIN PANELS
        JPanel bothPanel = new JPanel();
        JPanel balancePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JPanel residentPanel = new JPanel(new GridLayout(20, 1));

        bothPanel.setLayout(new GridLayout(1,2));
        bothPanel.add(balancePanel);
        bothPanel.add(residentPanel);
        //ATS ADMIN JITEMS
        JTextField IncomeField = new JTextField(2);
        JTextField IncomeDateField = new JTextField(4);
        JTextField IncomeDescriptionField = new JTextField(6);
        JTextField ExpenseField = new JTextField(2);
        JTextField ExpenseDateField = new JTextField(4);
        JTextField ExpenseDescriptionField = new JTextField(6);
        JButton addIncomeButton = new JButton("ADD");
        JButton removeIncomeButton = new JButton("REMOVE");
        JButton addExpenseButton = new JButton("ADD");
        JButton removeExpenseButton = new JButton("REMOVE");
        balancePanel.add(new JLabel("I | Desc"));
        balancePanel.add(IncomeDescriptionField);
        balancePanel.add(new JLabel("Amount"));
        balancePanel.add(IncomeField);
        balancePanel.add(addIncomeButton);
        balancePanel.add(new JLabel("Date"));
        balancePanel.add(IncomeDateField);
        balancePanel.add(removeIncomeButton);
        balancePanel.add(new JLabel("E | Desc"));
        balancePanel.add(ExpenseDescriptionField);
        balancePanel.add(new JLabel("Amount"));
        balancePanel.add(ExpenseField);
        balancePanel.add(addExpenseButton);
        balancePanel.add(new JLabel("Date"));
        balancePanel.add(ExpenseDateField);
        balancePanel.add(removeExpenseButton);
        balancePanel.add(balanceInformationLabel);

        residentPanel.add(new JLabel("<html>RESIDENT<br/></html>"));
        JTextField residentFirstNameField = new JTextField(6);
        JTextField residentLastNameField = new JTextField(6);
        JTextField residentTCKNField = new JTextField(6);
        JTextField residentFlatField = new JTextField(6);
        JTextField residentOwnershipField = new JTextField(6);
        JTextField residentPasswordField = new JTextField(6);
        JTextField residentSuperUserField = new JTextField(6);
        JButton addResidentButton = new JButton("ADD");
        JButton removeResidentButton = new JButton("REMOVE");
        residentPanel.add(new JLabel("TCKN"));
        residentPanel.add(residentTCKNField);
        residentPanel.add(new JLabel("First Name\t"));
        residentPanel.add(residentFirstNameField);
        residentPanel.add(new JLabel("Last Name\t"));
        residentPanel.add(residentLastNameField);
        residentPanel.add(new JLabel("Flat\t"));
        residentPanel.add(residentFlatField);
        residentPanel.add(new JLabel("Ownership (1 for true; 0 for false)\t"));
        residentPanel.add(residentOwnershipField);
        residentPanel.add(new JLabel("Password\t"));
        residentPanel.add(residentPasswordField);
        residentPanel.add(new JLabel("Super User (1 for true; 0 for false)\t"));
        residentPanel.add(residentSuperUserField);
        residentPanel.add(addResidentButton);
        residentPanel.add(removeResidentButton);
        residentPanel.add(residentInformationLabel);

        frame.add(bothPanel);
        frame.setVisible(true);
        addIncomeButton.addActionListener(e -> addEntry(IncomeField, IncomeDescriptionField, true));
        removeIncomeButton.addActionListener(e -> removeEntry(IncomeField, IncomeDescriptionField, IncomeDateField,true));
        addExpenseButton.addActionListener(e -> addEntry(ExpenseField, ExpenseDescriptionField, false));
        removeExpenseButton.addActionListener(e -> removeEntry(ExpenseField, ExpenseDescriptionField, ExpenseDateField,false));
        addResidentButton.addActionListener(e -> addResident(residentFirstNameField, residentLastNameField, residentFlatField, residentPasswordField, residentTCKNField, residentOwnershipField, residentSuperUserField));
        removeResidentButton.addActionListener(e -> removeResident(residentTCKNField));
    }
    private static boolean tryValidResident(JTextField firstNameField, JTextField lastNameField, JTextField flatField, JTextField passwordField, JTextField TCKNField, JTextField ownershipField, JTextField superUserField){
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || flatField.getText().isEmpty() || passwordField.getText().isEmpty() || TCKNField.getText().isEmpty())
            return false;
        try{
            if (Integer.parseInt(ownershipField.getText()) > 1 || Integer.parseInt(ownershipField.getText()) < 0 || Integer.parseInt(superUserField.getText()) > 1 || Integer.parseInt(superUserField.getText()) < 0)
                return false;
        } catch (NumberFormatException e){ return false; }
        return true;
    }
    public static void addResident(JTextField firstNameField, JTextField lastNameField, JTextField flatField, JTextField passwordField, JTextField TCKNField, JTextField ownershipField, JTextField superUserField) {
        if (tryValidResident(firstNameField, lastNameField, flatField, passwordField, TCKNField, ownershipField, superUserField)){
            SQLite3.addResident(firstNameField.getText(), lastNameField.getText(), flatField.getText(), passwordField.getText(), TCKNField.getText(), Integer.parseInt(ownershipField.getText()), Integer.parseInt(superUserField.getText()));
            residentInformationLabel.setText("User with " + TCKNField.getText() + " TCKN has successfully registered.");
            Log.log_msg("User with " + TCKNField.getText() + " TCKN has successfully registered.", true);
            return;
        }
        residentInformationLabel.setText("Enter valid values.");
    }
    public static void removeResident(JTextField TCKNField) {
        if (!TCKNField.getText().isEmpty()){
            SQLite3.removeResident(TCKNField.getText());
            residentInformationLabel.setText("Removed user with " + TCKNField.getText() + " TCKN if exists.");
            Log.log_msg("Removed user with " + TCKNField.getText() + " TCKN if exists.", true);
            return;
        }
        residentInformationLabel.setText("Enter valid TCKN.");
    }
    private static int tryValidAmountValue(JTextField amountField){
        int amountFieldValue = -1;
        if (!amountField.getText().isEmpty()){
            try{
                if (Integer.parseInt(amountField.getText()) > 0)
                    amountFieldValue = Integer.parseInt(amountField.getText());
            } catch (NumberFormatException e){ balanceInformationLabel.setText("Enter valid amount");}
        }
        else
            balanceInformationLabel.setText("Enter valid value");
        return amountFieldValue;
    }
    private static int tryValidAmountValue(JTextField amountField, JTextField descriptionField){
        int amountFieldValue = -1;
        if (!amountField.getText().isEmpty() && !descriptionField.getText().isEmpty()){
            try{
                if (Integer.parseInt(amountField.getText()) > 0)
                    amountFieldValue = Integer.parseInt(amountField.getText());
            } catch (NumberFormatException e){ balanceInformationLabel.setText("Enter valid amount");}
        }
        else
            balanceInformationLabel.setText("Enter valid values");
        return amountFieldValue;
    }
    private static int tryValidValues(JTextField amountField, JTextField descriptionField, JTextField dateField){
        int amountFieldValue = -1;
        if (!amountField.getText().isEmpty() && !descriptionField.getText().isEmpty() && !dateField.getText().isEmpty()){
            try{
                if (Integer.parseInt(amountField.getText()) > 0)
                    amountFieldValue = Integer.parseInt(amountField.getText());
            } catch (NumberFormatException e){ balanceInformationLabel.setText("Enter valid amount");}
        }
        else
            balanceInformationLabel.setText("Enter valid values");
        return amountFieldValue;
    }
    private static void addEntry(JTextField amountField, JTextField descriptionField, boolean isIncome){
        int amount = tryValidAmountValue(amountField, descriptionField);
        if (amount>0){
            Apartment.addEntry(amount, descriptionField.getText(), isIncome);
            if (isIncome){
                balanceInformationLabel.setText(amount + " TL amount income entry has successfully entered.");
                return;
            }
            balanceInformationLabel.setText(amount + " TL amount expense entry has successfully entered.");
        }
    }
    private static void depositIncome(JTextField amountField){
        int amount = tryValidAmountValue(amountField);
        if (amount>0){
            Apartment.addEntry(amount, Residents.current_resident, true);
            balanceInformationLabel.setText(amount + " TL amount income entry has successfully entered.");
        }
    }
    private static void removeEntry(JTextField amountField, JTextField descriptionField, JTextField dateField, boolean isIncome){
        int amount = tryValidValues(amountField, descriptionField, dateField);
        if (amount>0){
            Apartment.removeEntry(amount, descriptionField.getText(), dateField.getText(), isIncome);
            if (isIncome){
                balanceInformationLabel.setText(amount + " TL amount income entry has successfully removed.");
                return;
            }
            balanceInformationLabel.setText(amount + " TL amount entry has successfully removed.");
        }
    }
    private static void PrintLogout(JFrame frame){
        ClearFrameContext(frame);
        Residents.current_resident = null;
        ClearFrameContext(frame);
    }
    private static void PrintLogin(JFrame frame){
        ClearFrameContext(frame);
        JPanel residentPanel = new JPanel();
        JTextField TCKNField = new JTextField(5);
        JTextField passwordField = new JTextField(5);
        JButton loginButton = new JButton("LOGIN");
        residentPanel.add(new JLabel("TCKN:"));
        residentPanel.add(TCKNField);
        residentPanel.add(new JLabel("PASSWORD: :"));
        residentPanel.add(passwordField);
        residentPanel.add(loginButton);
        balanceInformationLabel.setText("Waiting for action...");
        residentPanel.add(balanceInformationLabel);
        frame.add(residentPanel);
        frame.setVisible(true);
        loginButton.addActionListener(e -> LoginFunc(frame, TCKNField.getText(), passwordField.getText()));
    }
    private static void LoginFunc(JFrame frame, String TCKN, String password){
        if (TCKN.isEmpty() || password.isEmpty())
            balanceInformationLabel.setText("Please enter valid values.");
        else if (SQLite3.tryLogin(TCKN, password))
            ClearFrameContext(frame);
        else
            balanceInformationLabel.setText("Incorrect login information.");
    }
    private static void PrintResident(JFrame frame){
        ClearFrameContext(frame);
        JPanel residentPanel = new JPanel();
        JTextField amountField = new JTextField(5);
        JButton depositButton = new JButton("DEPOSIT");
        residentPanel.add(new JLabel("Amount of entry:"));
        residentPanel.add(amountField);
        residentPanel.add(depositButton);
        balanceInformationLabel.setText("Waiting for action...");
        residentPanel.add(balanceInformationLabel);
        frame.add(residentPanel);
        frame.setVisible(true);
        depositButton.addActionListener(e -> depositIncome(amountField));
    }
}