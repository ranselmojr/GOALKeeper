package app.Controller;

import app.util.*;
import org.apache.velocity.VelocityContext;
import spark.*;
import java.util.*;
import static app.Application.*;


/**
 * Created by Romeo on 5/25/2017.
 */
public class MainController {

    public static Route mainPage = (Request request, Response response) -> {

        VelocityContext context = new VelocityContext();

        Map<String, Object> model = new HashMap<>();
        context.put("mainTitle", "Romeo");
        context.put("mary", "Mary Coronado");
        context.put("adam", "Adam Swogger");
        context.put("jonathan", "Jonathan Chandler");
        context.put("kevin", "Kevin Detweiler");
        context.put("romeo", "Romeo Anselmo");
        model.put("str", context);


        return ViewUtil.render(request, model, Path.Template.INDEX);
    };

    public static Route dashBoardPage = (Request request, Response response) -> {

        VelocityContext context = new VelocityContext();
        Map<String, Object> model = new HashMap<>();
        model.put("str", context);
        return ViewUtil.render(request, model, Path.Template.DASHBOARD);

    };

}
