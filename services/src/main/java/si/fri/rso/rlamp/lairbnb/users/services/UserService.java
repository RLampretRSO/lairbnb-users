package si.fri.rso.rlamp.lairbnb.users.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.rlamp.lairbnb.users.models.Lair;
import si.fri.rso.rlamp.lairbnb.users.models.Reservation;
import si.fri.rso.rlamp.lairbnb.users.models.User;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class UserService {

    @PersistenceContext
    private EntityManager em;

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
            user.setReservations(new ArrayList<Reservation>()); // TODO

            if (user.isHost()) {
                user.setLairs(new ArrayList<Lair>()); // TODO
            }
        }

        return user;
    }

    @Transactional
    public User createUser(User user) {
        if (user == null) return null;
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
}
