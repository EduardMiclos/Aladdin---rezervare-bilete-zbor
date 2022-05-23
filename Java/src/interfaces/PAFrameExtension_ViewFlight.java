package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classes.DatabaseConnection;

public class PAFrameExtension_ViewFlight extends JFrame {

	private JPanel pnlMain;

	public PAFrameExtension_ViewFlight(DatabaseConnection dbConn, String flightCode) {
		setTitle("List\u0103 rezerv\u0103ri - zborul " + flightCode);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 437, 401);
		pnlMain = new JPanel();
		pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(0, 0));
		setContentPane(pnlMain);
		
		try {
			dbConn.sendQuery("SELECT pj.clienti.nume, pj.clienti.prenume, pj.clienti.email,"
					+ "pj.clienti.telefon, pj.clienti.varsta, pj.clienti.optiunePlata FROM "
					+ "pj.rezervari, pj.clienti WHERE pj.rezervari.codZbor=\"" + flightCode + "\" "
							+ "AND pj.rezervari.id=pj.clienti.id;");
		} catch (SQLException sqlException) {
			JOptionPane.showMessageDialog(pnlMain, "A aparut o eroare la citirea din baza de date!", "Eroare baza de date", JOptionPane.ERROR_MESSAGE);
			sqlException.printStackTrace();
		}
	}

}
