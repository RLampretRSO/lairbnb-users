package si.fri.rso.rlamp.lairbnb.users.services;

import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.rlamp.lairbnb.users.models.Lair;
import si.fri.rso.rlamp.lairbnb.users.models.Reservation;
import si.fri.rso.rlamp.lairbnb.users.models.User;
import si.fri.rso.rlamp.lairbnb.users.services.config.UserServiceConfig;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class UserService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserServiceConfig userConfig;
    private Client httpClient = ClientBuilder.newClient();

    @Inject
    @DiscoverService(value = "lairbnb-reservations", version = "*", environment = "dev")
    private Optional<String> reservationsBaseUrl;

    @Inject
    @DiscoverService(value = "lairbnb-lairs", version = "*", environment = "dev")
    private Optional<String> lairsBaseUrl;

    public List<User> getAllUsers() {
        List<User> customers = em
                .createNamedQuery("User.findAll", User.class)
                .getResultList();

        return customers;
    }

    public List<User> getUsers(QueryParameters query) {
        List<User> customers = JPAUtils.queryEntities(em, User.class, query);
        return customers;
    }

    public Long getUserCount(QueryParameters query) {
        Long count = JPAUtils.queryEntitiesCount(em, User.class, query);
        return count;
    }

    public User getUser(Integer userId) {
        User user = em.find(User.class, userId);

        if (user != null) {
            user.setReservations(this.getReservations(user.getId(), user.isHost()));
            user.setLairs(this.getLairs(user.getId()));
        }

        return user;
    }

    @Transactional
    public User createUser(User user) {
        if (user == null) {
            return null;
        }
        em.persist(user);

        return user;
    }

    @Transactional
    public User putUser(Integer userId, User user) {
        if (user == null) return null;

        User u = em.find(User.class, userId);
        if (u == null) return null;

        user.setId(u.getId());
        user = em.merge(user);

        return user;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public boolean deleteUser(Integer userId) {
        User user = em.find(User.class, userId);
        if (user == null) return false;

        em.remove(user);

        return true;
    }

    public List<Reservation> getReservations(Integer userId, boolean asHost) {
        if (userConfig.isReservationServiceEnabled() && reservationsBaseUrl.isPresent()) {
            String userIdField = "userUserId";
            if (asHost) {
                userIdField = "hostUserId";
            }
            return httpClient.target(reservationsBaseUrl.get() +
                    "/v1/reservations?filter=" + userIdField + ":EQ:" + userId.toString())
                    .request().get(new GenericType<List<Reservation>>() {
                    });
        }

        return new ArrayList<Reservation>();
    }

    public List<Lair> getLairs(Integer userId) {
        if (userConfig.isLairServiceEnabled() && lairsBaseUrl.isPresent()) {
            return httpClient.target(lairsBaseUrl.get() +
                    "/v1/lairs?filter=ownerUserId:EQ:" + userId.toString())
                    .request().get(new GenericType<List<Lair>>() {
                    });
        }
        return new ArrayList<Lair>();
    }
}
