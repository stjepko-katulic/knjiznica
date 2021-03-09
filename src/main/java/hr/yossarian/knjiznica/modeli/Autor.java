package hr.yossarian.knjiznica.modeli;

public class Autor {

  private int autorId;
  private String ime;
  private String prezime;
  private String biografija;

  public Autor(int autorId, String ime, String prezime, String biografija) {
    this.autorId = autorId;
    this.ime = ime;
    this.prezime = prezime;
    this.biografija = biografija;
  }

  public Autor() {
  }

  public int getAutorId() {
    return autorId;
  }

  public void setAutorId(int autorId) {
    this.autorId = autorId;
  }

  public String getIme() {
    return ime;
  }

  public void setIme(String ime) {
    this.ime = ime;
  }

  public String getPrezime() {
    return prezime;
  }

  public void setPrezime(String prezime) {
    this.prezime = prezime;
  }

  public String getBiografija() {
    return biografija;
  }

  public void setBiografija(String biografija) {
    this.biografija = biografija;
  }

  @Override
  public String toString() {
    return "Knjiga{" +
            "autorId=" + autorId +
            ", ime='" + ime + '\'' +
            ", prezime='" + prezime + '\'' +
            '}';
  }
}
