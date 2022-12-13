public class Sommet {

    private final int NUMERO;
    private final int[] VOISINS;
    private final int[] ARCS;

    public Sommet(int numero, int[] voisins, int[] arcs){
        this.NUMERO = numero;
        this.VOISINS = voisins;
        this.ARCS = arcs;
    }

    public int getNUMERO() {
        return NUMERO;
    }

    public int[] getVOISINS() {
        return VOISINS;
    }

    public int[] getARCS() {
        return ARCS;
    }
}
