


/****************************************************************************



                          Kalender.java



Denna program är en almanacka. Man kan se en hel månad. vilka dagar som är
röda samt skriva in aktiviteter i varje dag. Dem dagar som inte ingår i den
valda månaden kommer inte att synas och är avaktiverade.



                                                      Vahid Shirvani (C)2010
****************************************************************************/




import javax.swing.border.*;    // TitledBorder, BevelBorder
import javax.swing.*;           // JPanel, JFrame, JButton, JLabel,
								// Border, ButtonGroup
								// UIManager, JToggleButton, JTextFiled,
								// JComboBox, BorderFactory
import java.awt.*;              // GridLayout, Font¨, Color
import java.awt.event.*;        // ActionListener, ActionEvent
import java.util.*;				// GregorianCalendar, Calendar





/* Den Översta panelen skapas här
 * Panelen är passiv jämfört med dem två andra panel som finns
 * Panelen innehåller namnet på dagar i veckan. Även färg och
 * storlek bestäms här
 */


class TopPanel extends JPanel
{
	final private String[]     dagarNamn = {
								"    Söndag" ,
								"    Måndag" ,
								"    Tisdag" ,
								"    Onsdag" ,
								"    Torsdag",
								"    Fredag" ,
								"    Lördag" };

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

/* Denna panel är den mittersta och mest aktiva och centrala
 * av programet. först i penelen tilldelas dem parametrar som
 * har kommit in till datamedlemar. In parametrarna är referenser
 * som till objekt som även används i den nedersta panelen.
 * alltså finns det en bro mellan dem.
 * sedan bestäm knapparnas unika utseende genom vissa logik
 * till sist appliceras fram räknande utseende på var och en av knapparna.
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
		 * Typ av ram bestäms, Skrifternas font och storlek anges
		 * samt grupperas knapparna så att endast en i taget kan
		 * väljas.
		 */

		TitledBorder[]    titled = new TitledBorder[35];
		BevelBorder     bevel = new BevelBorder (BevelBorder.RAISED);
		Border    border = BorderFactory.createLineBorder (Color.BLACK);
		ButtonGroup    group = new ButtonGroup ();
		Font    titledFont = new Font ("Serif", Font.BOLD, 25);
		Font    buttonFont = new Font ("Serif", Font.PLAIN, 8);





		/* Platsen för första ettan hittas i kalendern.
		 * Hur många dagar i månaden som finns bestäms
		 * referenserna till röda dagar skapas
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

			// knappar som inte ingår i månaden
			// ska listas ut och avaktiveras
			knappar[i] = new JToggleButton (" ");
			if ((i + 1) < dag || j > monthMaxDays)
			{
				dayNumber = " ";
				knappar[i].setEnabled (false);
			}
			else if (j <= monthMaxDays)
				dayNumber = j++ + " ";


			// Färg röd på söndagar
			gregColor = datum;
			gregColor.set (Calendar.DAY_OF_MONTH, j);
			if (Calendar.SUNDAY + 1 == gregColor.get (Calendar.DAY_OF_WEEK))
				farg = Color.RED;
			else
				farg = Color.BLACK;


			// varje knapp får sin unika karaktär och placeras i panelen
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




		/* Här kommer våran knapp lyssnare
		 * Den letar fram mellan alla 35 knappar vilken som har blivit
		 * intryck och sedan plockar motsvarande text från kommentar
		 * stränget och klistrar in det på textfältet
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



/* Här defineras våran understa del av programet
 * den understa delen består av en panel med en
 * textfält innuti.
 */


class FloorPanel extends JPanel
{


	FloorPanel (  final JTextField        textfalt
				, final JToggleButton[]   button
				, final String[]          komment )
	{

		/* Textfältets lyssnare plockar skriftet inutti sig
		 * själv och klistrat indet på motsvarande knapp
		 * som är nedtryckt.
		 * texten lagras även i sträng kommentar.
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
		 * ska vara tillgängligt på både mittersta och översta
		 * panelen. alltså finns det bro mellan två klasser.
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
		 * top, center och botten paneler läggs i.
		 * huvud panelen placeras i en ram som justeras
		 * till passande inställnigar.
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




		/* Utseendet på prgramet ska helst se ut som andra program
		 * i operativ systemet. Därför andras Look och Feel
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