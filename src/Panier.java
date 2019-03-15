import java.util.ArrayList;
import java.util.List;

public class Panier {

    private List<Livre> livres;

    public Panier(){
        livres = new ArrayList<>();
    }

    public void addLivres(List<Livre> l){
        livres.addAll(l);
    }

    public double calculPrix(double[] regleCalcul){
        // Prix sans réduction
        double res = livres.size() * 8;
        double currentRes;

        Groupement grGagnant = new Groupement(); // Sert en débug à consulter quel groupement à permis d'atteindre le prix le moins élevé

        Groupement gr = new Groupement();
        // Génération des différents groupements que l'on pourrait faire

        // On commence par déterminer une taille maximale d'une sous liste, qui va être le nombre de tomes différents
        long nbTomesDif = livres.stream().distinct().count();

        //On génère un ensemble de tailles possible en fonction du nombre de livres et de tomes différents
        List<List<Integer>> taillesPossibles = taillesPossibles(livres.size(), nbTomesDif);

        for (List<Integer> tailleVoulue : taillesPossibles){
            try {
                //Pour chaque taille générée on tente d'insérer les livres dans un groupement de ce format
                gr = new Groupement(tailleVoulue, livres);
                //Si ça marche, on en calcule le prix
                currentRes = gr.calculPrix(regleCalcul);
                if (res > currentRes ){
                    res = currentRes;
                    grGagnant = gr;
                }
            } catch (GroupementImpossibleException e) {

            }
        }
        return res;
    }

    // Mauvais algorithme, je n'ai pas réussi à écrire quelque chose déterminant toutes les combinaisons possibles, j'ai ça à défault de mieux
    private List<List<Integer>> taillesPossibles(int nbLivres, long nbTomesDif) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 1; i <= nbTomesDif ; i++){
            for (int j = 1; j <= nbTomesDif ; j ++){
                if (i == j){
                    List<Integer> subres = new ArrayList<>();
                    int somme = 0;
                    while (somme < nbLivres - j) {
                        subres.add(j);
                        somme += j;
                    }
                    if (nbLivres - somme != 0) {
                        subres.add(nbLivres - somme);
                    }
                    res.add(subres);
                }
                else {
                    for (int k = 0; k < nbLivres - i || k < nbLivres - j; k++) {
                        List<Integer> subres = new ArrayList<>();
                        int somme = 0;
                        for (int m = 1; m < k && somme < nbLivres - j; m++) {
                            subres.add(j);
                            somme += j;
                        }
                        while (somme < nbLivres - i) {
                            subres.add(i);
                            somme += i;
                        }
                        if (nbLivres - somme != 0) {
                            subres.add(nbLivres - somme);
                        }
                        if (!res.contains(subres)) {
                            res.add(subres);
                        }
                    }
                }
            }
        }
        return res;
    }
}
