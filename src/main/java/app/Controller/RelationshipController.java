package main.java.app.Controller;

import static main.java.app.util.RequestUtil.getSessionCurrentUser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import main.java.app.Model.Goal;
import main.java.app.Model.Note;
import main.java.app.Model.Relationship;
import main.java.app.Model.UserAccount;
import main.java.app.util.Path;
import main.java.app.util.ViewUtil;
import org.apache.velocity.VelocityContext;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by Kevin on 7/24/2017.
 */

public class RelationshipController {
	
	private static int getUserID(Request request) {
		UserAccount user = new UserAccount();
		return user.getUserAccount(
				request.session().attribute("currentUser")).getUserID();
	}

	public static Route relationshipList = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Relationship rel = new Relationship();
		ArrayList<Relationship> peers = new ArrayList<>();
		ArrayList<Relationship> superiors = new ArrayList<>();
		ArrayList<Relationship> directReports = new ArrayList<>();
		ArrayList<Relationship> allRelationships = new ArrayList<>();
		allRelationships = rel.getAllRelationships(getUserID(request));

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		for (Relationship r : allRelationships) {
			if (r.getCategory().equalsIgnoreCase("Peer")) {
				peers.add(r);
			} else if (r.getCategory().equalsIgnoreCase("Superior")) {
				superiors.add(r);
			} else {
				directReports.add(r);
			}
		}
		context.put("Superiors", superiors);
		context.put("Peers", peers);
		context.put("DirectReports", directReports);
		context.put("RelationshipList", allRelationships);
		model.put("str", context);

		return ViewUtil.render(request, model, Path.Template.RELATIONSHIPLIST);
	};

	public static Route relationshipAddPage = (Request request,
			Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Map<String, Object> model = new HashMap<>();
		return ViewUtil.render(request, model, Path.Template.RELATIONSHIPADD);
	};

	public static Route relationshipAdd = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}	
		
		String fname = request.queryParams("fname");
		String lname = request.queryParams("lname");
		String phone = request.queryParams("phone");
		String email = request.queryParams("email");
		String category = request.queryParams("category");
		Relationship rel = new Relationship();
		rel = rel.addRelationship(getUserID(request), fname, lname, phone, email, category);

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Relationship", rel);
		model.put("str", context);
		response.redirect("/relationshipview/" + rel.getRelid());

		return null;
	};

	public static Route relationshipView = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Relationship rel = new Relationship();
		Note note = new Note();
		int relid = Integer.parseInt(request.params("id"));

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Relationship", rel.getRelationship(relid));
		context.put("NoteList", note.getAllRelationshipNotes(relid));
		model.put("str", context);

		return ViewUtil.render(request, model, Path.Template.RELATIONSHIPVIEW);
	};

	public static Route deleteRelationship = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Relationship rel = new Relationship();
		ArrayList<Relationship> relList = rel.getAllRelationships(getUserID(request));
		int relid = Integer.parseInt(request.params("id"));
		rel.deleteRelationship(relid);

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("RelationshipList", relList);
		model.put("str", context);

		response.redirect("/relationshiplist/");

		return null;
	};

	public static Route viewRelNote = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}
	
		Goal goal = new Goal();
		Relationship rel = new Relationship();
		ArrayList<Goal> goalList = goal.getAllGoals(getUserID(request));
		int relid = Integer.parseInt(request.params("id"));
		
		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("GoalList", goalList);
		context.put("Relationship", rel.getRelationship(relid));
		model.put("str", context);

		return ViewUtil.render(request, model, Path.Template.RELNOTEADD);
	};

	public static Route addRelNote = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Note note = new Note();
		Goal goal = new Goal();
		int relid = Integer.parseInt(request.params("id"));
		int goalid = Integer.parseInt(request.queryParams("goalid"));
		String date = request.queryParams("date");
		String noteText = request.queryParams("note");

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Note", note.addNote(goalid, relid, date, noteText));
		context.put("GoalList", goal.getAllGoals(getUserID(request)));
		model.put("str", context);
		response.redirect("/relationshipview/" + relid);

		return null;
	};

	public static Route editRelNotePage = (Request request, Response response) -> {

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
		context.put("GoalList", goal.getAllGoals(getUserID(request)));
		model.put("str", context);

		return ViewUtil.render(request, model, Path.Template.RELNOTEEDIT);
	};

	public static Route editRelNote = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Relationship rel = new Relationship();
		Note note = new Note();
		int noteid = Integer.parseInt(request.params("id"));
		String noteText = request.queryParams("note");
		int goalid = Integer.parseInt(request.queryParams("goalid"));
		String date = request.queryParams("date");
		note = note.getNote(noteid);
		rel = rel.getRelationship(note.getRelid());
		note.editRelNote(noteid, goalid, date, noteText);
		
		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Note", note);
		context.put("Relationship", rel);
		model.put("str", context);

		response.redirect("/relationshipview/" + rel.getRelid());

		return null;
	};

	public static Route deleteRelNote = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Relationship rel = new Relationship();
		Note note = new Note();
		int noteid = Integer.parseInt(request.params("id"));
		note = note.getNote(noteid);
		rel = rel.getRelationship(note.getRelid());
		note.deleteNote(noteid);

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		model.put("str", context);

		response.redirect("/relationshipview/" + rel.getRelid());

		return null;
	};
	
	
	public static Route relationshipEditPage = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}
		
		Relationship rel = new Relationship();
		int relid = Integer.parseInt(request.params("id"));
		rel=rel.getRelationship(relid);

		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Relationships", rel.getAllRelationships(getUserID(request)));
		context.put("Relationship", rel.getRelationship(relid));
		model.put("str", context);

		return ViewUtil.render(request, model, Path.Template.RELATIONSHIPEDIT);
	};

	public static Route relationshipEdit = (Request request, Response response) -> {

		if (getSessionCurrentUser(request) == null) {
			response.redirect(Path.Web.LOGIN);
			return null;
		}

		Relationship rel = new Relationship();
		int relid = Integer.parseInt(request.params("id"));
		String fname = request.queryParams("fname");
		String lname = request.queryParams("lname");
		String phone = request.queryParams("phone");
		String email = request.queryParams("email");
		String category = request.queryParams("category");
		rel = rel.getRelationship(relid);
		rel.editRelationship(relid, fname, lname, phone, email, category);
		
		VelocityContext context = new VelocityContext();
		Map<String, Object> model = new HashMap<>();
		context.put("Relationship", rel);
		model.put("str", context);
		response.redirect("/relationshipview/" + rel.getRelid());
		return null;
	};
}