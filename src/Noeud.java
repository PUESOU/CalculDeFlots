
import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;

public class Noeud {
    private int id;
    public List<Arc> succ;
    
    public boolean mark;
    
    public Noeud(int id) {
        this.id = id;
        succ = new LinkedList<Arc>();
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
    
    public int getId() {
        return id;
    }
    
    public boolean matchId(int id) {
        return this.id == id;
    }
    
    public int compareId(int id) {
        return this.id - id;
    }
    
    public void addArc(Noeud cible) {
        ListIterator<Arc> it = succ.listIterator();
        
        while(it.hasNext()) {
            Arc arc = it.next();
            
            if(arc.getCibleId() == cible.getId()) {
                return;
            }
        }
        
        succ.add(new Arc(this, cible));
    }
    
    public boolean hasSuccesseur(int j) {
        ListIterator<Arc> it = succ.listIterator();
        
        while(it.hasNext()) {
            Arc arc = it.next();
            
            if(arc.getCibleId() == j) {
                return true;
            }
        }
        
        return false;
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
    
    public void removeSuccesseur(int j) {
    	ListIterator<Arc> it = succ.listIterator();
    	
    	while(it.hasNext()) {
    		Arc arc = it.next();
    		
    		if(arc.getCibleId() == j) {
    			it.remove();
    		}
    	}
    }
    
    public static void main(String[] args) {
        Graphe graphe = new Graphe(5);
        
        graphe.addNoeud(6);
        graphe.addNoeud(6);
        
        graphe.addArc(1, 2);
        graphe.addArc(1, 2);
        graphe.addArc(1, 5);
        graphe.addArc(2, 5);
        graphe.addArc(12, 5);
        
        System.out.println(graphe);
    }
}
