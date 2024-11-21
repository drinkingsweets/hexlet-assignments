package exercise.dto;

// BEGIN
public class BasePage {
    private static String flash = "";

    public static String getFlash() {
        return flash;
    }

    public static void setFlash(String newFlash) {
        flash = newFlash;
    }
}
// END
