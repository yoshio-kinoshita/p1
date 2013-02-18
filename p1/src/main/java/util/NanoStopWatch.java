package util;

public class NanoStopWatch {

	long startTime;
	long endTime;

	public void start() {
		this.startTime = java.lang.System.nanoTime();
	}

	public void stop() {
		this.endTime = java.lang.System.nanoTime();
	}

	public long getTime() {
		return endTime - startTime;
	}

}
