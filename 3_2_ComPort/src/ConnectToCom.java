import gnu.io.*;
import java.io.*;			// File

class ConnectToCom
{
	boolean status = false;

	OutputStream serialOutputStream;
	//InputStream serialInputStream;

	ConnectToCom (String ComNumber, int[] portParam )
	{
		int baudrate = portParam[0];
		int dataBits = portParam[1];
		int stopBits = portParam[2];
		int parity = portParam[3];
		int flowControl = portParam[4];
		
		// In order to take over the port it has to be checked if it's already
		// in use. If not the parameters will be set.
		try
		{
			CommPortIdentifier portIdentifier = 
				CommPortIdentifier.getPortIdentifier(ComNumber);
			if (portIdentifier.isCurrentlyOwned())
			{
				System.out.println("Port in use!");
			}
			else
			{
				System.out.println(portIdentifier.getName());

				SerialPort serialPort = (SerialPort) portIdentifier.open("UseComPort", 2000);
				serialPort.setSerialPortParams(baudrate, dataBits, stopBits, parity);
				serialPort.setFlowControlMode (flowControl);

				int b = serialPort.getBaudRate();
				System.out.println(Integer.toString(b));

				serialOutputStream = serialPort.getOutputStream();
				//serialInputStream = serialPort.getInputStream();
				//serialInputStream.close();
				
				status = true;
			}
		}
		catch (Exception ex)
		{
			System.out.println (ex.getMessage ());
		}

	}

	public boolean isConnected ()
	{
		return status;
	}

	public boolean write (String streamThisOut)
	{
		boolean isSuccess = false;

		try
		{
			serialOutputStream.write(streamThisOut.getBytes());
			serialOutputStream.flush();
			serialOutputStream.close();
			isSuccess = true;
		}
		catch (IOException ex)
		{
			System.out.println (ex.getMessage ());
		}

		return isSuccess;
	}
}
