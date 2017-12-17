package si.fri.rso.rlamp.lairbnb.users.services.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("properties")
public class UserServiceConfig {

    @ConfigValue(value = "external-services.reservation-service.enabled", watch = true)
    private boolean reservationServiceEnabled;

    @ConfigValue(value = "external-services.lair-service.enabled", watch = true)
    private boolean lairServiceEnabled;

    @ConfigValue(value = "service.persistence.create.allowed", watch = true)
    private boolean allowRegisteringNewUsers;

    @ConfigValue(value = "service.persistence.delete.allowed", watch = true)
    private boolean allowDeletingUsers;

    @ConfigValue(value = "service.healthy", watch = true)
    private boolean healthy;

    public boolean isReservationServiceEnabled() {
        return reservationServiceEnabled;
    }

    public void setReservationServiceEnabled(boolean reservationServiceEnabled) {
        this.reservationServiceEnabled = reservationServiceEnabled;
    }

    public boolean isLairServiceEnabled() {
        return lairServiceEnabled;
    }

    public void setLairServiceEnabled(boolean lairServiceEnabled) {
        this.lairServiceEnabled = lairServiceEnabled;
    }

    public boolean isAllowRegisteringNewUsers() {
        return allowRegisteringNewUsers;
    }

    public void setAllowRegisteringNewUsers(boolean allowRegisteringNewUsers) {
        this.allowRegisteringNewUsers = allowRegisteringNewUsers;
    }

    public boolean isAllowDeletingUsers() {
        return allowDeletingUsers;
    }

    public void setAllowDeletingUsers(boolean allowDeletingUsers) {
        this.allowDeletingUsers = allowDeletingUsers;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }
}
