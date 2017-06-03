package app.Controller;

import app.Model.UserAccount;
import app.util.Path;
import app.util.ViewUtil;
import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static app.util.RequestUtil.*;

/**
 * Created by Romeo on 6/2/2017.
 */
public class UserController {

    public static Route serverLoginPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
        return ViewUtil.render(request, model, Path.Template.LOGIN);
    };

    public static Route handleLoginPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        UserAccount user = new UserAccount();
        UserAccount userInfo = new UserAccount();
        boolean authenticated = false;

        userInfo = userInfo.getUserAccount(getQueryUsername(request));
        if (userInfo != null) {
            authenticated = BCrypt.checkpw(getQueryPassword(request), userInfo.getPassword());
        }
        if (!authenticated) {
            model.put("authenticationFailed", true);
            return ViewUtil.render(request, model, Path.Template.LOGIN);
        }
        model.put("authenticationSucceeded", true);
        request.session().attribute("currentUser", userInfo.getName());
        if (getQueryLoginRedirect(request) != null) {
            response.redirect(getQueryLoginRedirect(request));
        }
        response.redirect(Path.Web.DASHBOARD);
        //return ViewUtil.render(request, model, Path.Template.DASHBOARD);
        return null;
    };

    public static Route handleLogoutPost = (Request request, Response response) -> {
        request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true);
        response.redirect(Path.Web.INDEX);
        return null;
    };

    // The origin of the request (request.pathInfo()) is saved in the session so
    // the user can be redirected back after login
    public static void ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("currentUser") == null) {
            request.session().attribute("loginRedirect", request.pathInfo());
            response.redirect(Path.Web.DASHBOARD);
        }
    };
}
