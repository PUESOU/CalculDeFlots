
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashMap;

public class Noeud {
    private String id;
    public List<Arc> succ;
    
    public boolean mark;
    
    public static int stringType = 1;
    
    private HashMap<String, Arc> hmap;
    
    public Noeud(String id) {
        this.id = id;
        succ = new LinkedList<Arc>();
        hmap = new HashMap<String, Arc>();
    }

    public Noeud(int id) {
    	this(id+"");
    }
    
    public Noeud(Noeud noeud) {
    	this(noeud.getId());
    	mark = noeud.isMarked();
    }
    
    public String toString() {
        if(stringType == 0) {
        	return id;
        }
        
        if(stringType == 1) {
	    	StringBuffer res = new StringBuffer();
	        res.append(id);
	        res.append("(");
	        
	        ListIterator<Arc> it = succ.listIterator();
	        
	        while(it.hasNext()) {
	            Arc arc = it.next();
	            
	            res.append(arc.getCibleId());
	            
	            if(it.hasNext()) {
	                res.append(", ");
	            }
	        }
	        
	        res.append(")");
	        
	        return res.toString();
        }
        
    	return id;
    }
    
    public String getId() {
        return id;
    }
    
    public boolean matchId(String id) {
        return this.id.equals(id);
    }

    public boolean matchId(int id) {
        return this.id.equals(id+"");
    }
    
    public int compareId(String id) {
        return this.id.compareTo(id);
    }
    
    public void addArc(Noeud cible) {
        ListIterator<Arc> it = succ.listIterator();
        
        while(it.hasNext()) {
            Arc arc = it.next();
            
            if(arc.getCibleId().equals(cible.getId())) {
                return;
            }
        }
        
        Arc arc = new Arc(this, cible);
        
        succ.add(arc);
        hmap.put(cible.getId(), arc);
    }
    
    public boolean hasSuccesseur(String j) {
        return hmap.containsKey(j);
    }
    
    public boolean hasSuccesseur(int j) {
        return hasSuccesseur(j+"");
    }
    
    public ListIterator<Arc> arcsIterator() {
    	return succ.listIterator();
    }
    
    public boolean ciblesAllMarked() {
    	ListIterator<Arc> it = succ.listIterator();
    	
    	while(it.hasNext()) {
    		if(!it.next().getCible().mark) {
    			return false;
    		}
    	}
    	
    	return true;
    }
    
    public void mark() {mark = true;}
    public void unmark() {mark = false;}
    public boolean isMarked() {return mark;}
    public boolean isNotMarked() {return !mark;}
    
    public List<Arc> getSucc() {
    	return succ;
    }
    
    public void removeSuccesseur(String j) {
    	ListIterator<Arc> it = succ.listIterator();
    	
    	while(it.hasNext()) {
    		Arc arc = it.next();
    		
    		if(arc.getCibleId().equals(j)) {
    			it.remove();
    		}
    	}
    	
    	hmap.remove(j);
    }
    
    public void removeSuccesseur(int j) {
    	removeSuccesseur(j+"");
    }
    
    private boolean calledGetPaths = false;
    
    /**
     * Retourne la liste de tous les chemins d'arcs, partant du n�ud courant vers les feuilles.
     * @return la liste des listes d'arcs.
     */
    
    public List<List<Arc>> getPaths() {
    	List<List<Arc>> paths = new LinkedList<List<Arc>>();
    	
    	calledGetPaths = true;
		
    	for(Arc arc : succ) {
    		Noeud cible = arc.getCible();
    		
    		if(!cible.calledGetPaths) {
    			List<List<Arc>> subPaths = cible.getPaths();
	    		
	    		for(List<Arc> subPath : subPaths) {
	    			subPath.add(0, arc);
	    		}
	    		
	    		if(subPaths.size() == 0) {
	    			List<Arc> subPath = new LinkedList<Arc>();
	    			
	    			subPath.add(arc);
	    			
	    			subPaths.add(subPath);
	    		}
	    		
	    		paths.addAll(subPaths);
    		}
    		
    		else {
    			List<Arc> subPath = new LinkedList<Arc>();
    			subPath.add(arc);
    			
    			paths.add(subPath);
    		}
    	}
    	
    	calledGetPaths = false;
    	
    	return paths;
    }
    
    public Arc getArc(String cibleId) {
    	return hmap.get(cibleId);
    }
}
