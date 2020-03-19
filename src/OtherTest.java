
public class OtherTest {
	
	public static final int COUNT = 100;
	
    public static final String CSVFILENAME = "coursFF";
    
    public void createRecords(String fileName, int count) {
        /*** {E, 1, 2, 3, 4, 5, a, b, c, d, e, S} */
        ReseauTransport reseauTransport1 = new ReseauTransport(fileName + ".csv");
        ReseauTransport reseauTransport2 = new ReseauTransport(fileName + ".csv");
        
        MaxFlowSolver algo1 = new FullFordFulkerson();
        MaxFlowSolver algo2 = new BusackerGowen();
        
        Thread thread1 = new Thread(new ExecTimeTest(count, algo1, fileName + "-FullFord.csv", reseauTransport1));
        Thread thread2 = new Thread(new ExecTimeTest(count, algo2, fileName + "-Busacker.csv", reseauTransport2));
        
        thread1.start();
        thread2.start();
        
    }
    
    public static void main(String[] args) {
        OtherTest test = new OtherTest();
        test.createRecords(OtherTest.CSVFILENAME, OtherTest.COUNT);
    }
    
}
