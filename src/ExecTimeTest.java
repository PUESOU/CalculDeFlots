
public class ExecTimeTest implements Runnable {

	// The Algorithm the thread will be running
	private MaxFlowSolver algo;

	// File where execution times will be recorded
	private String timeRecords;

	// The Reseau we apply the algo on
	private ReseauTransport reseau;

	// How many time we want to calcul time the algo take
	private int count;

	public ExecTimeTest(int count, MaxFlowSolver algo, String timeRecords, ReseauTransport reseau) {
		this.count = count;
		this.algo = algo;
		this.timeRecords = timeRecords;
		this.reseau = reseau;
	}

	@Override
	public void run() {
		long[] durations = new long[this.count];

		for (int i = 0; i < this.count; i++) {
			durations[i] = getDuration();
		}

		for (long duration : durations) {
			System.out.println(this.timeRecords + " microsecond: " + duration);
		}
	}

	private long getDuration() {
		ReseauTransport copy = new ReseauTransport(this.reseau);

		long startTime = System.nanoTime();

		this.algo.solve(copy);

		long endTime = System.nanoTime();

		return (endTime - startTime) / 1000;
	}

}
