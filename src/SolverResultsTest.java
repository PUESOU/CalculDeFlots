
import java.util.List;
import java.util.ArrayList;

public class SolverResultsTest {
	public static void main(String[] args) {
		ReseauTransport reseauTransport = new ReseauTransport("coursFF.csv");
		List<MaxFlowSolver> solvers = new ArrayList<MaxFlowSolver>();
		
		solvers.add(new CompleteurFlot());
		solvers.add(new FordFulkerson());
		solvers.add(new FullFordFulkerson());
		solvers.add(new BusackerGowen());
		
		for(MaxFlowSolver solver : solvers) {
			ReseauTransport copy = new ReseauTransport(reseauTransport);
			
			solver.solve(copy);
			
			System.out.println("---- " + solver + " ----");
			System.out.println("Valeur du flot : " + copy.valeurFlot());
			System.out.println("Coût du flot : " + copy.coutFlot());
		}
	}
}
