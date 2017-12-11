package si.fri.rso.rlamp.users.api.v1.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.rso.rlamp.lairbnb.users.models.User;
import si.fri.rso.rlamp.lairbnb.users.services.UserService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/users")
public class UserResource {

    @Context
    protected UriInfo uriInfo;
    @Inject
    private UserService userBean;

    @GET
    @Path("/all")
    public Response getAllCustomers() {
        List<User> customers = userBean.getAllUsers();
        return Response.ok(customers).build();
    }

    @GET
    public Response getCustomers() {
        List<User> customers = userBean.getUsers(createQuery());
        return Response.ok(customers).build();
    }

    @GET
    @Path("/count")
    public Response getCount() {
        Long count = userBean.getUserCount(createQuery());
        return Response.ok(count).build();
    }

    @GET
    @Path("/{userId}")
    public Response getCustomer(@PathParam("userId") Integer userId) {
        User user = userBean.getUser(userId);
        return user != null
                ? Response.ok(user).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response addNewUser(User user) {
        userBean.createUser(user);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{userId}")
    public Response updateUser(@PathParam("userId") Integer userId, User user) {
        userBean.putUser(userId, user);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{userId}")
    public Response deleteUser(@PathParam("userId") Integer userId) {
        userBean.deleteUser(userId);
        return Response.noContent().build();
    }

    /**
     * Helper method for parsing query parameters from uri.
     *
     * @return query parameters
     */
    private QueryParameters createQuery() {
        return QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).defaultLimit(10).build();
    }
}
