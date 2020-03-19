
class FullFordFulkerson implements MaxFlowSolver {
	private MaxFlowSolver completeurFlot, fordFulkerson;
	
	public FullFordFulkerson() {
		completeurFlot = new CompleteurFlot();
		fordFulkerson = new FordFulkerson();
	}
	
	public void solve(ReseauTransport reseauTransport) {
		completeurFlot.solve(reseauTransport);
		fordFulkerson.solve(reseauTransport);
	}
	
	public String toString() {
		return "Full Ford-Fulkerson";
	}
}
