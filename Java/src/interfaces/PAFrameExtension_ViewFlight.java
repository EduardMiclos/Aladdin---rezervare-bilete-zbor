package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
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
	
	private boolean calculatePrice(DatabaseConnection dbConn, String flightCode, Client client, String flightDate) {
		try {
			dbConn.sendQuery("SELECT pj.curse.PretBusiness, pj.curse.pretEconomy, pj.curse.Discount_2 FROM curse, zboruri WHERE pj.curse.codCursa=pj.zboruri.codCursa AND pj.zboruri.codZbor='" + flightCode + "'");
			
			dbConn.rs.first();
			
			float pretBusiness = dbConn.rs.getFloat("pretBusiness");
			float pretEconomy = dbConn.rs.getFloat("pretEconomy");
			float discountLastMinute = dbConn.rs.getFloat("Discount_2");
			
			String[] date = flightDate.split("-");
			
			int year = Integer.parseInt(date[0]);
			int month = Integer.parseInt(date[1]);
			int day = Integer.parseInt(date[2]);
						
			LocalDate localDate = LocalDate.of(year, month, day);
			
			client.calculPret(pretBusiness, pretEconomy, discountLastMinute, localDate);
			
			return true;
		} catch (SQLException sqlException) {
			JOptionPane.showMessageDialog(pnlMain, "A aparut o eroare la citirea din baza de date!", "Eroare baza de date", JOptionPane.ERROR_MESSAGE);
			sqlException.printStackTrace();
			return false;
		}
	}
	
	private boolean writeReservation(String flightCode, DatabaseConnection dbConn, Client client) {
		try {
			dbConn.sendUpdate("INSERT INTO rezervari (codZbor, clasa, pretBilet) "
					+ "VALUES ('" + flightCode
					+ "','" + String.valueOf(client.getClasa()
					+ "','" + client.getPretBilet() + "')"));
			
			dbConn.sendUpdate("INSERT INTO clienti (nume, prenume, email, telefon, varsta, optiunePlata) "
					+ "VALUES ('" + client.getNume()
					+ "','" + client.getPrenume()
					+ "','" + client.getEmail()
					+ "','" + client.getTelefon()
					+ "','" + client.getVarsta()
					+ "','" + String.valueOf(client.getPlata()) + "')");
			
			return true;
		} catch (SQLException sqlException) {
			JOptionPane.showMessageDialog(pnlMain, "A aparut o eroare la scrierea in baza de date!", "Eroare baza de date", JOptionPane.ERROR_MESSAGE);
			sqlException.printStackTrace();
			
			return false;
		}
	}
	
	private boolean decrementSeats(DatabaseConnection dbConn, String flightCode, String targetSeatClass, int seats) {
		seats--;
		
		try {
			dbConn.sendUpdate("UPDATE zboruri SET " + targetSeatClass + " = " + seats + " WHERE codZbor = '" + flightCode + "'");
			return true;
		} catch (SQLException sqlException) {
			JOptionPane.showMessageDialog(pnlMain, "A aparut o eroare la scrierea in baza de date!", "Eroare baza de date", JOptionPane.ERROR_MESSAGE);
			sqlException.printStackTrace();
			return false;
		}
	}
	
	public PAFrameExtension_ViewFlight(DatabaseConnection dbConn, String flightCode, String flightDate) {
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
				
				if (newReservation == false) {
					JOptionPane.showMessageDialog(pnlMain, "Nu ati introdus o inregistrare noua!", "Eroare inregistrare", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				int businessSeats, economySeats;
				
				try {
					dbConn.sendQuery("SELECT businessRamase, economyRamase FROM zboruri WHERE codZbor='" + flightCode + "'");
					dbConn.rs.first();
					businessSeats = dbConn.rs.getInt("businessRamase");
					economySeats = dbConn.rs.getInt("economyRamase");
				} catch (SQLException sqlException) {
					JOptionPane.showMessageDialog(pnlMain, "A aparut o eroare la citirea din baza de date!", "Eroare baza de date", JOptionPane.ERROR_MESSAGE);
					sqlException.printStackTrace();
					return;
				}				
				
				try {
					nume = (String) tableModel.getValueAt(clienti.size(), 0);
					prenume = (String) tableModel.getValueAt(clienti.size(), 1);
					email = (String) tableModel.getValueAt(clienti.size(), 2);
					telefon = (String) tableModel.getValueAt(clienti.size(), 3);
					varsta = Integer.parseInt(tableModel.getValueAt(clienti.size(), 4).toString());
					plata = TipPlata.valueOf(tableModel.getValueAt(clienti.size(), 5).toString().trim());
					clasa = TipClasa.valueOf(tableModel.getValueAt(clienti.size(), 6).toString().trim());
					
					if (clasa == TipClasa.Business) {
						if (businessSeats == 0) {
							JOptionPane.showMessageDialog(pnlMain, "Nu mai exista locuri la clasa business!", "Eroare introducere pasager", JOptionPane.ERROR_MESSAGE);
							return;
						}
						else {
							if (decrementSeats(dbConn, flightCode, "businessRamase", businessSeats) == false) {
								return;
							}
						}
					}
					
					if (clasa == TipClasa.Economy) {
						if (economySeats == 0 ) {
							JOptionPane.showMessageDialog(pnlMain, "Nu mai exista locuri la clasa economy!", "Eroare introducere pasager", JOptionPane.ERROR_MESSAGE);
							return;
						}
						else {
							if (decrementSeats(dbConn, flightCode, "economyRamase", economySeats) == false) {
								return;
							}
						}
					}
					
					Client client = new Client(nume, prenume, email, telefon, varsta, plata, clasa);
					clienti.add(client);

					if (calculatePrice(dbConn, flightCode, client, flightDate) == true && writeReservation(flightCode, dbConn, client) == true) {
						JOptionPane.showMessageDialog(pnlMain, "Client introdus cu succes!");
					}

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
