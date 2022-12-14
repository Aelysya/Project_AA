import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Graphe {

    private final int ORDRE;
    private final Sommet[] SOMMETS;

    public Graphe(int ordre, Sommet[] sommets){
        this.ORDRE = ordre;
        this.SOMMETS = sommets;
    }

    public void dijkstra(int numeroSommetDepart, String fichierSortie) throws IOException {
        int[] distances = new int[ORDRE];
        boolean[] sommetsVisites = new boolean[ORDRE];
        //TODO tableau des prédecesseurs
        String[] predecesseurs = new String[ORDRE];

        //Initialisation des distances à "infini" et des sommets visités à "faux"
        for(int i = 0; i < ORDRE; ++i){
            distances[i] = Integer.MAX_VALUE;
            sommetsVisites[i] = false;
        }

        //La distance de la source à elle-même est mise à 0
        distances[numeroSommetDepart-1] = 0;
        predecesseurs[numeroSommetDepart-1] = "_";

        //Déclaration de variables qui serviront à calculer les distances minimales
        int distanceMin;
        int numeroVoisinPlusProche;

        //Calcul des distances minimales
        for(int i = 0; i < ORDRE - 1; ++i) {
            distanceMin = Integer.MAX_VALUE;
            numeroVoisinPlusProche = -1;

            //Recherche de la distance minimale parmis celles connues et récupération du sommet qui se trouve au bout de cette distance si il n'a pas déjà été visité
            for(int j = 0; j < ORDRE; ++j){
                if(!sommetsVisites[j] && distances[j] <= distanceMin){
                    distanceMin = distances[j];
                    numeroVoisinPlusProche = j;
                }
            }
            Sommet s = SOMMETS[numeroVoisinPlusProche];
            //Marquage du sommet comme étant visité
            sommetsVisites[numeroVoisinPlusProche] = true;

            //Mise à jour des distance minimales
            for(int k = 0; k < s.getVOISINS().length; ++k){
                if(!sommetsVisites[s.getVOISINS()[k] - 1] && distances[s.getVOISINS()[k] - 1] > distances[numeroVoisinPlusProche] + s.getARCS()[k]){
                    distances[s.getVOISINS()[k] - 1] = distances[numeroVoisinPlusProche] + s.getARCS()[k];
                }
            }
        }

        //Construction de l'affichage des chemins
        String affichageFinal = "Résultats de l'algorithme de Dijkstra depuis le sommet " + numeroSommetDepart + ": \n";
        for(int l = 0; l < ORDRE; ++l){
            affichageFinal += (l+1)
                    + " ("
                    + (distances[l] == Integer.MAX_VALUE || distances[l] == Integer.MIN_VALUE ? "infini" : distances[l])
                    + ", "
                    + predecesseurs[l] + ")\n";
        }

        //Affichage des plus courts chemins
        if(fichierSortie == null){
            System.out.println(affichageFinal);
        } else {
            File file = new File(fichierSortie);
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.append(affichageFinal);
            writer.flush();
            writer.close();
        }
    }
}
