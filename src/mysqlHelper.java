import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;
import java.io.*;

public class mysqlHelper {

	public mysqlHelper() throws SQLException {
		String url = "jdbc:mysql://kc-sce-appdb01.kc.umkc.edu/zlwdk4";
		String userID = "zlwdk4";
		String password = "dntdCgBGP";
	/**	try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url , userID, password);
			stmt = con.createStatement();
			
			
			String sqlCmd = "INSERT into Account (account_name,account_balance) VALUES(\"Secondary\",200);";
			
			//stmt.executeUpdate(sqlCmd);
			//stmt.execute(sqlCmd);
			
			
			cleanup();
		}
		catch (java.lang.ClassNotFoundException e ){
			System.out.println(e); 
            System.exit(0);
		}
		**/
 
		
        
        
	}
	
	public static Connection getConnection(){
		String url = "jdbc:mysql://kc-sce-appdb01.kc.umkc.edu/zlwdk4";
		String userID = "zlwdk4";
		String password = "dntdCgBGP";
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url , userID, password);
			//stmt = con.createStatement();
			
		}
		catch (java.lang.ClassNotFoundException e ){
			System.out.println(e); 
            System.exit(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
		
	}
	
	
	public ArrayList<AccountEntry> getData(){
		
		ArrayList<AccountEntry> entries = new ArrayList<AccountEntry>();
		Connection con = getConnection();
		String query = "SELECT * FROM Account";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				AccountEntry ae = new AccountEntry(
						rs.getInt("account_id"), 
						rs.getString("account_name"),
						rs.getInt("account_balance"));
				System.out.println(ae.toString());
				entries.add(ae);
			}
			System.out.println(entries);
			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entries;
		
		
		
		
		
	}
	
	public void cleanup() throws SQLException {
        // Close connection and statement
        // Connections, statements, and result sets are
        // closed automatically when garbage collected
        // but it is a good idea to free them as soon
        // as possible.
        // Closing a statement closes its current result set.
        // Operations that cause a new result set to be
        // created for a statement automatically close
        // the old result set.
        //stmt.close();
        //con.close();
    }

	public static void updateAll(ArrayList<AccountEntry> entries) {
		// TODO Auto-generated method stub
		
		for(int i = 1; i <5; i++){
			Connection con = getConnection();
			Statement stmt;
			try {
				stmt = con.createStatement();
				String updateQuery = "UPDATE Account SET account_balance=" + Integer.toString(entries.get(i - 1).getAccount_balance()) + 
						" WHERE account_id="+Integer.toString(i);
				stmt.executeUpdate(updateQuery);
				con.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}
		
		
		
	}
	
	
}
