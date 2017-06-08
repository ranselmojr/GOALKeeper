package app.Controller;

import app.Model.UserAccount;
import app.util.Path;
import app.util.ViewUtil;
import org.apache.commons.lang.ObjectUtils;
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

    public static Route registerPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.REGISTER);

    };
    public static Route handleRegisterPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        int zipcode = 00000;
        String name = request.queryParams("fullname");
        String username = request.queryParams("username");
        String email = request.queryParams("email");
        String password = request.queryParams("password");
        String homephone = request.queryParams("homephone");
        String mobilephone = request.queryParams("mobilephone");
        String line1 = request.queryParams("line1");
        String line2 = request.queryParams("line2");
        String city = request.queryParams("city");
        String state = request.queryParams("state");
        try{
            zipcode = Integer.parseInt(request.queryParams("zipcode"));
        }catch(NumberFormatException e){

        }

        UserAccount newUser = new UserAccount(name,username,password, email,
                homephone, mobilephone, line1, line2, city, state, zipcode);

        System.out.println(newUser.getPhone());

        boolean userResult = newUser.addAccount(newUser);

        if(!userResult){
            model.put("someError", true);
            return ViewUtil.render(request, model, Path.Template.REGISTER);
        }

        request.session().attribute("userRegistered", true);
        response.redirect(Path.Web.LOGIN);
        return null;

    };
}
