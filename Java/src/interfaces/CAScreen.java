package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.SpinnerListModel;
import javax.swing.JPopupMenu;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Panel;
import java.awt.SystemColor;

public class CAScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtInregistrare;

	private JButton btnPrim;
	private JButton btnPrecedent;
	private JButton btnUrmator;
	private JButton btnUltim;

	private JButton btnAdaugare;
	private JButton btnEditare;
	private JButton btnStergere;
	private JButton btnGasire;
	private JButton btnSalvare;
	private JButton btnRenuntare;

	private BazaDeDate bd;
	private static int idCurent;
	private static int dimensiune;

	private boolean adaugareInregistrare;
	private JSpinner spnEconomy;
	private JLabel lblLocuriEconomy;
	private JLabel lblPretBusiness;
	private JSpinner spnPretBusiness;
	private JLabel lblEuroIcon1;
	private JLabel lblPretEconomy;
	private JSpinner spnPretEconomy;
	private JLabel lblEuroIcon2;
	private JLabel lblPretDiscount1;
	private JSpinner spnDiscount1;
	private JLabel lblProcentIcon1;
	private JTextField txtTipAvion;
	private JTextField txtCodCursa;
	private JLabel lblTitlu;
	private JLabel lblProcentIcon2;
	private JSpinner spnDiscount2;
	private JLabel lblPretDiscount2;
	private JLabel lblTraseu;
	private JList lstZileOperare;
	private JList list;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CAScreen frame = new CAScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void afisareDate(String id, String nume, String varsta) {
		//txtId.setText(id);
		//txtNume.setText(nume);
		//txtVarsta.setText(varsta);

		txtInregistrare.setText(CAScreen.idCurent + "/" + CAScreen.dimensiune);
	}

	private void setStare(Stare stare) {
		if(CAScreen.idCurent > 1) {
			btnPrim.setEnabled(stare.equals(Stare.Navigare));
			btnPrecedent.setEnabled(stare.equals(Stare.Navigare));
		}
		else {
			btnPrim.setEnabled(false);
			btnPrecedent.setEnabled(false);
		}

		if(CAScreen.idCurent < CAScreen.dimensiune) {
			btnUrmator.setEnabled(stare.equals(Stare.Navigare));
			btnUltim.setEnabled(stare.equals(Stare.Navigare));			
		}
		else {
			btnUrmator.setEnabled(false);
			btnUltim.setEnabled(false);
		}

		btnAdaugare.setEnabled(stare.equals(Stare.Navigare));
		btnEditare.setEnabled(stare.equals(Stare.Navigare));
		btnStergere.setEnabled(stare.equals(Stare.Navigare));
		btnGasire.setEnabled(stare.equals(Stare.Navigare));

		btnSalvare.setEnabled(stare.equals(Stare.Editare));
		btnRenuntare.setEnabled(stare.equals(Stare.Editare));
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public CAScreen() throws SQLException {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				bd.deconectare();
			}
		});
		setTitle("Gestiune baza de date");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 548, 513);

		adaugareInregistrare = false;

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		bd = new BazaDeDate("jdbc:mysql://localhost:3306/pj", "root", "root");
		bd.conectare();
		bd.citireDate("SELECT * FROM Persoane");

		CAScreen.dimensiune = bd.dimensiuneTabela();
		CAScreen.idCurent = Math.min(bd.dimensiuneTabela(), 1);

		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);


		/* NAVIGARE --- PRIMUL ELEMENT. */
		btnPrim = new JButton("");
		btnPrim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(bd.rs.first()) {
						CAScreen.idCurent = 1;
						afisareDate(bd.rs.getString("Id"), bd.rs.getString("Nume"), bd.rs.getString("Varsta"));

						setStare(Stare.Navigare);
					}
				} catch (SQLException sqlException) {
					sqlException.printStackTrace();
				}
			}
		});
		btnPrim.setIcon(new ImageIcon(CAScreen.class.getResource("/icons/MoveFirst.png")));
		toolBar.add(btnPrim);


		/* NAVIGARE --- ELEMENTUL ANTERIOR. */
		btnPrecedent = new JButton("");
		btnPrecedent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(bd.rs.previous()) {
						CAScreen.idCurent -= 1;
						afisareDate(bd.rs.getString("Id"), bd.rs.getString("Nume"), bd.rs.getString("Varsta"));

						setStare(Stare.Navigare);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnPrecedent.setIcon(new ImageIcon(CAScreen.class.getResource("/icons/MovePrevious.png")));
		toolBar.add(btnPrecedent);


		/* NAVIGARE --- INREGISTRARE CURENTA. */
		txtInregistrare = new JTextField();
		toolBar.add(txtInregistrare);
		txtInregistrare.setColumns(10);


		/* NAVIGARE --- ELEMENTUL URMATOR. */
		btnUrmator = new JButton("");
		btnUrmator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(bd.rs.next()) {
						CAScreen.idCurent += 1;
						afisareDate(bd.rs.getString("Id"), bd.rs.getString("Nume"), bd.rs.getString("Varsta"));

						setStare(Stare.Navigare);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUrmator.setIcon(new ImageIcon(CAScreen.class.getResource("/icons/MoveNext.png")));
		toolBar.add(btnUrmator);


		/* NAVIGARE --- ULTIMUL ELEMENT. */
		btnUltim = new JButton("");
		btnUltim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(bd.rs.last()) {
						CAScreen.idCurent = CAScreen.dimensiune;
						afisareDate(bd.rs.getString("Id"), bd.rs.getString("Nume"), bd.rs.getString("Varsta"));

						setStare(Stare.Navigare);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUltim.setIcon(new ImageIcon(CAScreen.class.getResource("/icons/MoveLast.png")));
		toolBar.add(btnUltim);


		/* EDITARE -- ADAUGARE ELEMENT. */
		btnAdaugare = new JButton("");
		btnAdaugare.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/* Programul intra in starea de editare. */
				setStare(Stare.Editare);

				try {
					bd.rs.moveToInsertRow();

					CAScreen.dimensiune++;
					CAScreen.idCurent = CAScreen.dimensiune;

					/* Se pregateste interfata pentru introducerea
					 * de noi date. */
					afisareDate("", "", "");

					/* Setam pe true flag-ul de adaugare. */
					adaugareInregistrare = true;
				}
				catch(SQLException sqlException) {
					JOptionPane.showMessageDialog(contentPane, "A aparut o eroare la scrierea in baza de date", "Eroare baza de date", JOptionPane.ERROR_MESSAGE);

					try {
						bd.rs.first();

						afisareDate(bd.rs.getString("id"), bd.rs.getString("Nume"), bd.rs.getString("Varsta"));

						setStare(Stare.Navigare);
						CAScreen.idCurent = 1;
					} catch (SQLException exceptionSql) {}
				}
			}
		});
		btnAdaugare.setIcon(new ImageIcon(CAScreen.class.getResource("/icons/Add.png")));
		toolBar.add(btnAdaugare);


		/* EDITARE --- EDITARE ELEMENT. */
		btnEditare = new JButton("");
		btnEditare.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if(CAScreen.dimensiune > 0) {
					setStare(Stare.Editare);
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "Nu exista inregistrari de modificat!", "Eroare modificare inregistrare", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEditare.setIcon(new ImageIcon(CAScreen.class.getResource("/icons/Edit.png")));
		toolBar.add(btnEditare);


		/* EDITARE --- STERGERE ELEMENT. */
		btnStergere = new JButton("");
		btnStergere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(CAScreen.dimensiune == 0) {
						throw new SQLException();
					}
					
					boolean primElement = false;
					int idCurent = CAScreen.idCurent;

					if(bd.rs.isFirst() && !bd.rs.isLast()) {
						primElement = true;
					}
					else if(bd.rs.isLast() && !bd.rs.isFirst()) {
						CAScreen.idCurent -= 1;
					}
					else if(CAScreen.dimensiune > 0){
						CAScreen.idCurent -= 1;
					}
					else {
						CAScreen.idCurent = 0;
					}

					int raspunsDialog = JOptionPane.showConfirmDialog(contentPane, "Sunteti sigur ca doriti sa stergeti persoana selectata?", "Confirmare stergere", JOptionPane.OK_CANCEL_OPTION);

					if(raspunsDialog == 0) {
						bd.rs.deleteRow();					
						if(primElement == true) {
							bd.rs.first();
						}

						CAScreen.dimensiune--;
						setStare(Stare.Navigare);

						if(CAScreen.dimensiune > 0) {
							afisareDate(bd.rs.getString("id"), bd.rs.getString("Nume"), bd.rs.getString("Varsta"));
						}
						else {
							afisareDate("", "", "");
						}
					}
					else {
						CAScreen.idCurent = idCurent;
					}
				} catch (SQLException sqlException) {
					JOptionPane.showMessageDialog(contentPane, "Nu s-a putut sterge inregistrarea selectata!", "Eroare stergere inregistrare", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnStergere.setIcon(new ImageIcon(CAScreen.class.getResource("/icons/Delete.png")));
		toolBar.add(btnStergere);


		/* EDITARE --- GASIRE ELEMENT. */
		btnGasire = new JButton("");
		btnGasire.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String numeCautat = JOptionPane.showInputDialog("Introduceti numele persoanei cautate");

				try {
					bd.rs.beforeFirst();
					CAScreen.idCurent = 0;
					while(bd.rs.next()) {
						CAScreen.idCurent++;
						if(bd.rs.getString("Nume").equals(numeCautat)) {
							afisareDate(bd.rs.getString("id"), numeCautat, bd.rs.getString("Varsta"));
							setStare(Stare.Navigare);
							return;
						}
					}

					bd.rs.last();
					afisareDate(bd.rs.getString("id"), bd.rs.getString("Nume"), bd.rs.getString("Varsta"));
					setStare(Stare.Navigare);

					JOptionPane.showMessageDialog(contentPane, "Nu s-a putut gasi elementul cautat!", "Eroare gasire element", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException sqlException) {
					JOptionPane.showMessageDialog(contentPane, "Nu s-a putut efectua cautarea!", "Eroare la cautare", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnGasire.setIcon(new ImageIcon(CAScreen.class.getResource("/icons/find.JPG")));
		toolBar.add(btnGasire);


		/* EDITARE --- SALVARE ELEMENT. */
		btnSalvare = new JButton("");
		btnSalvare.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnSalvare.isEnabled()) {
					if(false){//txtId.getText().isEmpty() || txtNume.getText().isEmpty()){//txtVarsta.getText().isEmpty()) {
						JOptionPane.showMessageDialog(contentPane, "Toate campurile trebuie completate!", "Eroare inregistrari", JOptionPane.ERROR_MESSAGE);
					}
					else {
						try {
							//bd.rs.updateInt("id", Integer.parseInt(txtId.getText()));
							//bd.rs.updateString("Nume", txtNume.getText());
							//bd.rs.updateInt("Varsta", Integer.parseInt(txtVarsta.getText()));

							if(adaugareInregistrare == true) {
								bd.rs.insertRow();
								bd.rs.last();
								adaugareInregistrare = false;
							}
							else {
								bd.rs.updateRow();								
							}

							setStare(Stare.Navigare);
						} catch (SQLException sqlException) {
							JOptionPane.showMessageDialog(contentPane, "A aparut o eroare la scrierea in baza de date", "Eroare baza de date", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		btnSalvare.setIcon(new ImageIcon(CAScreen.class.getResource("/icons/save.JPG")));
		toolBar.add(btnSalvare);


		/* EDITARE --- RENUNTARE LA OPERATIA CURENTA. */
		btnRenuntare = new JButton("");
		btnRenuntare.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnRenuntare.isEnabled()) {
					setStare(Stare.Navigare);

					if(adaugareInregistrare == true) {
						adaugareInregistrare = false;

						CAScreen.dimensiune--;
						CAScreen.idCurent = CAScreen.dimensiune;

						if(CAScreen.dimensiune > 0) {
							try {
								bd.rs.last();
								afisareDate(bd.rs.getString("id"), bd.rs.getString("Nume"), bd.rs.getString("Varsta"));
							} catch (SQLException e1) {
								e1.printStackTrace();
							}							
						}
						else {
							afisareDate("", "", "");
						}
					}
				}
			}
		});
		
		btnRenuntare.setIcon(new ImageIcon(CAScreen.class.getResource("/icons/undo.JPG")));
		toolBar.add(btnRenuntare);

		JPanel pnlPrincipal = new JPanel();
		pnlPrincipal.setBackground(SystemColor.inactiveCaptionBorder);
		contentPane.add(pnlPrincipal, BorderLayout.CENTER);
		pnlPrincipal.setLayout(null);
		
		JLabel lblLocuriBusiness = new JLabel("Locuri business ramase");
		lblLocuriBusiness.setBounds(85, 149, 127, 13);
		lblLocuriBusiness.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlPrincipal.add(lblLocuriBusiness);
		
		JSpinner spnBusiness = new JSpinner();
		spnBusiness.setBounds(222, 147, 41, 20);
		spnBusiness.setModel(new SpinnerNumberModel(0, 0, 30, 1));
		pnlPrincipal.add(spnBusiness);
		
				JLabel lblCodCursa = new JLabel("Cod cursa");
				lblCodCursa.setBounds(113, 82, 63, 13);
				lblCodCursa.setFont(new Font("Tahoma", Font.PLAIN, 12));
				pnlPrincipal.add(lblCodCursa);
				
						JLabel lblTipAvion = new JLabel("Tip avion");
						lblTipAvion.setBounds(113, 105, 76, 13);
						lblTipAvion.setFont(new Font("Tahoma", Font.PLAIN, 12));
						pnlPrincipal.add(lblTipAvion);
						
						spnEconomy = new JSpinner();
						spnEconomy.setBounds(222, 178, 41, 20);
						spnEconomy.setModel(new SpinnerNumberModel(0, 0, 30, 1));
						pnlPrincipal.add(spnEconomy);
						
						lblLocuriEconomy = new JLabel("Locuri economy ramase");
						lblLocuriEconomy.setBounds(81, 180, 141, 13);
						lblLocuriEconomy.setFont(new Font("Tahoma", Font.PLAIN, 12));
						pnlPrincipal.add(lblLocuriEconomy);
						
						lblPretBusiness = new JLabel("Pret");
						lblPretBusiness.setBounds(297, 149, 28, 13);
						lblPretBusiness.setFont(new Font("Tahoma", Font.PLAIN, 12));
						pnlPrincipal.add(lblPretBusiness);
						
						spnPretBusiness = new JSpinner();
						spnPretBusiness.setBounds(328, 147, 76, 20);
						spnPretBusiness.setModel(new SpinnerNumberModel(new Float(0), new Float(0), new Float(2000), new Float(1)));
						pnlPrincipal.add(spnPretBusiness);
						
						lblEuroIcon1 = new JLabel("\u20AC");
						lblEuroIcon1.setBounds(409, 149, 41, 13);
						lblEuroIcon1.setFont(new Font("Tahoma", Font.PLAIN, 14));
						pnlPrincipal.add(lblEuroIcon1);
						
						lblPretEconomy = new JLabel("Pret");
						lblPretEconomy.setBounds(297, 180, 28, 13);
						lblPretEconomy.setFont(new Font("Tahoma", Font.PLAIN, 12));
						pnlPrincipal.add(lblPretEconomy);
						
						spnPretEconomy = new JSpinner();
						spnPretEconomy.setBounds(328, 178, 76, 20);
						spnPretEconomy.setModel(new SpinnerNumberModel(new Float(0), new Float(0), new Float(2000), new Float(1)));
						pnlPrincipal.add(spnPretEconomy);
						
						lblEuroIcon2 = new JLabel("\u20AC");
						lblEuroIcon2.setBounds(409, 180, 41, 13);
						lblEuroIcon2.setFont(new Font("Tahoma", Font.PLAIN, 14));
						pnlPrincipal.add(lblEuroIcon2);
						
						lblPretDiscount1 = new JLabel("Discount tip I (zboruri dus-intors)");
						lblPretDiscount1.setFont(new Font("Tahoma", Font.PLAIN, 12));
						lblPretDiscount1.setBounds(84, 224, 195, 13);
						pnlPrincipal.add(lblPretDiscount1);
						
						spnDiscount1 = new JSpinner();
						spnDiscount1.setModel(new SpinnerNumberModel(0, 0, 100, 1));
						spnDiscount1.setBounds(277, 222, 76, 20);
						pnlPrincipal.add(spnDiscount1);
						
						lblProcentIcon1 = new JLabel("%");
						lblProcentIcon1.setFont(new Font("Tahoma", Font.PLAIN, 14));
						lblProcentIcon1.setBounds(363, 223, 41, 13);
						pnlPrincipal.add(lblProcentIcon1);
						
						txtTipAvion = new JTextField();
						txtTipAvion.setBounds(174, 103, 230, 19);
						pnlPrincipal.add(txtTipAvion);
						txtTipAvion.setColumns(10);
						
						txtCodCursa = new JTextField();
						txtCodCursa.setColumns(10);
						txtCodCursa.setBounds(174, 80, 118, 19);
						pnlPrincipal.add(txtCodCursa);
						
						lblTitlu = new JLabel("GESTIUNE CURSE");
						lblTitlu.setFont(new Font("Tahoma", Font.BOLD, 25));
						lblTitlu.setBounds(140, 10, 264, 42);
						pnlPrincipal.add(lblTitlu);
						
						lblProcentIcon2 = new JLabel("%");
						lblProcentIcon2.setFont(new Font("Tahoma", Font.PLAIN, 14));
						lblProcentIcon2.setBounds(363, 248, 41, 13);
						pnlPrincipal.add(lblProcentIcon2);
						
						spnDiscount2 = new JSpinner();
						spnDiscount2.setModel(new SpinnerNumberModel(0, 0, 100, 1));
						spnDiscount2.setBounds(277, 247, 76, 20);
						pnlPrincipal.add(spnDiscount2);
						
						lblPretDiscount2 = new JLabel("Discount tip Ii (zboruri last-minute)");
						lblPretDiscount2.setFont(new Font("Tahoma", Font.PLAIN, 12));
						lblPretDiscount2.setBounds(84, 249, 195, 13);
						pnlPrincipal.add(lblPretDiscount2);
						
						lblTraseu = new JLabel("Traseu cursa");
						lblTraseu.setFont(new Font("Tahoma", Font.PLAIN, 14));
						lblTraseu.setBounds(210, 293, 100, 13);
						pnlPrincipal.add(lblTraseu);
						
						JComboBox comboZiOperare = new JComboBox();
						comboZiOperare.setEditable(true);
						comboZiOperare.setFont(new Font("Tahoma", Font.PLAIN, 13));
						comboZiOperare.setModel(new DefaultComboBoxModel(new String[] {"20.05.22", "24.05.22", "30.05.22"}));
						comboZiOperare.setBounds(174, 51, 230, 21);
						pnlPrincipal.add(comboZiOperare);
						
						JLabel lblZiOperare = new JLabel("Zi operare");
						lblZiOperare.setFont(new Font("Tahoma", Font.PLAIN, 12));
						lblZiOperare.setBounds(113, 56, 63, 13);
						pnlPrincipal.add(lblZiOperare);
						
						Panel panel = new Panel();
						panel.setBounds(10, 328, 504, 115);
						panel.setLayout(new BorderLayout(0, 0));
						
						table = new JTable();
						table.setModel(new DefaultTableModel(
							new Object[][] {
								{"Aeroportul Otopeni", "-", "09:24"},
								{"Aeroportul Traian Vuia", "10:30", "11:44"},
								{"Aeroportul Berlin Brandenburg", "14:55", "15:30"},
								{"Aeroportul International Madrid-Barajas", "17:45", "18:00"},
								{"Aeroportul International Leonardo Da Vinci", "19:40", null},
							},
							new String[] {
								"Aeroport", "Ora sosire", "Ora plecare"
							}
						));
						//panel.add(table);
						panel.add(new JScrollPane(table));

						pnlPrincipal.add(panel);
						
//						((DefaultListModel) lstZileOperare.getModel()).addElement("11.05.22");
//						((DefaultListModel) lstZileOperare.getModel()).addElement("13.05.22");
//						((DefaultListModel) lstZileOperare.getModel()).addElement("21.05.22");

		if(this.bd.rs.next()) {
			afisareDate(this.bd.rs.getString("Id"), this.bd.rs.getString("Nume"), this.bd.rs.getString("Varsta"));
			this.bd.rs.first();
		}

		txtInregistrare.setText(CAScreen.idCurent + "/" + CAScreen.dimensiune);

		setStare(Stare.Navigare);
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
