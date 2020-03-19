
public class OtherTest {

	public void createRecords(String fileName, int count) {
		/*** {E, 1, 2, 3, 4, 5, a, b, c, d, e, S} */
		ReseauTransport reseauTransport1 = new ReseauTransport(5, 5);
		ReseauTransport reseauTransport2 = new ReseauTransport(reseauTransport1);

		MaxFlowSolver algo1 = new FordFulkerson();
		MaxFlowSolver algo2 = new BusackerGowen();

		Thread thread1 = new Thread(new ExecTimeTest(count, algo1, fileName + "-Ford.csv", reseauTransport1));
		Thread thread2 = new Thread(new ExecTimeTest(count, algo2, fileName + "-Busacker.csv", reseauTransport2));

		thread1.start();
		thread2.start();

	}

	public static void main(String[] args) {
		int count = 100;
		OtherTest test = new OtherTest();
		test.createRecords("Comparaison" + count, count);
	}

}
