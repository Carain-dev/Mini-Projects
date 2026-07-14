package service;

import java.util.HashMap;
import java.util.Map;

// Tracks how many management operations each admin has performed.
// This data feeds directly into the Admin Activity Report.
public class AdminActivityTracker {

    // Holds the operation counts for a single admin
    private static class ActivityCount {
        int productsAdded = 0;
        int productsModified = 0;
        int productsDeleted = 0;
        int customersAdded = 0;
        int adminsAdded = 0;

        int getTotal() {
            return productsAdded + productsModified + productsDeleted + customersAdded + adminsAdded;
        }
    }

    private Map<String, ActivityCount> activityMap = new HashMap<>();

    private ActivityCount getOrCreate(String adminId) {
        ActivityCount count = activityMap.get(adminId);
        if (count == null) {
            count = new ActivityCount();
            activityMap.put(adminId, count);
        }
        return count;
    }

    public void recordProductAdded(String adminId) {
        getOrCreate(adminId).productsAdded++;
    }

    public void recordProductModified(String adminId) {
        getOrCreate(adminId).productsModified++;
    }

    public void recordProductDeleted(String adminId) {
        getOrCreate(adminId).productsDeleted++;
    }

    public void recordCustomerAdded(String adminId) {
        getOrCreate(adminId).customersAdded++;
    }

    public void recordAdminAdded(String adminId) {
        getOrCreate(adminId).adminsAdded++;
    }

    public int getProductsAdded(String adminId) {
        return activityMap.containsKey(adminId) ? activityMap.get(adminId).productsAdded : 0;
    }

    public int getProductsModified(String adminId) {
        return activityMap.containsKey(adminId) ? activityMap.get(adminId).productsModified : 0;
    }

    public int getProductsDeleted(String adminId) {
        return activityMap.containsKey(adminId) ? activityMap.get(adminId).productsDeleted : 0;
    }

    public int getCustomersAdded(String adminId) {
        return activityMap.containsKey(adminId) ? activityMap.get(adminId).customersAdded : 0;
    }

    public int getAdminsAdded(String adminId) {
        return activityMap.containsKey(adminId) ? activityMap.get(adminId).adminsAdded : 0;
    }

    public int getTotalOperations(String adminId) {
        return activityMap.containsKey(adminId) ? activityMap.get(adminId).getTotal() : 0;
    }
}
