import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //Remise en fonction du nombre de tomes différents, devrait idéalement être lu depuis un fichier de configuration, évolution faciles tant qu'il s'agit des pourcentages qui changent et non le mode d'application des remises (par exemple si on passait en remise brut)
        //Si le nombre de tomes est augmenté, il faut compléter le tableau
        double[] regleCalcul = {1, 1, 0.95, 0.9, 0.8, 0.75};

        Livre tome1 = new Livre("Tome 1", 1), tome2 = new Livre("Tome 2", 2), tome3 = new Livre("Tome 3", 3), tome4 = new Livre("Tome 4", 4), tome5 = new Livre("Tome 5", 5);
        Panier panier = new Panier();
        panier.addLivres(Arrays.asList(tome1, tome2, tome1, tome2, tome3, tome3, tome4, tome5));
        System.out.println(panier.calculPrix(regleCalcul));
    }
}
