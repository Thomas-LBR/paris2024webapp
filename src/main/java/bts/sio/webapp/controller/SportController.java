package bts.sio.webapp.controller;

import bts.sio.webapp.model.Sport;
import bts.sio.webapp.service.SportService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Data
@Controller
public class SportController {

    @Autowired
    private SportService sportservice; // Correction ici (SportService au lieu de portService)

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Sport> listSport = sportservice.getSports();
        model.addAttribute("sports", listSport); // Correction ici : "sports" au lieu de "sport", et listSport au lieu de listSports
        return "home";
    }

    @GetMapping("/createSport")
    public String createSport(Model model) {
        Sport s = new Sport();
        model.addAttribute("sport", s);
        return "sport/formNewSport";
    }

    @GetMapping("/updateSport/{id}")
    public String updateSport(@PathVariable("id") final int id, Model model) {
        Sport s = sportservice.getSport(id);
        model.addAttribute("sport", s);
        return "sport/formUpdateSport";
    }

    @GetMapping("/deleteSport/{id}")
    public ModelAndView deleteSport(@PathVariable("id") final int id) {
        sportservice.deleteSport(id);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/saveSport")
    public ModelAndView saveSport(@ModelAttribute Sport sport) { // Correction du nom de la méthode
        System.out.println("controller save=" + sport.getNom());
        if (sport.getId() != null) {
            Sport current = sportservice.getSport(sport.getId()); // Correction ici (sportservice au lieu de sportrevice)
            sport.setNom(current.getNom()); // Ceci écrase le nom existant, assurez-vous que c'est voulu.
        }
        sportservice.saveSport(sport);
        return new ModelAndView("redirect:/");
    }
}
