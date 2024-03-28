import java.util.*;

public abstract class Analysis {
    private static int total_incomes = 0;
    private static int total_expenses = 0;
    static public final HashMap<String, ArrayList<String>> incomes = new HashMap<>();
    static public final HashMap<String, ArrayList<String>> expenses = new HashMap<>();

    public static int getTotal_incomes() {
        return total_incomes;
    }

    public static void add_income(int incomeToAdd) {
        Analysis.total_incomes += incomeToAdd;
    }
    public static void remove_income(int incomeToRemove) { Analysis.total_incomes -= incomeToRemove; }

    public static int getTotal_expenses() {
        return total_expenses;
    }

    public static void add_expense(int expenseToAdd) {
        Analysis.total_expenses += expenseToAdd;
    }
    public static void remove_expense(int expenseToRemove) { Analysis.total_incomes -= expenseToRemove; }
    public static void update_Net() { Apartment.balance = total_incomes-total_expenses; }
    public static void update_hashMaps(){
        // UPDATE FROM SQLITE3
        ArrayList<HashMap<String, ArrayList<String>>> balanceSheet = SQLite3.getBalanceSheet();
        incomes.putAll(balanceSheet.get(0));
        expenses.putAll(balanceSheet.get(1));
        // UPDATE total_incomes & total_expenses & Apartment.balance
        for (Map.Entry<String, ArrayList<String>> element :balanceSheet.get(0).entrySet()){
            String[] amounts = element.getValue().get(1).split("DM");
            for (String amount : amounts)
                total_incomes += Integer.parseInt(amount);
        }
        for (Map.Entry<String, ArrayList<String>> element :balanceSheet.get(1).entrySet()){
            String[] amounts = element.getValue().get(1).split("DM");
            for (String amount : amounts)
                total_expenses += Integer.parseInt(amount);
        }
        update_Net();
    }
}
