import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;


public class Recorder{

	AudioInputStream ais;
	AudioFileFormat.Type type;
	File file;
	TargetDataLine dataLine;
	Runnable audioSys;
	
	public Recorder() {

		AudioFormat audioFormat = new AudioFormat( 
				AudioFormat.Encoding.PCM_SIGNED, // Encoding
				44100.0f, 	// SampleRate
				16, 		// sampleSizeInBits
				2, 			// Channels (Stereo)
				4, 			// frameSize
				44100.0f, 	// frameRate
				false);		// bigEndian (UNIX)
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
		
		/* Now, we are trying to get a TargetDataLine. The
		   TargetDataLine is used later to read audio data from it.
		   If requesting the line was successful, we are opening
		   it (important!).
		*/
		
		// Trying to request data line from OS 
		// if successful then opening it. 
		try{
			dataLine = (TargetDataLine) AudioSystem.getLine(info);
			dataLine.open(audioFormat);
		}catch(LineUnavailableException ex){
			System.out.println("unable to get a recording line");
			ex.printStackTrace();
			System.exit(1);
		}
		
		ais = new AudioInputStream(dataLine);
		type = AudioFileFormat.Type.WAVE;
		file = new File("sound.wav");
		
		audioSys = new Runnable(){

			public void run() {
				try{
					// This method acts like a buffer. It writes the data
					// streamed in to file. If no data comes in then the
					// method automatically returns.
					AudioSystem.write(ais, type, file);
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}
		};
	}
	
	public void startRecording(){
		// First starting the data line then so that
		// audio comes in then the thread starts.
		dataLine.start();
		new Thread(audioSys).start();
	}
	
	public void stopRecording(){
		// In order to close the audio file we only need
		// to stop the data streaming line. The file will
		// automatically be closed by AudioSystem
		dataLine.stop();
		dataLine.close();
	}
}
