import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

		average(durations);
		export(durations);
	}

	private long getDuration() {
		ReseauTransport copy = new ReseauTransport(this.reseau);

		long startTime = System.nanoTime();

		this.algo.solve(copy);

		long endTime = System.nanoTime();

		return (endTime - startTime) / 1000;
	}

	private void export(long[] durations) {
		StringBuffer buff = new StringBuffer("Temps Execution\n");
    	    	
		for(long duration: durations) {
			buff
				.append(duration)
				.append('\n');
		}
		
    	File outputFile = new File(this.timeRecords);
    	FileWriter out;
    	
    	try {
    		out = new FileWriter(outputFile);
    		
    		out.write(buff.toString());
    		out.close();
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
	}
	
	private void average(long[] durations) {
		long totalDuration = 0;
		for(long duration: durations) {
			System.out.println(this.timeRecords + " microsecond: " + duration);
			totalDuration += duration;
			System.out.println("cumulated total :" + totalDuration);
		}
		System.out.println("Sum:" + totalDuration);
		System.out.println("Average :" + (totalDuration/durations.length));
	}
	
}
