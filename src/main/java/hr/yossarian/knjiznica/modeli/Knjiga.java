package hr.yossarian.knjiznica.modeli;

public class Knjiga {

  private int knjigaId;
  private String isbn;
  private String naslov;
  private String opis;
  private String drzavaKnjizevnosti;
  private double ocjena;

  public Knjiga(int knjigaId, String isbn, String naslov, String opis, String drzavaKnjizevnosti, double ocjena) {
    this.knjigaId = knjigaId;
    this.isbn = isbn;
    this.naslov = naslov;
    this.opis = opis;
    this.drzavaKnjizevnosti = drzavaKnjizevnosti;
    this.ocjena = ocjena;
  }

  public Knjiga() {
  }

  public int getKnjigaId() {
    return knjigaId;
  }

  public void setKnjigaId(int knjigaId) {
    this.knjigaId = knjigaId;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getNaslov() {
    return naslov;
  }

  public void setNaslov(String naslov) {
    this.naslov = naslov;
  }

  public String getOpis() {
    return opis;
  }

  public void setOpis(String opis) {
    this.opis = opis;
  }

  public String getDrzavaKnjizevnosti() {
    return drzavaKnjizevnosti;
  }

  public void setDrzavaKnjizevnosti(String drzavaKnjizevnosti) {
    this.drzavaKnjizevnosti = drzavaKnjizevnosti;
  }

  public double getOcjena() {
    return ocjena;
  }

  public void setOcjena(double ocjena) {
    this.ocjena = ocjena;
  }

  @Override
  public String toString() {
    return "Knjiga{" +
            "knjigaId=" + knjigaId +
            ", isbn='" + isbn + '\'' +
            ", naslov='" + naslov + '\'' +
            ", drzavaKnjizevnosti='" + drzavaKnjizevnosti + '\'' +
            ", ocjena=" + ocjena +
            '}';
  }
}
