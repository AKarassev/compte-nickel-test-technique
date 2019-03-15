import java.util.*;


public class Groupement {

    // Une sous liste d'un groupement ne peut contenir qu'un seul exemplaire de chaque tome
    private List<List<Livre>> groupement;

    public Groupement(){
            groupement = new ArrayList<>();
    }

    // Calcul du prix total
    public double calculPrix(double[] regleCalcul){
        double res = 0.0;
        for (List l : groupement){
            res += l.size() * 8 * regleCalcul[l.size()];
        }
        return res;
    }


    // Ajout d'un livre dans la première sous-liste ne contenant pas déjà ce tome en tenant compte des limitations de taille par sous liste
    public void addLivre(int i, Livre l, List<Integer> tailleVoulue) throws GroupementImpossibleException{
        try {
            if (groupement.get(i).stream().anyMatch(livre -> livre.getTome() == l.getTome()) || groupement.get(i).size() == tailleVoulue.get(i)) {
                if (i == tailleVoulue.size()) {
                    throw new GroupementImpossibleException();
                } else {
                    addLivre(i + 1, l, tailleVoulue);
                }
            } else {
                groupement.get(i).add(l);
            }
        }catch (IndexOutOfBoundsException e){
            throw new GroupementImpossibleException();
        }
    }

    // Génère un groupement en fonction d'une liste de livres, et de citères de taille des sous-listes et de leur nombre
    public Groupement(List<Integer> tailleVoulue, List<Livre> livres) throws GroupementImpossibleException{
        // On commence par faire une copie de la liste de livres pour éviter que les modifications ultérieures n'empêchent le bon fonctionnement du code dans la classe Panier
        List<Livre> listeDeTravail = new ArrayList<>();
        listeDeTravail.addAll(livres);

        // On crée toutes les sous listes dont on va avoir besoin sans les remplir
        groupement = new ArrayList<>();
        for (int i = 0; i < tailleVoulue.size(); i++){
            groupement.add(new ArrayList<>());
        }

        // On cherche les livres les plus présents dans le panier, afin de pouvoir les traiter en premier et éviter d'avoir besoin de sous-listes supplémentaires à la fin
        Map<Livre, Integer> map = new HashMap<Livre, Integer>();
        for (Livre l : listeDeTravail) {
            Integer count = map.get(l);
            map.put(l, (count == null) ? 1 : count + 1);
        }

        // Tant qu'on a pas traité tous les livres
        while(!listeDeTravail.isEmpty()){
            // On cherche le livre le plus présent encore dans la liste de travail
            int count = 0;
            Livre livrePlusPresent = new Livre();
            for (Map.Entry<Livre, Integer> entry : map.entrySet()) {
                if (entry.getValue()> count){
                    count = entry.getValue();
                    livrePlusPresent = entry.getKey();
                }
            }
            // Si ce livre est en plus d'exemplaire que le groupement n'a de sous listes, alors le groupement n'est pas faisable, on retourne une exception
            if (count > tailleVoulue.size()){
                throw new GroupementImpossibleException();
            } else {
                // Sinon on ajoute chacun de ces exemplaires dans une sous liste différentes
                for (int i = 0; i < count ; i++){
                    addLivre(0, livrePlusPresent, tailleVoulue);
                }
            }
            // On enlève les livres traités de la liste de travail et de la map recensant lesquels sont les plus présents
            while(listeDeTravail.contains(livrePlusPresent)){
                listeDeTravail.remove(livrePlusPresent);
            }
            map.remove(livrePlusPresent);
        }

    }
}
