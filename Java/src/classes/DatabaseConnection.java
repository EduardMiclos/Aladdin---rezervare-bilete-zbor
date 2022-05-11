package classes;

import java.sql.*;

public class DatabaseConnection {
	private String url;
	private Statement sql;
	public ResultSet rs;
	private Connection con;

	private String username;
	private String password;

	public DatabaseConnection(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;

		this.sql = null;
		this.rs = null;
		this.con = null;
	}

	public void connect() {
		try {
			this.con = DriverManager.getConnection(this.url, username, password);
			this.sql = this.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void sendQuery(String query) throws SQLException{
		this.rs = this.sql.executeQuery(query);
	}

	public int tableSize() throws SQLException {		
		this.rs.beforeFirst();
		int indexPrim = this.rs.getRow();
		this.rs.last();
		int indexUltim = this.rs.getRow();
		
		this.rs.beforeFirst();		
		return indexUltim - indexPrim;
	}
}

