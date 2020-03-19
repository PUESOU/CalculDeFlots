
import java.util.List;
import java.util.ListIterator;

public class CompleteurFlot implements MaxFlowSolver {
	public static int getCapaciteResiduelleMin(List<Arc> path) {
		int capaciteResiduelleMin = path.get(0).getCapaciteResiduelle();
		
		for(int i = 1; i < path.size(); ++i) {
			Arc arc = path.get(i);
			
			int capaciteResiduelle = arc.getCapaciteResiduelle();
			
			if(capaciteResiduelle < capaciteResiduelleMin) {
				capaciteResiduelleMin = capaciteResiduelle;
			}
		}
		
		return capaciteResiduelleMin;
	}
	
	public static void algoZero(ReseauTransport reseauTransport) {
		List<List<Arc>> paths = reseauTransport.getNoeud("E").getPaths();
		
		for(List<Arc> path : paths) {
			int capaciteResiduelleMin = getCapaciteResiduelleMin(path);
			
			for(Arc arc : path) {
				arc.flux += capaciteResiduelleMin;
			}
		}
	}
	
	public void solve(ReseauTransport reseauTransport) {
		List<List<Arc>> paths = reseauTransport.getNoeud("E").getPaths();
		
		for(ListIterator<List<Arc>> it = paths.listIterator(paths.size()); it.hasPrevious(); ) {
			List<Arc> path = it.previous();
			
			int capaciteResiduelleMin = getCapaciteResiduelleMin(path);
			
			for(Arc arc : path) {
				arc.flux += capaciteResiduelleMin;
			}
		}
	}
	
	public String toString() {
		return "Construction de flot complet";
	}
}
