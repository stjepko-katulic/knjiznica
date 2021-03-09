package hr.yossarian.knjiznica.controllers;

import hr.yossarian.knjiznica.dao.MainRepository;
import hr.yossarian.knjiznica.modeli.Autor;
import hr.yossarian.knjiznica.modeli.Counter;
import hr.yossarian.knjiznica.modeli.ProcitanaKnjiga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {

  @Autowired
  MainRepository mainRepository;

  @GetMapping("/")
  public ModelAndView index() {
    List<ProcitanaKnjiga> procitaneKnjige = mainRepository.getProcitaneKnjige();
    List<Autor> listaSvihAutora = mainRepository.getSviAutori();
    ModelAndView modelAndView = new ModelAndView();

    modelAndView.setViewName("index");
    modelAndView.addObject("procitaneKnjige", procitaneKnjige);
    modelAndView.addObject("counter", new Counter());
    modelAndView.addObject("sviAutori", listaSvihAutora);

    return modelAndView;
  }

  @PostMapping("unosKnjige")
  public void unosKnjige(@RequestParam("autor") String autor,
                         @RequestParam("naslov") String naslov,
                         @RequestParam("isbn") String isbn,
                         @RequestParam("knjizevnost") String knjizevnost,
                         @RequestParam("ocjena") String ocjena,
                         @RequestParam("procitana") String procitana,
                         @RequestParam("sadrzaj") String sadrzaj) {

    Map<String, String> podaciZaKnjigu = new HashMap<>();
    sadrzaj=sadrzaj.replace('\'', Character.MIN_VALUE);

    podaciZaKnjigu.put("naslov", naslov);
    podaciZaKnjigu.put("isbn", isbn);
    podaciZaKnjigu.put("knjizevnost", knjizevnost);
    podaciZaKnjigu.put("ocjena", ocjena);
    podaciZaKnjigu.put("sadrzaj", sadrzaj);

    // zapis knjige u bazu
    mainRepository.zapisUBazuKnjiga(podaciZaKnjigu);
    mainRepository.upisUTablicuAutorKnjiga(autor, naslov, isbn, procitana);
  }

  @PostMapping("unosAutora")
  public List<Autor> unosAutora(@RequestParam("ime") String ime,
                                @RequestParam("prezime") String prezime,
                                @RequestParam("biografija") String biografija) {

    biografija = biografija.replace('\'', Character.MIN_VALUE);

    Map<String, String> podaciUZaAutora = new HashMap<>();
    podaciUZaAutora.put("ime", ime);
    podaciUZaAutora.put("prezime", prezime);
    podaciUZaAutora.put("biografija", biografija);

    mainRepository.upisUBazuAutora(podaciUZaAutora);

    return mainRepository.getSviAutori();

  }


}
