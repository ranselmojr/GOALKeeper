package main.java.app.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import main.java.app.Model.Goal;
import main.java.app.util.Path;
import main.java.app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import static main.java.app.util.RequestUtil.*;


/**
 * Created by Kevin on 7/16/2017.
 */


public class GoalController {

	//KEVIN ADDED
    /*public static Route goalPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.GOAL);
    };*/
    
    
  //Add to a new goal controller
    public static Route goalPage = (Request request, Response response) -> {
    	
    	/*if(getSessionCurrentUser(request) == null) {
    	    response.redirect(Path.Web.LOGIN);
    	    return null;
    	}*/
    	
    	int i = 0;
    	Goal goal = new Goal();

        VelocityContext context = new VelocityContext();
        
        Map<String, Object> model = new HashMap<>();
        
        /*for (Goal g : goal.getAllGoals(1)) {
        	context.put("GoalText", g.getAllGoals(1).get(i).getGoalText());
        	i++;
        };*/
        
        //context.put("GoalText", goal.getAllGoals(1).get(0).getGoalText());
        context.put("GoalList", goal.getAllGoals(1));
       // context.put("adam", "Adam Swogger");
       // context.put("jonathan", "Jonathan Chandler");
       // context.put("kevin", "Kevin Detweiler");
       // context.put("romeo", "Romeo Anselmo");
        model.put("str", context);


        return ViewUtil.render(request, model, Path.Template.GOAL);
    };
	
}
