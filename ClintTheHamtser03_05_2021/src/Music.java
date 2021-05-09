import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {
	Clip clip;
	AudioInputStream audioInputStream;
	String filePath;
	boolean loop;
	
	public Music(String filePath, boolean loop){
		this.filePath = filePath;
		this.loop = loop;
	}
	
	public void play() {
        setupAudioStream();			//reset AudioStream
		clip.start();
	}
	
	public void stop() {
		try {
			clip.stop();
			clip.close();
		}
		catch(NullPointerException ex) {
			return;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setupAudioStream() {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
			clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        if (loop) {
	        	clip.loop(Clip.LOOP_CONTINUOUSLY);
	        }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}