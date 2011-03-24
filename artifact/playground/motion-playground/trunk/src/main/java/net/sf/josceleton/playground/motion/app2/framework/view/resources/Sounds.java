package net.sf.josceleton.playground.motion.app2.framework.view.resources;


/**
 * Contains only one Clip instance, so a sound can only be played once, not multiple times in the same moment!
 */
public enum Sounds {
	BLIP("blip.wav"),
	BATTLE_MUSIC("battle_music.wav"),
	BATTLE_OVER("battle_over.wav"),
	PUNCH_HIT("punch_hit.wav"),
	PUNCH_MISS("punch_miss.wav")
	;
	
	private static final String AUDIO_RESOURCES_PATH = "audio/";
	
	final String fileName;
	
	private Sounds(String fileName) {
		this.fileName = AUDIO_RESOURCES_PATH + fileName;
	}
	
	public void start() {
		new Thread(new Runnable() {
			@Override public void run() {
				SoundInitializer.initialize(Sounds.this.fileName).start();
			}
		}).start();
	}
}