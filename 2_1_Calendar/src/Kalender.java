


/****************************************************************************



                          Kalender.java



Denna program �r en almanacka. Man kan se en hel m�nad. vilka dagar som �r
r�da samt skriva in aktiviteter i varje dag. Dem dagar som inte ing�r i den
valda m�naden kommer inte att synas och �r avaktiverade.



                                                      Vahid Shirvani (C)2010
****************************************************************************/




import javax.swing.border.*;    // TitledBorder, BevelBorder
import javax.swing.*;           // JPanel, JFrame, JButton, JLabel,
								// Border, ButtonGroup
								// UIManager, JToggleButton, JTextFiled,
								// JComboBox, BorderFactory
import java.awt.*;              // GridLayout, Font�, Color
import java.awt.event.*;        // ActionListener, ActionEvent
import java.util.*;				// GregorianCalendar, Calendar





/* Den �versta panelen skapas h�r
 * Panelen �r passiv j�mf�rt med dem tv� andra panel som finns
 * Panelen inneh�ller namnet p� dagar i veckan. �ven f�rg och
 * storlek best�ms h�r
 */


class TopPanel extends JPanel
{
	final private String[]     dagarNamn = {
								"    S�ndag" ,
								"    M�ndag" ,
								"    Tisdag" ,
								"    Onsdag" ,
								"    Torsdag",
								"    Fredag" ,
								"    L�rdag" };

	TopPanel ()
	{

		this.setLayout (new GridLayout (1, 7));
		JLabel[]     dagarEtikett = new JLabel[7];
		Font    font = new Font ("Serif", Font.BOLD, 20);


		for (int i = 0; i < 7; i++)
		{
			dagarEtikett[i] = new JLabel (dagarNamn[i]);
			dagarEtikett[i].setFont (font);
			if (i == 0)
				dagarEtikett[i].setForeground (Color.RED);
			this.add (dagarEtikett[i]);
		}
	}
}

/* Denna panel �r den mittersta och mest aktiva och centrala
 * av programet. f�rst i penelen tilldelas dem parametrar som
 * har kommit in till datamedlemar. In parametrarna �r referenser
 * som till objekt som �ven anv�nds i den nedersta panelen.
 * allts� finns det en bro mellan dem.
 * sedan best�m knapparnas unika utseende genom vissa logik
 * till sist appliceras fram r�knande utseende p� var och en av knapparna.
 */



class MidPanel extends JPanel
{


	private JTextField        textFalt;
	private JToggleButton[]   knappar;
	private String[]          kommentar;
	private GregorianCalendar greg;



	MidPanel (JTextField        textfalt
			, JToggleButton[]   button
			, String[]          komment
			, GregorianCalendar datum   )
	{


		// Datamedlemar tilldelas
		this.knappar =   button;
		this.greg =      datum;
		this.kommentar = komment;
		this.textFalt =  textfalt;




		/* Knapparnas utseende anpassas.
		 * Typ av ram best�ms, Skrifternas font och storlek anges
		 * samt grupperas knapparna s� att endast en i taget kan
		 * v�ljas.
		 */

		TitledBorder[]    titled = new TitledBorder[35];
		BevelBorder     bevel = new BevelBorder (BevelBorder.RAISED);
		Border    border = BorderFactory.createLineBorder (Color.BLACK);
		ButtonGroup    group = new ButtonGroup ();
		Font    titledFont = new Font ("Serif", Font.BOLD, 25);
		Font    buttonFont = new Font ("Serif", Font.PLAIN, 8);





		/* Platsen f�r f�rsta ettan hittas i kalendern.
		 * Hur m�nga dagar i m�naden som finns best�ms
		 * referenserna till r�da dagar skapas
		 */

		int dag = Calendar.SUNDAY;
		while (dag != greg.get (Calendar.DAY_OF_WEEK))
			dag++;

		int monthMaxDays = greg.getActualMaximum (Calendar.DAY_OF_MONTH);
		String    dayNumber = " ";
		int    j = 1;

		Color farg = null;
		GregorianCalendar    gregColor;




		for (int i = 0; i < 35; i++)
		{

			// knappar som inte ing�r i m�naden
			// ska listas ut och avaktiveras
			knappar[i] = new JToggleButton (" ");
			if ((i + 1) < dag || j > monthMaxDays)
			{
				dayNumber = " ";
				knappar[i].setEnabled (false);
			}
			else if (j <= monthMaxDays)
				dayNumber = j++ + " ";


			// F�rg r�d p� s�ndagar
			gregColor = datum;
			gregColor.set (Calendar.DAY_OF_MONTH, j);
			if (Calendar.SUNDAY + 1 == gregColor.get (Calendar.DAY_OF_WEEK))
				farg = Color.RED;
			else
				farg = Color.BLACK;


			// varje knapp f�r sin unika karakt�r och placeras i panelen
			titled[i] =	new TitledBorder (    border
											, dayNumber
											, TitledBorder.RIGHT
											, TitledBorder.TOP
											, titledFont
											, farg   );
			this.setLayout (new GridLayout (5, 7));
			knappar[i].setFont (buttonFont);
			group.add (knappar[i]);
			kommentar[i] = " " ;
			knappar[i].setBorder (titled[i]);
			this.add (knappar[i]);

		}




		/* H�r kommer v�ran knapp lyssnare
		 * Den letar fram mellan alla 35 knappar vilken som har blivit
		 * intryck och sedan plockar motsvarande text fr�n kommentar
		 * str�nget och klistrar in det p� textf�ltet
		 */


		ActionListener    knappLyssnare = new ActionListener ()
		{
			public void actionPerformed (ActionEvent e)
			{
				int i = 0;
				while (e.getSource () != MidPanel.this.knappar[i])
					i++;
				MidPanel.this.textFalt.setText (kommentar[i]);
			}
		};

		for (int i = 0; i < 35; i++)
			knappar[i].addActionListener (knappLyssnare);

		knappar[10].setSelected (true);
	}
}



/* H�r defineras v�ran understa del av programet
 * den understa delen best�r av en panel med en
 * textf�lt innuti.
 */


class FloorPanel extends JPanel
{


	FloorPanel (  final JTextField        textfalt
				, final JToggleButton[]   button
				, final String[]          komment )
	{

		/* Textf�ltets lyssnare plockar skriftet inutti sig
		 * sj�lv och klistrat indet p� motsvarande knapp
		 * som �r nedtryckt.
		 * texten lagras �ven i str�ng kommentar.
		 */

		ActionListener    lyssnare = new ActionListener ()
		{
			public void actionPerformed (ActionEvent e)
			{
				int i = 0;
				while (!button[i].isSelected ())
					i++;
				komment[i] = textfalt.getText ();
				button[i].setText (textfalt.getText ());
			}

		};

		textfalt.addActionListener (lyssnare);
		this.add (textfalt);

	}
}




class Kalender
{
	public static void main (String[] args)
	{


		/* Dessa fyra objekt skapas i main pga att deras referenser
		 * ska vara tillg�ngligt p� b�de mittersta och �versta
		 * panelen. allts� finns det bro mellan tv� klasser.
		 *
		 */


		JTextField    textField = new JTextField (20);
		JToggleButton[]    button = new JToggleButton[35];
		String[]    komment = new String[35];
		GregorianCalendar     greg =
						new GregorianCalendar (2010, Calendar.FEBRUARY, 1);



		JPanel    topPanel = new TopPanel ();
		JPanel    midPanel = new MidPanel ( 	  textField
												, button
												, komment
												, greg     );
		JPanel    floorPanel = new FloorPanel (   textField
												, button
												, komment );




		/* En huvud panel skapas och alla delar respektiv
		 * top, center och botten paneler l�ggs i.
		 * huvud panelen placeras i en ram som justeras
		 * till passande inst�llnigar.
		 */


		JPanel    huvudPanel = new JPanel ();
		huvudPanel.setLayout (new BorderLayout ());
		huvudPanel.add (topPanel, "North");
		huvudPanel.add (midPanel, "Center");
		huvudPanel.add (floorPanel, "South");


		JFrame    frame = new JFrame ();
		frame.setSize (800, 500);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.add (huvudPanel);
		frame.setVisible (true);




		/* Utseendet p� prgramet ska helst se ut som andra program
		 * i operativ systemet. D�rf�r andras Look och Feel
		 */

		String    utseende = UIManager.getSystemLookAndFeelClassName ();
		try
		{
			UIManager.setLookAndFeel (utseende);
		}
		catch (Exception e)
		{};


	}
}