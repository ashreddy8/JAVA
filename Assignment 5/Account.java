public class Account {
	protected int id;
	protected int customerId;
	protected String accountNumber;
	protected double balance;

	public Account(int id, int customerId, String accountNumber, double balance) {
		this.id = id;
		this.customerId = customerId;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Deposit amount must be greater than 0.");
		}
		balance += amount;
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Withdrawal amount must be greater than 0.");
		}
		if (amount > balance) {
			throw new IllegalArgumentException("Insufficient balance in account " + accountNumber + ".");
		}
		balance -= amount;
	}

	public String getAccountType() {
		return "ACCOUNT";
	}

	public String getAccountDetails() {
		return String.format("%s (%s) Balance: %.2f", accountNumber, getAccountType(), balance);
	}
}

