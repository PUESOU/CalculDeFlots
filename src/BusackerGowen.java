
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
				
				Arc u = gef.getArc(arc.getSourceId(), arc.getCibleId());
				u.flux = arc.getCapacite() - arc.flux;
				u.setPoids(arc.getPoids());
			}
			
			if(arc.flux > 0) {
				gef.addArc(arc.getCibleId(), arc.getSourceId());
				
				Arc u = gef.getArc(arc.getCibleId(), arc.getSourceId());
				u.flux = arc.flux - arc.getBorne();
				u.setPoids(-arc.getPoids());
			}
		}
		
		return gef;
	}
	
	/**
	 * Retourne le chemin le moins coûteux parmi tous les chemins allant de E à S dans le graphe d'écart associé au réseau de transport donné.
	 * @param reseauTransport le réseau de transport.
	 * @return le chemin au coût minimal dans GeF.
	 */
	
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
		
		return minimalCostPath(pathsToS);
	}
	
	/**
	 * Retourne le chemin au coût minimal parmi une liste.
	 * @param paths la liste de chemins.
	 * @return le chemin au coût minimal.
	 */
	
	public static List<Arc> minimalCostPath(List<List<Arc>> paths) {
		List<Arc> shortestPath = null;
		Integer minimalCost = null;
		
		for(List<Arc> path : paths) {
			int pathCost = 0;
			
			for(Arc arc : path) {
				pathCost += arc.getPoids();
			}
			
			if(minimalCost == null || pathCost < minimalCost) {
				minimalCost = pathCost;
				shortestPath = path;
			}
		}
		
		return shortestPath;
	}
	
	/**
	 * Retourne le coût d'un chemin.
	 * @param path le chemin d'arcs.
	 * @return la somme des coûts de chaque arc.
	 */
	
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
				Arc u = reseauTransport.getArc(arc.getSourceId(), arc.getCibleId());
				// if(u == null) {u = reseauTransport.getNoeud(arc.getCibleId()).findArc(arc.getSourceId());}
				
				if(u != null) {
					int d = u.getCapacite() - u.flux;
					
					if(delta == null || d < delta) {
						delta = d;
					}
				}
			}
			
			for(Arc arc : minimalCostPath) {
				Arc u = reseauTransport.getArc(arc.getSourceId(), arc.getCibleId());
				// if(u == null) {u = reseauTransport.getNoeud(arc.getCibleId()).findArc(arc.getSourceId());}
				
				if(u != null) {
					u.flux += delta;
				}
			}
			
			V += delta;
			C += m * delta;
		}
	}
	
	public String toString() {
		return "Busacker-Gowen";
	}
}
