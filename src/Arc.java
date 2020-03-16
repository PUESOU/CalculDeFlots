
public class Arc {
    private Noeud source;
    private Noeud cible;
    public double poids;// coût
    public int borne, capacite;// quantités min, max
    public int flux;// quantité courante
    
    public Arc(Noeud x, Noeud y) {
        this.source = x;
        this.cible = y;
        poids = 1;
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
    
    public boolean estSature() {
    	return flux >= capacite;
    }
    
    public int getCapaciteResiduelle() {
    	return capacite - flux;
    }
    
    public Noeud getSource() {
    	return source;
    }
    
    public double getPoids() {
    	return poids;
	}
    
    public Arc setPoids(double poids) {
    	this.poids = poids;
    	
    	return this;
    }
    
    public int getBorne() {
    	return borne;
    }
    
    public Arc setBorne(int borne) {
    	this.borne = borne;
    	
    	return this;
    }

    public int getCapacite() {
    	return capacite;
    }
    
    public Arc setCapacite(int capacite) {
    	this.capacite = capacite;
    	
    	return this;
    }

    public int getFlux() {
    	return flux;
    }
    
    public Arc setFlux(int flux) {
    	this.flux = flux;
    	
    	return this;
    }
}
