package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtPlecare = new JTextField();
	private JTextField txtSosire = new JTextField();
	private JTextField txtDataPlecare = new JTextField();
	private JTextField txtDataSosire = new JTextField();
	private JLabel lblDSosire = new JLabel("Data sosire:");

	

	/**
	 * Create the frame.
	 */
	public UserFrame() {
		setTitle("Aladdin - Date cursa");
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserFrame.class.getResource("/icons/icon.png")));
		setVisible(true);
		setBounds(100, 100, 550, 300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel MainPanel = new JPanel();
		contentPane.add(MainPanel);
		MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(30);
		MainPanel.add(verticalStrut);
		
		JLabel lblAutentificare = new JLabel("Introduceti date cursa");
		lblAutentificare.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutentificare.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblAutentificare.setAlignmentX(0.5f);
		MainPanel.add(lblAutentificare);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		MainPanel.add(verticalStrut_1);
		
		JPanel FieldsPanel = new JPanel();
		MainPanel.add(FieldsPanel);
		FieldsPanel.setLayout(new BoxLayout(FieldsPanel, BoxLayout.Y_AXIS));
		
		JPanel DestinatiePanel = new JPanel();
		DestinatiePanel.setAlignmentX(CENTER_ALIGNMENT);
		FieldsPanel.add(DestinatiePanel);
		
		JLabel lblDela = new JLabel("De la:");
		DestinatiePanel.add(lblDela);
		
		txtPlecare.setText("");
		DestinatiePanel.add(txtPlecare);
		txtPlecare.setColumns(10);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		DestinatiePanel.add(horizontalStrut);
		
		JLabel lblCatre = new JLabel("Catre:");
		DestinatiePanel.add(lblCatre);
		
		txtSosire.setText("");
		DestinatiePanel.add(txtSosire);
		txtSosire.setColumns(10);
		
		JPanel DataPanel = new JPanel();
		DataPanel.setAlignmentX(CENTER_ALIGNMENT);
		FieldsPanel.add(DataPanel);
		
		JLabel lblDPlecare = new JLabel("Data Plecare:");
		DataPanel.add(lblDPlecare);
		
		txtDataPlecare.setColumns(10);
		DataPanel.add(txtDataPlecare);
		
		JCheckBox cbxRetur = new JCheckBox("Retur");
		cbxRetur.setSelected(true);
		cbxRetur.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!cbxRetur.isSelected()) {
					lblDSosire.setVisible(false);
					txtDataSosire.setVisible(false);
				}
				else {
					lblDSosire.setVisible(true);
					txtDataSosire.setVisible(true);
				}
			}
		});
		DataPanel.add(cbxRetur);
		
		DataPanel.add(lblDSosire);
		
		txtDataSosire.setColumns(10);
		DataPanel.add(txtDataSosire);
		
		JPanel LocuriPanel = new JPanel();
		LocuriPanel.setAlignmentX(CENTER_ALIGNMENT);
		FieldsPanel.add(LocuriPanel);
		
		JLabel lblAdulti = new JLabel("Locuri Adulti:");
		lblAdulti.setHorizontalAlignment(SwingConstants.TRAILING);
		LocuriPanel.add(lblAdulti);
		
		JSpinner spinnAdulti = new JSpinner();
		spinnAdulti.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		LocuriPanel.add(spinnAdulti);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		LocuriPanel.add(horizontalStrut_1);
		
		JLabel lblCopii = new JLabel("Locuri copii:");
		LocuriPanel.add(lblCopii);
		
		JSpinner spinnCopii = new JSpinner();
		spinnCopii.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		LocuriPanel.add(spinnCopii);
		
		JComboBox cmbClasa = new JComboBox();
		cmbClasa.setModel(new DefaultComboBoxModel(new String[] {"Economy", "Business"}));
		LocuriPanel.add(cmbClasa);
		
		JPanel ButtonsPanel = new JPanel();
		ButtonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		MainPanel.add(ButtonsPanel);
		
		JButton btnInapoi = new JButton("< Pagina Principala");
		btnInapoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame MFrame;
				try {
					MFrame = new MainFrame();
					MFrame.setVisible(true);
					dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		ButtonsPanel.add(btnInapoi);
		
		JButton btnCautaCurse = new JButton("Cautare zbor");
		ButtonsPanel.add(btnCautaCurse);
	}
}
