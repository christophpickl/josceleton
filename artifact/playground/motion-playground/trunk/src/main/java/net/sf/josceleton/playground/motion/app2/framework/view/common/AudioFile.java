package net.sf.josceleton.playground.motion.app2.framework.view.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

@SuppressWarnings("restriction")
public final class AudioFile {
	
	public static final AudioFile S1 = new AudioFile("src/main/resources/audio/s1.wav");
	public static final AudioFile S2 = new AudioFile("src/main/resources/audio/s2.wav");
	
	private final AudioStream audio;
	
	private AudioFile(final String path) {
		final InputStream in;
		try {
			in = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("audio by path ["+path+"]", e);
		}
		try {
			this.audio = new AudioStream(in);
		} catch (IOException e) {
			throw new RuntimeException("audio by path ["+path+"]", e);
		}         
	}

	public void start() {
		AudioPlayer.player.start(this.audio);
	}

	public void stop() {
		AudioPlayer.player.stop(this.audio);
	}
}
