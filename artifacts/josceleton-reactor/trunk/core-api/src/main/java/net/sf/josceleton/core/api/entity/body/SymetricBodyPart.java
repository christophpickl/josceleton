package net.sf.josceleton.core.api.entity.body;

interface SymetricBodyPart<BP, LBP extends LeftBodyPart<BP>, RBP extends RightBodyPart<BP>> {

	/** This is part of the API as it will be visibile to user. */
	LBP LEFT();
	
	/** This is part of the API as it will be visibile to user. */
	RBP RIGHT();
	
}
