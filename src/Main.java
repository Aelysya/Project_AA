import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        //Variables des 3 arguments
        String fichierEntree, fichierSortie;
        int numeroSommetDepart;

        //Lecteur d'entrées utilisateur sur la console
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));

        if(args.length > 3){
            System.out.println("Trop d'arguments !");
            System.exit(1);
        }

        //Initialisation des fichier d'entrée et sortie selon les arguments de la ligne de commande
        String nomFichier;
        try{
            nomFichier = args[0];
            fichierEntree = "src/" + nomFichier;
        } catch (ArrayIndexOutOfBoundsException e){
            fichierEntree = null;
        }

        try{
            nomFichier = args[2];
            fichierSortie = "src/" + nomFichier;
        } catch (ArrayIndexOutOfBoundsException e){
            fichierSortie = null;
        }

        String[] data;
        String ligneLue;
        //Ignorer le warning, si l'argument n'est pas donné ça fait bien null
        if (fichierEntree == null) {
            System.out.println("Décrivez le graphe:");
            //Lecture du graphe sur la console
            ligneLue = entree.readLine();
            data = ligneLue.split("\\s+");
        } else {
            //Lecture du graphe dans le fichier donné
            BufferedReader reader = new BufferedReader(new FileReader(fichierEntree));
            ligneLue = reader.readLine();
            data = ligneLue.split("\\s+");
            reader.close();
        }

        int ordreGraphe = Integer.parseInt(data[0]);
        if(ordreGraphe <= 0){
            System.out.println("L'ordre du graphe doit être positif !");
            System.exit(1);
        }
        Sommet[] sommetsGraphe = new Sommet[ordreGraphe];

        //Initialisation du numéro du sommet de départ selon l'argument de la ligne de commande
        try{
            numeroSommetDepart = Integer.parseInt(args[1]);
        } catch (ArrayIndexOutOfBoundsException e){
            //Si l'argument n'existe pas, on demande à l'utilisateur
            System.out.println("Entrez le numéro du sommet de départ: ");
            numeroSommetDepart = Integer.parseInt(entree.readLine());
        }

        // On redemande le numéro de départ tant que l'utilisateur ne donne pas un nombre supérieur à 0 et inférieur à l'ordre du graphe
        while (numeroSommetDepart <= 0 || numeroSommetDepart > ordreGraphe){
            System.out.println("Le numéro du sommet de départ doit être positif sans dépasser l'ordre du graphe !\nEntrez le numéro du sommet de départ: ");
            numeroSommetDepart = Integer.parseInt(entree.readLine());
        }
        int compteurSommets = 0;

        for(int i = 1; i < data.length; ++i){
            //Si le nombre lu est 0, alors c'est la fin du graphe donc sortie de la boucle de construction
            if(Integer.parseInt(data[i]) == 0){
                break;
            }

            //Le numéro du sommet est le premier nombre lu
            int numeroSommet = Integer.parseInt(data[i]); i++;
            //On initialise les tableaux avec une taille égale à l'ordre du graphe pour être sûr d'avoir suffisamment de place
            int[] voisins = new int[ordreGraphe];
            int[] arcs = new int[ordreGraphe];
            //On déclare un compteur de sommet adjacents pour les tableaux insérer dans les tableaux ci-dessus
            int cpt = 0;

            //Si on lit 0, alors la liste des sommets adjacents est terminée
            while(Integer.parseInt(data[i]) != 0){
                voisins[cpt] = Integer.parseInt(data[i]); i++; //La première valeur est le numéro du sommet
                arcs[cpt] = Integer.parseInt(data[i]); i++; //La seconde est le coût de l'arc
                if(arcs[cpt] < 0){
                    System.out.println("L'un des coûts d'arc du graphe est négatif ! Arrêt du programme, vérifiez la description du graphe avant de relancer !");
                    System.exit(1);
                }
                cpt++;
            }

            //On déclare des nouveaux tableaux qui seront de la bonne taille (les précedénts contenaient des 0 lorsqu'il n'y avait pas d'arc vers certains sommets
            int[] vraisVoisins = new int[cpt];
            int[] vraisArcs = new int[cpt];

            //On rempli les nouveaux tableaux corrects avec les valeurs trouvées
            for(int j = 0; j < cpt; ++j){
                vraisVoisins[j] = voisins[j];
                vraisArcs[j] = arcs[j];
            }

            //Construction d'un nouveau sommet avec les valeurs trouvées
            Sommet s = new Sommet(numeroSommet, vraisVoisins, vraisArcs);
            //Insertion du nouveau sommet dans le tableau de sommets
            sommetsGraphe[compteurSommets] = s;
            compteurSommets++;
        }

        //Construction d'un graphe avec la liste de sommets créée
        Graphe graphe = new Graphe(ordreGraphe, sommetsGraphe);

        //Application de l'agorithme de Dijkstra
        graphe.dijkstra(numeroSommetDepart, fichierSortie);
    }
}
