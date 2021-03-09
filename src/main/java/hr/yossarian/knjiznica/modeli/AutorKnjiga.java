package hr.yossarian.knjiznica.modeli;

import java.time.LocalDate;

public class AutorKnjiga {

  private int idAuthor;
  private int idKnjiga;
  private LocalDate datumKadJeProcitanjaKnjiga;

  public AutorKnjiga(int idAuthor, int idKnjiga, LocalDate datumKadJeProcitanjaKnjiga) {
    this.idAuthor = idAuthor;
    this.idKnjiga = idKnjiga;
    this.datumKadJeProcitanjaKnjiga = datumKadJeProcitanjaKnjiga;
  }

  public int getIdAuthor() {
    return idAuthor;
  }

  public void setIdAuthor(int idAuthor) {
    this.idAuthor = idAuthor;
  }

  public int getIdKnjiga() {
    return idKnjiga;
  }

  public void setIdKnjiga(int idKnjiga) {
    this.idKnjiga = idKnjiga;
  }

  public LocalDate getDatumKadJeProcitanjaKnjiga() {
    return datumKadJeProcitanjaKnjiga;
  }

  public void setDatumKadJeProcitanjaKnjiga(LocalDate datumKadJeProcitanjaKnjiga) {
    this.datumKadJeProcitanjaKnjiga = datumKadJeProcitanjaKnjiga;
  }
}
