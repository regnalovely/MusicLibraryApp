package fr.dev.apptwo;

public class Music {
    private long id;
    private String titre;
    private String artiste;
    private String image;

    public Music(long id, String titre, String artiste, String image){
        this.id = id;
        this.titre = titre;
        this.artiste = artiste;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
