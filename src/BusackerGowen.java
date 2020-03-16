
import java.util.ArrayList;
import java.util.List;

public class BusackerGowen implements MaxFlowSolver {
	
	/**
	 * Crée le graphe d'écart associé GeF du réseau de transport donné.
	 * @param reseauTransport le graphe à partir duquel créer le graphe d'écart.
	 * @return le graphe d'écart associé.
	 */
	
	public static ReseauTransport gef(ReseauTransport reseauTransport) {
		ReseauTransport gef = new ReseauTransport();
		
		for(Noeud noeud : reseauTransport.getNoeuds()) {
			gef.addNoeud(noeud.getId());
		}
		
		for(Arc arc : reseauTransport.getArcs()) {
			if(!arc.estSature()) {
				gef.addArc(arc.getSourceId(), arc.getCibleId());
				
				Arc u = gef.findArc(arc.getSourceId(), arc.getCibleId());
				u.flux = arc.getCapacite() - arc.flux;
				u.setPoids(arc.getPoids());
			}
			
			if(arc.flux > 0) {
				gef.addArc(arc.getCibleId(), arc.getSourceId());
				
				Arc u = gef.findArc(arc.getCibleId(), arc.getSourceId());
				u.flux = arc.flux - arc.getBorne();
				u.setPoids(-arc.getPoids());
			}
		}
		
		return gef;
	}
	
	public static List<Arc> minimalCostGefPath(ReseauTransport reseauTransport) {
		ReseauTransport gef = gef(reseauTransport);
		List<List<Arc>> paths = gef.getNoeud("E").getPaths();
		List<List<Arc>> pathsToS = new ArrayList<List<Arc>>();

		for(List<Arc> path : paths) {
			List<Arc> constructedPath = new ArrayList<Arc>();
			
			for(Arc arc : path) {
				constructedPath.add(arc);
				
				if(arc.getCibleId().equals("S")) {
					pathsToS.add(constructedPath);
					
					break;
				}
			}
		}
		
		// System.out.println(Flotests.pathsToString(pathsToS));
		
		return minimalCostPath(pathsToS);
	}
	
	public static List<Arc> minimalCostPath(List<List<Arc>> paths) {
		List<Arc> shortestPath = null;
		Integer minimalCost = null;
		
		for(List<Arc> path : paths) {
			int pathCost = 0;
			
			for(Arc arc : path) {
				pathCost += arc.getPoids();
			}
			
			// System.out.println(pathCost);
			
			if(minimalCost == null || pathCost < minimalCost) {
				minimalCost = pathCost;
				shortestPath = path;
			}
		}
		
		return shortestPath;
	}
	
	public static int pathCost(List<Arc> path) {
		int pathCost = 0;
		
		for(Arc arc : path) {
			pathCost += arc.getPoids();
		}
		
		return pathCost;
	}
	
	public void solve(ReseauTransport reseauTransport) {
		int V = 0, C = 0;
		
		List<Arc> minimalCostPath;
		
		while((minimalCostPath = minimalCostGefPath(reseauTransport)) != null) {
			int m = pathCost(minimalCostPath);
			
			Integer delta = null;
			
			for(Arc arc : minimalCostPath) {
				Arc u = reseauTransport.getNoeud(arc.getSourceId()).findArc(arc.getCibleId());
				// if(u == null) {u = reseauTransport.getNoeud(arc.getCibleId()).findArc(arc.getSourceId());}
				
				if(u != null) {
					int d = u.getCapacite() - u.flux;
					
					if(delta == null || d < delta) {
						delta = d;
					}
				}
			}
			
			for(Arc arc : minimalCostPath) {
				Arc u = reseauTransport.getNoeud(arc.getSourceId()).findArc(arc.getCibleId());
				// if(u == null) {u = reseauTransport.getNoeud(arc.getCibleId()).findArc(arc.getSourceId());}
				
				if(u != null) {
					u.flux += delta;
				}
			}
			
			V += delta;
			C += m * delta;
			
			/**
			
			System.out.println("Coût : " + m);
			System.out.println(Flotests.pathToString(minimalCostPath));
			
			ReseauTransport gef = gef(reseauTransport);
			
			for(Noeud noeud : gef.getNoeuds()) {
				for(Arc arc : noeud.getSucc()) {
					Noeud.stringType = 0;
					// System.out.println(arcToString(arc));
					Noeud.stringType = 1;
				}
			}
			
			// printGraphPaths(reseauTransport);
			
			System.out.println("V = " + V + ", C = " + C);
			
			System.out.println("==================================================");
			
			/**/
			
		}
	}
	
	public String toString() {
		return "Busacker-Gowen";
	}
}
