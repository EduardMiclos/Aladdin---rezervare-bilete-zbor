package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import classes.Client;
import classes.DatabaseConnection;
import classes.TipClasa;
import classes.TipPlata;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PAFrameExtension_ViewFlight extends JFrame {

	private JPanel pnlMain;
	private JTable table;
	private DefaultTableModel tableModel;
	
	private Vector<Client> clienti = new Vector<Client>();
	private boolean newReservation = false;
	
	private void readReservations(DatabaseConnection dbConn, String flightCode)
	{
		try {
			dbConn.sendQuery("SELECT pj.clienti.nume, pj.clienti.prenume, pj.clienti.email,"
					+ "pj.clienti.telefon, pj.clienti.varsta, pj.clienti.optiunePlata, pj.rezervari.pretBilet, pj.rezervari.clasa FROM "
					+ "pj.rezervari, pj.clienti WHERE pj.rezervari.codZbor=\"" + flightCode + "\" "
							+ "AND pj.rezervari.id=pj.clienti.id;");
			
			
			String nume, prenume, email, telefon;
			int varsta;
			TipPlata plata;
			TipClasa clasa;
			
			while (dbConn.rs.next())
			{
				nume = dbConn.rs.getString("nume");
				prenume = dbConn.rs.getString("prenume");
				email = dbConn.rs.getString("email");
				telefon = dbConn.rs.getString("telefon");
				varsta = dbConn.rs.getInt("varsta");
				plata = TipPlata.valueOf(dbConn.rs.getString("optiunePlata"));
				clasa = TipClasa.valueOf(dbConn.rs.getString("clasa"));
				
				Client client = new Client(nume, prenume, email, telefon, varsta, plata, clasa);
				
				tableModel.addRow(new Object[]{
						nume, 
						prenume, 
						email, 
						telefon, 
						varsta,
						plata,
						clasa});
				
				clienti.add(client);
			}
			
			
			
		} catch (SQLException sqlException) {
			JOptionPane.showMessageDialog(pnlMain, "A aparut o eroare la citirea din baza de date!", "Eroare baza de date", JOptionPane.ERROR_MESSAGE);
			sqlException.printStackTrace();
		}
	}
	
	private void calculatePrice(Client client) {
		
	}
	
	private void writeReservation(Client client) {
		
	}
	
	public PAFrameExtension_ViewFlight(DatabaseConnection dbConn, String flightCode) {
		setTitle("List\u0103 rezerv\u0103ri - zborul " + flightCode);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1104, 401);
		pnlMain = new JPanel();
		pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(0, 0));
		setContentPane(pnlMain);
	
		JPanel pnlTable = new JPanel();
		table = new JTable();
		
		tableModel = new DefaultTableModel(
				new Object[][] {},
				new String[] {
						"Nume", "Prenume", "Email", "Telefon", "Varsta", "Optiune plata", "Clasa"
				});
		pnlTable.setLayout(new BorderLayout(0, 0));
				
		table.setModel(tableModel);
	
		JScrollPane scrollPane = new JScrollPane(table);
		pnlTable.add(scrollPane);
	
		pnlMain.add(pnlTable, BorderLayout.CENTER);
		
		JPanel pnlButtons = new JPanel();
		pnlMain.add(pnlButtons, BorderLayout.SOUTH);
		
		JButton btnNewRow = new JButton("Rezervare nou\u0103");
		btnNewRow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (newReservation == false) {
					newReservation = true;
					tableModel.addRow(new Object[]{
							"", 
							"", 
							"", 
							"", 
							"",
							"",
					""});
				}
				else {
					JOptionPane.showMessageDialog(pnlMain, "Completati intai prima inregistrare!", "Eroare adaugare inregistrare", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		pnlButtons.add(btnNewRow);
		
		btnNewRow.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 16));
		btnNewRow.setBackground(new Color(230, 231, 252));
		btnNewRow.setFocusPainted(false);
		btnNewRow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		JButton btnSaveTable = new JButton("Salveaz\u0103");
		btnSaveTable.addMouseListener(new MouseAdapter() {
			String nume, prenume, email, telefon;
			int varsta;
			TipPlata plata;
			TipClasa clasa;
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					nume = (String) tableModel.getValueAt(clienti.size(), 0);
					prenume = (String) tableModel.getValueAt(clienti.size(), 1);
					email = (String) tableModel.getValueAt(clienti.size(), 2);
					telefon = (String) tableModel.getValueAt(clienti.size(), 3);
					varsta = Integer.parseInt(tableModel.getValueAt(clienti.size(), 4).toString());
					plata = TipPlata.valueOf(tableModel.getValueAt(clienti.size(), 5).toString().trim());
					clasa = TipClasa.valueOf(tableModel.getValueAt(clienti.size(), 6).toString().trim());
					
					Client client = new Client(nume, prenume, email, telefon, varsta, plata, clasa);
					clienti.add(client);

					calculatePrice(client);
					writeReservation(client);
					
					JOptionPane.showMessageDialog(pnlMain, "Client introdus cu succes!");

					
				}
				catch(Exception exception)
				{
					JOptionPane.showMessageDialog(pnlMain, "Nu ati completat corect toate campurile!", "Eroare campuri de date", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				newReservation = false;
			}
		});
		pnlButtons.add(btnSaveTable);
		
		btnSaveTable.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 16));
		btnSaveTable.setBackground(new Color(230, 231, 252));
		btnSaveTable.setFocusPainted(false);
		btnSaveTable.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		readReservations(dbConn, flightCode);
	}

}
