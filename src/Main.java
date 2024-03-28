// APARTMENT TRACKING SYSTEM | APARTMAN TAKIP SISTEMI
// SD317 Güncel Programlama Dilleri Final Project
public class Main{
    // ATS project is designed to be run from this class.
    public static void main(String[] args) {
        Log.tryCreateLog();
        Analysis.update_hashMaps();
        UserInterface.main(null);
    }
}