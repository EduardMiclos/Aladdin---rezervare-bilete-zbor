package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Font;
import javax.swing.JPopupMenu;
import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import classes.ClasaLocuri;
import classes.CompanieAeriana;
import classes.Cursa;
import classes.DatabaseConnection;
import classes.NodTraseu;
import classes.ZiOperare;

import java.awt.Panel;
import java.awt.SystemColor;

public class CAFrame extends JFrame {

	private JPanel pnlMain;
	private JTextField txtReg;

	private JButton btnFirst;
	private JButton btnBefore;
	private JButton btnAfter;
	private JButton btnLast;

	private JButton btnAdd;
	private JButton btnFind;
	private JButton btnSave;
	private JButton btnUndo;

	private DatabaseConnection dbConn;
	private static int currentID;
	private static int dimension;

	private JComboBox comboDay;
	
	private boolean addItem;
	JSpinner spnBusinessSeats;
	private JSpinner spnEconomySeats;
	private JLabel lblEconomySeats;
	private JLabel lblBusinessPrice;
	private JSpinner spnBusinessPrice;
	private JLabel lblEuroIcon1;
	private JLabel lblEconomyPrice;
	private JSpinner spnEconomyPrice;
	private JLabel lblEuroIcon2;
	private JLabel lblPriceDiscount1;
	private JSpinner spnDiscount1;
	private JLabel lblProcentIcon1;
	private JTextField txtAirplaneType;
	private JTextField txtFlightCode;
	private JLabel lblTitle;
	private JLabel lblProcentIcon2;
	private JSpinner spnDiscount2;
	private JLabel lblPriceDiscount2;
	private JLabel lblRoute;
	private JTable table;
	private DefaultTableModel tableModel;

	CompanieAeriana companieAeriana;
	Cursa cursaCurenta;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CAFrame frame = new CAFrame("AmericanAirlines");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void displayData(Cursa cursa) {
		txtFlightCode.setText(cursa.getCodCursa());
		txtAirplaneType.setText(cursa.getTipAvion());
		
		spnBusinessSeats.setValue(cursa.getLocuriBusiness().getNumarLocuri());
		spnEconomySeats.setValue(cursa.getLocuriEconomy().getNumarLocuri());
		
		spnBusinessPrice.setValue(cursa.getLocuriBusiness().getPret());
		spnEconomyPrice.setValue(cursa.getLocuriEconomy().getPret());
		
		spnDiscount1.setValue(cursa.getDiscountDusIntors());
		spnDiscount2.setValue(cursa.getDiscountLastMinute());
		
		Vector<Date> date = new Vector<Date>();
		
		for (ZiOperare zi : cursa.getZileOperare()) {
			date.add(zi.getData());
		}
		
		comboDay.setModel(new DefaultComboBoxModel(date));

		tableModel = new DefaultTableModel(
				new Object[][] {},
				new String[] {
						"Aeroport", "Ora sosire", "Ora plecare"
				});
		
		table.setModel(tableModel);
		
		for(NodTraseu nod : cursa.getTrasee()) {			
			tableModel.addRow(new Object[]{
					nod.getNumeAeroport(),
					nod.getOraSosire(), 
					nod.getOraPlecare()});
		}
		
		txtReg.setText(CAFrame.currentID + "/" + CAFrame.dimension);
	}
	
	private void clearDisplay() {
		txtFlightCode.setText("");
		txtAirplaneType.setText("");
		
		spnBusinessSeats.setValue(spnBusinessSeats.getMinimumSize());
		spnEconomySeats.setValue(spnEconomySeats.getMinimumSize());
		
		spnBusinessPrice.setValue(spnBusinessPrice.getMinimumSize());
		spnEconomyPrice.setValue(spnEconomyPrice.getMinimumSize());
		
		spnDiscount1.setValue(spnDiscount1.getMinimumSize());
		spnDiscount2.setValue(spnDiscount2.getMinimumSize());
	}

	private void setStatus(Status Status) {
		if(CAFrame.currentID > 1) {
			btnFirst.setEnabled(Status.equals(interfaces.Status.Navigate));
			btnBefore.setEnabled(Status.equals(interfaces.Status.Navigate));
		}
		else {
			btnFirst.setEnabled(false);
			btnBefore.setEnabled(false);
		}

		if(CAFrame.currentID < CAFrame.dimension) {
			btnAfter.setEnabled(Status.equals(interfaces.Status.Navigate));
			btnLast.setEnabled(Status.equals(interfaces.Status.Navigate));			
		}
		else {
			btnAfter.setEnabled(false);
			btnLast.setEnabled(false);
		}

		btnAdd.setEnabled(Status.equals(interfaces.Status.Navigate));
		btnFind.setEnabled(Status.equals(interfaces.Status.Navigate));

		btnSave.setEnabled(Status.equals(interfaces.Status.Edit));
		btnUndo.setEnabled(Status.equals(interfaces.Status.Edit));
	}


	private void readFlights() throws SQLException {
		Cursa cursa;
		String codCursa, tipAvion, codTraseu;
		int locuriBusiness, locuriEconomy;
		float pretBusiness, pretEconomy;

		float discountDusIntors;
		float discountLastMinute;
	
		ClasaLocuri clsLocuriBusiness;
		ClasaLocuri clsLocuriEconomy;
		
		companieAeriana.setCurse(new Vector<Cursa>());
		
		dbConn.rs.beforeFirst();
		while(dbConn.rs.next()) {
			codCursa = dbConn.rs.getString("CodCursa");
			tipAvion = dbConn.rs.getString("TipAvion");
			
			locuriBusiness = dbConn.rs.getInt("LocuriBusiness");
			pretBusiness = dbConn.rs.getFloat("PretBusiness");
			
			locuriEconomy = dbConn.rs.getInt("LocuriEconomy");
			pretEconomy = dbConn.rs.getFloat("PretEconomy");
			
			clsLocuriBusiness = new ClasaLocuri(locuriBusiness, pretBusiness);
			clsLocuriEconomy = new ClasaLocuri(locuriEconomy, pretEconomy);
			
			discountDusIntors = dbConn.rs.getFloat("Discount_1");
			discountLastMinute = dbConn.rs.getFloat("Discount_2");
			
			codTraseu = dbConn.rs.getString("Traseu");
			
			cursa = new Cursa(codCursa, tipAvion, clsLocuriBusiness, clsLocuriEconomy, codTraseu, discountDusIntors, discountLastMinute);
			companieAeriana.getCurse().add(cursa);
		}
		
		
		String[] oreSosire, orePlecare, locatii;
		
		for (Cursa itrCursa : companieAeriana.getCurse()) {
			
			itrCursa.setTrasee(new Vector<NodTraseu>());
			itrCursa.setZileOperare(new Vector<ZiOperare>());
			
			/* Getting the route details from another table, making
			 * use of relational database concepts. */
			dbConn.sendQuery("SELECT * FROM trasee WHERE idTraseu='" + itrCursa.getCodTraseu() + "'");
			
			dbConn.rs.first();
			
			oreSosire = dbConn.rs.getString("Ore sosire").split(";");
			orePlecare = dbConn.rs.getString("Ore plecare").split(";");
			
			locatii = dbConn.rs.getString("Locatii").split(";");
			
			for(int i = 0; i < locatii.length; i++) {
				NodTraseu nodTraseu = new NodTraseu(locatii[i], orePlecare[i], oreSosire[i]);
				itrCursa.getTrasee().add(nodTraseu);
			}
			
			/* Getting all the flights. */
			dbConn.sendQuery("SELECT * FROM zboruri WHERE codCursa='" + itrCursa.getCodCursa() + "'");
			dbConn.rs.beforeFirst();
			
			
			while(dbConn.rs.next()) {
				Date date = dbConn.rs.getDate("ziOperare");
				int businessRamase = dbConn.rs.getInt("businessRamase");
				int economyRamase = dbConn.rs.getInt("economyRamase");
				
				ZiOperare ziOperare = new ZiOperare(date, businessRamase, economyRamase);
				itrCursa.getZileOperare().add(ziOperare);
			}
		}
		
		dbConn.sendQuery("SELECT * FROM curse WHERE CompanieAeriana='" + companieAeriana.getNumeCompanie() + "'");
		dbConn.rs.afterLast();
	}
	
	public CAFrame(String CAuser) throws SQLException {
		
		companieAeriana = new CompanieAeriana(CAuser);
		
		addWindowListener(new WindowAdapter() {

			/* Whenever the current window is closing,
			 * we disconnect from the database. */

			@Override
			public void windowClosing(WindowEvent e) {
				dbConn.disconnect();
			}
		});

		setTitle("Companie Aerian\u0103 - Gestiune baz\u0103 de date");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 842, 525);

		/* Initially, the add item option is disabled. */
		addItem = false;

		/* MAIN PANNEL. */
		pnlMain = new JPanel();
		pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlMain.setLayout(new BorderLayout(0, 0));
		setContentPane(pnlMain);
		/* ------------ */

		/* DATABASE CONNECTION. */
		/* Establishing a database connection to a localhost server. */
		dbConn = new DatabaseConnection("jdbc:mysql://localhost:3306/pj", "root", "root");
		
		dbConn.connect();			

		try {
			/* Select all the entries from the 'curse' table. */
			dbConn.sendQuery("SELECT * FROM curse WHERE CompanieAeriana='" + companieAeriana.getNumeCompanie() + "'");
		}
		catch(SQLException sqlException) {
			JOptionPane.showMessageDialog(pnlMain, "A aparut o eroare in timpul conectarii la baza de date!", "Eroare baza de date", JOptionPane.ERROR_MESSAGE);
			return;
		}

		/* Reading all the flights of the logged-in Airline. */
		readFlights();
		
		CAFrame.dimension = companieAeriana.getCurse().size();

		/* The first ID is either 1, if the table is not empty, or 0, if it is. */
		CAFrame.currentID = Math.min(CAFrame.dimension, 1);


		/* ====== NAVIGATION TOOLBAR ======. */
		JToolBar toolBar = new JToolBar();
		pnlMain.add(toolBar, BorderLayout.NORTH);


		/* NAVIGATION --- FIRST ELEMENT. */
		btnFirst = new JButton("");
		btnFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(CAFrame.dimension > 0) {
						CAFrame.currentID = 1;
						cursaCurenta = companieAeriana.getCurse().elementAt(0);
						
						displayData(cursaCurenta);
						setStatus(Status.Navigate);
					}
				}
		});
		btnFirst.setIcon(new ImageIcon(CAFrame.class.getResource("/icons/MoveFirst.png")));
		toolBar.add(btnFirst);
		/* ----------------------------- */


		/* NAVIGATION --- PREVIOUS ELEMENT. */
		btnBefore = new JButton("");
		btnBefore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(CAFrame.dimension > 0) {
					CAFrame.currentID -= 1 ;
					cursaCurenta = companieAeriana.getCurse().elementAt(CAFrame.currentID - 1);
					
					displayData(cursaCurenta);
					setStatus(Status.Navigate);
				}
			}
		});
		btnBefore.setIcon(new ImageIcon(CAFrame.class.getResource("/icons/MovePrevious.png")));
		toolBar.add(btnBefore);
		/* -------------------------------- */


		/* NAVIGATION --- CURRENT ELEMENT TEXT FIELD. */
		txtReg = new JTextField();
		toolBar.add(txtReg);
		txtReg.setColumns(10);
		/* ------------------------------------------ */


		/* NAVIGATION --- NEXT ELEMENT. */
		btnAfter = new JButton("");
		btnAfter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(CAFrame.dimension > 0) {
					CAFrame.currentID += 1 ;
					cursaCurenta = companieAeriana.getCurse().elementAt(CAFrame.currentID - 1);
					
					displayData(cursaCurenta);
					setStatus(Status.Navigate);
				}
			}
		});
		btnAfter.setIcon(new ImageIcon(CAFrame.class.getResource("/icons/MoveNext.png")));
		toolBar.add(btnAfter);
		/* -------------------------- */


		/* NAVIGATION --- LAST ELEMENT. */
		btnLast = new JButton("");
		btnLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(CAFrame.dimension > 0) {
					CAFrame.currentID = CAFrame.dimension;
					cursaCurenta = companieAeriana.getCurse().elementAt(CAFrame.currentID - 1);
					
					displayData(cursaCurenta);
					setStatus(Status.Navigate);
				}
			}
		});
		btnLast.setIcon(new ImageIcon(CAFrame.class.getResource("/icons/MoveLast.png")));
		toolBar.add(btnLast);
		/* ---------------------------- */


		/* EDIT --- ADDING ELEMENT. #TdbConn */
		btnAdd = new JButton("");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				/* The current state becomes 'Edit'. */
				setStatus(Status.Edit);

				try {
					/* We try to position the cursor on the inserting row. */
					dbConn.rs.moveToInsertRow();

					/* Incrementing the table dimension. */
					CAFrame.dimension++;

					/* We will point to the last element that was added,
					 * so the current id will be equal to the dimension. */
					CAFrame.currentID = CAFrame.dimension;


					/* Preparing the interface. */
					// afisareDate("", "", "");

					addItem = true;
				}
				catch(SQLException sqlException) {
					JOptionPane.showMessageDialog(pnlMain, "A aparut o eroare la scrierea in baza de date", "Eroare baza de date", JOptionPane.ERROR_MESSAGE);

					/* In case the operation fails, we return to the first element. */
					try {
						/* We try to position the cursor on the first row. */
						dbConn.rs.first();

						/* Showing the information to the user. */
						// afisareDate(dbConn.rs.getString("id"), dbConn.rs.getString("Nume"), dbConn.rs.getString("Varsta"));

						/* The current state changes to 'Navigate'. */
						setStatus(Status.Navigate);

						CAFrame.currentID = 1;
					} catch (SQLException exceptionSQL) {
						JOptionPane.showMessageDialog(pnlMain, "A aparut o eroare la citirea din baza de date!", "Eroare baza de date", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnAdd.setIcon(new ImageIcon(CAFrame.class.getResource("/icons/Add.png")));
		toolBar.add(btnAdd);
		/* ------------------------- */


		/* EDIT --- FINDING ELEMENT. #TdbConn */
		btnFind = new JButton("");
		btnFind.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String codCursaCautat = JOptionPane.showInputDialog("Introduceti codul cursei:");
				
				CAFrame.currentID = 0;
				for(Cursa itrCursa : companieAeriana.getCurse()) {
					currentID++;
					if(itrCursa.getCodCursa().equals(codCursaCautat)) {
						displayData(itrCursa);
						
						setStatus(Status.Navigate);
						return;
					}
				}
			}
		});
		btnFind.setIcon(new ImageIcon(CAFrame.class.getResource("/icons/find.JPG")));
		toolBar.add(btnFind);
		/* ----------------------------------- */


		/* EDIT --- SAVING ELEMENT. #TdbConn */
		btnSave = new JButton("");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnSave.isEnabled()) {
					if(false){//txtId.getText().isEmpty() || txtNume.getText().isEmpty()){//txtVarsta.getText().isEmpty()) {
						JOptionPane.showMessageDialog(pnlMain, "Toate campurile trebuie completate!", "Eroare inregistrari", JOptionPane.ERROR_MESSAGE);
					}
					else {
						try {
							//dbConn.rs.updateInt("id", Integer.parseInt(txtId.getText()));
							//dbConn.rs.updateString("Nume", txtNume.getText());
							//dbConn.rs.updateInt("Varsta", Integer.parseInt(txtVarsta.getText()));

							if(addItem == true) {
								dbConn.rs.insertRow();
								dbConn.rs.last();
								addItem = false;
							}
							else {
								dbConn.rs.updateRow();								
							}

							setStatus(Status.Navigate);
						} catch (SQLException sqlException) {
							JOptionPane.showMessageDialog(pnlMain, "A aparut o eroare la scrierea in baza de date", "Eroare baza de date", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		btnSave.setIcon(new ImageIcon(CAFrame.class.getResource("/icons/save.JPG")));
		toolBar.add(btnSave);
		/* ----------------------------- */


		/* EDIT --- UNDO. #TdbConn */
		btnUndo = new JButton("");
		btnUndo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnUndo.isEnabled()) {
					setStatus(Status.Navigate);

					if(addItem == true) {
						addItem = false;

						CAFrame.dimension--;
						CAFrame.currentID = CAFrame.dimension;

						if(CAFrame.dimension > 0) {
							try {
								dbConn.rs.last();
								// afisareDate(dbConn.rs.getString("id"), dbConn.rs.getString("Nume"), dbConn.rs.getString("Varsta"));
							} catch (SQLException e1) {
								e1.printStackTrace();
							}							
						}
						else {
							// afisareDate("", "", "");
						}
					}
				}
			}
		});
		btnUndo.setIcon(new ImageIcon(CAFrame.class.getResource("/icons/undo.JPG")));
		toolBar.add(btnUndo);
		/* -------------- */
		/* ================================= */


		/* ====== CENTER PANEL (MAIN) ======. */ 
		JPanel pnlCenter = new JPanel();
		pnlCenter.setBackground(SystemColor.inactiveCaptionBorder);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlCenter.setLayout(null);

		JLabel lblBusinessSeats = new JLabel("Locuri business");
		lblBusinessSeats.setBounds(111, 149, 101, 13);
		lblBusinessSeats.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlCenter.add(lblBusinessSeats);

		spnBusinessSeats = new JSpinner();
		spnBusinessSeats.setBounds(202, 147, 41, 20);
		spnBusinessSeats.setModel(new SpinnerNumberModel(0, 0, 30, 1));
		pnlCenter.add(spnBusinessSeats);

		JLabel lblFlightCode = new JLabel("Cod curs\u0103");
		lblFlightCode.setBounds(111, 82, 63, 13);
		lblFlightCode.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlCenter.add(lblFlightCode);

		JLabel lblAirplaneType = new JLabel("Tip avion");
		lblAirplaneType.setBounds(111, 105, 76, 13);
		lblAirplaneType.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlCenter.add(lblAirplaneType);

		spnEconomySeats = new JSpinner();
		spnEconomySeats.setBounds(202, 178, 41, 20);
		spnEconomySeats.setModel(new SpinnerNumberModel(0, 0, 30, 1));
		pnlCenter.add(spnEconomySeats);

		lblEconomySeats = new JLabel("Locuri economy");
		lblEconomySeats.setBounds(111, 180, 101, 13);
		lblEconomySeats.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlCenter.add(lblEconomySeats);

		lblBusinessPrice = new JLabel("Pre\u021B");
		lblBusinessPrice.setBounds(297, 149, 28, 13);
		lblBusinessPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlCenter.add(lblBusinessPrice);

		spnBusinessPrice = new JSpinner();
		spnBusinessPrice.setBounds(328, 147, 76, 20);
		spnBusinessPrice.setModel(new SpinnerNumberModel(new Float(0), new Float(0), new Float(2000), new Float(1)));
		pnlCenter.add(spnBusinessPrice);

		lblEuroIcon1 = new JLabel("\u20AC");
		lblEuroIcon1.setBounds(409, 149, 41, 13);
		lblEuroIcon1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlCenter.add(lblEuroIcon1);

		lblEconomyPrice = new JLabel("Pre\u021B");
		lblEconomyPrice.setBounds(297, 180, 28, 13);
		lblEconomyPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlCenter.add(lblEconomyPrice);

		spnEconomyPrice = new JSpinner();
		spnEconomyPrice.setBounds(328, 178, 76, 20);
		spnEconomyPrice.setModel(new SpinnerNumberModel(new Float(0), new Float(0), new Float(2000), new Float(1)));
		pnlCenter.add(spnEconomyPrice);

		lblEuroIcon2 = new JLabel("\u20AC");
		lblEuroIcon2.setBounds(409, 180, 41, 13);
		lblEuroIcon2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlCenter.add(lblEuroIcon2);

		lblPriceDiscount1 = new JLabel("Discount tip I (zboruri dus-\u00EEntors)");
		lblPriceDiscount1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPriceDiscount1.setBounds(84, 224, 195, 13);
		pnlCenter.add(lblPriceDiscount1);

		spnDiscount1 = new JSpinner();
		spnDiscount1.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		spnDiscount1.setBounds(277, 222, 76, 20);
		pnlCenter.add(spnDiscount1);

		lblProcentIcon1 = new JLabel("%");
		lblProcentIcon1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProcentIcon1.setBounds(363, 223, 41, 13);
		pnlCenter.add(lblProcentIcon1);

		txtAirplaneType = new JTextField();
		txtAirplaneType.setEditable(false);
		txtAirplaneType.setBounds(174, 103, 230, 19);
		pnlCenter.add(txtAirplaneType);
		txtAirplaneType.setColumns(10);

		txtFlightCode = new JTextField();
		txtFlightCode.setEditable(false);
		txtFlightCode.setColumns(10);
		txtFlightCode.setBounds(174, 80, 151, 19);
		pnlCenter.add(txtFlightCode);

		lblTitle = new JLabel("GESTIUNE CURSE - " + companieAeriana.getNumeCompanie());
		lblTitle.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 27));
		lblTitle.setBounds(141, 0, 539, 42);
		pnlCenter.add(lblTitle);

		lblProcentIcon2 = new JLabel("%");
		lblProcentIcon2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProcentIcon2.setBounds(363, 248, 41, 13);
		pnlCenter.add(lblProcentIcon2);

		spnDiscount2 = new JSpinner();
		spnDiscount2.setModel(new SpinnerNumberModel(0, 0, 100, 1));
		spnDiscount2.setBounds(277, 247, 76, 20);
		pnlCenter.add(spnDiscount2);

		lblPriceDiscount2 = new JLabel("Discount tip Ii (zboruri last-minute)");
		lblPriceDiscount2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPriceDiscount2.setBounds(84, 249, 195, 13);
		pnlCenter.add(lblPriceDiscount2);

		lblRoute = new JLabel("TRASEU CURS\u0102");
		lblRoute.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 18));
		lblRoute.setBounds(84, 293, 194, 29);
		pnlCenter.add(lblRoute);

		comboDay = new JComboBox();
		comboDay.setFont(new Font("Tahoma", Font.PLAIN, 13));
		comboDay.setBounds(174, 51, 230, 21);
		pnlCenter.add(comboDay);

		JLabel lblDay = new JLabel("Zile operare");
		lblDay.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDay.setBounds(101, 54, 63, 13);
		pnlCenter.add(lblDay);

		Panel panel = new Panel();
		panel.setBounds(10, 328, 787, 115);
		panel.setLayout(new BorderLayout(0, 0));

		table = new JTable();
		
		tableModel = new DefaultTableModel(
				new Object[][] {},
				new String[] {
						"Aeroport", "Ora sosire", "Ora plecare"
				});
				
		table.setModel(tableModel);
		

		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane);
		pnlCenter.add(panel);

		JPanel pnlDatePicker = new JPanel();
		pnlDatePicker.setBounds(460, 66, 337, 243);
		pnlCenter.add(pnlDatePicker);
		
		if(CAFrame.dimension > 0) {
			CAFrame.currentID = 1;
			cursaCurenta = companieAeriana.getCurse().elementAt(0);
			displayData(cursaCurenta);
		}

		txtReg.setText(CAFrame.currentID + "/" + CAFrame.dimension);
		setStatus(Status.Navigate);
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
