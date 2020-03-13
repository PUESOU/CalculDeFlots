
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;

public class Noeud {
    private String id;
    public List<Arc> succ;
    
    public boolean mark;
    
    public static int stringType = 1;
    
    public Noeud(String id) {
        this.id = id;
        succ = new LinkedList<Arc>();
    }

    public Noeud(int id) {
    	this(id+"");
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
        
        succ.add(new Arc(this, cible));
    }
    
    public boolean hasSuccesseur(String j) {
        ListIterator<Arc> it = succ.listIterator();
        
        while(it.hasNext()) {
            Arc arc = it.next();
            
            if(arc.getCibleId().equals(j)) {
                return true;
            }
        }
        
        return false;
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
    }
    
    public void removeSuccesseur(int j) {
    	removeSuccesseur(j+"");
    }
    
    /**
     * Retourne la liste de tous les chemins de nœuds, partant du nœud courant vers les feuilles.
     * @return la liste des listes de nœuds.
     */
    
    public List<List<Noeud>> getBranches() {
    	List<List<Noeud>> branches = new LinkedList<List<Noeud>>();
    	
    	for(Arc arc : succ) {
    		List<List<Noeud>> subBranches = arc.getCible().getBranches();
    		
    		for(List<Noeud> subBranch : subBranches) {
    			subBranch.add(0, this);
    		}
    		
    		branches.addAll(subBranches);
    	}
    	
    	if(branches.size() == 0) {
    		List<Noeud> branch = new LinkedList<Noeud>();
    		
    		branch.add(this);
    		
    		branches.add(branch);
    	}
    	
    	return branches;
    }
    
    /**
     * Retourne la liste de tous les chemins d'arcs, partant du nœud courant vers les feuilles.
     * @return la liste des listes d'arcs.
     */
    
    public List<List<Arc>> getPaths() {
    	List<List<Arc>> paths = new LinkedList<List<Arc>>();
    	
    	for(Arc arc : succ) {
    		List<List<Arc>> subPaths = arc.getCible().getPaths();
    		
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
    	
    	return paths;
    }
}
