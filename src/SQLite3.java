import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
// This class is the sql side of project.
public class SQLite3 {
    private static Connection conn;
    private static final String url = "jdbc:sqlite:Server.db";
    private static void runSQLQuery_noRS(String query){
        try {
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            statement.execute(query);
            conn.close();
        }catch (SQLException e){
            Log.log_msg("Got error while connecting to SQLite database.", false);
        }
    }
    private static HashMap<String, String> runSQLQuery_UsersRS(){
        HashMap<String, String> userList = new HashMap<>();
        try {
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT password, TCKN fROM Users");
            while (rs.next())
                userList.put(rs.getString(2), rs.getString(1));
            conn.close();
        }catch (SQLException e){
            Log.log_msg("Got error while connecting to SQLite database.", false);
        }
        return userList;
    }
    private static ArrayList<HashMap<String, ArrayList<String>>> runSQLQuery_BalanceRS(){
        ArrayList<HashMap<String, ArrayList<String>>> balanceList = new ArrayList<>();
        HashMap<String, ArrayList<String>> incomeList = new HashMap<>();
        HashMap<String, ArrayList<String>> expenseList = new HashMap<>();
        try {
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT Key, DateValue, AmountValue FROM IncomeTable");
            while (rs.next()){
                ArrayList<String> valueList = new ArrayList<>();
                valueList.add(rs.getString(2));
                valueList.add(rs.getString(3));
                incomeList.put(rs.getString(1), valueList);
            }
            rs = statement.executeQuery("SELECT Key, DateValue, AmountValue FROM ExpenseTable");
            while (rs.next()){
                ArrayList<String> valueList = new ArrayList<>();
                valueList.add(rs.getString(2));
                valueList.add(rs.getString(3));
                expenseList.put(rs.getString(1), valueList);
            }
            conn.close();
        }catch (SQLException e){
            Log.log_msg("Got error while connecting to SQLite database.", false);
        }
        balanceList.add(incomeList);
        balanceList.add(expenseList);
        return balanceList;
    }
    private static Residents getResident(String TCKN){
        try {
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * fROM Users WHERE TCKN = '" + TCKN + "'");
            Residents current_resident = new Residents(rs.getString(1), rs.getString(2), TCKN, rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getString(6));
            conn.close();
            return current_resident;
        }catch (SQLException e){
            Log.log_msg("Got error while connecting to SQLite database.", false);
        }
        return null;
    }
    // These three methods are alternative uses for adding residents. Two of them not used in current state.
    /*
    public static void addResident(String firstName, String lastName, String Flat, String password, String TCKN){
        String sql = "INSERT or IGNORE INTO Users(firstName, lastName, Flat, password, TCKN) VALUES ('" + firstName + "', '"+ lastName +"', '"+ Flat + "', '" + password + "', '" + TCKN + "')";
        runSQLQuery_noRS(sql);
    }*/
    /*
    public static void addResident(String firstName, String lastName, String Flat, String password, String TCKN, int ownership){
        String sql = "INSERT or IGNORE INTO Users(firstName, lastName, ownership, Flat, password, TCKN) VALUES ('" + firstName + "', '"+ lastName +"', " + ownership + ", '" + Flat + "', '" + password + "', '" + TCKN + "')";
        System.out.println(sql);
        runSQLQuery_noRS(sql);
    }*/
    public static void addResident(String firstName, String lastName, String Flat, String password, String TCKN, int ownership, int superUser){
        String sql = "INSERT or IGNORE INTO Users(firstName, lastName, ownership, Flat, superUser, password, TCKN) VALUES ('" + firstName + "', '"+ lastName +"', " + ownership + ", '" + Flat + "', " + superUser + ", '" + password + "', '" + TCKN + "')";
        runSQLQuery_noRS(sql);
    }
    public static void removeResident(String TCKN){
        String sql = "DELETE FROM Users WHERE TCKN = " + TCKN;
        runSQLQuery_noRS(sql);
    }
    public static boolean tryLogin (String TCKN, String password){
        HashMap<String, String> userList = runSQLQuery_UsersRS();
        for (Map.Entry<String, String> element : userList.entrySet()) {
            if (element.getKey().equals(TCKN) && element.getValue().equals(password)){
                Residents.current_resident = getResident(TCKN);
                return true;
            }
        }
        return false;
    }
    private static void replaceEntry(String key, String dateValue, String amountValue, boolean isIncome){
        String sql;
        if (isIncome)
            sql = "INSERT or REPLACE INTO IncomeTable(User, Key, DateValue, AmountValue) VALUES ('" + Residents.current_resident.getTCKN() + "', '" + key + "',  '" + dateValue + "', '" + amountValue + "')";
        else
            sql = "INSERT or REPLACE INTO ExpenseTable(User, Key, DateValue, AmountValue) VALUES ('" + Residents.current_resident.getTCKN() + "', '" + key + "',  '" + dateValue + "', '" + amountValue + "')";
        runSQLQuery_noRS(sql);
    }
    public static void updateSQLBalanceSheet(ArrayList<String> sourceDesc, String key, boolean isIncome){
        String dateData = sourceDesc.get(0);
        String amountData = sourceDesc.get(1);
        SQLite3.replaceEntry(key, dateData, amountData, isIncome);
    }
    public static void removeEntry(String key, boolean isIncome){
        String sql;
        if (isIncome)
            sql = "DELETE FROM IncomeTable WHERE Key = " + key;
        else
            sql = "DELETE FROM ExpenseTable WHERE Key = " + key;
        runSQLQuery_noRS(sql);
    }
    public static ArrayList<HashMap<String, ArrayList<String>>> getBalanceSheet(){
        return runSQLQuery_BalanceRS();
    }
}
