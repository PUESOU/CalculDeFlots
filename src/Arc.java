
public class Arc {
    private Noeud source;
    private Noeud cible;
    public double poids;// coût
    public int borne, capacite;// quantités min, max
    public int flux;
    
    public Arc(Noeud x, Noeud y) {
        this.source = x;
        this.cible = y;
    }
    
    public Arc(Noeud x, Noeud y, double poids, int capacite) {
    	this(x, y);
    	this.poids = poids;
    	this.capacite = capacite;
    }
    
    public String toString() {
        return "(" + source + ", " + cible + ")";
    }
    
    public String getSourceId() {
        return source.getId();
    }
    
    public String getCibleId() {
        return cible.getId();
    }
    
    public Noeud getCible() {
    	return cible;
    }
}
