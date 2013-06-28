
/***********************************************************************


						     Webblasare

Denna program är en enkel webläsare. Man kan öppna en viss webbsida som
existerar readan och se innehållet. Man kan även surfa vidare från den
sida man redan har öppnat genom att klika på ett nytt länk. Sidorna
öppnas inte perfekt utan visas lite gammaldags.


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


        /* Här skapar vi centrala objekt som bildar
         * webläsaren.
         */

        JButton    goKnapp = new JButton ("Go");
        adress = new JTextField ("http://m.aftonbladet.se");
        HTMLpanel = new JEditorPane ("http://m.aftonbladet.se");
        HTMLpanel.setContentType("text/html");
        HTMLpanel.setEditable (false);



		/* Vi lägger en lyssnare på både Go knappen och
		 * textfältet. Innehållet i textfältet omvandlas
		 * till URL sedan sätts våran JEditorPane visa
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


		/* Denna Hyperlink lyssnare är bort kommernterat
		 * Anledningen är att den inte är känslig mot
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


		/* Vi skapar våran ram här och inte i main
		 * panelen som innehåller allt läggs i denna
		 * ram och nödvändiga justeringar tillämpas
		 * på våran ram.
		 */

		JFrame    ram = new JFrame ("Webbläsare");
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


/* Här kommer våran main class
 * I våran main skapar vi endast ett objekt av typen
 * webblasare och samtidigt fångar undantag som
 * kan kastas därifrån
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