public class Graphe {

    private final int ORDRE;
    private final Sommet[] SOMMETS;

    public Graphe(int ordre, Sommet[] sommets){
        this.ORDRE = ordre;
        this.SOMMETS = sommets;
    }

    public void dijkstra(int numeroSommetDepart, String fichierSortie){
        for(Sommet s : SOMMETS){
            System.out.println(s.getNUMERO());
            for(int i = 0; i < s.getVOISINS().length; ++i){
                System.out.print(s.getVOISINS()[i] + " " + s.getARCS()[i] + "\n");
            }
        }
    }
}
