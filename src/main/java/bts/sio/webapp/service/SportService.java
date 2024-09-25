package bts.sio.webapp.service;

import bts.sio.webapp.model.Sport;
import bts.sio.webapp.repository.SportProxy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class SportService {

    @Autowired
    private SportProxy sportProxy;

    public Sport getSport(final int id) {
        return sportProxy.getSport(id);  // Récupérer un sport par ID
    }

    public Iterable<Sport> getSports() {
        return sportProxy.getSports();  // Correction ici pour récupérer tous les sports
    }

    public void deleteSport(final int id) {
        sportProxy.deleteSport(id);  // Supprimer un sport par ID
    }

    public Sport saveSport(Sport sport) {
        Sport savedSport;

        // Règle fonctionnelle : le nom du sport doit être en majuscules.
        sport.setNom(sport.getNom().toUpperCase());

        if (sport.getId() == null) {
            // Si l'ID est nul, alors c'est un nouveau sport.
            savedSport = sportProxy.createSport(sport);
        } else {
            // Sinon, on met à jour un sport existant.
            savedSport = sportProxy.updateSport(sport);
        }

        return savedSport;
    }
}
