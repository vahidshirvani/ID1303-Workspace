
/***********************************************************************


						     Webblasare

Denna program �r en enkel webl�sare. Man kan �ppna en viss webbsida som
existerar readan och se inneh�llet. Man kan �ven surfa vidare fr�n den
sida man redan har �ppnat genom att klika p� ett nytt l�nk. Sidorna
�ppnas inte perfekt utan visas lite gammaldags.


                                                  Vahid Shirvani (c)2010
***********************************************************************/


import java.net.*;             // URL
import java.awt.*;             // BorderLayout
import java.awt.event.*;       // ActionListener, ActionEvent
import javax.swing.event.*;    // HyperlinkListener, HyperlinkEvent
import javax.swing.*;          // JEditorPane, JTextField
                               // JScrollPane, JFrame,
                               // JButton, JPanel,
import java.io.*;              // IOException



class Webblasare
{

	// Datamedlemmar
	private JTextField    adress;
	private JEditorPane    HTMLpanel;

	Webblasare () throws IOException
	{


        /* H�r skapar vi centrala objekt som bildar
         * webl�saren.
         */

        JButton    goKnapp = new JButton ("Go");
        adress = new JTextField ("http://m.aftonbladet.se");
        HTMLpanel = new JEditorPane ("http://m.aftonbladet.se");
        HTMLpanel.setContentType("text/html");
        HTMLpanel.setEditable (false);



		/* Vi l�gger en lyssnare p� b�de Go knappen och
		 * textf�ltet. Inneh�llet i textf�ltet omvandlas
		 * till URL sedan s�tts v�ran JEditorPane visa
		 * up sidan.
		 */

		ActionListener    handalseLyssnare = new ActionListener ()
			{
				public void actionPerformed (ActionEvent e)
				{
					String s = adress.getText();

					try
					{
						URL    url = new URL (s);
						HTMLpanel.setPage (url);
					}
					catch (IOException ex)
					{}
				}
			};
		goKnapp.addActionListener (handalseLyssnare);
		adress.addActionListener (handalseLyssnare);


		/* Denna Hyperlink lyssnare �r bort kommernterat
		 * Anledningen �r att den inte �r k�nslig mot
		 * mus kliket.
		 */

		
		HyperlinkListener lankLyssnare = new HyperlinkListener ()
		{
			public void hyperlinkUpdate (HyperlinkEvent e)
			{
				try
				{
					URL    url = e.getURL ();
					adress.setText(e.getURL().toExternalForm());
					HTMLpanel.setPage (url);
				}
				catch (IOException ex)
				{
					warnUser("Can't follow link to " + 
							e.getURL().toExternalForm() + ": " + ex);
				}
			}
		};
		HTMLpanel.addHyperlinkListener (lankLyssnare);
		



        JPanel    topPanel = new JPanel ();
        topPanel.setLayout (new BorderLayout ());
        topPanel.add (adress, "Center");
        topPanel.add (goKnapp, "East");

		JPanel    panel = new JPanel ();
		panel.setLayout (new BorderLayout ());
		panel.add (topPanel, "North");
		panel.add (new JScrollPane (HTMLpanel),"Center");


		/* Vi skapar v�ran ram h�r och inte i main
		 * panelen som inneh�ller allt l�ggs i denna
		 * ram och n�dv�ndiga justeringar till�mpas
		 * p� v�ran ram.
		 */

		JFrame    ram = new JFrame ("Webbl�sare");
		ram.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		ram.setLocation (100, 100);
		ram.setSize (800, 400);
		ram.add (panel);
		ram.setVisible (true);
	}
	
	private void warnUser(String message){
		JOptionPane.showMessageDialog(null, message, "Error",
				JOptionPane.ERROR_MESSAGE);
	}
}


/* H�r kommer v�ran main class
 * I v�ran main skapar vi endast ett objekt av typen
 * webblasare och samtidigt f�ngar undantag som
 * kan kastas d�rifr�n
 */

class WebBrowser
{
	public static void main (String[] args)
	{
		try
		{
			Webblasare gg = new Webblasare ();
		}
		catch (IOException    e)
		{}
	}
}