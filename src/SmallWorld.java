
import java.util.ListIterator;
import java.util.List;

public class SmallWorld extends Graphe {
	
	// Modèle Small-World (1)
	
	public SmallWorld(int n, int d) {
		for(int i = 0; i < n; ++i) {
			addNoeud(new Noeud(i));
		}
		
		for(int i = 0; i < n; ++i) {
			for(int j = i + 1; j <= i + d; ++j) {
				int x = i, y = j % n;
				addArcDouble(x, y);
			}
		}
	}
	
	// Modèle Small-World (2)
	
	public SmallWorld(int n, int d, double p) {
		this(n, d);
		
		for(int v = 0; v < n; ++v) {
			List<Arc> arcs = getNoeud(v).getSucc();
			
			for(int i = 0; i < arcs.size(); ++i) {
				Arc arc = arcs.get(i);
				
				double r = Math.random();
				
				if(r < p) {
					do {
						r = Math.random() * n;
					} while(!(hasArcDouble(v, (int)r) || (int)r == v));
					
					removeArcDouble(v, arc.getCibleId());
					addArcDouble(v, (int)r);
				}
			}
		}
	}
	
	// J'ai pas fait le Modèle Small-World (3)
	
}
