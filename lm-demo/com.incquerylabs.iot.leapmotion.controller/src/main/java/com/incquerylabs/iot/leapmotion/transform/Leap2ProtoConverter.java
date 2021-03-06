package com.incquerylabs.iot.leapmotion.transform;

import java.util.Iterator;

import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Arm;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Bone;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.BoneList;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Finger;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Finger.Type;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.FingerList;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Frame;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Gesture;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Gesture.State;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.GestureList;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Hand;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.HandList;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Matrix;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Pointable;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Pointable.Zone;
import com.leapmotion.leap.CircleGesture;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.PointableList;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Tool;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.ToolList;
import com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Vector;

public class Leap2ProtoConverter {

	/**
	 * Frame conversion
	 */
	public static Frame convert(com.leapmotion.leap.Frame frame) {
		return Frame.newBuilder().setId(frame.id()).setTimestamp(frame.timestamp()).setFingers(convert(frame.fingers()))
				.setGestures(convert(frame.gestures())).setHands(convert(frame.hands()))
				.setPointables(convert(frame.pointables())).setTools(convert(frame.tools())).setValid(frame.isValid())
				.setCurrentFramePerSecond(frame.currentFramesPerSecond()).build();
	}

	/**
	 * GestureList conversion
	 */
	protected static GestureList convert(com.leapmotion.leap.GestureList gestureList) {
		GestureList.Builder builder = GestureList.newBuilder();
		Iterator<com.leapmotion.leap.Gesture> gestureIt = gestureList.iterator();
		while (gestureIt.hasNext()) {
			builder.addElement(convert(gestureIt.next()));
		}
		return builder.setCount(gestureList.count()).setEmpty(gestureList.isEmpty()).build();
	}

	/**
	 * Gesture conversion
	 */
	protected static Gesture convert(com.leapmotion.leap.Gesture gesture) {
		Gesture.Builder builder = Gesture.newBuilder();
		builder.setId(gesture.id()).setDuration(gesture.duration()).setDurationSeconds(gesture.durationSeconds())
				.setFrame(gesture.frame().id()).setHands(convert(gesture.hands()))
				.setPointables(convert(gesture.pointables())).setValid(gesture.isValid())
				.setState(State.valueOf(gesture.state().ordinal()))
				.setType(com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Gesture.Type
				.valueOf(gesture.type().ordinal()));
		
		switch(gesture.type()) {
		
			case TYPE_CIRCLE:
				CircleGesture cg = new CircleGesture(gesture);
				builder.setNormal(convert(cg.normal()));
				break;
			default:
				break;
		}

		return builder.build();
	}

	/**
	 * HandList conversion.
	 */
	protected static HandList convert(com.leapmotion.leap.HandList handList) {
		HandList.Builder builder = HandList.newBuilder();
		builder.setFrontmost(convert(handList.frontmost())).setLeftmost(convert(handList.leftmost()))
				.setRightmost(convert(handList.rightmost())).setEmpty(handList.isEmpty()).setCount(handList.count());

		Iterator<com.leapmotion.leap.Hand> handIt = handList.iterator();
		while (handIt.hasNext()) {
			builder.addElement(convert(handIt.next()));
		}
		return builder.build();
	}

	/**
	 * Hand conversion
	 */
	protected static Hand convert(com.leapmotion.leap.Hand hand) {
		return Hand.newBuilder().setArm(convert(hand.arm())).setBasis(convert(hand.basis()))
				.setConfidence(hand.confidence()).setDirection(convert(hand.direction())).setFrame(hand.frame().id())
				.setFingers(convert(hand.fingers())).setPointables(convert(hand.pointables()))
				.setGrabStrength(hand.grabStrength()).setId(hand.id()).setLeft(hand.isLeft()).setRight(hand.isRight())
				.setValid(hand.isValid()).setPalmNormal(convert(hand.palmNormal()))
				.setPalmPosition(convert(hand.palmPosition())).setPalmVelocity(convert(hand.palmVelocity()))
				.setPalmWidth(hand.palmWidth()).setPinchStrength(hand.pinchStrength())
				.setSphereCenter(convert(hand.sphereCenter())).setSphereRadius(hand.sphereRadius())
				.setStabilizedPalmPosition(convert(hand.stabilizedPalmPosition())).setTimeVisible(hand.timeVisible())
				.setWristPosition(convert(hand.wristPosition())).build();
	}

	/**
	 * PointableList conversion.
	 */
	protected static PointableList convert(com.leapmotion.leap.PointableList pointableList) {
		return PointableList.newBuilder().setCount(pointableList.count()).setEmpty(pointableList.isEmpty())
				.setFrontmost(convert(pointableList.frontmost())).setLeftmost(convert(pointableList.leftmost()))
				.setRightmost(convert(pointableList.rightmost())).build();
	}

	/**
	 * Pointable conversion.
	 */
	protected static Pointable convert(com.leapmotion.leap.Pointable pointable) {
		Pointable converted = Pointable.newBuilder().setId(pointable.id()).setValid(pointable.isValid())
				.setDirection(convert(pointable.direction())).setLength(pointable.length()).setWidth(pointable.width())
				.setFrame(pointable.frame().id()).setHand(pointable.hand().id()).setExtended(pointable.isExtended())
				.setFinger(pointable.isFinger()).setTool(pointable.isTool())
				.setStabilizedTipPosition(convert(pointable.stabilizedTipPosition()))
				.setTipPosition(convert(pointable.tipPosition())).setTipVelocity(convert(pointable.tipVelocity()))
				.setTouchZone(Zone.valueOf(pointable.touchZone().ordinal())).setTouchDistance(pointable.touchDistance())
				.setTimeVisible(pointable.timeVisible()).build();
		return converted;
	}

	/**
	 * FingerList conversion.
	 */
	protected static FingerList convert(com.leapmotion.leap.FingerList fingerList) {
		FingerList.Builder builder = FingerList.newBuilder().setCount(fingerList.count()).setEmpty(fingerList.isEmpty())
				.setFrontmost(convert(fingerList.frontmost())).setLeftmost(convert(fingerList.leftmost()))
				.setRightmost(convert(fingerList.rightmost()));

		Iterator<com.leapmotion.leap.Finger> fingerIt = fingerList.iterator();
		while (fingerIt.hasNext()) {
			Finger finger = convert(fingerIt.next());
			builder.addElement(finger);
			categorizedFinger(builder, finger);
		}
		return builder.build();
	}

	/*
	 * Categorized finger by type and state
	 */
	protected static FingerList.Builder categorizedFinger(FingerList.Builder builder, Finger finger) {
		if (finger.getExtended())
			builder.addExtended(finger);
		switch (finger.getType()) {
		case TYPE_INDEX:
			builder.addIndexes(finger);
			break;
		case TYPE_MIDDLE:
			builder.addMiddles(finger);
			break;
		case TYPE_PINKY:
			builder.addPinkies(finger);
			break;
		case TYPE_RING:
			builder.addRings(finger);
			break;
		case TYPE_THUMB:
			builder.addThumbs(finger);
			break;
		}
		return builder;
	}

	/**
	 * Finger conversion method.
	 */
	protected static Finger convert(com.leapmotion.leap.Finger finger) {
		Finger.Builder builder = Finger.newBuilder().setId(finger.id()).setValid(finger.isValid())
				.setDirection(convert(finger.direction())).setLength(finger.length()).setWidth(finger.width())
				.setFrame(finger.frame().id()).setHand(finger.hand().id()).setExtended(finger.isExtended())
				.setFinger(finger.isFinger()).setTool(finger.isTool())
				.setStabilizedTipPosition(convert(finger.stabilizedTipPosition()))
				.setTipPosition(convert(finger.tipPosition())).setTipVelocity(convert(finger.tipVelocity()))
				.setTouchZone(Zone.valueOf(finger.touchZone().ordinal())).setTouchDistance(finger.touchDistance())
				.setTimeVisible(finger.timeVisible()).setBones(createBoneList(finger))
				.setType(Type.valueOf(finger.type().ordinal()));

		return builder.build();
	}

	/**
	 * ToolList conversion.
	 */
	protected static ToolList convert(com.leapmotion.leap.ToolList toolList) {
		return ToolList.newBuilder().setCount(toolList.count()).setEmpty(toolList.isEmpty())
				.setFrontmost(convert(toolList.frontmost())).setLeftmost(convert(toolList.leftmost()))
				.setRightmost(convert(toolList.rightmost())).build();
	}

	/**
	 * Pointable conversion.
	 */
	protected static Tool convert(com.leapmotion.leap.Tool tool) {
		return Tool.newBuilder().setId(tool.id()).setValid(tool.isValid()).setDirection(convert(tool.direction()))
				.setLength(tool.length()).setWidth(tool.width()).setFrame(tool.frame().id()).setHand(tool.hand().id())
				.setExtended(tool.isExtended()).setFinger(tool.isFinger()).setTool(tool.isTool())
				.setStabilizedTipPosition(convert(tool.stabilizedTipPosition()))
				.setTipPosition(convert(tool.tipPosition())).setTipVelocity(convert(tool.tipVelocity()))
				.setTouchZone(Zone.valueOf(tool.touchZone().ordinal())).setTouchDistance(tool.touchDistance())
				.setTimeVisible(tool.timeVisible()).build();
	}

	/**
	 * Arm conversion.
	 */
	protected static Arm convert(com.leapmotion.leap.Arm arm) {
		return Arm.newBuilder().setBasis(convert(arm.basis())).setCenter(convert(arm.center()))
				.setDirection(convert(arm.direction())).setElbowPosition(convert(arm.elbowPosition()))
				.setValid(arm.isValid()).setWidth(arm.width()).setWristPosition(convert(arm.wristPosition())).build();
	}

	/**
	 * Bone conversion.
	 */
	protected static Bone convert(com.leapmotion.leap.Bone bone) {
		return Bone.newBuilder().setBasis(convert(bone.basis())).setCenter(convert(bone.center()))
				.setDirection(convert(bone.direction())).setValid(bone.isValid()).setLength(bone.length())
				.setNextJoint(convert(bone.nextJoint())).setPrevJoint(convert(bone.prevJoint()))
				.setType(
						com.incquerylabs.iot.leapmotion.proto.LeapMotionProtos.Bone.Type.valueOf(bone.type().ordinal()))
				.build();
	}

	/**
	 * BoneList conversion.
	 */
	protected static BoneList createBoneList(com.leapmotion.leap.Finger finger) {
		Bone metacarpal = convert(finger.bone(com.leapmotion.leap.Bone.Type.TYPE_METACARPAL));
		Bone proximal = convert(finger.bone(com.leapmotion.leap.Bone.Type.TYPE_PROXIMAL));
		Bone intermediate = convert(finger.bone(com.leapmotion.leap.Bone.Type.TYPE_INTERMEDIATE));
		Bone distal = convert(finger.bone(com.leapmotion.leap.Bone.Type.TYPE_DISTAL));
		return BoneList.newBuilder().addElement(distal).addElement(intermediate).addElement(proximal)
				.addElement(metacarpal).setCount(4).setEmpty(false).setDistal(distal).setIntermediate(intermediate)
				.setProximal(proximal).setMetacarpal(metacarpal).build();
	}

	/**
	 * Matrix conversion.
	 */
	protected static Matrix convert(com.leapmotion.leap.Matrix matrix) {
		Matrix.Builder mbuilder = copyAttributes(Matrix.newBuilder(), matrix);
		Matrix.Builder ibuilder = copyAttributes(Matrix.newBuilder(), matrix);
		mbuilder.setRigidInverse(ibuilder);
		ibuilder.setRigidInverse(mbuilder);

		return mbuilder.build();
	}

	protected static Matrix.Builder copyAttributes(Matrix.Builder builder, com.leapmotion.leap.Matrix matrix) {
		return builder.setXBasis(convert(matrix.getXBasis())).setYBasis(convert(matrix.getYBasis()))
				.setZBasis(convert(matrix.getZBasis())).setOrigin(convert(matrix.getOrigin()));
	}

	/**
	 * Vector conversion.
	 */
	protected static Vector convert(com.leapmotion.leap.Vector vector) {
		Vector.Builder vBuilder = copyAttributes(Vector.newBuilder(), vector);
		Vector.Builder nvBuilder = copyAttributes(Vector.newBuilder(), vector.normalized());
		Vector.Builder voBuilder = copyAttributes(Vector.newBuilder(), vector.opposite());
		Vector.Builder nvoBuilder = copyAttributes(Vector.newBuilder(), vector.normalized().opposite());

		buildVectors(vBuilder, nvBuilder, voBuilder);
		buildVectors(nvBuilder, nvBuilder, nvoBuilder);
		buildVectors(voBuilder, nvoBuilder, vBuilder);

		return vBuilder.build();
	}

	protected static Vector.Builder copyAttributes(Vector.Builder builder, com.leapmotion.leap.Vector vector) {
		return builder.setX(vector.getX()).setY(vector.getY()).setZ(vector.getZ()).setValid(vector.isValid())
				.setMagnitude(vector.magnitude()).setMagnitudeSquared(vector.magnitudeSquared())
				.setPitch(vector.pitch()).setRoll(vector.roll()).setYaw(vector.yaw());
	}

	protected static void buildVectors(Vector.Builder vB, Vector.Builder nvB, Vector.Builder ovB) {
		vB.setNormalized(nvB);
		vB.setOpposite(ovB);
	}
}
