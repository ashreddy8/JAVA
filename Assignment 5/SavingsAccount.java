public class SavingsAccount extends Account {
    private final double interestRate;

    public SavingsAccount(int id, int customerId, String accountNumber, double balance, double interestRate) {
        super(id, customerId, accountNumber, balance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
        // Savings account cannot go negative.
        super.withdraw(amount);
    }

    @Override
    public String getAccountType() {
        return "SAVINGS";
    }

    @Override
    public String getAccountDetails() {
        return String.format(
                "%s (%s) Balance: %.2f, Interest Rate: %.2f%%",
                accountNumber,
                getAccountType(),
                balance,
                interestRate);
    }
}
