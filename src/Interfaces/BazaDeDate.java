package Interfaces;

import java.sql.*;

public class BazaDeDate {
	private String url;
	private Statement sql;
	public ResultSet rs;
	private Connection con;

	private String username;
	private String password;

	public BazaDeDate(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;

		this.sql = null;
		this.rs = null;
		this.con = null;
	}

	public void conectare() {
		try {
			this.con = DriverManager.getConnection(this.url, username, password);
			this.sql = this.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deconectare() {
		try {
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void citireDate(String interogare) throws SQLException{
		this.rs = this.sql.executeQuery(interogare);
	}

	public int dimensiuneTabela() throws SQLException {		
		this.rs.beforeFirst();
		int indexPrim = this.rs.getRow();
		this.rs.last();
		int indexUltim = this.rs.getRow();
		
		this.rs.beforeFirst();		
		return indexUltim - indexPrim;
	}
}

