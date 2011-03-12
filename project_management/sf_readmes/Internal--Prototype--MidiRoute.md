Readme
=============

This prototype reroutes captured motion data as MIDI messages.

It is fully configurable via a simple _configuration syntax_:

	<JointPart>, <XyzDirection>, <MidiChannel>, <ControllerNumber>

The following sample registers the x and y-movements of the left hand,
to MIDI channel 1 and controller number 1 for x, and 2 for y:

	# comments and empty lines are possible
	l_hand, X, 0, 1
	l_hand, Y, 0, 2

List of available joint parts:  
* head
* neck
* torso
* x_hand
* x_shoulder
* x_elbow
* x_hand
* x_hip
* x_knee
* x_ankle
* x_foot