public class Residents extends Apartment{
    private final String firstName;
    private final String lastName;
    private final String TCKN;
    //private final int ownership;
    private final String Flat;
    //private final String password;
    private final int superUser;
    public static Residents current_resident = null;
    /*
    public static boolean EstablishCurrentUser(String firstName, String lastName, String TCKN, int ownership, String flat, int superUser, String password) {
        current_resident = new Residents(firstName, lastName, TCKN, ownership, flat, superUser, password);
        return true;
    }*/
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getTCKN() {
        return TCKN;
    }/*
    public boolean getOwnership() {
        return ownership == 1;
    }*/
    public String getFlat() {
        return Flat;
    }
    public boolean getSuperUser() {
        return superUser == 1;
    }

    public Residents(String firstName, String lastName, String TCKN, int ownership, String flat, int superUser, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.TCKN = TCKN;
        //this.ownership = ownership;
        this.Flat = flat;
        this.superUser = superUser;
        //this.password = password;
    }
    /*
    public boolean Deposit(int money) {
        if(money > 0){
            Apartment.addEntry(money, this, true);
            return true;
        }
        Log.log_msg("Flat:" + this.getFlat() + ", " + this.getFirstName() + " " + this.getLastName() + " is failed to deposit.", false);
        return false;
    }*/
    /*
    public boolean Expense(int money) {
        if (this.getSuperUser() && money > 0) {
            Apartment.addEntry(money, this, false);
            return true;
        }
        Log.log_msg("Flat: " + this.getFlat() + ", " + this.getFirstName() + " " + this.getLastName() + " has failed to enter " + money + " TL expense entry due insufficient permission.", false);
        return false;
    }*/
}
