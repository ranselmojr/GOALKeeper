package app.Controller;

import java.util.HashMap;
import java.util.Map;
import org.apache.velocity.VelocityContext;
import app.Model.Behavior;
import app.Model.Goal;
import app.Model.Note;
import app.Model.Relationship;
import app.Model.Status;
import app.Model.UserAccount;
import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;
import static app.util.RequestUtil.*;

/**
 * Created by Kevin on 7/16/2017.
 */

public class GoalController {
	
	private static int getUserID(Request request) {
		UserAccount user = new UserAccount();
		return user.getUserAccount(
				request.session().attribute("currentUser")).getUserID();
	}

	public static Route goalList = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Goal goal = new Goal();

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("GoalList", goal.getAllGoals(getUserID(request)));
		model.put("str", context);

		return ViewUtil.render(request, model, Path.Template.GOAL);
	};


	public static Route goalForm = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Goal goal = new Goal();
		Note note = new Note();
		Behavior behavior = new Behavior();
		Status status = new Status();
		int goalid = Integer.parseInt(request.params("id"));

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Goal", goal.getGoal(goalid));
		context.put("BehaviorList", behavior.getAllBehaviors(goalid));
		context.put("NoteList", note.getAllGoalNotes(goalid));
		context.put("Status", status.getStatus(goalid));
		model.put("str", context);

		return ViewUtil.render(request, model, Path.Template.GOALFORM);
	};

	public static Route goalAddPage = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Map<String, Object> model = new HashMap<>();
		return ViewUtil.render(request, model, Path.Template.GOALADD);

	};

	public static Route goalAdd = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		String goalText = request.queryParams("goal");
		String dueDate = request.queryParams("due_date");
		String percent_comp = request.queryParams("percent_comp");
		Goal goal = new Goal();
		goal = goal.addGoal(getUserID(request), goalText, dueDate, percent_comp);

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Goal", goal);
		model.put("str", context);
		response.redirect("/goalform/" + goal.getGoalid());

		return null;
	};

	public static Route goalEditPage = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}
		
		Goal goal = new Goal();
		Status status = new Status();
		int goalid = Integer.parseInt(request.params("id"));
		status = status.getStatus(goalid);

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Status", status);
		context.put("Goal", goal.getGoal(goalid));
		model.put("str", context);

		return ViewUtil.render(request, model, Path.Template.GOALEDIT);

	};

	public static Route goalEdit = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Goal goal = new Goal();
		int goalid = Integer.parseInt(request.params("id"));
		String goalText = request.queryParams("goal");
		String dueDate = request.queryParams("due_date");
		String percent_comp = request.queryParams("percent_comp");
		goal = goal.getGoal(goalid);
		goal.editGoal(goalid, goalText, dueDate, percent_comp);
		
		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Goal", goal);
		model.put("str", context);
		response.redirect("/goalform/" + goal.getGoalid());
		
		return null;
	};

	public static Route deleteGoal = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}
		
		Goal goal = new Goal();
		int goalid = Integer.parseInt(request.params("id"));
		goal.deleteGoal(goalid);

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("GoalList", goal.getAllGoals(getUserID(request)));
		model.put("str", context);

		return ViewUtil.render(request, model, Path.Template.GOAL);
	};

	public static Route viewBehavior = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Goal goal = new Goal();
		Behavior behavior = new Behavior();
		int obid = Integer.parseInt(request.params("id"));
		behavior = behavior.getBehavior(obid);

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Behavior", behavior);
		context.put("Goal", goal.getGoal(behavior.getGoalid()));
		model.put("str", context);

		return ViewUtil.render(request, model, Path.Template.BEHAVIORVIEW);
	};

	public static Route addBehaviorPage = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Goal goal = new Goal();
		int goalid = Integer.parseInt(request.params("id"));

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Goal", goal.getGoal(goalid));
		model.put("str", context);

		return ViewUtil.render(request, model, Path.Template.BEHAVIORADD);
	};

	public static Route addBehavior = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}
		
		Behavior behavior = new Behavior();
		int goalid = Integer.parseInt(request.params("id"));
		String behaviorText = request.queryParams("behavior");

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Behavior", behavior.addBehavior(goalid, behaviorText));
		model.put("str", context);
		response.redirect("/goalform/" + goalid);

		return null;
	};

	public static Route editBehavior = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}
		
		Goal goal = new Goal();
		Behavior behavior = new Behavior();
		int obid = Integer.parseInt(request.params("id"));
		String behaviorText = request.queryParams("behavior");
		behavior = behavior.getBehavior(obid);
		behavior.editBehavior(obid, behaviorText);
		goal = goal.getGoal(behavior.getGoalid());

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Behavior", behavior);
		context.put("Goal", goal.getGoal(behavior.getGoalid()));
		model.put("str", context);

		response.redirect("/goalform/" + goal.getGoalid());

		return null;
	};

	public static Route viewNote = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}
	
		Goal goal = new Goal();
		Relationship rel = new Relationship();
		int goalid = Integer.parseInt(request.params("id"));
		
		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Relationships", rel.getAllRelationships(getUserID(request)));
		context.put("Goal", goal.getGoal(goalid));
		model.put("str", context);

		return ViewUtil.render(request, model, Path.Template.NOTEADD);
	};

	public static Route addNote = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Note note = new Note();
		Relationship rel = new Relationship();
		int goalid = Integer.parseInt(request.params("id"));
		int relid = Integer.parseInt(request.queryParams("relid"));
		String date = request.queryParams("date");
		String noteText = request.queryParams("note");

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Note", note.addNote(goalid, relid, date, noteText));
		context.put("Relationships", rel.getAllRelationships(getUserID(request)));
		model.put("str", context);
		
		response.redirect("/goalform/" + goalid);

		return null;
	};

	public static Route editNotePage = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}
		
		Goal goal = new Goal();
		Note note = new Note();
		Relationship rel = new Relationship();
		int noteid = Integer.parseInt(request.params("id"));
		note = note.getNote(noteid);
		goal = goal.getGoal(note.getGoalid());
		rel = rel.getRelationship(note.getRelid());

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Note", note);
		context.put("Goal", goal);
		context.put("Relationship", rel);
		context.put("Relationships", rel.getAllRelationships(getUserID(request)));
		model.put("str", context);

		return ViewUtil.render(request, model, Path.Template.NOTEVIEW);
	};

	public static Route editNote = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Goal goal = new Goal();
		Note note = new Note();
		int noteid = Integer.parseInt(request.params("id"));
		String noteText = request.queryParams("note");
		int relid = Integer.parseInt(request.queryParams("relid"));
		String date = request.queryParams("date");
		note = note.getNote(noteid);
		goal = goal.getGoal(note.getGoalid());
		note.editNote(noteid, relid, date, noteText);

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Note", note);
		context.put("Goal", goal);
		model.put("str", context);

		response.redirect("/goalform/" + goal.getGoalid());

		return null;
	};

	public static Route deleteNote = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Goal goal = new Goal();
		Note note = new Note();
		int noteid = Integer.parseInt(request.params("id"));
		note = note.getNote(noteid);
		goal = goal.getGoal(note.getGoalid());
		note.deleteNote(noteid);

		response.redirect("/goalform/" + goal.getGoalid());

		return null;
	};

	public static Route deleteBehavior = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Goal goal = new Goal();
		Behavior behavior = new Behavior();
		int obid = Integer.parseInt(request.params("id"));
		behavior = behavior.getBehavior(obid);
		goal = goal.getGoal(behavior.getGoalid());
		behavior.deleteBehavior(obid);

		response.redirect("/goalform/" + goal.getGoalid());

		return null;
	};
}
