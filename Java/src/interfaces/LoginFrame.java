package interfaces;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classes.DatabaseConnection;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginFrame extends JFrame {
	
	private JPanel contentPane = new JPanel();
	private JTextField txtUser = new JTextField();;
	private JPasswordField pwdPassword = new JPasswordField();

	private JPanel pnlButtons;
	
	private JButton btnLogin;
	private JButton btnBack;
	private JLabel lblUserWarning = new JLabel("");
	private JLabel lblPassWarning = new JLabel("");

	private Color colorWarning = new Color(207, 157, 157);
	private DatabaseConnection db;

	private JButton createButton(String btnText, int fontSize, Color color) {
		JButton btnCustom = new JButton(btnText);
		btnCustom.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, fontSize));
		btnCustom.setAlignmentX(CENTER_ALIGNMENT);
		btnCustom.setBackground(color);
		btnCustom.setFocusPainted(false);
		btnCustom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    	
	    pnlButtons.add(btnCustom);
	    
	    return btnCustom;
	}

	
	/* --- USER LOGIN --- */
	public boolean logareUtilizator(ResultSet rs) {
		boolean loginSuccess = true;
		
		try {							
			/* Move the cursor before the first element. */
			rs.beforeFirst();

			String dbUsername;
			while (rs.next()) {
				dbUsername = rs.getString("user");
				if (txtUser.getText().equals(dbUsername)) {
					break;
				}
			}

			/* If the cursor finds itself behind the last user,
			 * it means that we haven't found the entered username. */
			if (rs.isAfterLast()) {
				lblUserWarning.setToolTipText("Utilizator inexistent");
				lblUserWarning.setVisible(true);
				txtUser.setBackground(colorWarning);
				
				loginSuccess = false;
			} 

			/* Else, we check to see if the password is also correct. */
			else if (!String.valueOf(pwdPassword.getPassword()).equals(rs.getString("password"))) {
				lblPassWarning.setToolTipText("Parola incorecta");
				lblPassWarning.setVisible(true);
				pwdPassword.setBackground(colorWarning);
				
				loginSuccess = false;
			}

			rs.beforeFirst();
			
			return loginSuccess;
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			
			return false;
		}
	}
	/* ------------------ */

	public LoginFrame(char tipUtilizator) {		
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginFrame.class.getResource("/icons/icon.png")));
		setTitle("Aladdin - Autentificare");
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				db.disconnect();
			}
		});
		
		String titleCompletion = "";
		
		switch (tipUtilizator) {
		case 'P': {
			db = new DatabaseConnection("jdbc:mysql://localhost:3306/pj", "root", "root");
			db.connect();

			titleCompletion = "personal aeroport";
			
			try {
				db.sendQuery("SELECT * FROM personal_aeroport");
			} catch (SQLException sqlException) {
				JOptionPane.showMessageDialog(contentPane, "A aparut o eroare in timpul conectarii la baza de date!",
						"Eroare baza de date", JOptionPane.ERROR_MESSAGE);
				return;
			}
			break;
		}
		case 'C': {
			db = new DatabaseConnection("jdbc:mysql://localhost:3306/pj", "root", "root");
			db.connect();

			titleCompletion = "companie aeriana";
			
			try {
				db.sendQuery("SELECT * FROM companii_aeriene");
			}
			catch (SQLException sqlException) {
				JOptionPane.showMessageDialog(contentPane, "A aparut o eroare in timpul conectarii la baza de date!",
						"Eroare baza de date", JOptionPane.ERROR_MESSAGE);
				return;
			}
			break;
		}
		default:{
			break;
		}
	}

		/* --- CONTENT PANE. --- */
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Autentificare " + titleCompletion);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 24));
		lblTitle.setBounds(10, 28, 414, 36);
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblTitle);
		/* --------------------- */

		
		/* --- MAIN PANEL. --- */
		JPanel pnlMain = new JPanel();
		pnlMain.setBounds(10, 75, 414, 138);
		contentPane.add(pnlMain);
		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
		/* ------------------- */
		
		
		/* --- LOGIN PANEL. --- */
		JPanel pnlLogin = new JPanel();
		pnlMain.add(pnlLogin);
		pnlLogin.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlLogin.setBounds(0, 0, 100, 100);
		/* -------------------  */
		
		
		/* --- LABEL PANEL. --- */
		JPanel pnlLabels = new JPanel();
		pnlLogin.add(pnlLabels);
		pnlLabels.setLayout(new BoxLayout(pnlLabels, BoxLayout.Y_AXIS));

		JLabel lblUser = new JLabel("Utilizator");
		lblUser.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 10));
		lblUser.setAlignmentX(Component.RIGHT_ALIGNMENT);
		pnlLabels.add(lblUser);

		pnlLabels.add(Box.createVerticalStrut(23));

		JLabel lblPassword = new JLabel("Parola");
		lblPassword.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 10));
		lblPassword.setAlignmentX(Component.RIGHT_ALIGNMENT);
		pnlLabels.add(lblPassword);
		/* -------------------  */
		
		
		/* --- TEXT PANEL. --- */
		JPanel pnlTexts = new JPanel();
		pnlLogin.add(pnlTexts);
		pnlTexts.setLayout(new BoxLayout(pnlTexts, BoxLayout.Y_AXIS));

		txtUser.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(txtUser.getText().isBlank()) {
					btnLogin.setEnabled(false);	
				}
				else if(!(pwdPassword.getPassword().length == 0)){
					btnLogin.setEnabled(true);
				}
			}
		});
		txtUser.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblUserWarning.setVisible(false);
				txtUser.setBackground(Color.WHITE);
			}
		});

		pnlTexts.add(txtUser);
		pnlTexts.add(Box.createVerticalStrut(15));
		txtUser.setColumns(10);
		pwdPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(pwdPassword.getPassword().length == 0) {
					btnLogin.setEnabled(false);	
				}
				else if(!txtUser.getText().isBlank()){
					btnLogin.setEnabled(true);
				}
			}
		});

		pwdPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				lblPassWarning.setVisible(false);
				pwdPassword.setBackground(Color.WHITE);
			}
		});

		pnlTexts.add(pwdPassword);
		/* ------------------  */
		
		
		/* --- WARNINGS PANEL. --- */
		JPanel pnlWarnings = new JPanel();
		pnlLogin.add(pnlWarnings);
		GridBagLayout gbl_pnlWarnings = new GridBagLayout();
		gbl_pnlWarnings.columnWidths = new int[]{16, 0};
		gbl_pnlWarnings.rowHeights = new int[]{16, 16, 16, 0};
		gbl_pnlWarnings.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_pnlWarnings.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlWarnings.setLayout(gbl_pnlWarnings);

		lblUserWarning.setIcon(new ImageIcon(LoginFrame.class.getResource("/icons/warning.png")));
		lblUserWarning.setVisible(true);
		GridBagConstraints gbc_lblUserWarning = new GridBagConstraints();
		gbc_lblUserWarning.fill = GridBagConstraints.BOTH;
		gbc_lblUserWarning.insets = new Insets(0, 0, 5, 0);
		gbc_lblUserWarning.gridx = 0;
		gbc_lblUserWarning.gridy = 0;
		pnlWarnings.add(lblUserWarning, gbc_lblUserWarning);

		Component verticalStrut = Box.createVerticalStrut(12);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.fill = GridBagConstraints.BOTH;
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		pnlWarnings.add(verticalStrut, gbc_verticalStrut);

		lblPassWarning.setIcon(new ImageIcon(LoginFrame.class.getResource("/icons/warning.png")));
		lblPassWarning.setVisible(false);
		GridBagConstraints gbc_lblPassWarning = new GridBagConstraints();
		gbc_lblPassWarning.fill = GridBagConstraints.BOTH;
		gbc_lblPassWarning.gridx = 0;
		gbc_lblPassWarning.gridy = 2;
		pnlWarnings.add(lblPassWarning, gbc_lblPassWarning);
		/* ----------------------  */
		
		
		pnlButtons = new JPanel();
		pnlMain.add(pnlButtons);
		pnlButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		
		btnLogin = createButton("Autentificare", 15, new Color(230, 231, 252));
		btnBack = createButton("Inapoi", 15, new Color(230, 231, 252));
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					MainFrame mainFrame = new MainFrame();
					mainFrame.setLocationRelativeTo(null);
					mainFrame.setVisible(true);
					dispose();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		});
		pnlButtons.add(btnBack);

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				boolean loginSuccess = logareUtilizator(db.rs);
				
				if(loginSuccess == true) {
					switch(tipUtilizator) {
					case 'C':{
						try {
							CAFrame frame = new CAFrame(txtUser.getText());
							frame.setLocationRelativeTo(null);
							frame.setVisible(true);
							
							dispose();
						} catch (SQLException sqlException) {
							sqlException.printStackTrace();
						}
					}
					}
				}
			}
		});
		pnlButtons.add(btnLogin);
		btnLogin.setEnabled(false);

	}
}