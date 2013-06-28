
/*******************************************************************



                            Render


I denna laboration s� anv�nder jag AntiAliasing, komposit och
Gradient. Man ritar en ellipse och en triangel med var och en av
musknapparna och sedan kan se effekten av dessa tre.


                                              Vahid Shirvani (c)2010
*******************************************************************/



import javax.swing.*;       // JPanel, JToolBar, JToggleButton,
							// JFrame, Box
import java.awt.event.*;    // ActionListener, ActionEvent
                            // MouseListener, MouseEvent
import java.awt.*;          // Shape, Color, BorderLayout
							// Graphics, Grapics2D, AlphaComposite
							// RenderingHints, GradientPaint
import java.awt.geom.*;     // Ellipse2D, Ellipse2D.Double
                            // Rectangle2D, Rectangle2D.Double

class Display extends JPanel
{

	/* Anledningen till att dessa har blivit valda till att
	 * bli v�ra datamedlemmar �r att dem ska vara tillg�nliga
	 * fr�n v�ran inre klass. Observera att �ven v�ran inre klass
	 * �r en data medlem.
	 */

	private JToggleButton    kmp;
	private JToggleButton    grt;
	private JToggleButton    ana;

	private JPanel    RitDisplay;



	Display ()
	{


		 /* Referenserna skapades ovan. Men det f�rsta vi g�r
		  * i konstruktorn �r att skapa sj�lva objektet.
		  */


		RitDisplay = new VPanel ();
		kmp = new JToggleButton (" Komposition ");
		grt = new JToggleButton (" Gradient ");
		ana = new JToggleButton (" AntiAliasing ");


		/* Vi har tre knappar i rutan och s� klart ska dem
		 * f� sina lyssnare. En lyssnare r�ker f�r allihopa.
		 * Lyssnaren skapas lokalt. Efter lokala klassen
		 * l�ggs en lyssnare p� varje knapp
		 */


		ActionListener knappLyssnare = new ActionListener ()
		{
			public void actionPerformed (ActionEvent e)
			{
				RitDisplay.repaint ();
			}
		};

		kmp.addActionListener (knappLyssnare);
		grt.addActionListener (knappLyssnare);
		ana.addActionListener (knappLyssnare);



		/* Alla tre knappar l�ggs i en tollbar och den
		 * platsen fylls med klister. Sedan best�ms layouten
		 * p� panelen dvs i vilket omr�de saker och ting ska
		 * placeras.
		 */


		JToolBar tools = new JToolBar ();
		tools.add (kmp);
		tools.add (grt);
		tools.add (ana);
		tools.add (Box.createGlue ());

		this.setLayout (new BorderLayout());
		this.add (tools, "North");
		this.add (RitDisplay, "Center");


	}



	/* Denna klass �r en inre klass till Dilspay allts�
	 * betyder detta att den kan anv�nda alla datamedlemmar
	 * av sin omgivande klass. Typ som knapparna.
	 */

	class VPanel extends JPanel
	{

		private Shape    figur1 = null;
		private Shape    figur2 = null;
		private Color    farg1 = new Color (225, 125, 100);
		private Color    farg2 = new Color (100, 125, 255);

		public VPanel ()
		{

			this.setBackground (Color.WHITE);



			/* V�ran muslyssnare defineras lokalt precis som
			 * knapp lyssnaren ovan. Vad muslyssnaren g�r �r att
			 * den ritar en ellipse eller en triangel beroende
			 * p� vilken knapp som trycks p� mus pekarens position.
			 * Efter att figuren och sin placering har best�mts ritas
			 * av paintComponent som anropas via repaint.
			 */


			MouseListener musLyssnare = new MouseAdapter ()
			{
				int startx;
				int starty;
				int slutx;
				int sluty;
				int musKnapp;

				public void mousePressed (MouseEvent e)
				{
					startx = e.getX ();
					starty = e.getY ();
				}

				public void mouseReleased (MouseEvent e)
				{
					slutx = e.getX ();
					sluty = e.getY ();
					if (e.getButton () == MouseEvent.BUTTON1)
						figur1 = new Rectangle2D.Double (startx,
													starty,
													slutx - startx,
													sluty - starty);
					else if (e.getButton () == MouseEvent.BUTTON3)
						figur2 = new Ellipse2D.Double (startx,
													starty,
													slutx - startx,
													sluty - starty);
					repaint ();
				}
			};

			this.addMouseListener (musLyssnare);
		}

		public void paintComponent (Graphics gr)
		{
			super.paintComponent (gr);
			Graphics2D    g = (Graphics2D) gr;


			/* Beroende p� om knappen Antialiasing �r nedtryck eller
			 * ej s� �ndras vissa instllningar i v�ran Graphics2D
			 */

			if (ana.isSelected ())
			{
				g.setRenderingHint (RenderingHints.KEY_ANTIALIASING,
									RenderingHints.VALUE_ANTIALIAS_ON);
				g.setRenderingHint (RenderingHints.KEY_COLOR_RENDERING,
									RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			}
			else
			{
				g.setRenderingHint (RenderingHints.KEY_ANTIALIASING,
									RenderingHints.VALUE_ANTIALIAS_DEFAULT);
				g.setRenderingHint (RenderingHints.KEY_COLOR_RENDERING,
									RenderingHints.VALUE_COLOR_RENDER_DEFAULT);
			}




			/* Vi skapar v�ran Gradient paint oavset om tillh�rande
			 * knapp �r nedtryckt eller ej. Men f�rgen p� Graphics
			 * �ndras inte ifall den �r false
			 */

			g.setPaint (farg1);
			GradientPaint gp1 = new GradientPaint (0,0,
													farg1,
													this.getWidth (),
													this.getHeight (),
													farg2);
			if (grt.isSelected ())
				g.setPaint (gp1);
			if (figur1 != null)
				g.fill (figur1);

			g.setPaint (farg2);
			GradientPaint gp2 = new GradientPaint (0,0,
													farg2,
													this.getWidth (),
													this.getHeight (),
													farg1);
			if (grt.isSelected ())
				g.setPaint (gp2);
			AlphaComposite    composite = AlphaComposite.getInstance (
											AlphaComposite.SRC_OVER, 0.6f);
			if (kmp.isSelected ())
				g.setComposite (composite);
			if (figur2 != null)
				g.fill (figur2);
		}
	}
}



/* I v�ran main s� skapar vi endast en Display objekt.
 * objekt av den inre klassen beh�ver inte skapas h�r.
 * Display objektet l�ggs i en ram och l�mpliga justeringar
 * g�r och vissas.
 */

class Render
{
	public static void main (String[] args)
	{
		Display    display = new Display ();

		JFrame    frame = new JFrame ("RitPogram");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setSize (600, 300);
		frame.setLocation (100, 100);
		frame.add (display);
		frame.setVisible (true);
	}
}
