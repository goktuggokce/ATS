import java.util.*;

public abstract class Apartment {
    protected static int balance = 0;
    public static void addEntry(int money, Residents resident, boolean positiveChange) {
        // ADD ENTRY as person
        Date date = new Date();
        String[] editedDate = date.toString().split(" ");
        final String fDate = editedDate[2] + editedDate[1] + editedDate[5] + " " + editedDate[3];
        String resident_key = resident.getFirstName() + " " + resident.getLastName();
        if (positiveChange){
            addIncome(money, resident_key, fDate);
            Log.log_msg("Flat:" + resident.getFlat() + ", " + resident_key + " is deposited " + money + " TL.", true);
        }
        else{
            addExpense(money, resident_key, fDate);
            Log.log_msg("Flat:" + resident.getFlat() + ", " + resident_key + " is entered " + money + " TL expense entry.", true);
        }
    }
    public static void addEntry(int money, String outDescription, boolean positiveChange) {
        // ADD ENTRY via description
        Date date = new Date();
        String[] editedDate = date.toString().split(" ");
        final String fDate = editedDate[2] + editedDate[1] + editedDate[5] + " " + editedDate[3];
        if (positiveChange){
            addIncome(money, outDescription, fDate);
            Log.log_msg("Administer is deposited " + money + " TL " + outDescription, true);
        }
        else{
            addExpense(money, outDescription, fDate);
            Log.log_msg("Administrator has entered " + money + " TL expense entry.", true);
        }
    }

    private static void addIncome(int money, String outDescription, String fDate) {
        // Manual admin income entry process
        balance += money;
        Analysis.add_income(money);
        if (Analysis.incomes.containsKey(outDescription)){
            ArrayList<String> description = Analysis.incomes.get(outDescription);
            Analysis.incomes.put(outDescription, new ArrayList<>(Arrays.asList(description.get(0) + "DM" + fDate, description.get(1) + "DM" + money)));
        }
        else
            Analysis.incomes.put(outDescription, new ArrayList<>(Arrays.asList(fDate, String.valueOf(money))));
        ArrayList<String> description = Analysis.incomes.get(outDescription);
        SQLite3.updateSQLBalanceSheet(description, outDescription, true);
    }

    private static void addExpense(int money, String outDescription, String fDate) {
        // Manual admin expense entry process
        balance -= money;
        Analysis.add_expense(money);
        if (Analysis.expenses.containsKey(outDescription)){
            ArrayList<String> description = Analysis.expenses.get(outDescription);
            Analysis.expenses.put(outDescription, new ArrayList<>(Arrays.asList(description.get(0) + "DM" + fDate, description.get(1) + "DM" + money)));
        }
        else
            Analysis.expenses.put(outDescription, new ArrayList<>(Arrays.asList(fDate, String.valueOf(money))));
        ArrayList<String> description = Analysis.expenses.get(outDescription);
        SQLite3.updateSQLBalanceSheet(description, outDescription, false);
    }

    public static void removeEntry(int money, String description, String date, boolean isIncome){
        // Manual admin remove entry process
        if (isIncome){
            if (Analysis.incomes.containsKey(description)) {
                ArrayList<String> dateAndAmount = Analysis.incomes.get(description);
                ArrayList<String> dateList = new ArrayList<>(Arrays.asList(dateAndAmount.get(0).split("DM")));
                ArrayList<String> amountList = new ArrayList<>(Arrays.asList(dateAndAmount.get(1).split("DM")));
                if (dateList.contains(date) && amountList.contains(Integer.toString(money))){
                    balance -= money;
                    Analysis.remove_income(money);
                    dateList.remove(date);
                    amountList.remove(Integer.toString(money));
                    String dates;
                    String amounts;
                    StringBuilder datesBuilder = new StringBuilder();
                    for (String dlDate: dateList)
                        datesBuilder.append(dlDate).append("DM");
                    dates = datesBuilder.toString();
                    StringBuilder amountsBuilder = new StringBuilder();
                    for (String alAmounts: amountList)
                        amountsBuilder.append(alAmounts).append("DM");
                    amounts = amountsBuilder.toString();

                    ArrayList<String> newInformationStringArray = new ArrayList<>();
                    newInformationStringArray.add(dates);
                    newInformationStringArray.add(amounts);
                    if(!dates.isEmpty() && !amounts.isEmpty()){
                        Analysis.incomes.put(description, newInformationStringArray);
                        SQLite3.updateSQLBalanceSheet(Analysis.incomes.get(description), description, true);
                    }
                    else{
                        SQLite3.removeEntry(description, true);
                        Analysis.incomes.remove(description);
                    }
                    Log.log_msg("Administrator has removed " + description + " " + date + " " + money + " TL income entry.", true);
                    return;
                }
            }
            Log.log_msg("Administrator has tried to remove " + description + " " + date + " " + money + " TL income which is not existed entry.", false);
        }
        else {
            if (Analysis.expenses.containsKey(description)) {
                ArrayList<String> dateAndAmount = Analysis.expenses.get(description);
                ArrayList<String> dateList = new ArrayList<>(Arrays.asList(dateAndAmount.get(0).split("DM")));
                ArrayList<String> amountList = new ArrayList<>(Arrays.asList(dateAndAmount.get(1).split("DM")));
                if (dateList.contains(date) && amountList.contains(Integer.toString(money))){
                    balance -= money;
                    Analysis.remove_expense(money);
                    dateList.remove(date);
                    amountList.remove(Integer.toString(money));
                    String dates;
                    String amounts;
                    StringBuilder datesBuilder = new StringBuilder();
                    for (String dlDate: dateList)
                        datesBuilder.append(dlDate).append("DM");
                    dates = datesBuilder.toString();
                    StringBuilder amountsBuilder = new StringBuilder();
                    for (String alAmounts: amountList)
                        amountsBuilder.append(alAmounts).append("DM");
                    amounts = amountsBuilder.toString();

                    ArrayList<String> newInformationStringArray = new ArrayList<>();
                    newInformationStringArray.add(dates);
                    newInformationStringArray.add(amounts);
                    if(!dates.isEmpty() && !amounts.isEmpty()){
                        Analysis.expenses.put(description, newInformationStringArray);
                        SQLite3.updateSQLBalanceSheet(Analysis.expenses.get(description), description, false);
                    }
                    else{
                        Analysis.expenses.remove(description);
                        SQLite3.removeEntry(description, false);
                    }
                    Log.log_msg("Administrator has removed " + description + " " + date + " " + money + " TL expense entry.", true);
                    return;
                }
            }
            Log.log_msg("Administrator has tried to remove " + description + " " + date + " " + money + " TL expense which is not existed entry.", false);
        }
    }
}
