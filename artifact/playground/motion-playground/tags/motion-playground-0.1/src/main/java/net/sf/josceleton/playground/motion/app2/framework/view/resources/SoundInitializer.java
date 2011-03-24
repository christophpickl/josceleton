package net.sf.josceleton.playground.motion.app2.framework.view.resources;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

class SoundInitializer {
//	private static final Log LOG = LogFactory.getLog(SoundInitializer.class);
	
	// play a long file: http://www.anyexample.com/programming/java/java_play_wav_sound_file.xml
	static Clip initialize(final String audioFilePath) {
		final URL url = SoundInitializer.class.getClassLoader().getResource(audioFilePath);
		if(url == null) {
			throw new RuntimeException("unable to find: " + audioFilePath);
		}
		
		final AudioInputStream audioIn;
		try {
			audioIn = AudioSystem.getAudioInputStream(url);
		} catch (UnsupportedAudioFileException e) {
			throw new RuntimeException("Could not open sound (unsupported audio) by URL: " + url, e);
		} catch (IOException e) {
			throw new RuntimeException("Could not open sound (I/O exception) stream by URL: " + url, e);
		}
		final Clip clip;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			throw new RuntimeException("Could not get clip from audio by URL: " + url, e);
		}
		try {
			clip.open(audioIn);
		} catch (LineUnavailableException e) {
			throw new RuntimeException("Could not open audio clip (line unavailable) by URL: " + url, e);
		} catch (IOException e) {
			throw new RuntimeException("Could not open audio clip (I/O exception) by URL: " + url, e);
		}
		return clip;
	}
	/*
	public static void main(String[] args) throws Exception {
		InputStream in = new FileInputStream("src/main/resources/audio/punch_hit.wav");
		final AudioStream as1 = new AudioStream(in);         
		AudioData data = as1.getData();
		
		
		JFrame f = new JFrame();
		JButton b = new JButton("play");
		b.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent actionevent) {
//				LOG.trace("start PUNCH HIT");
//				Sound.PUNCH_HIT.start();
//				AudioPlayer.player.start(as1);
//				AudioPlayer.player.start(cas);
			}
		});
		JButton b2 = new JButton("stop");
		b2.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent actionevent) {
//				LOG.trace("STOP");
//				Sound.PUNCH_HIT.getClip().stop();
//				AudioPlayer.player.stop(as1);
			}
		});
		JPanel p = new JPanel();
		p.add(b);
		p.add(b2);
		f.getContentPane().add(p);
		f.pack();
		f.setVisible(true);
	}
	*/
}

//public class SoundLineTest {
//public static void main(String[] args) {
//   SourceDataLine soundLine = null;
//   int BUFFER_SIZE = 64*1024;  // 64 KB
//
//   // Set up an audio input stream piped from the sound file.
//   try {
//      File soundFile = new File("gameover.wav");
//      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
//      AudioFormat audioFormat = audioInputStream.getFormat();
//      DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
//      soundLine = (SourceDataLine) AudioSystem.getLine(info);
//      soundLine.open(audioFormat);
//      soundLine.start();
//      int nBytesRead = 0;
//      byte[] sampledData = new byte[BUFFER_SIZE];
//      while (nBytesRead != -1) {
//         nBytesRead = audioInputStream.read(sampledData, 0, sampledData.length);
//         if (nBytesRead >= 0) {
//            // Writes audio data to the mixer via this source data line.
//            soundLine.write(sampledData, 0, nBytesRead);
//         }
//      }
//   } catch (UnsupportedAudioFileException ex) {
//      ex.printStackTrace();
//   } catch (IOException ex) {
//      ex.printStackTrace();
//   } catch (LineUnavailableException ex) {
//      ex.printStackTrace();
//   } finally {
//      soundLine.drain();
//      soundLine.close();
//   }
//}
//}
