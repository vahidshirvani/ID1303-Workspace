import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;


public class Player{

	File file;
	AudioInputStream audioInputStream;
	SourceDataLine line;
	Runnable streamToLine;
	boolean stopFlag = false;
	
	public Player(){
	
		file = new File("sound.wav");
		try{
			audioInputStream = AudioSystem.getAudioInputStream(file);
		}catch(Exception ex){
			// IOException
			// UnsupportedAudioFileException
			ex.printStackTrace();
		}
		
		// AudioInputStream will fetch the format of the file
		// which is needed to ask OS to open such a line
		// with those specifications.
		AudioFormat audioFormat = audioInputStream.getFormat();
		
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		
		try{
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(audioFormat);
		}catch(Exception ex){
			// OR LineUnavailableException
			ex.printStackTrace();
		}
		
		streamToLine = new Runnable(){

			// Line is finally prepared. Only data needs to be wrote to the
			// line. Data will be streamed from the file into the buffer until
			// we reach the end of file. And buffer will be 
			// written on the line.
			public void run() {
				int bytesRead = 0;
				byte[] buffer = new byte[128000]; // 128KB
				while(bytesRead != -1 && !stopFlag){
					try{
						bytesRead = audioInputStream.read(buffer, 0, buffer.length);
					}catch(IOException ex){
						ex.printStackTrace();
					}
					if(bytesRead >= 0){
						line.write(buffer, 0, bytesRead); // Even returns how much have been written
					}
				}
				stopPlay();
			}
		};
	}
	
	public void startplay(){
		// line has been opened before which means data can be recieved
		// But it still need to be started so that it will be passed into
		// sound card.
		line.start();
		new Thread(streamToLine).start();
	}
	
	public void stopPlay(){
		// Drain will wait until all data has been written.
		// Then it's time to close the line.
		stopFlag = true;
		line.drain();
		line.close();
	}
}
