package bts.sio.webapp.repository;


import bts.sio.webapp.CustomProperties;
import bts.sio.webapp.model.Sport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class SportProxy {
    @Autowired
    private CustomProperties props;

    /**
     * Get all athletes
     * @return An iterable of all athlete
     */
    public Iterable<Sport> getSports() {

        String baseApiUrl = props.getApiUrl();
        String getSportsUrl = baseApiUrl + "/sports";
        System.out.println("url=" + getSportsUrl);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Sport>> response = restTemplate.exchange(
                getSportsUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Sport>>() {}
        );

        log.debug("Get Sports call " + response.getStatusCode().toString());

        return response.getBody();
    }

    /**
     * Get an athlete by the id
     * @param id The id of the athlete
     * @return The athlete which matches the id
     */
    public Sport getSport(int id) {
        String baseApiUrl = props.getApiUrl();
        String getEmployeeUrl = baseApiUrl + "/sport/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Sport> response = restTemplate.exchange(
                getEmployeeUrl,
                HttpMethod.GET,
                null,
                Sport.class
        );

        log.debug("Get Sport call " + response.getStatusCode().toString());

        return response.getBody();
    }

    /**
     * Add a new athlete
     * @param s A new athlete (without an id)
     * @return The athlete full filled (with an id)
     */
    public Sport createSport(Sport s) {

        String baseApiUrl = props.getApiUrl();
        String createSportUrl = baseApiUrl + "/sport";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Sport> request = new HttpEntity<Sport>(s);
        ResponseEntity<Sport> response = restTemplate.exchange(
                createSportUrl,
                HttpMethod.POST,
                request,
                Sport.class);

        log.debug("Create Sport call " + response.getStatusCode().toString());

        return response.getBody();
    }

    /**
     * Update an athlete - using the PUT HTTP Method.
     * @param e Existing athlete to update
     */
    public Sport updateSport(Sport e) {
        String baseApiUrl = props.getApiUrl();
        String updateSportUrl = baseApiUrl + "/sport/" + e.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Sport> request = new HttpEntity<Sport>(e);
        ResponseEntity<Sport> response = restTemplate.exchange(
                updateSportUrl,
                HttpMethod.PUT,
                request,
                Sport.class);

        log.debug("Update Sport call " + response.getStatusCode().toString());

        return response.getBody();
    }

    /*
     * Delete an athlete using exchange method of RestTemplate
     * instead of delete method in order to log the response status code.
     * @param e The athlete to delete
     */
    public void deleteSport(int id) {
        String baseApiUrl = props.getApiUrl();
        String deleteSportUrl = baseApiUrl + "/sport/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(
                deleteSportUrl,
                HttpMethod.DELETE,
                null,
                Void.class);

        log.debug("Delete Sport call " + response.getStatusCode().toString());
    }
}
