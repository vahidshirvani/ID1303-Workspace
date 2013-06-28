

/*********************************************************************


                        CollectionInterface


I denna java fil visas hur en pekare av ett sort typ kan peka till
olika typer av objekter. Polymorfims anv�nds vid flera tillf�llen.
�ven typ parametrar och en gererisk metod (flexibel cod) visas.



                                               Vahid Shirvani (c) 2010
*********************************************************************/


import java.util.*;     // Collection, ArrayList, LinkedList,
						// Set, List, HashSet, Vector, AbstractList
import java.awt.*;      // Shape
import java.awt.geom.*; // Rectangle2D, Ellipse2D, Line2D.Float,
						// QuadCurve2D.Float, Area
//import java.lang.*;   // CharSequense, String, StringBuilder,
						// StringBuffer
                        // Number, Integer, Double, Float, Long



class CollectionInterface
{


	public static void main (String[] args)
	{


		/* Det finns tv� olika List i javas standard bibliotek.
		 * F�r att kunna skillja dem �t s� tydlig g�rs det
		 * genom att ange v�gen till List, s� h�r java.util.List
		 * List �r en Interface och dess pekare kan peka till
		 * ArrayList. Eftersom Typparameten �r CharSequence s�
		 * kan listan inneh�lla alla objekter som implementerar
		 * denna gr�nssnitt.
		 */

		java.util.List<CharSequence>    al =
								new ArrayList<CharSequence> ();
		al.add ("Eric");
		al.add (new String ("Vahid"));
		al.add (new StringBuilder ("Farid"));
		al.add (new StringBuffer ("Mikael"));
		print (al);



		/* LinkedList implementerar gr�nssnitett Collection
		 * d�rf�r anv�nder vi Collection referens.
		 * Polymorfism sker h�r vid varje toString
		 */

		Collection<Number>    ll = new LinkedList<Number> ();
		ll.add (new Integer (11111));
		ll.add (new Double  (22222));
		ll.add (new Float   (3333f));
		ll.add (new Long    (4444l));
		print (ll);



		/* Eftersom classen HashSet implementerar gr�nssnitett
		 * Set s� g�r det utm�rkt att anv�nda Set referens.
		 * Gl�m inte att det inte g�r att skapa objekt av
		 * gr�nssnitett Set.
		 * Beh�llaren har typparametern Shape och det betyder
		 * att den kan beh�lla alla sorters objekt som
		 * implementerar interfacet. Eftersom Shape objekter
		 * inte har n�gon bra str�ngrepresentation s� kommer
		 * resultatet inte se v�nligt ut p� konsollen,
		 */

		Set<Shape>    hs = new HashSet<Shape> ();
		hs.add (new Rectangle2D.Double (0, 0, 1, 1));
		hs.add (new Ellipse2D.Double (0, 0, 1, 1));
		hs.add (new Line2D.Float (0f, 0f, 1f, 1f));
		hs.add (new QuadCurve2D.Float (0f, 0f, 0f
									, 1f, 1f, 1f));
		print (hs);



		/* Observera att polymorfism sker h�r. Referensen
		 * AbstractList inneh�ller inte metoden add.
		 * N�r denna metod anropas s�
		 * �r det add i Vector klassen som k�rs.
		 */

		Area[]    area = new Area[3];
		area[0] = new Area ();
		area[1] = new Area ();
		area[2] = new Area ();
		AbstractList<Shape>    v = new Vector<Shape> ();
		vektorTillColl (area, v);
		print (v);
	}



		/* Denna metod omvandlar en vektor till en collection
		 * eller s� kallad lista. Metoden toArray () i standard
		 * biblioteket g�r precis tv�rtom
		 * Metoden �r en generisk, det betyder att den �r flexibel
		 * och kan ta emot olika typer av vektorer.
		 * S� klart m�ste listan vara f�rdefenerad med samma sort
		 * lagringstyp som vektorn.
		 * Inparametern p� metoden �r Collection vilket betyder att
		 * den kan ta imot alla referenser som
		 * implementerar Collection.
		 */

		static <T> void vektorTillColl (T[]    vektor,
										Collection<T>    c)
		{
			for (T    v: vektor)
				c.add (v);
		}



		/* Denna metod skriver ut alla element i en Collection
		 * p� consollen. Str�ngrepresentationen hos objektet
		 * kommer att anropas genom polymorfism.
		 * F�r att kunna vandra i en lista s� beh�ver man en
		 * pekare av typen Iterator.
		 */

		static void print (Collection coll)
		{
			Iterator     iter = coll.iterator ();
			while (iter.hasNext ())
				System.out.print (iter.next () + " ");
			System.out.println ();
		}
}
