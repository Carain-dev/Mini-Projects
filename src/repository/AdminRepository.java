package repository;

import java.util.ArrayList;
import java.util.List;

import data.DummyData;
import model.Admin;

public class AdminRepository {

    private List<Admin> adminList;

    public AdminRepository() {
        this.adminList = new ArrayList<>(DummyData.getInitialAdmins());
    }

    public List<Admin> getAllAdmins() {
        return adminList;
    }

    // Searches for an admin by email, used during login
    public Admin findByEmail(String email) {
        for (Admin admin : adminList) {
            if (admin.getEmail().equalsIgnoreCase(email)) {
                return admin;
            }
        }
        return null;
    }

    public Admin findById(String adminId) {
        for (Admin admin : adminList) {
            if (admin.getAdminId().equalsIgnoreCase(adminId)) {
                return admin;
            }
        }
        return null;
    }

    public boolean isEmailTaken(String email) {
        return findByEmail(email) != null;
    }

    public void addAdmin(Admin admin) {
        adminList.add(admin);
    }
}
