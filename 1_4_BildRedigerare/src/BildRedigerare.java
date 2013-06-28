

/******************************************************************************



                            BildRedigerare



I denna program s� kan man �ppna en bild fr�n nogon st�lle i datorn och
redigeria f�rg kontrasten i den. Sedan kan bilden sparas. ppl�sningen p�
bilden anpassa till rutan.



                                                         Vahid Shirvani (c)2010
******************************************************************************/




import javax.swing.*;               // JFrame, JMenu, JMenuItem, JPanel,
						 			// JFileChooser, JSplitPane, JSlider,
						 			// SwingConstants, JMenuBar
import javax.swing.event.* ;        // ChangeListener, ChangeEvent
import java.awt.*;                  // BorderLayout, Graphics, Graphics2D
import java.awt.event.*;            // ActionListener, ActionEvenet
import java.awt.image.*;            // BufferedImage, WritableRaster, ColorModel
import java.io.*;                   // File
import javax.imageio.*;				// ImageIO



class NorthBar extends JMenuBar
{

	/* Alla dessa tre datamedlemmar som placeras h�r
	 * kan anv�ndas utav dem inre klasserna som till
	 * exempel lyssnarna som brukar defenireas lokalt.
	 * Observera att en av medlemmarna �r en objekt som
	 * som dess klass defeniras i denna fil.
	 */

	private JMenuItem[]    arkivMenuItem;
	private JMenuItem[]    hjalpMenuItem;
	private BildPanel      bildPanel = null;


	NorthBar (BildPanel    bp)
	{

		/* Konstruktorn har en inparameter. Anledningen till denna
		 * in parameter �r att vi ha samma referens som skapas i main
		 * Det finns en s� kallad bro mellan v�ra klasser.
		 * Detta betyder att vi kan komma �t datamedlemmarna i klassen
		 * genom den grafiska gr�nsnitet.
		 */

		this.bildPanel = bp;



		/* H�r f�ljer kod f�r v�ra menyer.
		 * Dem d�ps och plaseras i en menybar.
		 */

		JMenu arkivMenu = new JMenu ("Arkiv");
		String[]    arkivNamn = {"�ppna", "Spara", "St�ng"};
		arkivMenuItem = new JMenuItem[arkivNamn.length];
		for (int i = 0; i < arkivNamn.length; i++)
		{
			arkivMenuItem[i] = new JMenuItem (arkivNamn[i]);
			arkivMenu.add (arkivMenuItem[i]);
			if (i == arkivNamn.length - 2)
				arkivMenu.addSeparator ();
		}

		JMenu hjalpMenu = new JMenu ("Hj�lp");
		String[]    hjalpNamn = {"About"};
		hjalpMenuItem = new JMenuItem[hjalpNamn.length];
		for (int i = 0; i < hjalpNamn.length; i++)
		{
			hjalpMenuItem[i] = new JMenuItem (hjalpNamn[i]);
			hjalpMenu.add (hjalpMenuItem[i]);
		}


		this.add (arkivMenu);
		this.add (hjalpMenu);




		/* En gemensam lyssnare l�ggs p� alla knappar. Genom getSource
		 * kan man veta vilken knapp som blivit tryckt.
		 * lyssnaren defereras lokalt och d�rf�r kan den ha tillg�ng
		 * till omgvande klassens resurser.
		 */

		ActionListener    menuListener = new ActionListener ()
		{
			public void actionPerformed (ActionEvent e)
			{
				Object    source = e.getSource ();
				File    aktuellKatalog = new File (".");
				JFileChooser    chooser = new JFileChooser (aktuellKatalog);
				int    harValt;
				File    filBild;
				BufferedImage    bild;
				BufferedImage    tmpBild;
				if (source == arkivMenuItem[0])
				{
					harValt = chooser.showOpenDialog (null);
					if (harValt == JFileChooser.APPROVE_OPTION)
					{
						filBild = chooser.getSelectedFile ();
						try
						{
							bild = ImageIO.read (filBild);
							tmpBild = ImageIO.read (filBild);
							if (bild != null)
							{
								bildPanel.setBild (bild, tmpBild);
							}
						}
						catch (IOException ex)
						{
							ex.printStackTrace ();
						}
						bildPanel.repaint ();
					}
				}
				else if (source == arkivMenuItem[1])
				{
					/*harValt = chooser.showSaveDialog (null);
					if (harValt == JFileChooser.APPROVE_OPTION)
					{
						filBild = chooser.getSelectedFile ();
						try
						{
							bild = ImageIO.read (filBild);
							if (bild != null)
								bildPanel.setBild (bild);
						}
						catch (IOException ex)
						{
							ex.printStackTrace ();
						}
					}*/
				}
				else if (source == arkivMenuItem[2])
					System.exit (0);
				else
					System.out.println ();
			}
		};
		for (int i = 0; i < arkivMenuItem.length; i++)
			arkivMenuItem[i].addActionListener (menuListener);
		for (int i = 0; i < hjalpMenuItem.length; i++)
			hjalpMenuItem[i].addActionListener (menuListener);
	}
}




/* Denna panel ska inneh�lla v�ran bild. Egentligen s� har
 * vi tv� styckna bilder. en bild som redegeras och en annan
 * som �r den orginala. Sk�let till detta �r att man kanske vill
 * dra tillbaka slider f�rgen och minska f�rgen p� bilden.
 */


class BildPanel extends JPanel
{
	private BufferedImage    bild;
	private BufferedImage    tmpBild;
	private Color    sliderColor;

	BildPanel ()
	{
		this.bild = new BufferedImage (1, 1, BufferedImage.TYPE_INT_ARGB);
		this.tmpBild = new BufferedImage (1, 1, BufferedImage.TYPE_INT_ARGB);
		this.sliderColor = new Color (0, true);
	}



	/* Denna klass har sin datamedlemmar, konstruktor och metoder
	 * Metoderna fungerar som publika gr�nssnitett f�r andra
	 * klasser som har tillg�ng till referenser till denna klass.
	 * Den ena klssen kan �ndra f�rgen och andra kan �ndra bilden
	 * genom set metoderna.
	 */


	BildPanel (BufferedImage    bi, Color    co)
	{
		this.bild = bi;
		this.sliderColor = co;
	}

	public void setBild (BufferedImage    bi, BufferedImage    tbi)
	{
		this.bild = bi;
		this.tmpBild = tbi;
	}

	public void setFarg (Color    co)
	{
		this.sliderColor = co;
	}



	/* I denna panitCompnent metod s� till�mpas f�rgen
	 * fr�n slider till bilden. Varje pixel fr�n bilden
	 * l�ses av separat och f�rgen adderas med f�rgen
	 * som redan fanns i pixlet.
	 */

	public void paintComponent (Graphics gr)
	{
		super.paintComponent (gr);
		Graphics2D    g = (Graphics2D) gr;

		int    bildBredd = bild.getWidth ();
		int    bildHojd = bild.getHeight ();

		//CloneableBufferedImage cloneBild = (CloneableBufferedImage) bild;
		//BufferedImage    tmpBild = bild; //(BufferedImage) cloneBild.clone ();

		WritableRaster    raster = tmpBild.getRaster ();
		ColorModel    model = tmpBild.getColorModel ();

		Object    pixel = null;
		Color    pixelColor;
		int    pixelRed, pixelGreen, pixelBlue;
		int    sliderRed, sliderGreen, sliderBlue;
		int    argb = 0;
		int    sliderARGB = 0;
		for (int i = 0; i < bildBredd; i ++)
			for (int j = 0; j < bildHojd; j++)
			{

				pixel = raster.getDataElements (i, j, null);

				pixelRed = model.getRed (pixel);
				pixelGreen = model.getGreen (pixel);
				pixelBlue = model.getBlue (pixel);

				sliderRed = sliderColor.getRed ();
				sliderGreen = sliderColor.getGreen ();
				sliderBlue = sliderColor.getBlue ();

				pixelRed = (pixelRed + sliderRed < 255)? pixelRed += sliderRed : 255;
				pixelGreen = (pixelGreen + sliderGreen < 255)? pixelGreen += sliderGreen : 255;
				pixelBlue = (pixelBlue + sliderBlue < 255)? pixelBlue += sliderBlue : 255;

				pixelColor = new Color (pixelRed, pixelGreen, pixelBlue);
				argb = pixelColor.getRGB ();
				pixel = model.getDataElements (argb, null);
				raster.setDataElements (i, j , pixel);
			}
		g.drawImage (tmpBild, 0, 0, this.getWidth (), this.getHeight (), null);
		tmpBild.setData (bild.getRaster ());

	}

	/*
	class CloneableBufferedImage extends BufferedImage implements Cloneable
	{
		CloneableBufferedImage ()
		{
			super(1, 1, BufferedImage.TYPE_INT_ARGB);
		}

		public Object clone ()
		{
			try
			{
				return super.clone();
			}
			catch (CloneNotSupportedException    e)
			{
				e.printStackTrace ();
				return null;
			}
		}
	}
	*/
}



/* V�ran splitter klass inneh�ller b�de bilden och slider.
 * Den har dessa tv� som datamedlemmar.
 * Den ena allts� BildPanel kommer in som referens och inte skapas.
 * anledningen till detta �r att den ska vara samma bild och inte
 * n�gon ny. Allts� har vi en bro.
 */


class Splittrare extends JSplitPane
{
	private JSlider[]    sliderFarg;
	private BildPanel    bildPanel;

	Splittrare (BildPanel    bp)
	{
		this.bildPanel = bp;


		/* Alla tre slider skapas och lyssnare l�ggs p� dem.
		 * deras jusretingar g�r samtidigt. sedan l�ggs dem
		 * i panelen.
		 */

		JPanel    sliderPanel = new JPanel ();
		sliderFarg = new JSlider[3];
		for (int i = 0; i < sliderFarg.length; i++)
		{
			sliderFarg[i] = new JSlider (SwingConstants.VERTICAL, 0, 100, 0);
			sliderPanel.add (sliderFarg[i]);
		}

		ChangeListener    sliderLyssnare = new ChangeListener ()
		{
			int[]    farg = new int[3];
			Color    sliderColor;
			public void stateChanged (ChangeEvent    e)
			{
				for (int i = 0; i < sliderFarg.length; i++)
					farg[i] = sliderFarg[i].getValue ();
				sliderColor = new Color (farg[0], farg[1], farg[2]);
				bildPanel.setFarg (sliderColor);
				bildPanel.repaint ();
			}
		};


		for (int i = 0; i < sliderFarg.length; i++)
			sliderFarg[i].addChangeListener (sliderLyssnare);

		this.setContinuousLayout (false);
		this.setDividerSize (2);
		this.setDividerLocation (640);
		this.setLeftComponent (bildPanel);
		this.setRightComponent (sliderPanel);
	}


}



/* I v�ran main skapas objekter av alla tre klasser. Mellan
 * tv� av dessa l�ggs en bro allts� i detta fall �r det bilden.
 * tillsist l�ggs allt i en ram och visas.
 */

class BildRedigerare
{

	public static void main (String[] arg)
	{
		BildPanel   bildPanel = new BildPanel ();
		JMenuBar    northBar = new NorthBar (bildPanel);
		JSplitPane    splittrare = new Splittrare (bildPanel);
		JPanel    huvudPanel = new JPanel ();
		JFrame    frame = new JFrame ();


		/*
		try
		{
			UIManager.setLookAndFeel (
					UIManager.getSystemLookAndFeelClassName ());
		}
		catch (Exception e)
		{
			e.printStackTrace ();
		}
		*/


		huvudPanel.setLayout (new BorderLayout ());
		huvudPanel.add (northBar, "North");
		huvudPanel.add (splittrare, "Center");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setBounds (100, 100, 800, 500);
		frame.add (huvudPanel);
		frame.setVisible (true);
	}
}