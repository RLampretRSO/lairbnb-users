package si.fri.rso.rlamp.users.api.v1.health;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import si.fri.rso.rlamp.lairbnb.users.services.config.UserServiceConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class UserServiceHealthCheck implements HealthCheck {
    @Inject
    private UserServiceConfig userConfig;

    @Override
    public HealthCheckResponse call() {
        if(userConfig.isHealthy()) {
            return HealthCheckResponse.named(UserServiceHealthCheck.class.getSimpleName()).up().build();
        }
        return HealthCheckResponse.named(UserServiceHealthCheck.class.getSimpleName()).down().build();
    }
}
