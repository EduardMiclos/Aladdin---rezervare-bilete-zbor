package interfaces;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import classes.Client;
import classes.Cursa;
import classes.DatabaseConnection;
import classes.TipPlata;
import classes.Zbor;

import java.awt.Color;
import javax.swing.ScrollPaneConstants;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFormattedTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;

public class UserFrame_test extends JFrame {
	private JPanel contentPane;
	private JPanel pnlFlights = new JPanel();
	private JPanel pnlDest = new JPanel();

	private JTextField txtPlecare = new JTextField();
	private JTextField txtSosire = new JTextField();
	private JLabel lblPlecareWarning = new JLabel("");
	private JLabel lblSosireWarning = new JLabel("");
	private JDatePickerImpl dpickerPlecare;
	private JDatePickerImpl dpickerSosire;
	private JLabel lblDPlecareWarning = new JLabel("");
	private JLabel lblDSosireWarning = new JLabel("");
	private JCheckBox cbxRetur = new JCheckBox("Retur");
	private JComboBox cmbClasa = new JComboBox();
	private JSpinner spinnAdulti = new JSpinner();
	private JSpinner spinnCopii = new JSpinner();
	private JButton btnCautaCurse = new JButton("Cautare zbor");

	private JPanel pnlOptions = new JPanel();
	private JLabel lblLipsaCurse = new JLabel("Nu au fost gasite curse cu destinatia aleasa");
	private JLabel lblMesajInformativ = new JLabel();
	private JLabel lblDSosire = new JLabel("Data sosire:");
	private JButton btnRezervare = new JButton("Rezervare");

	private Color colorWarning = new Color(245, 113, 113);
	private Color colorPositive = new Color(133, 200, 138);
	private boolean testFailed = false;
	private boolean btnCautaCursaPressed = false;
	private Dimension optionMinDim = new Dimension(720, 60);
	private Dimension optionPrefDim = new Dimension(780, 70);
	private Dimension optionMaxDim = new Dimension(950, 85);
	private DatabaseConnection bd;
	private LocalDateTime Today = LocalDateTime.now();
	private LocalDateTime dateLeave = null;
	private LocalDateTime dateArrive = null;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy");
	private ArrayList<Zbor> matchedFlights = null;
	private ArrayList<Cursa> selectedFlight = new ArrayList<Cursa>();
	private JPanel[] matchedOptions = null;
	private int tableSize = 0;

	private JRadioButton rdbtnCard = new JRadioButton("Card");
	private JRadioButton rdbtnViramentBancar = new JRadioButton("Virament bancar");
	private JRadioButton rdbtnCash = new JRadioButton("Cash");
	private JTextField txtPrenumeDetinator;
	private JTextField txtNumeDetinator;
	private JPanel pnlPassengers = new JPanel();
	private JPanel pnlPayFields = new JPanel();
	private JButton btnFinalizare = new JButton("Finalizare");
	private JPanel[] Passengers = null;
	private ArrayList<Client> clienti = null;
	private boolean flagFocus = false;
	private boolean flagKeys = false;
	private boolean flagDebitCard = false;
	private boolean flagViramentBancar = false;

	/* LABEL TEXT ALIGNMENT */
	public void alignLabels(JPanel pnl) {
		Component[] leaveComponents = pnl.getComponents();
		for (Component c : leaveComponents) {
			// System.out.println(c.getClass().getName());
			if (c.getClass().getName() != null && c.getClass().getName().equals("javax.swing.JPanel")
					&& !c.getClass().getName().equals("javax.swing.Box$Filler")) {
				Component[] pnlComponents = ((JPanel) c).getComponents();
				for (Component l : pnlComponents) {
					((JLabel) l).setHorizontalAlignment(SwingConstants.CENTER);
				}
			} else if (c.getClass().getName() != null && !c.getClass().getName().equals("javax.swing.Box$Filler")) {
				((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
			}
		}
	}

	/*
	 * DATA EXTRACTION --- EXTRACTING DATA FROM THE DPICKER AND CONVERTING INTO
	 * LOCALDATETIME.
	 */
	public void extractDate() {
		Date c1 = (Date) dpickerPlecare.getModel().getValue();
		if (c1 != null) {
			Instant iC1 = c1.toInstant();
			dateLeave = LocalDateTime.ofInstant(iC1, ZoneId.systemDefault());
			if (!dateLeave.truncatedTo(ChronoUnit.DAYS).equals(Today.truncatedTo(ChronoUnit.DAYS))) {
				dateLeave = dateLeave.truncatedTo(ChronoUnit.DAYS);
			}
		}
		Date c2 = (Date) dpickerSosire.getModel().getValue();
		if (c2 != null) {
			Instant iC2 = c2.toInstant();
			dateArrive = LocalDateTime.ofInstant(iC2, ZoneId.systemDefault());
			dateArrive = dateArrive.truncatedTo(ChronoUnit.DAYS);
		}
	}

	/* FELDS VALIDATION --- FIELDS CANNOT BE EMPTY + SOME DPICKER VALIDATION. */
	public void fieldsValid() {
		if (txtPlecare.getText().isBlank()) {
			lblPlecareWarning.setVisible(true);
			lblPlecareWarning.setToolTipText("Camp obligatoriu!");
			txtPlecare.setBackground(colorWarning);
			txtPlecare.requestFocusInWindow();
			testFailed = true;
		}
		if (txtSosire.getText().isBlank()) {
			lblSosireWarning.setVisible(true);
			lblSosireWarning.setToolTipText("Camp obligatoriu!");
			txtSosire.setBackground(colorWarning);
			txtSosire.requestFocusInWindow();
			testFailed = true;
		}
		if (dateLeave == null) {
			lblDPlecareWarning.setVisible(true);
			lblDPlecareWarning.setToolTipText("Camp obligatoriu!");
			dpickerPlecare.getJFormattedTextField().setBackground(colorWarning);
			dpickerPlecare.getButtonFocusable();
			testFailed = true;
		} else if (dateLeave != null && dateLeave.isBefore(Today)) {
			lblDPlecareWarning.setVisible(true);
			lblDPlecareWarning.setToolTipText("Data plecarii nu poate fi in trecut!");
			dpickerPlecare.getJFormattedTextField().setBackground(colorWarning);
			dpickerPlecare.getButtonFocusable();
			testFailed = true;

		}
		if (cbxRetur.isSelected() && dateArrive == null) {
			lblDSosireWarning.setVisible(true);
			lblDSosireWarning.setToolTipText("Camp obligatoriu!");
			dpickerSosire.getJFormattedTextField().setBackground(colorWarning);
			dpickerSosire.getButtonFocusable();
			testFailed = true;
		} else if (dateArrive != null && dateArrive.isBefore(dateLeave)) {
			lblDSosireWarning.setVisible(true);
			lblDSosireWarning.setToolTipText("Data sosirii nu poate fi inaintea datei plecarii!");
			dpickerSosire.getJFormattedTextField().setBackground(colorWarning);
			dpickerSosire.getButtonFocusable();
			testFailed = true;

		}
	}

	/* GETTING THE NEXT DAY. */
	public LocalDateTime endOfDay(LocalDateTime d) {
		if (d != null) {
			if (d.truncatedTo(ChronoUnit.DAYS).equals(Today.truncatedTo(ChronoUnit.DAYS))) {
				return d.plusDays(1).truncatedTo(ChronoUnit.DAYS);
			} else {
				return d.plusDays(1);
			}
		} else
			return null;
	}

	/* flightTwoWays --- GENERATING JPANEL FOR ROUND-TRIP. */
	/* NOT RELATED TO DATABASE. */
	public JPanel flightTwoWays(Zbor departure, Zbor arrival) {

		JPanel pnlFlight = new JPanel();

		pnlOptions.add(pnlFlight);
		pnlOptions.revalidate();
		pnlFlight.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(5, 2, 5, 2),
				new MatteBorder(1, 1, 1, 1, Color.DARK_GRAY)));
		pnlFlight.setLayout(new BoxLayout(pnlFlight, BoxLayout.Y_AXIS));

		JPanel pnlLeave = new JPanel();
		GridLayout gridLeave = new GridLayout(3, 5, 5, 5);
		pnlFlight.add(pnlLeave);
		pnlFlight.revalidate();
		pnlLeave.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		pnlLeave.setAlignmentX(LEFT_ALIGNMENT);
		pnlLeave.setLayout(gridLeave);
		pnlLeave.setMinimumSize(optionMinDim);
		pnlLeave.setPreferredSize(optionPrefDim);
		pnlLeave.setMaximumSize(optionMaxDim);

		JLabel lblCompanyName = new JLabel(departure.getNumeCompanie());
		pnlLeave.add(lblCompanyName);
		pnlLeave.revalidate();

		JLabel lblOraPlecare = new JLabel(
				departure.getDataPlecare().getHour() + ":" + departure.getDataPlecare().getMinute());
		pnlLeave.add(lblOraPlecare);
		pnlLeave.revalidate();

		Duration flightDuration1 = Duration.between(departure.getDataPlecare(), departure.getDataSosire());
		long secondsDuration = flightDuration1.getSeconds();
		JLabel lblTimp = new JLabel(secondsDuration / 3600 + "h " + (secondsDuration % 3600) / 60 + "min");
		pnlLeave.add(lblTimp);
		pnlLeave.revalidate();

		JLabel lblOraSosire = new JLabel(
				departure.getDataSosire().getHour() + ":" + departure.getDataSosire().getMinute());
		pnlLeave.add(lblOraSosire);
		pnlLeave.revalidate();

		Component vStrut_1 = Box.createVerticalStrut(20);
		pnlLeave.add(vStrut_1);
		pnlLeave.revalidate();

		JLabel lblCodZbor = new JLabel(departure.getCodZbor());
		pnlLeave.add(lblCodZbor);
		pnlLeave.revalidate();

		JLabel lblAeroportPlecare = new JLabel(departure.getAeroportPlecare());
		pnlLeave.add(lblAeroportPlecare);
		pnlLeave.revalidate();

		Component vStrut_2 = Box.createVerticalStrut(5);
		pnlLeave.add(vStrut_2);
		pnlLeave.revalidate();

		JLabel lblAeroportSosire = new JLabel(departure.getAeroportSosire());
		pnlLeave.add(lblAeroportSosire);
		pnlLeave.revalidate();

		Component vStrut_3 = Box.createVerticalStrut(20);
		pnlLeave.add(vStrut_3);
		pnlLeave.revalidate();

		JPanel pnlSeats = new JPanel();
		pnlLeave.add(pnlSeats);
		pnlLeave.revalidate();
		pnlSeats.setLayout(new BoxLayout(pnlSeats, BoxLayout.X_AXIS));

		JLabel lblLocuriBusiness = new JLabel("Business: " + departure.getBusinessRamase() + "  ");
		pnlSeats.add(lblLocuriBusiness);
		pnlSeats.revalidate();

		JLabel lblLocuriEconomy = new JLabel("Economy: " + departure.getEconomyRamase() + "  ");
		pnlSeats.add(lblLocuriEconomy);
		pnlSeats.revalidate();

		JLabel lblOrasPlecare = new JLabel(departure.getOrasPornire());
		pnlLeave.add(lblOrasPlecare);
		pnlLeave.revalidate();

		Component vStrut_4 = Box.createVerticalStrut(20);
		pnlLeave.add(vStrut_4);
		pnlLeave.revalidate();

		JLabel lblOrasSosire = new JLabel(departure.getOrasDestinatie());
		pnlLeave.add(lblOrasSosire);
		pnlLeave.revalidate();

		Component vStrut_5 = Box.createVerticalStrut(20);
		pnlLeave.add(vStrut_5);
		pnlLeave.revalidate();

		alignLabels(pnlLeave);

		JPanel pnlReturn = new JPanel();
		GridLayout gridReturn = new GridLayout(3, 5, 5, 5);
		pnlFlight.add(pnlReturn);
		pnlFlight.revalidate();
		pnlReturn.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		pnlReturn.setAlignmentX(LEFT_ALIGNMENT);
		pnlReturn.setLayout(gridReturn);
		pnlReturn.setMinimumSize(optionMinDim);
		pnlReturn.setPreferredSize(optionPrefDim);
		pnlReturn.setMaximumSize(optionMaxDim);

		JLabel lblCompanyName_1 = new JLabel(arrival.getNumeCompanie());
		lblCompanyName_1.setAlignmentX(1.0f);
		pnlReturn.add(lblCompanyName_1);
		pnlReturn.revalidate();

		JLabel lblOraPlecare_1 = new JLabel(
				arrival.getDataPornire().getHour() + ":" + arrival.getDataPornire().getMinute());
		pnlReturn.add(lblOraPlecare_1);
		pnlReturn.revalidate();

		Duration flightDuration2 = Duration.between(departure.getDataPornire(), departure.getDataSosire());
		long secondsDuration_1 = flightDuration2.getSeconds();
		JLabel lblTimp_1 = new JLabel(secondsDuration_1 / 3600 + "h " + (secondsDuration_1 % 3600) / 60 + "min");
		pnlReturn.add(lblTimp_1);
		pnlReturn.revalidate();

		JLabel lblOraSosire_1 = new JLabel(
				arrival.getDataSosire().getHour() + ":" + arrival.getDataSosire().getMinute());
		pnlReturn.add(lblOraSosire_1);
		pnlReturn.revalidate();

		Component vStrut_6 = Box.createVerticalStrut(20);
		pnlReturn.add(vStrut_6);
		pnlReturn.revalidate();

		JLabel lblCodZbor_1 = new JLabel(arrival.getCodZbor());
		pnlReturn.add(lblCodZbor_1);
		pnlReturn.revalidate();

		JLabel lblAeroportPlecare_1 = new JLabel(arrival.getAeroportPlecare());
		pnlReturn.add(lblAeroportPlecare_1);
		pnlReturn.revalidate();

		Component vStrut_7 = Box.createVerticalStrut(20);
		pnlReturn.add(vStrut_7);
		pnlReturn.revalidate();

		JLabel lblAeroportSosire_1 = new JLabel(arrival.getAeroportSosire());
		pnlReturn.add(lblAeroportSosire_1);
		pnlReturn.revalidate();

		Component vStrut8 = Box.createVerticalStrut(20);
		pnlReturn.add(vStrut8);
		pnlReturn.revalidate();

		JPanel pnlSeats_1 = new JPanel();
		pnlReturn.add(pnlSeats_1);
		pnlReturn.revalidate();
		pnlSeats_1.setLayout(new BoxLayout(pnlSeats_1, BoxLayout.X_AXIS));

		JLabel lblLocuriBusiness_1 = new JLabel("Business: " + arrival.getLocuriBusiness() + "  ");
		pnlSeats_1.add(lblLocuriBusiness_1);
		pnlSeats_1.revalidate();

		JLabel lblLocuriEconomy_1 = new JLabel("Economy: " + arrival.getLocuriEconomy() + "  ");
		pnlSeats_1.add(lblLocuriEconomy_1);
		pnlSeats_1.revalidate();

		JLabel lblOrasPlecare_1 = new JLabel(arrival.getOrasPornire());
		pnlReturn.add(lblOrasPlecare_1);
		pnlReturn.revalidate();

		Component vStrut_9 = Box.createVerticalStrut(20);
		pnlReturn.add(vStrut_9);
		pnlReturn.revalidate();

		JLabel lblOrasSosire_1 = new JLabel(arrival.getOrasDestinatie());
		pnlReturn.add(lblOrasSosire_1);
		pnlReturn.revalidate();

		float flightPrice = 0f;
		if (cmbClasa.getSelectedItem().toString().equals("Economy")) {
			flightPrice = (departure.getPretEconomy() * departure.getDiscount()
					+ arrival.getPretEconomy() * arrival.getDiscount())
					* Float.parseFloat(spinnAdulti.getValue().toString())
					+ (departure.getPretEconomy() * departure.getDiscount()
							+ arrival.getPretEconomy() * arrival.getDiscount())
							* Float.parseFloat(spinnCopii.getValue().toString());
		} else
			flightPrice = (departure.getPretBusiness() * departure.getDiscount()
					+ arrival.getPretBusiness() * arrival.getDiscount())
					* Float.parseFloat(spinnAdulti.getValue().toString())
					+ (departure.getPretBusiness() * departure.getDiscount()
							+ arrival.getPretBusiness() * arrival.getDiscount())
							* Float.parseFloat(spinnCopii.getValue().toString());
		int aux = (int) (flightPrice * 100);
		flightPrice = (float) (aux / 100d);
		JLabel lblPret = new JLabel("" + flightPrice + " \u20ac");
		pnlReturn.add(lblPret);
		pnlReturn.revalidate();

		alignLabels(pnlReturn);

		return pnlFlight;

	}

	/* flightOneWay --- GENERATING JPANEL FOR ONE WAY TRIP. */
	/* NOT RELATED TO DATABASE. */
	public JPanel flightOneWay(Cursa departure) {
		JPanel pnlFlight = new JPanel();

		pnlOptions.add(pnlFlight);
		pnlOptions.revalidate();
		pnlFlight.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(5, 2, 5, 2),
				new MatteBorder(1, 1, 1, 1, Color.DARK_GRAY)));
		pnlFlight.setLayout(new BoxLayout(pnlFlight, BoxLayout.Y_AXIS));

		JPanel pnlLeave = new JPanel();
		GridLayout gridLeave = new GridLayout(3, 5, 5, 5);
		pnlFlight.add(pnlLeave);
		pnlFlight.revalidate();
		pnlLeave.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		pnlLeave.setAlignmentX(LEFT_ALIGNMENT);
		pnlLeave.setLayout(gridLeave);
		pnlLeave.setMinimumSize(optionMinDim);
		pnlLeave.setPreferredSize(optionPrefDim);
		pnlLeave.setMaximumSize(optionMaxDim);

		JLabel lblCompanyName = new JLabel(departure.getNumeCompanie());
		pnlLeave.add(lblCompanyName);
		pnlLeave.revalidate();

		JLabel lblOraPlecare = new JLabel(
				departure.getDataPornire().getHour() + ":" + departure.getDataPornire().getMinute());
		pnlLeave.add(lblOraPlecare);
		pnlLeave.revalidate();

		Duration flightDuration = Duration.between(departure.getDataPornire(), departure.getDataSosire());
		long secondsDuration = flightDuration.getSeconds();
		JLabel lblTimp = new JLabel(secondsDuration / 3600 + "h " + (secondsDuration % 3600) / 60 + "min");
		pnlLeave.add(lblTimp);
		pnlLeave.revalidate();

		JLabel lblOraSosire = new JLabel(
				"" + departure.getDataSosire().getHour() + ":" + departure.getDataSosire().getMinute());
		pnlLeave.add(lblOraSosire);
		pnlLeave.revalidate();

		Component vStrut_1 = Box.createVerticalStrut(20);
		pnlLeave.add(vStrut_1);
		pnlLeave.revalidate();

		JLabel lblCodZbor = new JLabel(departure.getCodZbor());
		pnlLeave.add(lblCodZbor);
		pnlLeave.revalidate();

		JLabel lblAeroportPlecare = new JLabel(departure.getAeroportPlecare());
		pnlLeave.add(lblAeroportPlecare);
		pnlLeave.revalidate();

		Component vStrut_2 = Box.createVerticalStrut(20);
		pnlLeave.add(vStrut_2);
		pnlLeave.revalidate();

		JLabel lblAeroportSosire = new JLabel(departure.getAeroportSosire());
		pnlLeave.add(lblAeroportSosire);
		pnlLeave.revalidate();

		Component vStrut_4 = Box.createVerticalStrut(20);
		pnlLeave.add(vStrut_4);
		pnlLeave.revalidate();

		JPanel pnlSeats = new JPanel();
		pnlLeave.add(pnlSeats);
		pnlLeave.revalidate();
		pnlSeats.setLayout(new BoxLayout(pnlSeats, BoxLayout.X_AXIS));

		JLabel lblLocuriBusiness = new JLabel("Business: " + departure.getLocuriBusiness() + "  ");
		pnlSeats.add(lblLocuriBusiness);
		pnlSeats.revalidate();

		JLabel lblLocuriEconomy = new JLabel("Economy: " + departure.getLocuriEconomy() + "  ");
		pnlSeats.add(lblLocuriEconomy);
		pnlSeats.revalidate();

		JLabel lblOrasPlecare = new JLabel(departure.getOrasPornire());
		pnlLeave.add(lblOrasPlecare);
		pnlLeave.revalidate();

		Component vStrut_5 = Box.createVerticalStrut(20);
		pnlLeave.add(vStrut_5);
		pnlLeave.revalidate();

		JLabel lblOrasSosire = new JLabel(departure.getOrasDestinatie());
		pnlLeave.add(lblOrasSosire);
		pnlLeave.revalidate();

		float flightPrice = 0.f;
		if (cmbClasa.getSelectedItem().toString().equals("Economy")) {
			flightPrice = departure.getPretEconomy() * Float.parseFloat(spinnAdulti.getValue().toString())
					+ departure.getPretEconomy() * Float.parseFloat(spinnCopii.getValue().toString());
		} else {
			flightPrice = departure.getPretBusiness() * Float.parseFloat(spinnAdulti.getValue().toString())
					+ departure.getPretBusiness() * Float.parseFloat(spinnCopii.getValue().toString());
		}
		int aux = (int) (flightPrice * 100);
		flightPrice = (float) (aux / 100d);
		JLabel lblPret = new JLabel(flightPrice + " \u20ac");
		pnlLeave.add(lblPret);
		pnlLeave.revalidate();

		alignLabels(pnlLeave);

		return pnlFlight;

	}

	/* onlyPairs --- SELECT ROUND-TRIP ONLY WITH SAME COMPANYNAME. */

	public ArrayList<Zbor> onlyPairs() {
		ArrayList<Zbor> pairs = new ArrayList<Zbor>();

		for (int i = 0; i < tableSize; i++) {
			if (matchedFlights.get(i).getAeroportPlecare().contains(txtPlecare.getText())) {

				for (int j = i + 1; j < tableSize; j++) {
					if (matchedFlights.get(j).getAeroportPlecare().contains(txtSosire.getText())) {
						pairs.add(matchedFlights.get(i));
						pairs.add(matchedFlights.get(j));
					}
				}
			} else if (matchedFlights.get(i).getAeroportPlecare().contains(txtSosire.getText())) {
				for (int j = i + 1; j < tableSize; j++) {
					if (matchedFlights.get(j).getAeroportPlecare().contains(txtPlecare.getText())) {
						pairs.add(matchedFlights.get(j));
						pairs.add(matchedFlights.get(i));
					}
				}
			}
		}

		return pairs.size() == 0 ? pairs : null;
	}

	/* SELECTING THE DESIRED OPTION. */
	public void selection() {
		Component[] components = pnlOptions.getComponents();
		for (Component c : components) {
			if (!c.getClass().toString().equals("class javax.swing.JLabel")) {
				c.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						for (int j = 1; j <= tableSize; j++) {
							((JComponent) pnlOptions.getComponent(j))
									.setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(5, 2, 5, 2),
											new MatteBorder(1, 1, 1, 1, Color.DARK_GRAY)));
						}
						((JComponent) c).setBorder(new CompoundBorder(BorderFactory.createEmptyBorder(5, 2, 5, 2),
								new MatteBorder(3, 1, 3, 1, colorPositive)));
						btnRezervare.setEnabled(true);
						((JPanel) c).setFocusable(true);
						((JPanel) c).requestFocus();

						selectedFlight.removeAll(selectedFlight);
						for (int i = 1; i <= tableSize; i++) {
							if (((JComponent) components[i]).equals(c)) {
								if (cbxRetur.isSelected()) {
									selectedFlight.add(matchedFlights.get(i - 1));
									selectedFlight.add(matchedFlights.get(i));
									i++;
								} else {
									selectedFlight.add(matchedFlights.get(i - 1));
								}
							}
						}
					}
				});
			}
		}
	}

	/* SEARCHING AVAILABLE FLIGHTS THAT MATCHES INPUT DATA */
	public void cautaCurse() {

		try {
			LocalDateTime fullDLeave = endOfDay(dateLeave);
			LocalDateTime fullDArrive = endOfDay(dateArrive);

			String query;

			query = "SELECT * FROM trasee" + "	LEFT JOIN curse ON curse.Traseu=trasee.idTraseu"
					+ " LEFT JOIN zboruri ON zboruri.codCursa=curse.codCursa" + " WHERE" + " ((Locatii LIKE '%"
					+ txtPlecare.getText() + "%' AND Locatii LIKE '%" + txtSosire.getText() + "%')" + " AND (LOCATE('"
					+ txtPlecare.getText() + "', Locatii) < LOCATE('" + txtSosire.getText() + "', Locatii))"
					+ " AND zboruri.ziOperare = '" + dateLeave.format(formatter) + "'" + " )";

			if (cbxRetur.isSelected()) {
				query += " OR" + " ((Locatii LIKE '%" + txtSosire.getText() + "%' AND Locatii LIKE '%"
						+ txtPlecare.getText() + "%')" + " AND (LOCATE('" + txtPlecare.getText()
						+ "', Locatii) > LOCATE('" + txtSosire.getText() + "', Locatii))" + " AND zboruri.ziOperare = '"
						+ dateArrive.format(formatter) + " )";
			}

			bd.sendQuery(query);

			/* If no flight was found. */
			if (!bd.rs.next()) {
				tableSize = 0;
				lblMesajInformativ
						.setText("\tRezultate cautare -  " + dateLeave.format(formatterDate) + " : " + tableSize);
				lblMesajInformativ.setHorizontalAlignment(SwingConstants.LEFT);
				lblMesajInformativ.setFont(new Font("Tahoma", Font.PLAIN, 24));
				pnlOptions.add(lblMesajInformativ);
				pnlOptions.revalidate();
				pnlOptions.add(lblLipsaCurse);
				pnlOptions.revalidate();
				btnRezervare.setEnabled(false);
			} else {
				bd.rs.beforeFirst();
				tableSize = bd.tableSize();
				int i = 0;


				matchedFlights = new ArrayList<Zbor>();

				while (bd.rs.next() && i < tableSize) {
					String startAirport, stopAirport;
					
					String[] locations = bd.rs.getString("Locatii").split(";");
					String[] departures = bd.rs.getString("Ore plecare").split(";");
					String[] arrivals = bd.rs.getString("Ore sosire").split(";");
					
					LocalDateTime departureTime, arrivalTime;
					
					int departureIndex, arrivalIndex, idx = 0;
					
					for (String location : locations) {
						if (location.contains(txtPlecare.getText())) {
							startAirport = location;
							departureIndex = idx;
						}
						if (location.contains(txtSosire.getText())) {
							stopAirport = location;
							arrivalIndex = idx;
						}
						idx++;
					}
					
					Date flightDay = bd.rs.getDate("ziOperare");
					
					
					String departureHour = departures[departureIndex].split(":")[0];
					String departureMinute = departures[departureIndex].split(":")[1];
					
					String arrivalHour = arrivals[arrivalIndex].split(":")[1];
					String arrivalMinute = arrivals[arrivalIndex].split(":")[1];
					
					departureTime =  LocalDateTime.of(
							flightDay.getYear(), 
							flightDay.getMonth(), 
							flightDay.getDate(), 
							Integer.parseInt(departureHour),
							Integer.parseInt(departureMinute));
					
					arrivalTime = LocalDateTime.of(
							flightDay.getYear(), 
							flightDay.getMonth(), 
							flightDay.getDate(), 
							Integer.parseInt(arrivalHour),
							Integer.parseInt(arrivalMinute));
					
					if (arrivalTime.isBefore(departureTime)) {
						arrivalTime.plusDays(1);
					}
					
					matchedFlights.add(new Zbor(
							bd.rs.getString("codZbor"),
							bd.rs.getString("codCursa"),
							bd.rs.getInt("businessRamase"),
							bd.rs.getInt("economyRamase"),
							startAirport,
							stopAirport,
							departureTime,
							arrivalTime
							));
					
					/* matchedFlights.add(new Cursa(bd.rs.getString("numeCompanie"), bd.rs.getString("codZbor"),
							bd.rs.getString("aeroportPornire"), bd.rs.getString("aeroportSosire"),
							bd.rs.getString("orasPornire"), bd.rs.getString("orasSosire"),
							LocalDateTime.parse("" + bd.rs.getDate("dataPornire") + " " + bd.rs.getTime("dataPornire"),
									formatter),
							LocalDateTime.parse("" + bd.rs.getDate("dataSosire") + " " + bd.rs.getTime("dataSosire"),
									formatter),
							bd.rs.getInt("locuriEconomy"), bd.rs.getInt("locuriBusiness"),
							bd.rs.getFloat("pretEconomy"), bd.rs.getFloat("pretBusiness"), bd.rs.getFloat("discount")));
							*/
					++i;
				}

				if (cbxRetur.isSelected()) {

					/*
					 * Rewriting matchedFlights so it only contains the pairs.
					 */
					matchedFlights = onlyPairs();

					if (matchedFlights != null) {
						tableSize = matchedFlights.size();
						JPanel[] matchedOptions = new JPanel[tableSize / 2];
						int j = 0;
						
						for (i = 0; i < tableSize; i = i + 2, j++) {
							matchedOptions[j] = flightTwoWays(matchedFlights.get(i), matchedFlights.get(i + 1));
						}
						
						tableSize = tableSize / 2;
						lblMesajInformativ.setText("\tRezultate cautare - " + dateLeave.format(formatterDate)
								+ " retur - " + dateArrive.format(formatterDate) + " : " + tableSize);
						lblMesajInformativ.setHorizontalAlignment(SwingConstants.LEFT);
						lblMesajInformativ.setFont(new Font("Tahoma", Font.PLAIN, 24));
						pnlOptions.add(lblMesajInformativ);
						pnlOptions.revalidate();

						for (i = 0; i < tableSize; i++) {
							pnlOptions.add(matchedOptions[i]);
							pnlOptions.revalidate();
						}
						selection();
					} else {
						tableSize = 0;
						lblMesajInformativ
								.setText("Rezultate cautare - " + dateLeave.format(formatterDate) + " : " + tableSize);
						lblMesajInformativ.setHorizontalAlignment(SwingConstants.LEFT);
						lblMesajInformativ.setFont(new Font("Tahoma", Font.PLAIN, 24));
						pnlOptions.add(lblMesajInformativ);
						pnlOptions.revalidate();
						pnlOptions.add(lblLipsaCurse);
						pnlOptions.revalidate();
						btnRezervare.setEnabled(false);
					}
				} else {
					JPanel[] matchedOptions = new JPanel[tableSize];
					for (i = 0; i < tableSize; i++) {
						matchedOptions[i] = flightOneWay(matchedFlights.get(i));
					}
					lblMesajInformativ
							.setText("Rezultate cautare - " + dateLeave.format(formatterDate) + " : " + tableSize);
					lblMesajInformativ.setHorizontalAlignment(SwingConstants.LEFT);
					lblMesajInformativ.setFont(new Font("Tahoma", Font.PLAIN, 24));
					pnlOptions.add(lblMesajInformativ);
					pnlOptions.revalidate();
					for (i = 0; i < tableSize; i++) {
						pnlOptions.add(matchedOptions[i]);
						pnlOptions.revalidate();
					}
					selection();
				}

			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

	/* NOT RELATED TO DATABASE. */
	public void debitCardPayment() throws ParseException {
		JLabel lblNumarCardWarning = new JLabel(" ");
		JLabel lblDataExpWarning = new JLabel(" ");
		JLabel lblPrenumePossWarn = new JLabel(" ");
		JLabel lblCodWarning = new JLabel(" ");
		JLabel lblNumePossWarn = new JLabel(" ");

		JPanel pnlDebitCard = new JPanel();
		pnlDebitCard.setLayout(new BoxLayout(pnlDebitCard, BoxLayout.X_AXIS));
		pnlPayFields.add(pnlDebitCard);
		pnlPayFields.revalidate();

		JPanel pnlLeft = new JPanel();
		pnlDebitCard.add(pnlLeft);
		pnlDebitCard.revalidate();
		pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));

		JLabel lblInfo = new JLabel("Noi acceptam: ");
		pnlLeft.add(lblInfo);
		pnlLeft.revalidate();

		JLabel lblCardsImage = new JLabel("");
		lblCardsImage.setIcon(new ImageIcon(UserFrame.class.getResource("/images/payment_cards.png")));
		pnlLeft.add(lblCardsImage);
		pnlLeft.revalidate();

		Component verticalStrut_10 = Box.createVerticalStrut(5);
		pnlLeft.add(verticalStrut_10);
		pnlLeft.revalidate();

		JLabel lblNumarCard = new JLabel("Numar Card:");
		pnlLeft.add(lblNumarCard);
		pnlLeft.revalidate();

		MaskFormatter cardFormatter = new MaskFormatter("#### #### #### ####");
		cardFormatter.setPlaceholderCharacter('0');
		cardFormatter.setAllowsInvalid(false);
		cardFormatter.setValueContainsLiteralCharacters(false);
		cardFormatter.setOverwriteMode(true);

		JFormattedTextField ftxtNumarCard = new JFormattedTextField(cardFormatter);
		ftxtNumarCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (ftxtNumarCard.getText().equals("0000 0000 0000 0000")) {
					ftxtNumarCard.setText("");
				}
			}
		});
		ftxtNumarCard.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!flagKeys) {
					lblNumarCardWarning.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
					lblNumarCardWarning.setToolTipText("Camp obligatoriu");
					ftxtNumarCard.setBackground(colorWarning);
					flagKeys = false;
				}
			}
		});
		ftxtNumarCard.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				flagKeys = true;
				lblNumarCardWarning.setIcon(null);
				lblNumarCardWarning.setToolTipText(null);
				ftxtNumarCard.setBackground(Color.WHITE);
			}
		});
		ftxtNumarCard.setText("");
		pnlLeft.add(ftxtNumarCard);
		pnlLeft.revalidate();

		Component verticalStrut_11 = Box.createVerticalStrut(5);
		pnlLeft.add(verticalStrut_11);
		pnlLeft.revalidate();

		JLabel lblDataExpirarii = new JLabel("Data Expirarii: ");
		pnlLeft.add(lblDataExpirarii);
		pnlLeft.revalidate();

		MaskFormatter dataExpFormatter = new MaskFormatter("##/##");
		dataExpFormatter.setPlaceholder("LLAA");
		dataExpFormatter.setValueContainsLiteralCharacters(false);

		JFormattedTextField ftxtDataExp = new JFormattedTextField(dataExpFormatter);
		ftxtDataExp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (ftxtDataExp.getText().equals("LL/AA")) {
					ftxtDataExp.setText("");
				}
			}
		});
		ftxtDataExp.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				flagKeys = true;
				lblDataExpWarning.setIcon(null);
				lblDataExpWarning.setToolTipText(null);
				ftxtDataExp.setBackground(Color.WHITE);
			}
		});
		ftxtDataExp.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!flagKeys) {
					lblDataExpWarning.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
					lblDataExpWarning.setToolTipText("Camp obligatoriu!");
					ftxtDataExp.setBackground(colorWarning);
					flagKeys = false;
				}
			}
		});
		ftxtDataExp.setText("");
		pnlLeft.add(ftxtDataExp);
		pnlLeft.revalidate();

		Component verticalStrut_12 = Box.createVerticalStrut(5);
		pnlLeft.add(verticalStrut_12);
		pnlLeft.revalidate();

		JLabel lblPrenumePoss = new JLabel("Prenumele detinatorului cardului:  ");
		pnlLeft.add(lblPrenumePoss);
		pnlLeft.revalidate();

		txtPrenumeDetinator = new JTextField();
		txtPrenumeDetinator.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				flagFocus = true;
				lblPrenumePossWarn.setIcon(null);
				lblPrenumePossWarn.setToolTipText(null);
				txtPrenumeDetinator.setBackground(Color.WHITE);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (flagFocus && txtPrenumeDetinator.getText().isBlank()) {
					lblPrenumePossWarn.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
					lblPrenumePossWarn.setToolTipText("Camp obligatoriu");
					txtPrenumeDetinator.setBackground(colorWarning);
					flagFocus = false;
				}
			}
		});
		pnlLeft.add(txtPrenumeDetinator);
		pnlLeft.revalidate();
		txtPrenumeDetinator.setColumns(10);

		JPanel pnlLeftWarnings = new JPanel();
		pnlDebitCard.add(pnlLeftWarnings);
		pnlDebitCard.revalidate();
		pnlLeftWarnings.setLayout(new BoxLayout(pnlLeftWarnings, BoxLayout.Y_AXIS));

		Component verticalStrut_20 = Box.createVerticalStrut(57);
		pnlLeftWarnings.add(verticalStrut_20);
		pnlLeftWarnings.revalidate();

		pnlLeftWarnings.add(lblNumarCardWarning);
		pnlLeftWarnings.revalidate();

		Component verticalStrut_21 = Box.createVerticalStrut(25);
		pnlLeftWarnings.add(verticalStrut_21);
		pnlLeftWarnings.revalidate();

		pnlLeftWarnings.add(lblDataExpWarning);
		pnlLeftWarnings.revalidate();

		Component verticalStrut_22 = Box.createVerticalStrut(25);
		pnlLeftWarnings.add(verticalStrut_22);
		pnlLeftWarnings.revalidate();

		pnlLeftWarnings.add(lblPrenumePossWarn);
		pnlLeftWarnings.revalidate();

		Component horizontalStrut_4 = Box.createHorizontalStrut(10);
		pnlDebitCard.add(horizontalStrut_4);
		pnlDebitCard.revalidate();

		JPanel pnlRight = new JPanel();
		pnlDebitCard.add(pnlRight);
		pnlDebitCard.revalidate();

		pnlRight.setLayout(new BoxLayout(pnlRight, BoxLayout.Y_AXIS));

		Component verticalStrut_13 = Box.createVerticalStrut(86);
		pnlRight.add(verticalStrut_13);
		pnlRight.revalidate();

		JLabel lblCodCVV = new JLabel("Cod CVV: ");
		pnlRight.add(lblCodCVV);
		pnlRight.revalidate();

		MaskFormatter codCVVFormatter = new MaskFormatter("###");
		codCVVFormatter.setPlaceholder("000");
		codCVVFormatter.setValueContainsLiteralCharacters(false);

		JFormattedTextField ftxtCodCVV = new JFormattedTextField(codCVVFormatter);
		ftxtCodCVV.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (ftxtCodCVV.getText().equals("000")) {
					ftxtCodCVV.setText("");
				}
			}
		});
		ftxtCodCVV.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				flagKeys = true;
				lblCodWarning.setIcon(null);
				lblCodWarning.setToolTipText(null);
				ftxtCodCVV.setBackground(Color.WHITE);
			}
		});
		ftxtCodCVV.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!flagKeys) {
					lblCodWarning.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
					lblCodWarning.setToolTipText("Camp obligatoriu!");
					ftxtCodCVV.setBackground(colorWarning);
					flagKeys = false;
				}
			}
		});
		pnlRight.add(ftxtCodCVV);
		pnlRight.revalidate();

		Component verticalStrut_14 = Box.createVerticalStrut(5);
		pnlRight.add(verticalStrut_14);
		pnlRight.revalidate();

		JLabel lblNumePoss = new JLabel("Numele detinatorului cardului: ");
		pnlRight.add(lblNumePoss);
		pnlRight.revalidate();

		txtNumeDetinator = new JTextField();
		txtNumeDetinator.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				flagFocus = true;
				lblNumePossWarn.setIcon(null);
				lblNumePossWarn.setToolTipText(null);
				txtNumeDetinator.setBackground(Color.WHITE);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (flagFocus && txtNumeDetinator.getText().isBlank()) {
					lblNumePossWarn.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
					lblNumePossWarn.setToolTipText("Camp obligatoriu!");
					txtNumeDetinator.setBackground(colorWarning);
					flagFocus = false;
				}
			}
		});
		pnlRight.add(txtNumeDetinator);
		pnlRight.revalidate();
		txtNumeDetinator.setColumns(10);

		JPanel pnlRightWarnings = new JPanel();
		pnlDebitCard.add(pnlRightWarnings);
		pnlDebitCard.revalidate();
		pnlRightWarnings.setLayout(new BoxLayout(pnlRightWarnings, BoxLayout.Y_AXIS));

		Component verticalStrut_23 = Box.createVerticalStrut(100);
		pnlRightWarnings.add(verticalStrut_23);
		pnlRightWarnings.revalidate();

		pnlRightWarnings.add(lblCodWarning);
		pnlRightWarnings.revalidate();

		Component verticalStrut_24 = Box.createVerticalStrut(23);
		pnlRightWarnings.add(verticalStrut_24);
		pnlRightWarnings.revalidate();

		pnlRightWarnings.add(lblNumePossWarn);
		pnlRightWarnings.revalidate();
	}

	/* NOT RELATED TO DATABASE. */
	public void viramentBancar() throws ParseException {
		JFormattedTextField ftxtIBAN;
		JLabel lblIBANWarning = new JLabel(" ");
		JTextField txtDetinator = new JTextField();
		JLabel lblDetinatorWarning = new JLabel(" ");

		JPanel pnlViramentBancar = new JPanel();
		pnlPayFields.add(pnlViramentBancar);
		pnlPayFields.revalidate();

		JLabel lblIBAN = new JLabel("IBAN: ");
		pnlViramentBancar.add(lblIBAN);
		pnlViramentBancar.revalidate();

		MaskFormatter ibanFormatter = new MaskFormatter("UU##UUUU################");
		ibanFormatter.setPlaceholder("RO00ABCD0000000000000000");

		ftxtIBAN = new JFormattedTextField(ibanFormatter);
		ftxtIBAN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (ftxtIBAN.getText().equals("RO00ABCD0000000000000000")) {
					ftxtIBAN.setText("");
				}
			}
		});
		ftxtIBAN.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				flagFocus = true;
				lblIBANWarning.setIcon(null);
				lblIBANWarning.setToolTipText(null);
				ftxtIBAN.setBackground(Color.WHITE);
			}

			@Override
			public void focusLost(FocusEvent e) {
				System.out.println(ftxtIBAN.getText());
				if (flagFocus && ftxtIBAN.getText().isBlank()) {
					lblIBANWarning.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
					lblIBANWarning.setToolTipText("Camp obligatoriu!");
					ftxtIBAN.setBackground(colorWarning);
					flagFocus = false;
				} else if (ftxtIBAN.getText().contains(" ")) {
					lblIBANWarning.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
					lblIBANWarning.setToolTipText("IBAN-ul trebuie sa contina 24 de caractere!");
					ftxtIBAN.setBackground(colorWarning);
					flagFocus = false;
				}
			}
		});
		pnlViramentBancar.add(ftxtIBAN);
		pnlViramentBancar.revalidate();
		ftxtIBAN.setColumns(24);

		pnlViramentBancar.add(lblIBANWarning);
		pnlViramentBancar.revalidate();

		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		pnlViramentBancar.add(horizontalStrut_4);
		pnlViramentBancar.revalidate();

		JLabel lblDetinator = new JLabel("Detinator cont: ");
		pnlViramentBancar.add(lblDetinator);
		pnlViramentBancar.revalidate();

		txtDetinator.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				flagFocus = true;
				lblDetinatorWarning.setIcon(null);
				lblDetinatorWarning.setToolTipText(null);
				txtDetinator.setBackground(Color.WHITE);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (flagFocus && txtDetinator.getText().isBlank()) {
					lblDetinatorWarning.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
					lblDetinatorWarning.setToolTipText("Camp obligatoriu!");
					txtDetinator.setBackground(colorWarning);
					flagFocus = false;
				}
			}
		});
		txtDetinator.setText("");
		pnlViramentBancar.add(txtDetinator);
		pnlViramentBancar.revalidate();
		txtDetinator.setColumns(18);

		pnlViramentBancar.add(lblDetinatorWarning);
		pnlViramentBancar.revalidate();
	}

	/* NOT RELATED TO DATABASE. */
	public JPanel pasageri(int type, int index) throws ParseException {

		JPanel pnlPersoana = new JPanel();
		JLabel lblPasager = new JLabel("\tPasagerul " + index + ": ");
		JPanel pnlDatePasager = new JPanel();
		JPanel pnlLabels = new JPanel();
		JLabel lblNume = new JLabel("Nume: ");
		Component vStrut_1 = Box.createVerticalStrut(18);
		JLabel lblPrenume = new JLabel("Prenume: ");
		Component vStrut_2 = Box.createVerticalStrut(18);
		JLabel lblVarsta = new JLabel("Varsta: ");
		Component vStrut_3 = Box.createVerticalStrut(18);
		JLabel lblTelefon = new JLabel("Telefon: ");
		Component vStrut_4 = Box.createVerticalStrut(18);
		JLabel lblEmail = new JLabel("Email: ");
		JPanel pnlTextFields = new JPanel();
		Component vStrut_5 = Box.createVerticalStrut(13);
		JTextField txtPrenume = new JTextField();
		Component vStrut_6 = Box.createVerticalStrut(13);
		JSpinner spinVarsta = new JSpinner();
		Component vStrut_7 = Box.createVerticalStrut(13);
		JFormattedTextField ftxtTelefon = new JFormattedTextField(new MaskFormatter("##########"));
		Component vStrut_8 = Box.createVerticalStrut(13);
		JTextField txtEmail = new JTextField();
		JTextField txtNume = new JTextField();
		JPanel pnlTextWarnings = new JPanel();
		Component vStrut_9 = Box.createVerticalStrut(20);
		Component vStrut_10;
		Component vStrut_11 = Box.createVerticalStrut(20);
		JLabel lblNumeWarning = new JLabel(" ");
		JLabel lblPrenumeWarning = new JLabel(" ");
		JLabel lblTelefonWarning = new JLabel(" ");
		JLabel lblEmailWarning = new JLabel(" ");

		pnlPassengers.add(pnlPersoana);
		pnlPassengers.revalidate();
		pnlPersoana.setLayout(new BoxLayout(pnlPersoana, BoxLayout.Y_AXIS));

		pnlPersoana.add(lblPasager);
		pnlPersoana.revalidate();

		pnlPersoana.add(pnlDatePasager);
		pnlPersoana.revalidate();

		pnlDatePasager.add(pnlLabels);
		pnlDatePasager.revalidate();
		pnlLabels.setLayout(new BoxLayout(pnlLabels, BoxLayout.Y_AXIS));
		pnlLabels.setAlignmentX(SwingConstants.RIGHT);

		pnlLabels.add(lblNume);
		pnlLabels.revalidate();
		lblNume.setAlignmentX(SwingConstants.RIGHT);

		pnlLabels.add(vStrut_1);
		pnlLabels.revalidate();

		pnlLabels.add(lblPrenume);
		pnlLabels.revalidate();
		lblPrenume.setAlignmentX(SwingConstants.RIGHT);

		pnlLabels.add(vStrut_2);
		pnlLabels.revalidate();

		pnlLabels.add(lblVarsta);
		pnlLabels.revalidate();
		lblVarsta.setAlignmentX(SwingConstants.RIGHT);

		if (type == 1) {
			pnlLabels.add(vStrut_3);
			pnlLabels.revalidate();

			pnlLabels.add(lblTelefon);
			pnlLabels.revalidate();
			lblTelefon.setAlignmentX(SwingConstants.RIGHT);

			pnlLabels.add(vStrut_4);
			pnlLabels.revalidate();

			pnlLabels.add(lblEmail);
			pnlLabels.revalidate();
			lblEmail.setAlignmentX(SwingConstants.RIGHT);
		}

		pnlDatePasager.add(pnlTextFields);
		pnlDatePasager.revalidate();
		if (type == 1) {
			pnlTextFields.setPreferredSize(new Dimension(170, 160));
		} else {
			pnlTextFields.setPreferredSize(new Dimension(170, 90));
		}
		pnlTextFields.setLayout(new BoxLayout(pnlTextFields, BoxLayout.Y_AXIS));

		txtNume.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				flagFocus = true;
				lblNumeWarning.setIcon(null);
				lblNumeWarning.setToolTipText(null);
				txtNume.setBackground(Color.WHITE);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (flagFocus && txtNume.getText().isBlank()) {
					lblNumeWarning.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
					lblNumeWarning.setToolTipText("Camp obligatoriu!");
					txtNume.setBackground(colorWarning);
					flagFocus = false;
				}
			}
		});
		pnlTextFields.add(txtNume);
		pnlTextFields.revalidate();
		txtNume.setColumns(10);

		pnlTextFields.add(vStrut_5);
		pnlTextFields.revalidate();

		txtPrenume.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				flagFocus = true;
				lblPrenumeWarning.setIcon(null);
				lblPrenumeWarning.setToolTipText(null);
				txtPrenume.setBackground(Color.WHITE);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (flagFocus && txtPrenume.getText().isBlank()) {
					lblPrenumeWarning.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
					lblPrenumeWarning.setToolTipText("Camp obligatoriu!");
					txtPrenume.setBackground(colorWarning);
					flagFocus = false;
				}
			}
		});
		pnlTextFields.add(txtPrenume);
		pnlTextFields.revalidate();
		txtPrenume.setColumns(10);

		pnlTextFields.add(vStrut_6);
		pnlTextFields.revalidate();

		spinVarsta.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		pnlTextFields.add(spinVarsta);
		pnlTextFields.revalidate();

		if (type == 1) {
			pnlTextFields.add(vStrut_7);
			pnlTextFields.revalidate();

			ftxtTelefon.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					flagFocus = true;
					lblTelefonWarning.setIcon(null);
					lblTelefonWarning.setToolTipText(null);
					ftxtTelefon.setBackground(Color.WHITE);
				}

				@Override
				public void focusLost(FocusEvent e) {
					System.out.println(ftxtTelefon.getText());
					if (flagFocus && ftxtTelefon.getText().isBlank()) {
						lblTelefonWarning.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
						lblTelefonWarning.setToolTipText("Camp obligatoriu!");
						ftxtTelefon.setBackground(colorWarning);
						flagFocus = false;
					} else if (ftxtTelefon.getText().contains(" ")) {
						lblTelefonWarning.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
						lblTelefonWarning.setToolTipText("Numarul de telefon trebuie sa contina 10 cifre!");
						ftxtTelefon.setBackground(colorWarning);
						flagFocus = false;
					}
				}
			});
			pnlTextFields.add(ftxtTelefon);
			pnlTextFields.revalidate();

			pnlTextFields.add(vStrut_8);
			pnlTextFields.revalidate();

			txtEmail.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					flagFocus = true;
					lblEmailWarning.setIcon(null);
					lblEmailWarning.setToolTipText(null);
					txtEmail.setBackground(Color.WHITE);
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (flagFocus && txtEmail.getText().isBlank()) {
						lblEmailWarning.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
						lblEmailWarning.setToolTipText("Camp obligatoriu!");
						txtEmail.setBackground(colorWarning);
						flagFocus = false;
					}
				}
			});
			pnlTextFields.add(txtEmail);
			pnlTextFields.revalidate();
			txtEmail.setColumns(20);
		}

		pnlDatePasager.add(pnlTextWarnings);
		pnlDatePasager.revalidate();
		pnlTextWarnings.setLayout(new BoxLayout(pnlTextWarnings, BoxLayout.Y_AXIS));

		pnlTextWarnings.add(lblNumeWarning);
		pnlTextWarnings.revalidate();

		pnlTextWarnings.add(vStrut_9);
		pnlTextWarnings.revalidate();

		pnlTextWarnings.add(lblPrenumeWarning);
		pnlTextWarnings.revalidate();

		if (type == 1) {
			vStrut_10 = Box.createVerticalStrut(50);
		} else {
			vStrut_10 = Box.createVerticalStrut(35);
		}
		pnlTextWarnings.add(vStrut_10);
		pnlTextWarnings.revalidate();

		if (type == 1) {
			pnlTextWarnings.add(lblTelefonWarning);
			pnlTextWarnings.revalidate();

			pnlTextWarnings.add(vStrut_11);
			pnlTextWarnings.revalidate();

			pnlTextWarnings.add(lblEmailWarning);
			pnlTextWarnings.revalidate();
		}

		return pnlPersoana;
	}

	/* NOT RELATED TO DATABASE. */
	public JPanel[] generatePassengers() {
		int size = (int) spinnAdulti.getValue() + (int) spinnCopii.getValue();
		JPanel[] Passengers = new JPanel[size];
		for (int i = 0; i < size; i++) {
			try {
				if (i == 0) {
					Passengers[i] = pasageri(1, i + 1);
				} else {
					Passengers[i] = pasageri(2, i + 1);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return Passengers;
	}

	/* NOT RELATED TO DATABASE. */
	public ArrayList<Client> extractPassengers() {
		ArrayList<Client> clients = new ArrayList<Client>();
		Client client = null;
		float pret = 0f;

		for (Component cParent : Passengers) {
			JPanel cChild = (JPanel) ((JPanel) ((Container) cParent).getComponent(1)).getComponent(1);
			Component[] cGrandChildren = cChild.getComponents();
			client = null;
			if (cGrandChildren.length == 9) {
				client = new Client(((JTextField) cGrandChildren[0]).getText(),
						((JTextField) cGrandChildren[2]).getText(),
						Integer.parseInt(((JSpinner) cGrandChildren[4]).getValue().toString()),
						((JTextField) cGrandChildren[6]).getText(), ((JTextField) cGrandChildren[8]).getText(), 0f);
			} else if (cGrandChildren.length == 5) {
				client = new Client(((JTextField) cGrandChildren[0]).getText(),
						((JTextField) cGrandChildren[2]).getText(),
						Integer.parseInt(((JSpinner) cGrandChildren[4]).getValue().toString()), 0f);
			}
			if (cbxRetur.isSelected()) {
				client.setPretBilet(client.calculaPret(cmbClasa.getSelectedItem().toString(), selectedFlight.get(0),
						selectedFlight.get(1)));
			} else {
				client.setPretBilet(client.calculaPret(cmbClasa.getSelectedItem().toString(), selectedFlight.get(0)));
			}

			clients.add(client);

		}
		System.out.println(clients.size());
		return clients;
	}

	/* NOT RELATED TO DATABASE. */
	public String getSelectedButtonText(ButtonGroup buttonGroup) {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				return button.getText();
			}
		}

		return null;
	}

	public UserFrame_test() throws ParseException {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				bd.disconnect();
			}
		});

		/* CONNECTING TO DATABASE. */
		bd = new DatabaseConnection("jdbc:mysql://localhost:3306/pj", "root", "root");
		bd.connect();

		/* UserFrame PROPERTIES --- APPEARENCE AND LAYOUT. */
		setTitle("Aladdin");
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserFrame.class.getResource("/icons/icon.png")));
		setVisible(true);
		setBounds(100, 100, 900, 540);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		CardLayout pages = new CardLayout();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(pages);

		/* DEST PANEL --- FOR USER INPUT DATA. */
		contentPane.add(pnlDest, "DEST PANEL");
		pnlDest.setLayout(new BoxLayout(pnlDest, BoxLayout.X_AXIS));

		/*
		 * MAIN PANEL --- THE PARENT PANEL FOR INFORMATIVE MESSAGE, FIELDS AREA AND
		 * BUTTNONS AREA.
		 */
		JPanel pnlMain = new JPanel();
		pnlDest.add(pnlMain);
		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));

		Component verticalStrut = Box.createVerticalStrut(30);
		pnlMain.add(verticalStrut);

		JLabel lblAutentificare = new JLabel("Introduceti date cursa");
		lblAutentificare.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblAutentificare.setAlignmentX(0.5f);
		pnlMain.add(lblAutentificare);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		pnlMain.add(verticalStrut_1);

		/* FIELDS PANEL --- A PANEL USED TO OBTAIN USER INPUT DATA. */
		JPanel pnlFields = new JPanel();
		pnlMain.add(pnlFields);
		pnlFields.setLayout(new BoxLayout(pnlFields, BoxLayout.Y_AXIS));

		JPanel pnlDestination = new JPanel();
		pnlFields.add(pnlDestination);

		JLabel lblDela = new JLabel("De la:");
		pnlDestination.add(lblDela);
		txtPlecare.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnCautaCursaPressed) {
					lblPlecareWarning.setVisible(false);
					txtPlecare.setBackground(Color.WHITE);
				}
			}
		});

		pnlDestination.add(txtPlecare);
		txtPlecare.setColumns(10);

		lblPlecareWarning.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
		lblPlecareWarning.setVisible(false);
		pnlDestination.add(lblPlecareWarning);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		pnlDestination.add(horizontalStrut);

		JLabel lblCatre = new JLabel("Catre:");
		pnlDestination.add(lblCatre);
		txtSosire.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnCautaCursaPressed) {
					lblSosireWarning.setVisible(false);
					txtSosire.setBackground(Color.WHITE);
				}
			}
		});

		pnlDestination.add(txtSosire);
		txtSosire.setColumns(10);

		lblSosireWarning.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
		lblSosireWarning.setVisible(false);
		pnlDestination.add(lblSosireWarning);

		JPanel pnlDate = new JPanel();
		pnlFields.add(pnlDate);

		JLabel lblDPlecare = new JLabel("Data Plecare:");
		pnlDate.add(lblDPlecare);

		UtilDateModel calendarPlecare = new UtilDateModel();
		Properties propPlecare = new Properties();
		propPlecare.put("text.today", "Today");
		propPlecare.put("text.month", "Month");
		propPlecare.put("text.year", "Year");
		JDatePanelImpl dpnlDataPlecare = new JDatePanelImpl(calendarPlecare, propPlecare);
		dpickerPlecare = new JDatePickerImpl(dpnlDataPlecare, new DateLabelFormatter());
		dpickerPlecare.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (btnCautaCursaPressed) {
					lblDPlecareWarning.setVisible(false);
					dpickerPlecare.getJFormattedTextField().setBackground(Color.WHITE);
				}
			}
		});

		dpickerPlecare.getJFormattedTextField().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnCautaCursaPressed) {
					lblDPlecareWarning.setVisible(false);
					dpickerPlecare.getJFormattedTextField().setBackground(Color.WHITE);
				}
			}
		});
		pnlDate.add(dpickerPlecare);

		/*
		 * A CHECKBOX IS USED IN ORDER TO GENERATE ANOTHER TEXT FIELD FOR ROUND TRIP.
		 */
		cbxRetur.setSelected(true);
		cbxRetur.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!cbxRetur.isSelected()) {
					lblDSosire.setVisible(false);
					dpickerSosire.setVisible(false);
				} else {
					lblDSosire.setVisible(true);
					dpickerSosire.setVisible(true);
				}
			}
		});

		lblDPlecareWarning.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
		lblDPlecareWarning.setVisible(false);
		pnlDate.add(lblDPlecareWarning);

		pnlDate.add(cbxRetur);

		pnlDate.add(lblDSosire);

		UtilDateModel calendarSosire = new UtilDateModel();
		Properties propSosire = new Properties();
		propSosire.put("text.today", "Today");
		propSosire.put("text.month", "Month");
		propSosire.put("text.year", "Year");
		JDatePanelImpl dpnlDataSosire = new JDatePanelImpl(calendarSosire, propSosire);
		dpickerSosire = new JDatePickerImpl(dpnlDataSosire, new DateLabelFormatter());
		dpickerSosire.setShowYearButtons(true);
		dpickerSosire.getJFormattedTextField().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnCautaCursaPressed) {
					lblDSosireWarning.setVisible(false);
					dpickerSosire.getJFormattedTextField().setBackground(Color.WHITE);
				}
			}
		});
		pnlDate.add(dpickerSosire);

		lblDSosireWarning.setIcon(new ImageIcon(UserFrame.class.getResource("/icons/warning.png")));
		lblDSosireWarning.setVisible(false);
		pnlDate.add(lblDSosireWarning);

		JPanel pnlPlaces = new JPanel();
		pnlFields.add(pnlPlaces);

		JLabel lblAdulti = new JLabel("Locuri adulti:");
		pnlPlaces.add(lblAdulti);

		/* spinnAdulti --- A JSpinner USED TO SPECIFY THE NUMBER OF ADULT SEATS. */
		spinnAdulti.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		pnlPlaces.add(spinnAdulti);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		pnlPlaces.add(horizontalStrut_1);

		JLabel lblCopii = new JLabel("Locuri copii:");
		pnlPlaces.add(lblCopii);

		/* spinnCopii --- A JSpinner USED TO SPECIFY THE NUMBER OF CHILDREN SEATS. */
		spinnCopii.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		pnlPlaces.add(spinnCopii);

		/* cmbClasa --- A COMBO BOX USED TO SELECT THE SEAT CLASS. */
		cmbClasa.setModel(new DefaultComboBoxModel(new String[] { "Economy", "Business" }));
		pnlPlaces.add(cmbClasa);

		/* BUTTONS PANEL --- A PANEL INTENDET FOR CONTINUE AND BACK BUTTONS. */
		JPanel pnlDestButtons = new JPanel();
		pnlMain.add(pnlDestButtons);

		/* btnInapoi --- THE BUTTON USED FOR ACCESSING MAIN FRAME. */
		JButton btnInapoi = new JButton("Pagina Principala");
		btnInapoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame MFrame;
				try {
					MFrame = new MainFrame();
					MFrame.setVisible(true);
					dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		pnlDestButtons.add(btnInapoi);

		/* btnCautaCurse --- THE BUTTON USED FOR ACCESSING THE FLIGHTS PANEL. */
		btnCautaCurse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				extractDate();
				btnCautaCursaPressed = true;
				fieldsValid();

				if (!testFailed) {
					cautaCurse();
					pages.show(UserFrame_test.this.getContentPane(), "FLIGHTS PANEL");
					selection();

					pnlPayFields.removeAll();
					pnlPayFields.revalidate();
					pnlPayFields.repaint();

					pnlPassengers.removeAll();
					pnlPassengers.revalidate();
					pnlPassengers.repaint();

				} else {
					testFailed = false;
					btnCautaCursaPressed = true;
				}

			}
		});
		pnlDestButtons.add(btnCautaCurse);

		/*
		 * -----------------------------------------------------------------------------
		 * -
		 */

		/* FLIGHT PANEL --- FOR SHOWING THE AVAILABLE FLIGHTS. */
		contentPane.add(pnlFlights, "FLIGHTS PANEL");
		pnlFlights.setLayout(new BoxLayout(pnlFlights, BoxLayout.Y_AXIS));

		/*
		 * pnlOptions --- A PANEL USED FOR PRINTING THE AVAILABLE FLIGHT OPTIONS.
		 * PROVIDED WITH A SCROLL PANE.
		 */
		JScrollPane scrOptions = new JScrollPane(pnlOptions);
		pnlOptions.setLayout(new BoxLayout(pnlOptions, BoxLayout.Y_AXIS));

		scrOptions.setBounds(new Rectangle(0, 0, 0, 0));
		pnlFlights.add(scrOptions);

		/* pnlFlightsButtons --- AN AREA FOR BUTTONS. */
		JPanel pnlFlightsButtons = new JPanel();
		pnlFlightsButtons.setBorder(new EmptyBorder(10, 0, 10, 0));
		pnlFlightsButtons.setAlignmentX(RIGHT_ALIGNMENT);
		pnlFlights.add(pnlFlightsButtons);
		pnlFlightsButtons.setLayout(new BoxLayout(pnlFlightsButtons, BoxLayout.X_AXIS));

		/* BUTTON DESIGNED FOR ACCESING PREVIOUS PAGE. */
		JButton btnInapoiDest = new JButton("Inapoi");
		btnInapoiDest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlOptions.removeAll();
				pnlOptions.revalidate();
				pnlOptions.repaint();
				pages.show(UserFrame_test.this.getContentPane(), "DEST PANEL");
			}
		});
		btnInapoiDest.setAlignmentX(0.0f);
		pnlFlightsButtons.add(btnInapoiDest);

		Component rigidArea = Box.createRigidArea(new Dimension(10, 20));
		pnlFlightsButtons.add(rigidArea);

		/* BUTTON TO ACCESS USER DATA NEXT PAGE. */
		btnRezervare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Passengers = generatePassengers();
				
				pages.show(UserFrame_test.this.getContentPane(), "USER DATA");

			}
		});

		btnRezervare.setEnabled(false);
		btnRezervare.setAlignmentX(0.0f);
		pnlFlightsButtons.add(btnRezervare);

		JPanel pnlUserData = new JPanel();
		contentPane.add(pnlUserData, "USER DATA");
		pnlUserData.setLayout(new BoxLayout(pnlUserData, BoxLayout.Y_AXIS));

		JScrollPane scrPassengers = new JScrollPane(pnlPassengers);
		scrPassengers.setBounds(new Rectangle(0, 0, 0, 0));
		pnlPassengers.setLayout(new BoxLayout(pnlPassengers, BoxLayout.Y_AXIS));
		pnlPassengers.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnlUserData.add(scrPassengers);

		JPanel pnlPayment = new JPanel();
		pnlUserData.add(pnlPayment);
		pnlPayment.setLayout(new BoxLayout(pnlPayment, BoxLayout.Y_AXIS));

		JPanel pnlRadioBtn = new JPanel();
		pnlPayment.add(pnlRadioBtn);
		pnlRadioBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

		ButtonGroup rdButtons = new ButtonGroup();

		rdbtnCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFinalizare.setEnabled(true);
				flagViramentBancar = false;
				if (rdbtnCard.isSelected() && !flagDebitCard) {
					try {
						pnlPayFields.removeAll();
						pnlPayFields.revalidate();
						pnlPayFields.repaint();
						debitCardPayment();
						flagDebitCard = true;
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		pnlRadioBtn.add(rdbtnCard);
		rdButtons.add(rdbtnCard);

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		pnlRadioBtn.add(horizontalStrut_2);

		rdbtnViramentBancar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFinalizare.setEnabled(true);
				flagDebitCard = false;
				if (!flagViramentBancar) {
					pnlPayFields.removeAll();
					pnlPayFields.revalidate();
					pnlPayFields.repaint();
					try {
						viramentBancar();
						flagViramentBancar = true;
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		pnlRadioBtn.add(rdbtnViramentBancar);
		rdButtons.add(rdbtnViramentBancar);

		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		pnlRadioBtn.add(horizontalStrut_3);

		rdbtnCash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFinalizare.setEnabled(true);
				pnlPayFields.removeAll();
				pnlPayFields.revalidate();
				pnlPayFields.repaint();
				flagDebitCard = false;
				flagViramentBancar = false;
			}
		});
		pnlRadioBtn.add(rdbtnCash);
		rdButtons.add(rdbtnCash);

		pnlPayment.add(pnlPayFields);
		pnlPayFields.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnlPayFields.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel pnlUserDataButtons = new JPanel();
		pnlUserData.add(pnlUserDataButtons);
		pnlUserDataButtons.setAlignmentX(Component.LEFT_ALIGNMENT);

		JButton btnInapoiFlights = new JButton("Înapoi");
		btnInapoiFlights.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pages.show(UserFrame_test.this.getContentPane(), "FLIGHTS PANEL");
			}
		});
		pnlUserDataButtons.add(btnInapoiFlights);

		Component rigidArea_1 = Box.createRigidArea(new Dimension(5, 10));
		pnlUserDataButtons.add(rigidArea_1);
		btnFinalizare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clienti = extractPassengers();
				TipPlata plata = null;
				switch (getSelectedButtonText(rdButtons)) {
				case "Card":
					plata = TipPlata.Card;
					break;
				case "Virament bancar":
					plata = TipPlata.ViramentBancar;
					break;
				case "Cash":
					plata = TipPlata.Cash;
					break;
				}
				for (Client c : clienti) {
					c.setPlata(plata);
				}
				// JavaMailUtil.sendMail(clienti.get(0).getEmail().toString());
			}
		});

		pnlUserDataButtons.add(btnFinalizare);
		btnFinalizare.setEnabled(false);
	}
}