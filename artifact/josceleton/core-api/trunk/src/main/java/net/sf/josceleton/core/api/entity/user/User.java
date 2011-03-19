package net.sf.josceleton.core.api.entity.user;

/**
 * This value object represents a registered user by osceleton.
 * 
 * The reason an user got two IDs is that osceleton reuses already used numbers for the ID assignment.
 * Therefore Josceleton adds its own real unique ID.
 * 
 * @since 0.1
 */
public interface User {
	
	/**
	 * @return the user's unique ID within a single connection.
	 * @since 0.1
	 */
	int getUniqueId();
	
	/**
	 * @return the user's (not necessarily) unique ID provided by OSCeleton.
	 * @since 0.1
	 */
	int getOsceletonId();
	
}
