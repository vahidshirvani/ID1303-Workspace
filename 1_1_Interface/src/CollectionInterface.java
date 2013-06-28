

/*********************************************************************


                        CollectionInterface


I denna java fil visas hur en pekare av ett sort typ kan peka till
olika typer av objekter. Polymorfims används vid flera tillfällen.
Även typ parametrar och en gererisk metod (flexibel cod) visas.



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


		/* Det finns två olika List i javas standard bibliotek.
		 * För att kunna skillja dem åt så tydlig görs det
		 * genom att ange vägen till List, så här java.util.List
		 * List är en Interface och dess pekare kan peka till
		 * ArrayList. Eftersom Typparameten är CharSequence så
		 * kan listan innehålla alla objekter som implementerar
		 * denna gränssnitt.
		 */

		java.util.List<CharSequence>    al =
								new ArrayList<CharSequence> ();
		al.add ("Eric");
		al.add (new String ("Vahid"));
		al.add (new StringBuilder ("Farid"));
		al.add (new StringBuffer ("Mikael"));
		print (al);



		/* LinkedList implementerar gränssnitett Collection
		 * därför använder vi Collection referens.
		 * Polymorfism sker här vid varje toString
		 */

		Collection<Number>    ll = new LinkedList<Number> ();
		ll.add (new Integer (11111));
		ll.add (new Double  (22222));
		ll.add (new Float   (3333f));
		ll.add (new Long    (4444l));
		print (ll);



		/* Eftersom classen HashSet implementerar gränssnitett
		 * Set så går det utmärkt att använda Set referens.
		 * Glöm inte att det inte går att skapa objekt av
		 * gränssnitett Set.
		 * Behållaren har typparametern Shape och det betyder
		 * att den kan behålla alla sorters objekt som
		 * implementerar interfacet. Eftersom Shape objekter
		 * inte har någon bra strängrepresentation så kommer
		 * resultatet inte se vänligt ut på konsollen,
		 */

		Set<Shape>    hs = new HashSet<Shape> ();
		hs.add (new Rectangle2D.Double (0, 0, 1, 1));
		hs.add (new Ellipse2D.Double (0, 0, 1, 1));
		hs.add (new Line2D.Float (0f, 0f, 1f, 1f));
		hs.add (new QuadCurve2D.Float (0f, 0f, 0f
									, 1f, 1f, 1f));
		print (hs);



		/* Observera att polymorfism sker här. Referensen
		 * AbstractList innehåller inte metoden add.
		 * När denna metod anropas så
		 * är det add i Vector klassen som körs.
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
		 * eller så kallad lista. Metoden toArray () i standard
		 * biblioteket gör precis tvärtom
		 * Metoden är en generisk, det betyder att den är flexibel
		 * och kan ta emot olika typer av vektorer.
		 * Så klart måste listan vara fördefenerad med samma sort
		 * lagringstyp som vektorn.
		 * Inparametern på metoden är Collection vilket betyder att
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
		 * på consollen. Strängrepresentationen hos objektet
		 * kommer att anropas genom polymorfism.
		 * För att kunna vandra i en lista så behöver man en
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
