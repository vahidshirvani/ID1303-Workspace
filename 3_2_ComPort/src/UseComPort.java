import javax.swing.*;		// JFrame, JPanel

class UseComPort
{
	public static void main (String[] arg)
	{
		MainPanel panel = new MainPanel ();

		JFrame frame = new JFrame ("ComPort");
		frame.setBounds (100, 100, 520, 230);
		frame.add (panel);
		frame.setVisible (true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}


