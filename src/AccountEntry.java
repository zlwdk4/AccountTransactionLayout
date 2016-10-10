
public class AccountEntry {
	private int account_id;
	private String account_name; 
	private int account_balance;
	
	AccountEntry(int id, String name, int bal){
		setAccount_id(id);
		setAccount_name(name);
		setAccount_balance(bal);
	}
	
	
	@Override
	public String toString(){
		String rval = "";
		rval += "account id: " + Integer.toString(account_id);
		rval += "  -  account name: " + account_name;
		rval += "  -  account balance: " + Integer.toString(account_balance);
		
		return rval;
	}
	
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public int getAccount_balance() {
		return account_balance;
	}
	public void setAccount_balance(int account_balance) {
		this.account_balance = account_balance;
	}
	
}
