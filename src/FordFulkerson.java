
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class FordFulkerson implements MaxFlowSolver {
	public void solve(ReseauTransport reseauTransport) {
		List<Arc> chaine;
		
		while((chaine = chaineAmeliorante(reseauTransport)) != null) {
			List<Arc> arcsAvants = arcsAvants(chaine);
			List<Arc> arcsArrieres = arcsArrieres(chaine);
			
			int delta = augmentationPossible(arcsAvants, arcsArrieres);
			
			for(Arc arc : arcsAvants) {
				arc.flux += delta;
			}
			
			for(Arc arc : arcsArrieres) {
				arc.flux -= delta;
			}
		}
	}
	
	public static List<Arc> chaineAmeliorante(ReseauTransport reseauTransport) {
		List<Arc> chaine = new LinkedList<Arc>();
		
		LinkedList<Noeud> Q = new LinkedList<Noeud>();
		reseauTransport.unmarkNoeuds();
		Noeud E = reseauTransport.getNoeud("E"), S = reseauTransport.getNoeud("S");
		E.mark();
		Q.addLast(E);
		
		while(!Q.isEmpty() && S.isNotMarked()) {
			Noeud x = Q.getFirst();
			Q.removeFirst();
			
			boolean trouve = false;
			
			for(Arc arc : x.getSucc()) {
				Noeud y = arc.getCible();
				
				if(y.isNotMarked()) {
					if(arc.flux < arc.getCapacite()) {
						y.mark();
						Q.addLast(y);
						
						chaine.add(arc);
						trouve = true;
					}
				}
			}
			
			for(Arc arc : reseauTransport.getPred(x)) {
				Noeud y = arc.getSource();
				
				if(y.isNotMarked()) {
					if(arc.flux > arc.getBorne()) {
						y.mark();
						Q.addLast(y);
						
						chaine.add(arc);
						trouve = true;
					}
				}
			}
			
			if(!trouve) {
				for(ListIterator<Arc> it = chaine.listIterator(); it.hasNext(); ) {
					Arc arc = it.next();
					
					if(arc.getSourceId().equals(x.getId()) || arc.getCibleId().equals(x.getId())) {
						it.remove();
					}
				}
			}
		}
		
		if(S.isMarked()) {
			return chaine;
		}
		
		return null;
	}
	
	public static List<Arc> arcsAvants(List<Arc> chaine) {
		List<Arc> arcsAvants = new ArrayList<Arc>();
		
		Noeud cursor = null;
		
		for(Arc arc : chaine) {
			if(cursor == null) {cursor = arc.getSource();}
			
			if(arc.getSource() == cursor) {// arc avant
				arcsAvants.add(arc);
				
				cursor = arc.getCible();
			}
			
			else if(arc.getCible() == cursor) {// arc arrière
				cursor = arc.getSource();
			}
		}
		
		return arcsAvants;
	}
	
	public static List<Arc> arcsArrieres(List<Arc> chaine) {
		List<Arc> arcsArrieres = new ArrayList<Arc>();
		
		Noeud cursor = null;
		
		for(Arc arc : chaine) {
			if(cursor == null) {cursor = arc.getSource();}
			
			if(arc.getSource() == cursor) {// arc avant
				cursor = arc.getCible();
			}
			
			else if(arc.getCible() == cursor) {// arc arrière
				arcsArrieres.add(arc);
				
				cursor = arc.getSource();
			}
		}
		
		return arcsArrieres;
	}
	
	public String toString() {
		return "Ford-Fulkerson";
	}
	
	public static int augmentationPossible(List<Arc> arcsAvants, List<Arc> arcsArrieres) {
		Integer deltaMoins = null, deltaPlus = null;
		
		for(Arc arc : arcsAvants) {
			int d = arc.getCapacite() - arc.flux;
			
			if(deltaMoins == null || d < deltaMoins) {
				deltaMoins = d;
			}
		}
		
		for(Arc arc : arcsArrieres) {
			int d = arc.flux - arc.getBorne();
			
			if(deltaPlus == null || d < deltaPlus) {
				deltaPlus = d;
			}
		}
		
		// 
		
		int delta;
		
		if(deltaMoins == null && deltaPlus != null) {
			delta = deltaPlus;
		}
		
		else if(deltaPlus == null && deltaMoins != null) {
			delta = deltaMoins;
		}
		
		else {
			delta = Math.min(deltaMoins, deltaPlus);
		}
		
		return delta;
	}
}
