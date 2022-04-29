package Interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public MainFrame() throws IOException {
		setVisible(true);
		setTitle("Aladdin - Identificare");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/icons/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 518, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlMain = new JPanel();
		contentPane.add(pnlMain, BorderLayout.CENTER);
		pnlMain.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel pnlChild = new JPanel();
		pnlMain.add(pnlChild);
		pnlChild.setLayout(new BoxLayout(pnlChild, BoxLayout.Y_AXIS));
		
		
		JButton btnClient = new JButton("Client");
		btnClient.setAlignmentX(CENTER_ALIGNMENT);
		btnClient.setMargin(new Insets(0, 49, 0, 49));
		pnlChild.add(btnClient);
		
		Component verticalStrut = Box.createVerticalStrut(15);
		pnlChild.add(verticalStrut);
		
		JButton btnPersonalAeroport = new JButton("Personal aeroport");
		JFrame thisFrame=this;
		btnPersonalAeroport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginFrame login=new LoginFrame("p");
				thisFrame.setVisible(false);
			}
		});
		btnPersonalAeroport.setAlignmentX(CENTER_ALIGNMENT);
		btnPersonalAeroport.setHorizontalAlignment(SwingConstants.RIGHT);
		pnlChild.add(btnPersonalAeroport);
		
		Component verticalStrut_1 = Box.createVerticalStrut(15);
		pnlChild.add(verticalStrut_1);
		
		JButton btnCompanieAeriana = new JButton("Companie aeriana");
		btnCompanieAeriana.setAlignmentX(CENTER_ALIGNMENT);
		pnlChild.add(btnCompanieAeriana);
		
		BufferedImage wPic = ImageIO.read(this.getClass().getResource("/images/banner.png"));
		JLabel lblBanner = new JLabel(new ImageIcon(wPic));
		
		contentPane.add(lblBanner, BorderLayout.NORTH);
	}

}
