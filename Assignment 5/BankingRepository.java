import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BankingRepository {
    private final SQLiteConnection sqliteConnection;

    public BankingRepository(SQLiteConnection sqliteConnection) {
        this.sqliteConnection = sqliteConnection;
    }

    public void initializeDatabase() {
        String customersTableSql = """
                CREATE TABLE IF NOT EXISTS customers (
                    id INTEGER PRIMARY KEY,
                    name TEXT NOT NULL,
                    email TEXT NOT NULL UNIQUE
                );
                """;

        String accountsTableSql = """
                CREATE TABLE IF NOT EXISTS accounts (
                    id INTEGER PRIMARY KEY,
                    customer_id INTEGER NOT NULL,
                    account_number TEXT NOT NULL UNIQUE,
                    account_type TEXT NOT NULL,
                    balance REAL NOT NULL,
                    interest_rate REAL,
                    loan_limit REAL,
                    FOREIGN KEY (customer_id) REFERENCES customers(id)
                );
                """;

        try (Connection conn = sqliteConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(customersTableSql);
            stmt.execute(accountsTableSql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database schema.", e);
        }
    }

    public boolean hasAnyCustomers() {
        String sql = "SELECT COUNT(*) AS total FROM customers;";

        try (Connection conn = sqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            return rs.next() && rs.getInt("total") > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check customers table.", e);
        }
    }

    public void insertCustomer(Customer customer) {
        String sql = "INSERT OR REPLACE INTO customers(id, name, email) VALUES(?, ?, ?);";

        try (Connection conn = sqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customer.getId());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getEmail());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert customer " + customer.getName(), e);
        }
    }

    public void insertAccount(Account account) {
        String sql = """
                INSERT OR REPLACE INTO accounts(
                    id, customer_id, account_number, account_type, balance, interest_rate, loan_limit
                ) VALUES(?, ?, ?, ?, ?, ?, ?);
                """;

        try (Connection conn = sqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, account.getId());
            pstmt.setInt(2, account.getCustomerId());
            pstmt.setString(3, account.getAccountNumber());
            pstmt.setString(4, account.getAccountType());
            pstmt.setDouble(5, account.getBalance());

            if (account instanceof SavingsAccount savingsAccount) {
                pstmt.setDouble(6, savingsAccount.getInterestRate());
                pstmt.setNull(7, java.sql.Types.REAL);
            } else if (account instanceof LoanAccount loanAccount) {
                pstmt.setNull(6, java.sql.Types.REAL);
                pstmt.setDouble(7, loanAccount.getLoanLimit());
            } else {
                pstmt.setNull(6, java.sql.Types.REAL);
                pstmt.setNull(7, java.sql.Types.REAL);
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert account " + account.getAccountNumber(), e);
        }
    }

    public Map<Customer, List<Account>> getConsolidatedCustomerAccounts() {
        String sql = """
                SELECT
                    c.id AS customer_id,
                    c.name,
                    c.email,
                    a.id AS account_id,
                    a.account_number,
                    a.account_type,
                    a.balance,
                    a.interest_rate,
                    a.loan_limit
                FROM customers c
                LEFT JOIN accounts a ON c.id = a.customer_id
                ORDER BY c.id, a.id;
                """;

        Map<Integer, Customer> customerById = new LinkedHashMap<>();
        Map<Integer, List<Account>> accountsByCustomerId = new LinkedHashMap<>();

        try (Connection conn = sqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int customerId = rs.getInt("customer_id");
                customerById.putIfAbsent(
                        customerId,
                        new Customer(customerId, rs.getString("name"), rs.getString("email")));

                int accountId = rs.getInt("account_id");
                if (!rs.wasNull()) {
                    Account account = buildAccountFromResultSet(rs);
                    accountsByCustomerId.computeIfAbsent(customerId, key -> new ArrayList<>()).add(account);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch consolidated customer account information.", e);
        }

        Map<Customer, List<Account>> consolidated = new LinkedHashMap<>();
        for (Map.Entry<Integer, Customer> entry : customerById.entrySet()) {
            int customerId = entry.getKey();
            consolidated.put(entry.getValue(), accountsByCustomerId.getOrDefault(customerId, new ArrayList<>()));
        }

        return consolidated;
    }

    public Account getAccountByNumber(String accountNumber) {
        String sql = """
                SELECT
                    customer_id,
                    id AS account_id,
                    account_number,
                    account_type,
                    balance,
                    interest_rate,
                    loan_limit
                FROM accounts
                WHERE account_number = ?;
                """;

        try (Connection conn = sqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountNumber);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return buildAccountFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch account " + accountNumber, e);
        }
    }

    public void updateAccountBalance(String accountNumber, double newBalance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?;";

        try (Connection conn = sqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, accountNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update account balance for " + accountNumber, e);
        }
    }

    private Account buildAccountFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("account_id");
        int customerId = rs.getInt("customer_id");
        String accountNumber = rs.getString("account_number");
        String accountType = rs.getString("account_type");
        double balance = rs.getDouble("balance");

        if ("SAVINGS".equalsIgnoreCase(accountType)) {
            double interestRate = rs.getDouble("interest_rate");
            return new SavingsAccount(id, customerId, accountNumber, balance, interestRate);
        }

        if ("LOAN".equalsIgnoreCase(accountType)) {
            double loanLimit = rs.getDouble("loan_limit");
            return new LoanAccount(id, customerId, accountNumber, balance, loanLimit);
        }

        return new Account(id, customerId, accountNumber, balance);
    }
}
