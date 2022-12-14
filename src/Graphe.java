public class Graphe {

    private final int ORDRE;
    private final Sommet[] SOMMETS;

    public Graphe(int ordre, Sommet[] sommets){
        this.ORDRE = ordre;
        this.SOMMETS = sommets;
    }

    public void dijkstra(int numeroSommetDepart, String fichierSortie){
        Sommet sommetDepart = SOMMETS[numeroSommetDepart - 1];
        int[] distances = new int[ORDRE];
        boolean[] sommetsVisites = new boolean[ORDRE];

        //Initialisation des distances à "infini" et des sommets visités à "faux"
        for(int i = 0; i < ORDRE; ++i){
            distances[i] = Integer.MAX_VALUE;
            sommetsVisites[i] = false;
        }

        //La distance de la source à elle-même est mise à 0 et on la marque comme visitée
        distances[numeroSommetDepart-1] = 0;
        sommetsVisites[numeroSommetDepart-1] = true;

        //Initialisation des distances vers les sommets voisins de celui de départ
        for(int i = 0; i < sommetDepart.getVOISINS().length; ++i){
            distances[sommetDepart.getVOISINS()[i]] = sommetDepart.getARCS()[i];
        }

        //Déclaration de variables qui serviront à calculer les distances minimales
        int distanceMin;
        int numeroVoisinPlusProche;

        //Calcul des distances minimales
        for(int i = 0; i < ORDRE - 1; ++i) {
            distanceMin = Integer.MAX_VALUE;
            numeroVoisinPlusProche = -1;

            //Recherche de la distance minimale parmis celles connues et récupération du sommet qui se trouve au bout de cette distance si il n'a pas déjà été visité
            for(int j = 0; j < ORDRE; ++j){
                if(distanceMin > distances[j] && distances[j] != 0){
                    if(!sommetsVisites[j]){
                        distanceMin = distances[j];
                        numeroVoisinPlusProche = j + 1;
                    }
                }
            }
            Sommet s = SOMMETS[numeroVoisinPlusProche];

            //Déclaration de variables qui serviront à parcourir les sommets adjacents au sommet courant
            int numeroVoisin;
            int coutPourAllerVersVoisin;

            //Mise à jour des distance minimales
            for(int k = 0; k < s.getVOISINS().length; ++k){
                numeroVoisin = s.getVOISINS()[k] + 1;
                coutPourAllerVersVoisin = s.getARCS()[k];
                if(!sommetsVisites[numeroVoisin] && distances[numeroVoisin] > distances[k] + coutPourAllerVersVoisin){
                    distances[numeroVoisin] = distances[k] + coutPourAllerVersVoisin;
                }
            }

            //Marquage du sommet comme étant visité
            sommetsVisites[numeroVoisinPlusProche] = true;
        }

        if(fichierSortie == null){
            System.out.println("Résultats de l'algorithme de Dijkstra depuis le sommet " + numeroSommetDepart + ": ");
            for(int l = 0; l < ORDRE; ++l){
                System.out.println("-> " + (l+1) + ": " + distances[l]);
            }
        }
    }
}
