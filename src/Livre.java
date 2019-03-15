public class Livre {

    private String titre;

    private int tome;

    public Livre(String titre, int tome) {
        this.titre = titre;
        this.tome = tome;
    }

    public Livre(){

    }

    public String getTitre() {
        return titre;
    }

    public int getTome() {
        return tome;
    }
}
