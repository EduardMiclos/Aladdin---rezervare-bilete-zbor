package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Cursor;

import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	
	/* Main JPanel. */
	private JPanel pnlMain;
	
	/* Second JPanel, containing the buttons. */
	private JPanel pnlButtons;
	
	private static String frameTitle = "Aladdin - Rezerva usor bilete de zbor";

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JButton createButton(String btnText, int fontSize, int x, int y, int w, int h, Color color) {
		JButton btnCustom = new JButton(btnText);
		btnCustom.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, fontSize));
		btnCustom.setBounds(x, y, w, h);
		btnCustom.setAlignmentX(CENTER_ALIGNMENT);
		btnCustom.setBackground(color);
		btnCustom.setFocusPainted(false);
		btnCustom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    
	    pnlButtons.add(btnCustom);
	    
	    return btnCustom;
	}
	
	public MainFrame() throws IOException {
		setVisible(true);
		setResizable(false);
		setTitle(frameTitle);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/icons/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 518, 450);
		
		/* MAIN PANEL. */
		pnlMain = new JPanel();
		pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnlMain);
		pnlMain.setLayout(new BorderLayout(0, 0));
		
		pnlMain.setBackground(new Color(161, 195, 204));
		JLabel lblBanner = new JLabel(new ImageIcon("images\\banner.png"));
		
		pnlMain.add(lblBanner, BorderLayout.NORTH);
		/* ------------ */
		
		/* BUTTONS PANEL. */
		pnlButtons = new JPanel();
		pnlMain.add(pnlButtons, BorderLayout.CENTER);
		pnlButtons.setLayout(null);
		pnlButtons.setBackground(new Color(147, 180, 189));
		/* -------------- */
		
		/* CLIENT BUTTON --- TICKET RESERVATION. */
		createButton("REZERVARE BILETE", 
				15, 
				89, 10, 316, 50, 
				new Color(217, 192, 240));
	    /* ---------------------------------- */
		
	    /* COMPANIE AERIANA BUTTON --- FLIGHT MANAGEMENT SYSTEM. */
		JButton btnCompanieAeriana = createButton("Login companie aerian\u0103", 
				11, 
				289, 85, 183, 21, 
				new Color(230, 231, 252));
		
		btnCompanieAeriana.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginFrame frame = new LoginFrame('C');
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				dispose();
			}
		});
		/* ---------------------------------------------------- */
		
		/* PERSONAL AEROPORT BUTTON --- FLIGHT MANAGEMENT SYSTEM. */
		JButton btnPersonalAeroport = createButton("Login personal aeroport", 
				11, 
				22, 85, 183, 21, 
				new Color(230, 231, 252));
		
		btnPersonalAeroport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginFrame frame = new LoginFrame('P');
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				dispose();
			}
		});
		/* ------------------------------------------------------ */
	}

}
