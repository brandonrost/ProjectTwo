package com.revature.exceptions;

public class MusicNotAddedException extends Exception {

	/**
	 * This checked exception occurs when a Music object can not be added to the persistent state on a MusicDAO object. 
	 */
	private static final long serialVersionUID = -6359742109297285412L;

	public MusicNotAddedException() {
		super();
	}

	public MusicNotAddedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public MusicNotAddedException(String arg0) {
		super(arg0);
	}

	public MusicNotAddedException(Throwable arg0) {
		super(arg0);
	}

}
