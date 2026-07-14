# SuperMarket Billing System

A console-based supermarket billing and inventory management system built in
core Java. This project was built to practice OOP concepts, Collections, and
File Handling without using any frameworks (no Spring, no Hibernate, no
Maven/Gradle).

## Project Description

SuperMarket Billing System simulates a real supermarket checkout counter from
the terminal. There are two roles - Admin and Customer. Admins manage the
product inventory, create new customer/admin accounts, and view business
reports. Customers browse products, build a shopping cart, check out using an
in-app wallet, earn loyalty rewards, and view their past purchase history.

All data is kept in memory using ArrayLists and HashMaps (no database), so
the system resets to the original dummy data every time the program is
restarted.

## Features

- **Authentication** - Email/password login for Admins and Customers, with a
  maximum of 3 attempts before returning to the main menu.
- **Inventory Management** - Add, modify, delete, search, restock, and view
  (with sorting) products across 7 categories.
- **Customer & Admin Creation** - Admins can onboard new customers (starting
  wallet of Rs.1000) and new admins directly from the Admin Menu.
- **Shopping Cart** - Add products to cart, increase/decrease quantity,
  remove items, or clear the cart entirely, with live stock validation.
- **Billing & Checkout** - Itemized bill generation, wallet-based payment,
  and automatic inventory deduction.
- **Loyalty Rewards System**
  - Spend Rs.5000+ in a single bill -> instant Rs.100 cashback (no points).
  - Otherwise, earn 1 loyalty point per Rs.100 spent. Every 50 points is
    automatically redeemed for Rs.100 cashback.
- **Purchase History** - View past bills sorted latest-first or
  oldest-first, and drill into any bill for the full invoice.
- **Admin Reports**
  1. Low Stock Report (quantity < 10)
  2. Products Never Purchased
  3. Top Spending Customers
  4. Admin Activity Report (tracks who performed the most management actions)
- **Input Validation** - Blocks negative prices/quantities, duplicate IDs,
  blank names/emails, and invalid menu choices.
- **Auto-Generated IDs** - Products (P1xx), Customers (C1xx), Admins (A1xx),
  Bills (B1xxx) are all generated automatically.

## Technologies Used

- Java 17
- Core Java only: OOP, ArrayList, HashMap, Comparator, Collections.sort(),
  Scanner, LocalDateTime, Exception Handling
- No external frameworks or build tools - compiles with plain `javac`

## Folder Structure

```
SuperMarketBillingSystem/
  src/
    Main.java
    model/
      Product.java
      Customer.java
      Admin.java
      CartItem.java
      Bill.java
    service/
      AuthenticationService.java
      InventoryService.java
      CartService.java
      BillingService.java
      PurchaseHistoryService.java
      ReportService.java
      AdminActivityTracker.java
    repository/
      ProductRepository.java
      CustomerRepository.java
      AdminRepository.java
      BillRepository.java
    utility/
      InputValidator.java
      IDGenerator.java
      MenuPrinter.java
    data/
      DummyData.java
```

## Sample Login Credentials

**Admin**
| Email | Password |
|---|---|
| rajesh.admin@market.com | admin123 |
| priya.admin@market.com | admin123 |

**Customer**
| Email | Password |
|---|---|
| arun@example.com | cust123 |
| divya@example.com | cust123 |

(Every customer starts with a wallet balance of Rs.1000 and 0 loyalty points.)

## How to Run

1. Make sure Java 17 (or later) is installed: `java -version`
2. From the `SuperMarketBillingSystem/src` folder, compile everything:
   ```
   javac -d ../out Main.java model/*.java data/*.java repository/*.java service/*.java utility/*.java
   ```
3. Run the compiled program:
   ```
   cd ../out
   java Main
   ```

## Sample Console Output

```
==================================
SUPERMARKET BILLING SYSTEM
==================================

1 Login
2 Exit

Enter Choice : 1
Enter Email: arun@example.com
Enter Password: cust123

Welcome
Arun Prakash
Customer

----- CUSTOMER MENU -----
1 View Products
2 Search Product
3 Add Product to Cart
4 View Cart
5 Checkout
6 Purchase History
7 Logout

Enter Choice : 5

--- Checkout ---
Product              Quantity  Unit Price  Subtotal
Bisleri Water 1L     5         20.0        100.0
Total: 100.0

========== INVOICE ==========
Bill Number : B1001
Customer    : Arun Prakash
Date        : 13-07-2026 18:13:35
------------------------------
Total          : 100.0
Wallet Before  : 1000.0
Wallet After   : 900.0
Cashback       : 0.0
Loyalty Earned : 1
==============================
```

## Future Improvements

- Persist data to files (or a real database) instead of resetting on restart.
- Password masking/hashing instead of storing plain text.
- A proper GUI (JavaFX or a web front end) instead of a console interface.
- Discount coupons and multi-item promotional offers.
- Unit tests for the service layer using JUnit.
