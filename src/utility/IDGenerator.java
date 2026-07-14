package utility;

// Generates simple sequential IDs for products, customers, admins, and bills.
// Counters start right after the highest ID already used in the dummy data.
public class IDGenerator {

    private static int productCounter = 126;
    private static int customerCounter = 111;
    private static int adminCounter = 106;
    private static int billCounter = 1001;

    public static String generateProductId() {
        String id = "P" + productCounter;
        productCounter++;
        return id;
    }

    public static String generateCustomerId() {
        String id = "C" + customerCounter;
        customerCounter++;
        return id;
    }

    public static String generateAdminId() {
        String id = "A" + adminCounter;
        adminCounter++;
        return id;
    }

    public static String generateBillId() {
        String id = "B" + billCounter;
        billCounter++;
        return id;
    }
}
