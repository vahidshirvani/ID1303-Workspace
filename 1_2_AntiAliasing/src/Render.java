
/*******************************************************************



                            Render


I denna laboration så använder jag AntiAliasing, komposit och
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
	 * bli våra datamedlemmar är att dem ska vara tillgänliga
	 * från våran inre klass. Observera att även våran inre klass
	 * är en data medlem.
	 */

	private JToggleButton    kmp;
	private JToggleButton    grt;
	private JToggleButton    ana;

	private JPanel    RitDisplay;



	Display ()
	{


		 /* Referenserna skapades ovan. Men det första vi gör
		  * i konstruktorn är att skapa själva objektet.
		  */


		RitDisplay = new VPanel ();
		kmp = new JToggleButton (" Komposition ");
		grt = new JToggleButton (" Gradient ");
		ana = new JToggleButton (" AntiAliasing ");


		/* Vi har tre knappar i rutan och så klart ska dem
		 * få sina lyssnare. En lyssnare räker för allihopa.
		 * Lyssnaren skapas lokalt. Efter lokala klassen
		 * läggs en lyssnare på varje knapp
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



		/* Alla tre knappar läggs i en tollbar och den
		 * platsen fylls med klister. Sedan bestäms layouten
		 * på panelen dvs i vilket område saker och ting ska
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



	/* Denna klass är en inre klass till Dilspay alltså
	 * betyder detta att den kan använda alla datamedlemmar
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



			/* Våran muslyssnare defineras lokalt precis som
			 * knapp lyssnaren ovan. Vad muslyssnaren gör är att
			 * den ritar en ellipse eller en triangel beroende
			 * på vilken knapp som trycks på mus pekarens position.
			 * Efter att figuren och sin placering har bestämts ritas
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


			/* Beroende på om knappen Antialiasing är nedtryck eller
			 * ej så ändras vissa instllningar i våran Graphics2D
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




			/* Vi skapar våran Gradient paint oavset om tillhörande
			 * knapp är nedtryckt eller ej. Men färgen på Graphics
			 * ändras inte ifall den är false
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



/* I våran main så skapar vi endast en Display objekt.
 * objekt av den inre klassen behöver inte skapas här.
 * Display objektet läggs i en ram och lämpliga justeringar
 * gör och vissas.
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
