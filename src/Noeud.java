
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;

public class Noeud {
    private String id;
    public List<Arc> succ;
    
    public boolean mark;
    
    public Noeud(String id) {
        this.id = id;
        succ = new LinkedList<Arc>();
    }

    public Noeud(int id) {
    	this(id+"");
    }
    
    public String toString() {
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
}
