package hr.yossarian.knjiznica.modeli;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ProcitanaKnjiga {

  private List<Autor> autori;
  private Knjiga knjiga;
  private Date datumProcitana;

  public ProcitanaKnjiga(List<Autor> autori, Knjiga knjiga, Date datumProcitana) {
    this.autori = autori;
    this.knjiga = knjiga;
    this.datumProcitana = datumProcitana;
  }

  public List<Autor> getAutori() {
    return autori;
  }

  public Knjiga getKnjiga() {
    return knjiga;
  }

  public LocalDate getDatumProcitana() {
    return datumProcitana.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
  }
}
