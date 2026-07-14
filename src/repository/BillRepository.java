package repository;

import java.util.ArrayList;
import java.util.List;

import model.Bill;

public class BillRepository {

    private List<Bill> billList;

    public BillRepository() {
        this.billList = new ArrayList<>();
    }

    public List<Bill> getAllBills() {
        return billList;
    }

    public void addBill(Bill bill) {
        billList.add(bill);
    }

    public List<Bill> getBillsByCustomerId(String customerId) {
        List<Bill> customerBills = new ArrayList<>();
        for (Bill bill : billList) {
            if (bill.getCustomerId().equalsIgnoreCase(customerId)) {
                customerBills.add(bill);
            }
        }
        return customerBills;
    }

    public Bill findByBillNumber(String billNumber) {
        for (Bill bill : billList) {
            if (bill.getBillNumber().equalsIgnoreCase(billNumber)) {
                return bill;
            }
        }
        return null;
    }
}
