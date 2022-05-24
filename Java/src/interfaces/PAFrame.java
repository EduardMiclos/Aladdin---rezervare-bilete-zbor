package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import classes.DatabaseConnection;

import java.awt.SystemColor;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PAFrame extends JFrame {

	private JPanel pnlMain;
	private JTable table;
	private DefaultTableModel tableModel;
	private DatabaseConnection dbConn;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PAFrame frame = new PAFrame("aeroTimisoara", "Traian Vuia");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void readFlights(String location)
	{
		try {
			String query = "SELECT zboruri.codZbor, zboruri.ziOperare, zboruri.businessRamase,"
					+ "zboruri.economyRamase, curse.CompanieAeriana, trasee.Locatii FROM zboruri,"
					+ "curse, trasee WHERE zboruri.codCursa=curse.CodCursa AND trasee.locatii"
					+ " LIKE \'%" + location +"%'";
			
			dbConn.sendQuery(query);
			
			String codZbor = "";
			Date ziOperare = null;
			int businessRamase = 0;
			int economyRamase = 0;
			String companieAeriana = "";
			
			while(dbConn.rs.next())
			{
				codZbor = dbConn.rs.getString("codZbor");
				ziOperare = dbConn.rs.getDate("ziOperare");
				businessRamase = dbConn.rs.getInt("businessRamase");
				economyRamase = dbConn.rs.getInt("economyRamase");
				companieAeriana = dbConn.rs.getString("CompanieAeriana");
				
				tableModel.addRow(new Object[]{
						codZbor, 
						ziOperare, 
						businessRamase, 
						economyRamase, 
						companieAeriana});
			}
		} catch (SQLException sqlException) {
			JOptionPane.showMessageDialog(pnlMain, "A aparut o eroare la citirea din baza de date!", "Eroare baza de date", JOptionPane.ERROR_MESSAGE);
			sqlException.printStackTrace();
		}
	}
	
	public PAFrame(String PAUser, String location) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dbConn.disconnect();
			}
		});
		setTitle("Sistem administrativ - personal aeroport");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 723, 512);
		pnlMain = new JPanel();
		pnlMain.setBackground(SystemColor.inactiveCaption);
		pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(0, 0));
		setContentPane(pnlMain);
		
		JPanel pnlCenter = new JPanel();
		pnlCenter.setBackground(SystemColor.inactiveCaption);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlCenter.setLayout(null);
		
		JLabel lblUser = new JLabel("USER: " + PAUser);
		lblUser.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
		lblUser.setBounds(89, 137, 433, 54);
		pnlCenter.add(lblUser);
		
		JLabel lblTitle = new JLabel("SISTEM ADMINISTRATIV - PERSONAL AEROPORT");
		lblTitle.setForeground(new Color(0, 0, 0));
		lblTitle.setFont(new Font("Yu Gothic UI", Font.BOLD, 25));
		lblTitle.setBounds(55, 86, 577, 74);
		pnlCenter.add(lblTitle);
		
		JLabel lblImage = new JLabel(new ImageIcon("images\\user.png"));
		lblImage.setBounds(247, 22, 205, 74);
		pnlCenter.add(lblImage);
		
		JPanel pnlTable = new JPanel();
		pnlTable.setBounds(19, 237, 652, 137);
		pnlCenter.add(pnlTable);
		
		dbConn = new DatabaseConnection("jdbc:mysql://localhost:3306/pj", "root", "root");
		dbConn.connect();
		
		table = new JTable();
		
		tableModel = new DefaultTableModel(
				new Object[][] {},
				new String[] {
						"Cod zbor", "Zi operare", "Business", "Economy", "Companie Aeriana"
				})
				{
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
				};
		pnlTable.setLayout(new BorderLayout(0, 0));
				
		table.setModel(tableModel);
		table.setBounds(65, 372, 489, -112);
	
		JScrollPane scrollPane = new JScrollPane(table);
		pnlTable.add(scrollPane);
		pnlCenter.add(pnlTable);
			
		JLabel lblLocation = new JLabel("LOCA\u021AIE: " + location);
		lblLocation.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
		lblLocation.setBounds(65, 156, 555, 54);
		pnlCenter.add(lblLocation);
		
		JButton btnViewFlight = new JButton("VEZI LIST\u0102 REZERV\u0102RI");
		btnViewFlight.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int tableRow = table.getSelectedRow();
				
				if (tableRow == -1)
				{
					JOptionPane.showMessageDialog(pnlMain, "Nu ati selectat niciun zbor!", "Eroare vizualizare zbor", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					PAFrameExtension_ViewFlight viewFlight = new PAFrameExtension_ViewFlight(dbConn, table.getValueAt(tableRow, 0).toString(), table.getValueAt(tableRow,  1).toString());
					viewFlight.setVisible(true);
				}
			}
		});
		btnViewFlight.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 16));
		btnViewFlight.setBounds(263, 395, 189, 48);
		
		btnViewFlight.setBackground(new Color(230, 231, 252));
		btnViewFlight.setFocusPainted(false);
		btnViewFlight.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		pnlCenter.add(btnViewFlight);
		
		readFlights(location);
	}
}
