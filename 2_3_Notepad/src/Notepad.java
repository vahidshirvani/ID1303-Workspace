
/******************************************************************************


                                  Notepad


Denna program är en textredigerare. man kan öppna en text fil från något ställe
i datorn sedan ändra filen och dessutom spara filen lokalt. Ifall texten ser
liten ut kan man även ändra på fonten och storlek.


                                                         Vahid Shirvani (c)2010
******************************************************************************/


import javax.swing.*;		// JFrame, JPanel, JMenu, JMenuItem,
							// JMenuBar, JDialog, JList,
							// JTextArea, JScrollBar, JOptionPane
							// JFileChooser
import java.awt.*;   	    // Font, GridLayout, BorderLayout
import java.awt.event.*;	// ActionListener, ActionEvent
import java.io.*;           // BufferedReader, PrintWriter, FileReader, FileWriter


class TopMenu extends JMenuBar
{

	/* Här defenerar vi den översta delen av programet.
	 * alla knappar i menyerna skapas och en gemensam
	 * lyssnare sätt på alla.
	 */

	private JMenu    fileMenu;
	private JMenu    editMenu;
	private JMenu    helpMenu;

	private JTextArea    textArea;
	private JDialog    fontWindow;



	/* Observera att när man ska skapa den eversta delen
	 * så ska man ange två parametrar. dessa två parametrar
	 * är en bro mellan olika delar av programet.
	 */

	TopMenu (JTextArea ta, JDialog fw)
	{

		this.textArea = ta;
		this.fontWindow = fw;

		fileMenu = new JMenu ("File");
		String[]    fileString = {"Open", "Save", "Close"};
		final JMenuItem[]    fileMenuItem =
							new JMenuItem[fileString.length];
		for (int i = 0; i < fileString.length; i++)
		{
			fileMenuItem[i] = new JMenuItem (fileString[i]);
			fileMenu.add (fileMenuItem[i]);
			if (i == fileString.length - 2)
				fileMenu.addSeparator ();
		}

		editMenu = new JMenu ("Edit");
		String[]    editString = {"Font"};
		final JMenuItem[]    editMenuItem =
							new JMenuItem[editString.length];
		for(int i = 0; i < editString.length; i++)
		{
			editMenuItem[i] = new JMenuItem (editString[i]);
			editMenu.add (editMenuItem[i]);
		}

		helpMenu = new JMenu ("Help");
		String[]    helpString = {"About"};
		final JMenuItem[]    helpMenuItem =
							new JMenuItem[helpString.length];
		for(int i = 0; i < helpString.length; i++)
		{
			helpMenuItem[i] = new JMenuItem (helpString[i]);
			helpMenu.add (helpMenuItem[i]);
		}

		/* här kommer våran lyssnare. En gemensam lyssnare
		 * för alla menyknapparna. vi hittar fram källan
		 * och gör den händelsen som är defenerad för knappen.
		 */


		ActionListener    menuListener = new ActionListener ()
		{
			public void actionPerformed (ActionEvent e)
			{
				Object    source = e.getSource ();
				JFileChooser   	chooser = new JFileChooser (new File ("."));

				if (source == fileMenuItem[0])
				{
					int    returnVal = chooser.showOpenDialog (null);
					if (returnVal == JFileChooser.APPROVE_OPTION)
					{
						try
						{
							BufferedReader    in = new BufferedReader (
								new FileReader (
									chooser.getSelectedFile ()));
							String    line = in.readLine ();
							while (line != null)
							{
								textArea.append (line);
								textArea.append (
									System.getProperty ("line.separator"));
								line = in.readLine ();
							}
							in.close ();
						}
						catch (IOException ex)
						{
							ex.printStackTrace ();
						}

					}


				}

				else if (source == fileMenuItem[1])
				{
					int    returnVal = chooser.showSaveDialog (null);
					if (returnVal == JFileChooser.APPROVE_OPTION)
					{
						try
						{
							PrintWriter    out = new PrintWriter (
								new FileWriter (
									chooser.getSelectedFile ()));
							out.println (textArea.getText ());
						out.close ();
						}
						catch (IOException ex)
						{}
					}
				}

				else if (source == fileMenuItem[2])
				{
					System.exit (0);
				}

				else if (source == editMenuItem[0])
				{
					fontWindow.setVisible (true);
				}

				else if (source == helpMenuItem[0])
				{
					String    text =
								"Notepad 1.0" +
								System.getProperty("line.separator") +
								"CopyRight(c)2010 Vahid Shirvani" +
								System.getProperty("line.separator") +
								"All Right Reserved";
					JOptionPane    about = new JOptionPane (text);
					JDialog    dialog =
									about.createDialog (null, "About");
					dialog.setBounds (400, 250, 300, 150);
					dialog.setVisible (true);
				}
			}
		};

		for (JMenuItem menu : fileMenuItem)
			menu.addActionListener (menuListener);

		for (JMenuItem menu : editMenuItem)
			menu.addActionListener (menuListener);

		for (JMenuItem menu : helpMenuItem)
			menu.addActionListener (menuListener);

		this.add (fileMenu);
		this.add (editMenu);
		this.add (helpMenu);
	}
}



/* Här defeneras våran font fönster. I font fönstret så kan man
 * välja fram ett viss teckensnitt som kan tillämas på text arean
 * observera att referensen till textarean är ett bro mellan
 * olika delar av proramet.
 */

class FontWindow extends JDialog
{
	private JList    nameList;
	private JList    styleList;
	private JList    sizeList;
	private JList    colorList;

	private Font    font;

	private JTextArea    textArea;

	public Font getFont ()
	{
		if (font == null)
			font = new Font ("SERIF", Font.PLAIN, 20);
		return this.font;
	}

	FontWindow (JTextArea    ta)
	{

		this.textArea = ta;


		/* Den översta delen av font fönstret besår av
		 * etikettet.
		 */

		JLabel    nameLabel = new JLabel ("Name");
		JLabel    styleLabel = new JLabel ("Style");
		JLabel    sizeLabel = new JLabel ("Size");
		JPanel    labelPanel = new JPanel ();
		labelPanel.setLayout (new GridLayout (1, 3));
		labelPanel.add (nameLabel);
		labelPanel.add (styleLabel);
		labelPanel.add (sizeLabel);



		String[]    nameString = {	"Dialog",
									"Dialog Input",
									"SansSerif",
									"Serif",
									"MoniSpaced" };
		nameList = new JList (nameString);
		nameList.setSelectedIndex (0);


		/* Den mittersta delen av font fönstret består av tre listor
		 * Varje lista innehåller någon typ av information som kan
		 * bilda våran slutliga font.
		 */

		final int[]    styleInt = {	Font.PLAIN,
									Font.BOLD,
									Font.ITALIC,
									Font.BOLD + Font.ITALIC };
		String[]    styleString = {	"Plain",
									"Bold",
									"Italic",
									"Bold + Italic"};
		styleList = new JList (styleString);
		styleList.setSelectedIndex (0);

		String[]    sizeString = {"10", "15", "20", "30"};
		sizeList = new JList (sizeString);
		sizeList.setSelectedIndex (0);

		JPanel    centerPanel = new JPanel ();
		centerPanel.setLayout (new GridLayout (1, 3, 10, 10));
		centerPanel.add (nameList);
		centerPanel.add (styleList);
		centerPanel.add (sizeList);


		final JButton    okButton = new JButton ("  OK  ");
		final JButton    cancelButton = new JButton ("Cancel");
		JPanel    buttonPanel = new JPanel ();
		buttonPanel.add (okButton);
		buttonPanel.add (cancelButton);

		/*
		 * Den understa delen av font fönstret. Det ligger
		 * två knappar som har en gemensam lyssnare. Beroende
		 * på vilken  knapp som har trycks kommer handelse ske.
		 */

		ActionListener    buttonListener = new ActionListener ()
		{
			public void actionPerformed (ActionEvent e)
			{
				Object    source = e.getSource ();
				if (source == okButton)
				{
					String    selectedName =
							(String) nameList.getSelectedValue ();
					int   selectedStyle =
							styleInt[styleList.getSelectedIndex ()];
					int   selectedSize =
							Integer.parseInt (
								(String) sizeList.getSelectedValue () );

					font = new Font (	selectedName,
										selectedStyle,
										selectedSize   );
					textArea.setFont (font);
					FontWindow.this.setVisible (false);
				}
				if (source == cancelButton)
				{
					FontWindow.this.setVisible (false);
				}
			}
		};
		okButton.addActionListener (buttonListener);
		cancelButton.addActionListener (buttonListener);

		this.setTitle ("Font Window");
		this.setLayout (new BorderLayout ());
		this.add (labelPanel, "North");
		this.add (centerPanel, "Center");
		this.add (buttonPanel,"South");
		this.setBounds (150, 150, 400, 200);
		this.setVisible (false);

	}

}



/* Här kommer våran main. Först skapar vi textarean som ska vara
 * tillgänglig för både font fönstret och meny fönstret. Sedan
 * kommer panelen som innehåller topmenyn och textaren. tillsist
 * placeras allt i ett ram och visas upp.
 */

class Notepad
{
	public static void main (String[] args)
	{

		JTextArea    textArea = new JTextArea ();
		FontWindow    fontWindow = new FontWindow (textArea);
		JMenuBar    topMenu = new TopMenu (textArea, fontWindow);


		JPanel    panel = new JPanel ();
		panel.setLayout (new BorderLayout ());
		panel.add (topMenu, "North");
		panel.add (textArea, "Center");


		JFrame    ram = new JFrame ("Notepad");
		ram.setBounds (100, 100, 800, 600);
		ram.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		ram.add (panel);
		ram.setVisible (true);
	}
}