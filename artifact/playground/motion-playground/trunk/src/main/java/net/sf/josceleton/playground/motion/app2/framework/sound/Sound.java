package net.sf.josceleton.playground.motion.app2.framework.sound;


/**
 * Contains only one Clip instance, so a sound can only be played once, not multiple times in the same moment!
 */
public enum Sound {
	BLIP("blip.wav"),
	BATTLE_MUSIC("battle_music.wav"),
	BATTLE_OVER("battle_over.wav"),
	PUNCH_HIT("punch_hit.wav"),
	PUNCH_MISS("punch_miss.wav")
	;
	
	private static final String AUDIO_RESOURCES_PATH = "audio/";
	
	private final String fileName;
	
	private Sound(String fileName) {
		this.fileName = AUDIO_RESOURCES_PATH + fileName;
	}
	
	public void start() {
		new Thread(new Runnable() {
			@Override public void run() {
				SoundInitializer.initialize(Sound.this.fileName).start();
			}
		}).start();
	}
}