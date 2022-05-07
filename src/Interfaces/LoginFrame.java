package Interfaces;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class LoginFrame extends JFrame {

	private JPanel contentPane = new JPanel();
	private JTextField txtUser = new JTextField();;
	private JPasswordField pwdPassword = new JPasswordField();
	private JLabel lblUserWarning = new JLabel("");
	private JLabel lblPassWarning = new JLabel("");

	private boolean companieFrameDeschis;
	private boolean bdpDeschisa=false;
	private boolean bdcDeschisa=false;
	private Color colorWarning = new Color(245, 113, 113);
	private BazaDeDate bdPersonal;
	private BazaDeDate bdCompanii;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	
	public void logareUtilizator(ResultSet rs)
	{
		try {
			rs.beforeFirst();
			while(rs.next()) {
				if(txtUser.getText().equals(rs.getString("utilizator"))) {
					break;
				}
			}
			if(rs.isAfterLast()) {
				lblUserWarning.setToolTipText("Utilizator inexistent");
				lblUserWarning.setVisible(true);
				txtUser.setBackground(colorWarning);
			}
			else if(!String.valueOf(pwdPassword.getPassword()).equals(rs.getString("parola"))) {
				lblPassWarning.setToolTipText("Parola incorecta");
				lblPassWarning.setVisible(true);
				pwdPassword.setBackground(colorWarning);
			}

			rs.beforeFirst();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public LoginFrame(String tipUtilizator) {
		companieFrameDeschis = false;
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginFrame.class.getResource("/icons/icon.png")));
		setTitle("Aladdin - Autentificare");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				try {
					if (companieFrameDeschis == false) {
						JFrame frame = new MainFrame();
					}
					if (bdpDeschisa) {
						bdPersonal.deconectare();
					}
					if (bdcDeschisa) {
						bdCompanii.deconectare();
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		if (tipUtilizator.indexOf("Personal Aeroport") != -1) {
			System.out.println("Ha!");
			bdPersonal = new BazaDeDate("jdbc:mysql://localhost:3306/proiect_fis", "root", "root");
			bdPersonal.conectare();
			bdpDeschisa=true;
			try {
				bdPersonal.citireDate("SELECT * FROM login_personal");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (tipUtilizator.indexOf("Companie Aeriana") != -1) {
			bdCompanii = new BazaDeDate("jdbc:mysql://localhost:3306/proiect_fis", "root", "root");
			bdCompanii.conectare();
			bdcDeschisa=true;
			try {
				bdCompanii.citireDate("SELECT * FROM login_companii");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAutentificare = new JLabel("Autentificare" + tipUtilizator);
		lblAutentificare.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutentificare.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblAutentificare.setBounds(10, 28, 414, 36);
		lblAutentificare.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblAutentificare);

		JPanel MainPanel = new JPanel();
		MainPanel.setBounds(10, 75, 414, 138);
		contentPane.add(MainPanel);
		MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.Y_AXIS));

		JPanel LoginPanel = new JPanel();
		MainPanel.add(LoginPanel);
		LoginPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		LoginPanel.setBounds(0, 0, 100, 100);

		JPanel LabelPanel = new JPanel();
		LoginPanel.add(LabelPanel);
		LabelPanel.setLayout(new BoxLayout(LabelPanel, BoxLayout.Y_AXIS));

		JLabel lblUser = new JLabel("Utilizator");
		LabelPanel.add(lblUser);
		LabelPanel.add(Box.createVerticalStrut(23));

		JLabel lblPassword = new JLabel("Parola");
		LabelPanel.add(lblPassword);

		JPanel TextPanel = new JPanel();
		LoginPanel.add(TextPanel);
		TextPanel.setLayout(new BoxLayout(TextPanel, BoxLayout.Y_AXIS));

		txtUser.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (txtUser.getText().isBlank()) {
					lblUserWarning.setToolTipText("Camp Obligatoriu!");
					lblUserWarning.setVisible(true);
					txtUser.setBackground(colorWarning);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				lblUserWarning.setVisible(false);
				txtUser.setBackground(Color.WHITE);
			}
		});
		TextPanel.add(txtUser);
		TextPanel.add(Box.createVerticalStrut(15));
		txtUser.setColumns(10);

		pwdPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (String.valueOf(pwdPassword.getPassword()).isBlank()) {
					lblPassWarning.setToolTipText("Camp Obligatoriu!");
					lblPassWarning.setVisible(true);
					pwdPassword.setBackground(colorWarning);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				lblPassWarning.setVisible(false);
				pwdPassword.setBackground(Color.WHITE);
			}
		});

		pwdPassword.setText("");
		TextPanel.add(pwdPassword);

		JPanel WarningsPanel = new JPanel();
		LoginPanel.add(WarningsPanel);
		GridBagLayout gbl_WarningsPanel = new GridBagLayout();
		gbl_WarningsPanel.columnWidths = new int[] { 16, 0 };
		gbl_WarningsPanel.rowHeights = new int[] { 16, 10, 16, 0 };
		gbl_WarningsPanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_WarningsPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		WarningsPanel.setLayout(gbl_WarningsPanel);

		lblUserWarning.setIcon(new ImageIcon(LoginFrame.class.getResource("/icons/warning.png")));
		lblUserWarning.setVerticalAlignment(SwingConstants.TOP);
		lblUserWarning.setVisible(false);
		GridBagConstraints gbc_lblUserWarning = new GridBagConstraints();
		gbc_lblUserWarning.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblUserWarning.insets = new Insets(0, 0, 5, 0);
		gbc_lblUserWarning.gridx = 0;
		gbc_lblUserWarning.gridy = 0;
		WarningsPanel.add(lblUserWarning, gbc_lblUserWarning);

		Component verticalStrut = Box.createVerticalStrut(10);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.anchor = GridBagConstraints.NORTH;
		gbc_verticalStrut.fill = GridBagConstraints.HORIZONTAL;
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		WarningsPanel.add(verticalStrut, gbc_verticalStrut);

		lblPassWarning.setIcon(new ImageIcon(LoginFrame.class.getResource("/icons/warning.png")));
		lblPassWarning.setVerticalAlignment(SwingConstants.BOTTOM);
		lblPassWarning.setVisible(false);
		GridBagConstraints gbc_lblPassWarning = new GridBagConstraints();
		gbc_lblPassWarning.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblPassWarning.gridx = 0;
		gbc_lblPassWarning.gridy = 2;
		WarningsPanel.add(lblPassWarning, gbc_lblPassWarning);

		JPanel ButtonPanel = new JPanel();
		MainPanel.add(ButtonPanel);
		ButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnBack = new JButton("< Inapoi");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		ButtonPanel.add(btnBack);

		JButton btnLogin = new JButton("Autentificare");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bdpDeschisa) {
					logareUtilizator(bdPersonal.rs);
				}
				else if(bdcDeschisa) {
					logareUtilizator(bdCompanii.rs);
				}
			}
		});

		ButtonPanel.add(btnLogin);

	}
}
