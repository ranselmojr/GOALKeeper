package app;

import app.Controller.*;
import app.util.*;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;

// File locations
// Controller ->    /src/main/java/app/Controller
// Template ->      /src/main/resources/template
// Javascript ->    /src/main/resources/public/js/vendor
// CSS ->           /src/main/resources/public/css

// Declaring path and template locations -> /src/main/java/app/util/Path.java


/**
 * Created by Romeo on 5/25/2017.
 */
public class Application {

    public static void main(String[] args) {

        port(8080);

        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        enableDebugScreen();  //Remove this for production


        // Set up before-filters (called before each get/post)
        before("*",                  Filters.addTrailingSlashes);
        before("*",                  Filters.handleLocaleChange);

        // Set up routes
        //
        // This is where the routes being directed to the Controllers


        get(Path.Web.INDEX,          MainController.mainPage);
        get(Path.Web.DASHBOARD,      MainController.dashBoardPage);
        get(Path.Web.FEEDBACK,       MainController.feedbackPage);
        get(Path.Web.ABOUT,          MainController.aboutPage);

        get(Path.Web.LEARN,          LearningController.learnPage);
        get(Path.Web.QUESTION,       LearningController.questionPage);
        post(Path.Web.QUESTION,      LearningController.handleQuestionPage);

        get(Path.Web.LOGIN,          UserController.serverLoginPage);
        post(Path.Web.LOGIN,         UserController.handleLoginPost);
        post(Path.Web.LOGOUT,        UserController.handleLogoutPost);
        get(Path.Web.REGISTER,       UserController.registerPage);
        post(Path.Web.REGISTER,      UserController.handleRegisterPage);
        get(Path.Web.FORGOT,         UserController.forgotPage);
        post(Path.Web.FORGOT,        UserController.handleForgotPage);
        get(Path.Web.RESET,          UserController.resetPage);
        post(Path.Web.RESET,         UserController.handleResetPage);
        get(Path.Web.GOAL,           GoalController.goalPage);




        get("*",                     ViewUtil.notFound);

        //Set up after-filters (called after each get/post)
        after("*",                   Filters.addGzipHeader);



    }
}
