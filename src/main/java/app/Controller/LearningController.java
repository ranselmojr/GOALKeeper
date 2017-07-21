package app.Controller;

import app.util.Path;
import app.util.PostMail;
import app.util.ViewUtil;
import org.apache.velocity.VelocityContext;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static app.util.RequestUtil.getSessionCurrentUser;


/**
 * Created by Romeo on 5/25/2017.
 */
public class LearningController {

    public static Route learnPage = (Request request, Response response) -> {

        if(getSessionCurrentUser(request) == null) {
            response.redirect(Path.Web.LOGIN);
            return null;
        }
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.LEARN);

    };

    public static Route questionPage = (Request request, Response response) -> {

        if(getSessionCurrentUser(request) == null) {
            response.redirect(Path.Web.LOGIN);
            return null;
        }
        System.out.println(getSessionCurrentUser(request));
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.QUESTION);

    };

    public static Route handleQuestionPage = (Request request, Response response) -> {

        String name = request.queryParams("fullname");
        String email = request.queryParams("email");
        String body = request.queryParams("message");


        String message = "</p> Message from " + name + "</p>" +
                "<p>" + body + "</p>"+
                "<p>Please reply to " + email;
        try
        {
            PostMail objPostMail = new PostMail();
            String[] objStringArray = new String[1];
            objStringArray[0] = new String("kevindet@hotmail.com");
            objPostMail.postMail(objStringArray, "GOALKeeper Question Submission", message);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        response.redirect(Path.Web.LEARN);
        return null;

    };
}
