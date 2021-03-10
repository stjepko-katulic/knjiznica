package hr.yossarian.knjiznica.dao;

import hr.yossarian.knjiznica.modeli.Autor;
import hr.yossarian.knjiznica.modeli.Knjiga;
import hr.yossarian.knjiznica.modeli.ProcitanaKnjiga;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MainRepository {

  @Autowired
  JdbcTemplate jdbcTemplate;



  public List<ProcitanaKnjiga> getProcitaneKnjige() {
    List<ProcitanaKnjiga> procitaneKnjige = new ArrayList<>();

    String upitSveProcitaneKnjige="select * " +
            "                      from knjiznicadb.knjiga";

    List<Knjiga> sveProcitaneKnjige = jdbcTemplate.query(upitSveProcitaneKnjige, rowMapperSveProcitaneKnjige);

    for (Knjiga knjiga : sveProcitaneKnjige) {
      int idKnjige = knjiga.getKnjigaId();

      String upitAutor = "select * " +
              "from autor a join autor_knjiga ak on a.autor_id = ak.id_autor " +
              "join knjiga k on ak.id_knjiga = k.knjiga_id " +
              "where id_knjiga = " + idKnjige;

      String upitDatumCitanja = "select datum_citanja from autor_knjiga where id_knjiga="+idKnjige + " limit 1";
      Date datumCitanja = jdbcTemplate.queryForObject(upitDatumCitanja, Date.class);

      List<Autor> autori = jdbcTemplate.query(upitAutor, rowMapperAutor);
      ProcitanaKnjiga procitaneKnjiga = new ProcitanaKnjiga(autori, knjiga, datumCitanja);
      procitaneKnjige.add(procitaneKnjiga);
    }

    // sortiramo pročitane knjige prema datumu kad su pročitane
    procitaneKnjige=
            procitaneKnjige.stream().sorted(Comparator.comparing(x->x.getDatumProcitana())).collect(Collectors.toList());

    return procitaneKnjige;
  }



  public void upisUBazuAutora(Map<String,String> autor) {

    String upisAutora = "insert into knjiznicadb.autor " +
            "(ime, prezime, biografija) " +
            "values ('" + autor.get("ime") + "','" + autor.get("prezime") +
            "','" + autor.get("biografija") +"')";

    jdbcTemplate.update(upisAutora);

  }



  public List<Autor> getSviAutori() {
    String sviAutori = "select * from autor order by prezime";
    List<Autor> listaAutora = jdbcTemplate.query(sviAutori, rowMapperAutor);
    return listaAutora;
  }




  public void upisUTablicuAutorKnjiga(String autor, String naslovKnjige, String isbn, String datumProcitano) {
    String autorIme = autor.substring(autor.indexOf(',')+2, autor.length());
    String autorPrezime = autor.substring(0, autor.indexOf(','));

    String idAutor = jdbcTemplate.queryForObject("select autor_id from autor where ime='"+
            autorIme + "' and prezime='" + autorPrezime + "'", String.class);

    String knjigaId = jdbcTemplate.queryForObject("select knjiga_id from knjiga where isbn='" +
            isbn + "' and naslov ='" + naslovKnjige + "'", String.class);

    String datum = datumProcitano.substring(6,10) + "-" + datumProcitano.substring(3,5) +
            "-" + datumProcitano.substring(0,2);



    jdbcTemplate.update("insert into autor_knjiga (id_autor, id_knjiga, datum_citanja) values ('" +
            idAutor + "','" + knjigaId + "','" + datum + "')");

  }




  public void zapisUBazuKnjiga(Map<String, String> podaciZaKnjigu) {
    String upisUBazu = "insert into knjiga " +
            "(isbn, naslov, opis, drzava_knjizevnosti, ocjena) " +
            "values ('" + podaciZaKnjigu.get("isbn") + "','" + podaciZaKnjigu.get("naslov") + "','" +
            podaciZaKnjigu.get("sadrzaj") + "','" + podaciZaKnjigu.get("knjizevnost") + "','" +
            podaciZaKnjigu.get("ocjena") + "')";

    jdbcTemplate.update(upisUBazu);
  }




  RowMapper<Knjiga> rowMapperSveProcitaneKnjige = new RowMapper<Knjiga>() {
    @Override
    public Knjiga mapRow(ResultSet rs, int rowNum) throws SQLException {
      int knjigaId = rs.getInt("knjiga_id");
      String isbn = rs.getString("isbn");
      String naslov = rs.getString("naslov");
      String opis = rs.getString("opis");
      String drzavaKnjizevnosti = rs.getString("drzava_knjizevnosti");
      Double ocjena = rs.getDouble("ocjena");

      Knjiga knjiga = new Knjiga(knjigaId, isbn, naslov, opis, drzavaKnjizevnosti, ocjena);
      return knjiga;
    }
  };



  RowMapper<Autor> rowMapperAutor = new RowMapper<Autor>() {
    @Override
    public Autor mapRow(ResultSet rs, int rowNum) throws SQLException {
      int autorId = rs.getInt("autor_id");
      String ime = rs.getString("ime");
      String prezime = rs.getString("prezime");
      String biografija = rs.getString("biografija");
      Autor autor = new Autor(autorId, ime, prezime, biografija);
      return autor;
    }
  };

}
