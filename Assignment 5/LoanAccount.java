public class LoanAccount extends Account {
    private final double loanLimit;

    public LoanAccount(int id, int customerId, String accountNumber, double balance, double loanLimit) {
        super(id, customerId, accountNumber, balance);
        this.loanLimit = loanLimit;
    }

    public double getLoanLimit() {
        return loanLimit;
    }

    @Override
    public void deposit(double amount) {
        // For loan account, deposit means repayment, so outstanding loan decreases.
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0.");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Repayment cannot exceed outstanding loan for account " + accountNumber + ".");
        }
        balance -= amount;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0.");
        }
        if (balance + amount > loanLimit) {
            throw new IllegalArgumentException(
                    "Loan limit exceeded for account " + accountNumber + ". Maximum allowed: " + loanLimit);
        }
        balance += amount;
    }

    @Override
    public String getAccountType() {
        return "LOAN";
    }

    @Override
    public String getAccountDetails() {
        return String.format(
                "%s (%s) Outstanding Loan: %.2f, Loan Limit: %.2f",
                accountNumber,
                getAccountType(),
                balance,
                loanLimit);
    }
}
