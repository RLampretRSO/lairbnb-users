package si.fri.rso.rlamp.users.api.v1.resources;

import org.json.JSONArray;
import org.json.JSONObject;
import si.fri.rso.rlamp.lairbnb.users.models.User;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/demo")
public class DemoResource {

    @GET
    @Path("/info")
    public Response getProjectInfo() {
        String json = new JSONObject()
                .put("clani", new JSONArray().put("rl7811"))
                .put("opis_projekta", "Lairbnb - aplikacija za oddajanje in najemanje 'brlogov'.")
                .put("mikrostoritve", new JSONArray().put("http://168.1.149.132:32147/v1/users")
                        .put("http://168.1.149.132:30861/v1/lairs")
                        .put("http://168.1.149.132:31398/v1/reservations"))
                .put("github", new JSONArray().put("https://github.com/RLampretRSO/lairbnb-users")
                        .put("https://github.com/RLampretRSO/lairbnb-lairs")
                        .put("https://github.com/RLampretRSO/lairbnb-reservations"))
                .put("travis", new JSONArray().put("https://travis-ci.org/RLampretRSO/lairbnb-users")
                        .put("https://travis-ci.org/RLampretRSO/lairbnb-lairs")
                        .put("https://travis-ci.org/RLampretRSO/lairbnb-reservations"))
                .put("dockerhub", new JSONArray().put("https://hub.docker.com/r/rlamp/lairbnb-users/")
                        .put("https://hub.docker.com/r/rlamp/lairbnb-lairs/")
                        .put("https://hub.docker.com/r/rlamp/lairbnb-reservations/"))
                .toString();

        return Response.ok(json).build();
    }
}
