package it.taa.gallmetzer.maa.utils;

public class Challenge {

	private final long ID;
	private boolean passed;

	public Challenge(long ID) {
		this.ID = ID;
		this.passed = false;
	}

	public long getID() {
		return this.ID;
	}

	public void setBool(boolean b) {
		this.passed = b;

	}

	public boolean hasPassed() {
		return this.passed;
	}

}
