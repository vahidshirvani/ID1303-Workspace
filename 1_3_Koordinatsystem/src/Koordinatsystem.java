

/**********************************************************************



                         Koordinatsystem


I denna klass s� ritar ett koordinat system up. Inga positoner p�
Linjer, kurvor, text och firurer �r kanstanta. utan det best�ms av progamet
i realtid med �ndring. Po�ngen med denna korrdinat system �r att man ska
kunna rita till exempel en figur p� en viss koordinat genom att l�sa av
x och y v�rden. L�ttare sagt har man origo i mitten.



                                                 Vahid Shirvani (c)2010
**********************************************************************/





import javax.swing.*;       // JPanel, JFrame
import java.awt.*;          // Graphics, Graphics2D, BasicStroke
import java.awt.geom.*;     // Line2D.Double, Point2D.Double, Color
							// CubicCurve.Double, Ellipse2D

class KPanel extends JPanel
{


	public void paintComponent (Graphics gr)
	{


		/* H�r har vi ingen behov av konstuktor eller
		 * datamedlemmar. Det ska endast ritas och
		 * dess kod skriv i paintKomponent
		 * F�rsta linjen �r super anropet till paintComponent
		 * hos super klassen sedan omvaldlas Graphic contextet
		 * till den nya versionen.
		 */

		super.paintComponent (gr);
		this.setBackground (Color.BLACK);
		Graphics2D    g = (Graphics2D) gr;



		/* Vi tar h�jden och bredden p� sk�rmen f�r att
		 * allt ska utg� ifr�n detta och �r beroende p� dem.
		 * Antal rutor och i x,y-led best�ms.  Dessutom
		 * best�ms skalan,
		 */

		int    bredd = this.getWidth ();
		int    hojd = this.getHeight ();

		int    xLinjer = 30;
		int    yLinjer = 30;

		int    xEnhet = bredd / xLinjer;
		int    yEnhet = hojd / yLinjer;

		System.out.println ("Rutans bredd ar: " + bredd);
		System.out.println ("Rutans hojd ar:  " + hojd);




		/* H�r kommer all kod f�r parallella linjer som �r vertikala
		 * Samtidigt best�ms f�rgen och siffror som ska st� bredvid
		 * dem. Efter att n�dv�ndiga information om linjen �r samlad
		 * s� ritas den. detta uprepas till alla linjer �r ritade.
		 */

		Line2D[]    xEnhetLinjer = new Line2D[xLinjer];
		int    xEnhetLinje = 1;
		int    xSiffror = xLinjer / 2;
		String    xString = "";
		for (int i = 0; i < xLinjer; i++)
		{
			xString = "" + (-1) * xSiffror--;

			g.setPaint (Color.YELLOW);
			if (i  != xLinjer / 2)
				g.drawString (xString, xEnhetLinje , hojd / 2);

			xEnhetLinjer[i] = new Line2D.Double ( xEnhetLinje += xEnhet
												, 0 //hojd / 2 + 2
												, xEnhetLinje
												, hojd );// 2 - 2 );
			g.setPaint (Color.GRAY);
			if (i  == xLinjer / 2 - 1)
				g.setPaint (Color.RED);
			g.draw (xEnhetLinjer[i]);
		}



		/* H�r �r det samma logik som ovan f�r utom att det handlar om
		 * horisontella linjer.
		 */

		Line2D[]    yEnhetLinjer = new Line2D[yLinjer];
		int    yEnhetLinje = 1;
		int    ySiffror = yLinjer / 2;
		String    yString = "";

		for (int i = 0; i < yLinjer; i++)
		{
			yString = "" + ySiffror--;

			g.setPaint (Color.YELLOW);
			if (i != yLinjer / 2)
				g.drawString (yString, bredd / 2 , yEnhetLinje);

			yEnhetLinjer[i] = new Line2D.Double ( 0 //bredd / 2 - 2
												, yEnhetLinje += yEnhet
												, bredd // 2 + 2
												, yEnhetLinje );
			g.setPaint (Color.GRAY);
			if (i == yLinjer / 2 - 1)
				g.setPaint (Color.RED);
			g.draw (yEnhetLinjer[i]);
		}

		/*
		Point2D    startPunkt = new Point2D.Double (-1 * bredd / 2, 0);
		Point2D    ctrl1Punkt = new Point2D.Double (-1 * bredd / 4, hojd / 2);
		Point2D    ctrl2Punkt = new Point2D.Double (bredd / 4, -1 * hojd / 2);
		Point2D    slutPunkt  = new Point2D.Double (bredd / 2, 0);
		*/




		/* H�r kan man otydligt se att n�r man ska ange punkter
		 * i koordinatsystemet beh�ver man inte t�nka p� pixlar
		 * och att man ligger i fj�de kvadrante. Utan origo
		 * �r i mitten och varje ruta �r en enhet.
		 */

		Point2D    startPunkt = new Point2D.Double (-10, 0);
		Point2D    ctrl1Punkt = new Point2D.Double (-5, (-1) * -5);
		Point2D    ctrl2Punkt = new Point2D.Double (5, (-1) * 10);
		Point2D    slutPunkt  = new Point2D.Double (7, 0);
		Ellipse2D    ellips = new Ellipse2D.Double (-8, (-1) * 8, 3, 3);
		CubicCurve2D    kubiskKurva =
						new CubicCurve2D.Double (   startPunkt.getX ()
													, startPunkt.getY ()
													, ctrl1Punkt.getX ()
													, ctrl1Punkt.getY ()
													, ctrl2Punkt.getX ()
													, ctrl2Punkt.getY ()
													, slutPunkt.getX  ()
													, slutPunkt.getY  () );

		/* F�r att flytta origo fr�n �vre v�nsta h�rnet till mitten
		 * s� anv�nder vi translate. och f�r att �ndra skala
		 * anv�nder vi scale.
		 */

		g.translate (bredd / 2, hojd / 2);
		g.setPaint (Color.GREEN);
		g.setStroke (new BasicStroke (0.1f));
		g.scale (xEnhet, yEnhet);
		g.draw (kubiskKurva);
		g.setPaint (Color.BLUE);
		g.fill (ellips);
	}
}


/* H�r kommer v�ran main. endast en objekt av panelen
 * som ska ritas skapas och l�ggs i en ram. Dessutom m�ste
 * vissa justeringar p� ramen g�ras.
 */

class Koordinatsystem
{
	public static void main (String[] args)
	{
		JPanel    kPanel = new KPanel ();
		JFrame    frame = new JFrame ();
		frame.setBounds (100, 100, 600, 600);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.add (kPanel);
		frame.setVisible (true);
	}
}