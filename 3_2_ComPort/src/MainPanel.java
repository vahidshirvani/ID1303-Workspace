// MainPanel.java
// (C)2011 Vahid Shirvani


import gnu.io.*;
import java.io.*;			// File
import javax.swing.*;		// JFrame, JPanel
import javax.swing.border.*;// TitledBorder
import java.awt.event.*;	// ActionListener, ActionEvent
import java.awt.*;			// BorderLayout


class MainPanel extends JPanel
{

	private static final long serialVersionUID = 6056081986009426322L;
	File file;
	JTextField fileTextField;
	JLabel readyLabel;

	StringBuffer stringB = new StringBuffer ();
	ConnectToCom ctc = null;

	JButton browseButton;
	JButton connectButton;
	JButton sendButton;

	String portName = "COM1";
	int[] serielParam = new int[5];

	JComboBox port;
	JComboBox baud;
	JComboBox data;
	JComboBox stop;
	JComboBox parity;
	JComboBox flow;



	MainPanel ()
	{

		this.setLayout (new BorderLayout ());

		fileTextField = new JTextField ("Choose your file!", 32);
		fileTextField.setEnabled (false);
		browseButton = new JButton ("Browse");
		connectButton = new JButton ("Connect");
		sendButton = new JButton ("send");
		sendButton.setEnabled (false);
		readyLabel = new JLabel ("Not connected!");

		// All the buttons share the same listener. The source will be 
		//identified and respective code will be executed.
		ActionListener buttonListener = new ActionListener ()
			{
				public void actionPerformed (ActionEvent event)
				{
					Object source = event.getSource ();
					if (source == browseButton)
					{
						JFileChooser fileChooser = new JFileChooser ();
						int haveBeenChosed = fileChooser.showOpenDialog (null);
						file = fileChooser.getSelectedFile ();
						fileTextField.setText(file.getAbsolutePath ());
						try
						{
							BufferedReader  in = new BufferedReader (new FileReader (file));
							String line = in.readLine ();
							while (line != null)
							{
								stringB.append (line + "\n\r");
								line = in.readLine ();
							}
						}
						catch (IOException ex)
						{
							System.out.println (ex.getMessage ());
						}
					}
					else if (source == connectButton)
					{
						ctc = new ConnectToCom (portName, serielParam);
						if (ctc.isConnected ())
						{
							sendButton.setEnabled (true);
							readyLabel.setText ("Connected!");
						}

					}
					else if (source == sendButton)
					{
						if (ctc.write (stringB.toString ()))
							readyLabel.setText ("Have Sent");
					}

				}
			};

		browseButton.addActionListener (buttonListener);
		connectButton.addActionListener (buttonListener);
		sendButton.addActionListener (buttonListener);

		// add the north part
		JPanel browsePanel = new JPanel ();
		Border browseBorder = new TitledBorder (	new LineBorder (Color.LIGHT_GRAY, 1),
													"Step 1: File", TitledBorder.LEFT, TitledBorder.TOP);
		browsePanel.setBorder (browseBorder);
		browsePanel.add(fileTextField);
		browsePanel.add(browseButton);
		this.add (browsePanel, BorderLayout.NORTH);

		// add the center part
		JPanel connectPanel = new JPanel ();
		Border connectBorder = new TitledBorder (	new LineBorder (Color.LIGHT_GRAY, 1),
													"Step 2: Configuration", TitledBorder.LEFT, TitledBorder.TOP);
		connectPanel.setBorder (connectBorder);
		this.add (connectPanel, BorderLayout.CENTER);

		// add the south part
		JPanel sendPanel = new JPanel ();
		Border sendBorder = new TitledBorder (	new LineBorder (Color.LIGHT_GRAY, 1),
												"Step 3: Ready", TitledBorder.LEFT, TitledBorder.TOP);
		sendPanel.setBorder (sendBorder);
				sendPanel.add(readyLabel);
		sendPanel.add(sendButton);
		this.add (sendPanel, BorderLayout.SOUTH);




		final String[] portNames = {	"COM1", "COM2", "COM3", "COM4", "COM5",
										"COM6", "COM7", "COM8", "COM9", "COM10",
										"COM11", "COM12", "COM13", "COM14"};
		port = new JComboBox (portNames);
		port.setSelectedIndex (7);
		portName = portNames[port.getSelectedIndex ()];

		final String[] buadNames = {	"115200", "57600", "38400", "19200",
										"9600", "4800", "2400", "1200", "600"};
		final int[] buadNumbers = {		115200, 57600, 38400, 19200,
										9600, 4800, 2400, 1200, 600};
		baud = new JComboBox (buadNames);
		baud.setSelectedIndex (4);
		serielParam[0] = buadNumbers[baud.getSelectedIndex ()];

		final String[] dataNames = {"5", "6", "7", "8"};
		final int[] dataNumbers = {	SerialPort.DATABITS_5,
									SerialPort.DATABITS_6,
									SerialPort.DATABITS_7,
									SerialPort.DATABITS_8};
		data = new JComboBox (dataNames);
		data.setSelectedIndex (3);
		serielParam[1] = dataNumbers[data.getSelectedIndex ()];

		final String[] stopNames = {"1", "1.5", "2"};
		final int[] stopNumbers = {	SerialPort.STOPBITS_1,
									SerialPort.STOPBITS_1_5,
									SerialPort.STOPBITS_2};
		stop = new JComboBox (stopNames);
		stop.setSelectedIndex (0);
		serielParam[2] = stopNumbers[stop.getSelectedIndex ()];

		final String[] parityNames = {"Even", "Mark", "None", "Odd", "Space"};
		final int[] parityNumbers = {	SerialPort.PARITY_EVEN,
										SerialPort.PARITY_MARK,
										SerialPort.PARITY_NONE,
										SerialPort.PARITY_ODD,
										SerialPort.PARITY_SPACE};
		parity = new JComboBox (parityNames);
		parity.setSelectedIndex (2);
		serielParam[3] = parityNumbers[parity.getSelectedIndex ()];

		final String[] flowNames = {	"None", "XON/XOFF", "RTS/CTS"}; // , "DSR/DTR"
		final int[] flowNumbers = {		SerialPort.FLOWCONTROL_NONE,
										SerialPort.FLOWCONTROL_XONXOFF_IN |
										SerialPort.FLOWCONTROL_XONXOFF_OUT,
										SerialPort.FLOWCONTROL_RTSCTS_IN |
										SerialPort.FLOWCONTROL_RTSCTS_OUT};
		flow = new JComboBox (flowNames);
		flow.setSelectedIndex (1);
		serielParam[4] = flowNumbers[flow.getSelectedIndex ()];

		// All combos share same listener. When any combo changes, 
		// data from all will be loaded into serial parameter.
		ActionListener comboListener = new ActionListener ()
				{
					public void actionPerformed (ActionEvent event)
					{
						portName = portNames[port.getSelectedIndex ()];
						serielParam[0] = buadNumbers[baud.getSelectedIndex ()];
						serielParam[1] = dataNumbers[data.getSelectedIndex ()];
						serielParam[2] = stopNumbers[stop.getSelectedIndex ()];
						serielParam[3] = parityNumbers[parity.getSelectedIndex ()];
						serielParam[4] = flowNumbers[flow.getSelectedIndex ()];
					}
				};

		port.addActionListener (comboListener);
		baud.addActionListener (comboListener);
		data.addActionListener (comboListener);
		stop.addActionListener (comboListener);
		parity.addActionListener (comboListener);
		flow.addActionListener (comboListener);


		connectPanel.add (port);
		connectPanel.add (baud);
		connectPanel.add (data);
		connectPanel.add (stop);
		connectPanel.add (parity);
		connectPanel.add (flow);
		connectPanel.add(connectButton);
	}
}
