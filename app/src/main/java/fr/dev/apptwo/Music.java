package fr.dev.apptwo;

public class Music {
    private long id;
    private String titre;
    private String artiste;

    public Music(long id, String titre, String artiste){
        this.id = id;
        this.titre = titre;
        this.artiste = artiste;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }
}
