package net.sf.josceleton.motion.impl.gesture;

import java.util.Collection;
import java.util.LinkedList;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.motion.api.gesture.GestureFactory;
import net.sf.josceleton.motion.api.gesture.HitWallGesture;

public class GestureFactoryImpl implements GestureFactory {

	@Override
	public HitWallGesture newHitWall() {
		final Collection<Joint> foo = new LinkedList<Joint>();
		foo.add(Joints.HAND().LEFT());
		return new HitWallGestureImpl(foo);
	}

}
