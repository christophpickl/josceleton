Readme
=============

This prototype reroutes captured motion data to MIDI.

It is fully configurable via so-called _MIDI Mappings_:

	<JointPart>, <XyzDirection>, <MidiChannel>, <ControllerNumber>

The following example registers the x- and y-movements of the left hand
to MIDI channel 1 and controller number 1 for x, and 2 for y:

	# comments and empty lines will be omitted
	l_hand, X, 0, 1
	l_hand, Y, 0, 2


List of available joint parts:

* head
* neck
* torso
* l_hand, r_hand
* l_shoulder, r_shoulder
* l_elbow, r_elbow
* l_hand, r_hand
* l_hip, r_hip
* l_knee, r_knee
* l_ankle, r_ankle
* l_foot, r_foot
