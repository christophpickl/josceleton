package net.sf.josceleton.core.api.entity;

/**
 * Represents OSC addresses used for user related messages sent by OSCeleton.
 * 
 * @since 0.1
 */
public enum UserState {

	/**
	 * Represents the message sent for address <code>/new_user</code>.
	 * 
	 * Indicates OSCeleton is currently waiting to identify skeleton by detecting a PSI-position.
	 * 
	 * @since 0.1
	 */
	WAITING,

	/**
	 * Represents the message sent for address <code>/new_skel</code>.
	 * 
	 * Indicating that skeleton data will now be processed.
	 * 
	 * @since 0.1
	 */
	PROCESSING,

	/**
	 * Represents the message sent for address <code>/lost_user</code>.
	 * 
	 * Indicates OSCeleton lost track of a user.
	 * 
	 * @since 0.1
	 */
	DEAD;
	
}
