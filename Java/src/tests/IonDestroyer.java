package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import classes.DatabaseConnection;
import interfaces.UserFrame;

class IonDestroyer {
	private static UserFrame userFrame;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("Test de conectare cu date corecte")
	void db_test1() {
		DatabaseConnection db;
		db = new DatabaseConnection("jdbc:mysql://localhost:3306/schema1", "root", "root");
		assertEquals(db.connect(), true);
		assertEquals(db.disconnect(), true);
	}

	@Test
	@DisplayName("Test de conectare cu URL gresit")
	void db_test2() {
		DatabaseConnection db;                          //        dis wrong
		db = new DatabaseConnection("jdbc:mysql://localhost:3306/fsghdgndghb", "root", "root");
		assertEquals(db.connect(), false);
		//assertEquals(db.disconnect(), true); // useless
	}

	@Test
	@DisplayName("Test de query corect")
	void db_test3() {
		DatabaseConnection db;
		db = new DatabaseConnection("jdbc:mysql://localhost:3306/schema1", "root", "root");
		db.connect();
		
		assertEquals(db.sendQuery("SELECT * FROM CURSE"), true);
		
		db.disconnect();
	}

	@Test
	@DisplayName("Test de query string gol")
	void db_test4() {
		DatabaseConnection db;
		db = new DatabaseConnection("jdbc:mysql://localhost:3306/schema1", "root", "root");
		db.connect();
		
		assertEquals(db.sendQuery(""), false);
		
		db.disconnect();
	}

	@Test
	@DisplayName("Test de query gresit")
	void db_test5() {
		DatabaseConnection db;
		db = new DatabaseConnection("jdbc:mysql://localhost:3306/schema1", "root", "root");
		db.connect();
		
		assertEquals(db.sendQuery("fghfsg"), false);
		
		db.disconnect();
	}

	@Test
	@DisplayName("Test de query null")
	void db_test6() {
		DatabaseConnection db;
		db = new DatabaseConnection("jdbc:mysql://localhost:3306/schema1", "root", "root");
		db.connect();
		
		assertEquals(db.sendQuery(null), false);
		
		db.disconnect();
	}

	@Test
	@DisplayName("Test de tablesize() fara conectiune la BD")
	void db_test7() {
		DatabaseConnection db;
		db = new DatabaseConnection("jdbc:mysql://localhost:3306/schema1", "root", "root");
		
		assertEquals(db.tableSize(), -1);

		db.disconnect();
	}

	@Test
	@DisplayName("Test de tablesize() fara trimiterea unui query")
	void db_test8() {
		DatabaseConnection db;
		db = new DatabaseConnection("jdbc:mysql://localhost:3306/schema1", "root", "root");
		db.connect();
		
		assertEquals(db.tableSize(), -1);
		
		db.disconnect();
	}

	@Test
	@DisplayName("Test de tablesize() corect")
	void db_test9() {
		DatabaseConnection db;
		db = new DatabaseConnection("jdbc:mysql://localhost:3306/schema1", "root", "root");
		db.connect();
		db.sendQuery("SELECT * FROM CURSE");
		
		assertEquals(db.tableSize() >= 0, true);

		db.disconnect();
	}

	@Test
	@DisplayName("debitCardPayment()")
	void card_test1() {
		try {
			userFrame = new UserFrame();
			userFrame.debitCardPayment();
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	@DisplayName("viramentBancar()")
	void virament() {
		try {
			userFrame = new UserFrame();
			userFrame.viramentBancar();
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	@DisplayName("getSelectedButtonText() cu buttonGroup gol")
	void gsb_test1() throws ParseException {
		userFrame = new UserFrame();
		ButtonGroup rdButtons = new ButtonGroup();
		
		assertEquals(userFrame.getSelectedButtonText(rdButtons), null);
	}

	@Test
	@DisplayName("getSelectedButtonText() cu buttonGroup continand un element null")
	void gsb_test2() throws ParseException {
		userFrame = new UserFrame();
		ButtonGroup rdButtons = new ButtonGroup();
		rdButtons.add(null);
		
		assertEquals(userFrame.getSelectedButtonText(rdButtons), null);
	}

	@Test
	@DisplayName("getSelectedButtonText() cu buttonGroup continand un element selectat")
	void gsb_test3() throws ParseException {
		userFrame = new UserFrame();
		ButtonGroup rdButtons = new ButtonGroup();
		
		JRadioButton button1 = new JRadioButton();
		button1.setText("Buton1");

		rdButtons.add(button1);
		button1.setSelected(true); // il selectam
		
		assertEquals(userFrame.getSelectedButtonText(rdButtons), "Buton1");
	}

	@Test
	@DisplayName("getSelectedButtonText() cu buttonGroup continand un element neselectat")
	void gsb_test4() throws ParseException {
		userFrame = new UserFrame();
		ButtonGroup rdButtons = new ButtonGroup();
		
		JRadioButton button1 = new JRadioButton();
		button1.setText("Buton1");

		rdButtons.add(button1);
		
		assertEquals(userFrame.getSelectedButtonText(rdButtons), null);
	}

	@Test
	@DisplayName("getSelectedButtonText() cu buttonGroup continand multiple elemente si unul selectat")
	void gsb_test5() throws ParseException {
		userFrame = new UserFrame();
		ButtonGroup rdButtons = new ButtonGroup();
		
		int i = 0;
		JRadioButton[] buttons = {
				new JRadioButton("Buton" + (i++)),
				new JRadioButton("Buton" + (i++)),
				new JRadioButton("Buton" + (i++)),
				new JRadioButton("Buton" + (i++)),
				new JRadioButton("Buton" + (i++))
		};

		for (JRadioButton button : buttons)
			rdButtons.add(button);
		
		buttons[2].setSelected(true);
		
		assertEquals(userFrame.getSelectedButtonText(rdButtons), "Buton2");
	}

	@Test
	@DisplayName("getSelectedButtonText() cu buttonGroup continand multiple elemente si multiple selectate")
	void gsb_test6() throws ParseException {
		userFrame = new UserFrame();
		ButtonGroup rdButtons = new ButtonGroup();
		
		int i = 0;
		JRadioButton[] buttons = {
				new JRadioButton("Buton" + (i++)),
				new JRadioButton("Buton" + (i++)),
				new JRadioButton("Buton" + (i++)),
				new JRadioButton("Buton" + (i++)),
				new JRadioButton("Buton" + (i++))
		};

		for (JRadioButton button : buttons)
			rdButtons.add(button);

		buttons[2].setSelected(true);
		buttons[3].setSelected(true);
		
		assertEquals(userFrame.getSelectedButtonText(rdButtons), "Buton3");
	}

	@Test
	@DisplayName("endOfDay cu parametru null")
	void doomsday_test1() throws ParseException {
		userFrame = new UserFrame();
		LocalDateTime dateTime = null;
		
		assertEquals(userFrame.endOfDay(dateTime), null);
	}

	@Test
	@DisplayName("endOfDay cu data_string corect")
	void doomsday_test2() throws ParseException {
		userFrame = new UserFrame();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String dateStr = "2002-04-03 00:00:00";
		LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);

		String dateStr_test = "2002-04-03 23:59:59";
		LocalDateTime dateTime_test = LocalDateTime.parse(dateStr_test, formatter);
		
		assertEquals(userFrame.endOfDay(dateTime).truncatedTo(ChronoUnit.DAYS), dateTime_test.truncatedTo(ChronoUnit.DAYS));
	}

	@Test
	@DisplayName("endOfDay cu data_string corect")
	void doomsday_test3() throws ParseException {
		userFrame = new UserFrame();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String dateStr = "2002-04-03 23:59:59";
		LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);

		String dateStr_test = "2002-04-03 23:59:59";
		LocalDateTime dateTime_test = LocalDateTime.parse(dateStr_test, formatter);
		
		assertEquals(userFrame.endOfDay(dateTime).truncatedTo(ChronoUnit.DAYS), dateTime_test.truncatedTo(ChronoUnit.DAYS));
	}

}
