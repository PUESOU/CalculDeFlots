
public class Arc {
    private Noeud source;
    private Noeud cible;
    private double poids;
    
    public Arc(Noeud x, Noeud y) {
        this.source = x;
        this.cible = y;
    }
    
    public String toString() {
        return "(" + source + ", " + cible + ")";
    }
    
    public int getSourceId() {
        return source.getId();
    }
    
    public int getCibleId() {
        return cible.getId();
    }
    
    public Noeud getCible() {
    	return cible;
    }
}
