package app.Controller;

import app.Model.UserAccount;
import app.util.Path;
import app.util.PostMail;
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
        request.session().attribute("currentUser", userInfo.getUserName());
        if (getQueryLoginRedirect(request) != null) {
            response.redirect(getQueryLoginRedirect(request));
        }
        response.redirect(Path.Web.GOAL);
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

        String message = "<p><b>Hello " + name + ",</b></p>" +
                "<p>You are now a Registered User in GOALkeeper</p>";
        try
        {
            PostMail objPostMail = new PostMail();
            String[] objStringArray = new String[1];
            objStringArray[0] = new String(email);
            objPostMail.postMail(objStringArray, "Welcome to GOALkeeper", message);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        request.session().attribute("userRegistered", true);
        response.redirect(Path.Web.LOGIN);
        return null;

    };

    public static Route forgotPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.FORGOT);

    };

    public static Route handleForgotPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        String email = request.queryParams("email");


        UserAccount user = new UserAccount(email);

        if(user.isEmailExist()){
            model.put("sentSuccess", true);
            String url = request.host();
            String message = "<h3>Password Reset</h3>"+
                    "<p>Please click the link " +
                    "<a href=\"http://" + url + "/reset/" + email + "\">Reset Password</a></p>";

            try
            {
                PostMail objPostMail = new PostMail();
                String[] objStringArray = new String[1];
                objStringArray[0] = new String(email);
                objPostMail.postMail(objStringArray, "GOALkeeper Password Reset", message);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }else{
            model.put("userNotExist", true);
        }
        return ViewUtil.render(request, model, Path.Template.FORGOT);
    };

    public static Route resetPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.RESET);
    };

    public static Route handleResetPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        String password = request.queryParams("password");
        String confirm = request.queryParams("confirm_password");
        String email = request.params("email");


        if(!password.equals(confirm) || password.length() <= 7){
            model.put("confirmNotMatched", true);
            return ViewUtil.render(request, model, Path.Template.RESET);
        }else{
            UserAccount user = new UserAccount();
            user.resetPassword(email, password);
            String message = "<h3>Your password has been reset</h3>";
            try
            {
                PostMail objPostMail = new PostMail();
                String[] objStringArray = new String[1];
                objStringArray[0] = new String(email);
                objPostMail.postMail(objStringArray, "Password Reset Confirmation", message);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }



        response.redirect(Path.Web.LOGIN);
        return null;
    };
}
