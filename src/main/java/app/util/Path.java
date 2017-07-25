package app.util;

import lombok.*;

public class Path {

    // The @Getter methods are needed in order to access
    // the variables from Velocity Templates
    //
    // Declare the actual routes here
    public static class Web {
        @Getter public static final String INDEX = "/";
        @Getter public static final String LOGIN = "/login/";
        @Getter public static final String LOGOUT = "/logout/";
        @Getter public static final String DASHBOARD = "/dashboard/";
        @Getter public static final String REGISTER = "/register/";
        @Getter public static final String FORGOT = "/forgot/";
        @Getter public static final String RESET = "/reset/:email/";
        @Getter public static final String LEARN = "/learning/learn/";
        @Getter public static final String QUESTION = "/learning/question/";
        @Getter public static final String FEEDBACK = "/feedback/";
        @Getter public static final String ABOUT = "/about/";

        
        @Getter public static final String GOAL = "/goallist/";
        @Getter public static final String GOALFORM = "/goalform/:id/";
        @Getter public static final String GOALADD = "/goaladd/";
        @Getter public static final String GOALEDIT = "/goaledit/:id/";
        @Getter public static final String GOALDELETE = "/goaldelete/:id/";
        
        @Getter public static final String BEHAVIORADD = "/behavioradd/:id/";
        @Getter public static final String BEHAVIORVIEW = "/behaviorview/:id/";
        @Getter public static final String NOTEADD = "/noteadd/:id/";
        @Getter public static final String NOTEVIEW = "/noteview/:id/";
        @Getter public static final String NOTEDELETE = "/notedelete/:id/";
        @Getter public static final String BEHAVIORDELETE = "/behaviordelete/:id/";
        @Getter public static final String FEEDBACKPROVIDER = "/feedbackprovider/:id/";
        @Getter public static final String RELATIONSHIPLIST = "/relationshiplist/";
        @Getter public static final String RELATIONSHIPADD = "/relationshipadd/";
        @Getter public static final String RELATIONSHIPVIEW = "/relationshipview/:id/";
        @Getter public static final String RELATIONSHIPDELETE = "/relationshipdelete/:id/";
        @Getter public static final String RELNOTEADD = "/relnoteadd/:id/";
        @Getter public static final String RELNOTEEDIT = "/relnoteedit/:id/";
        @Getter public static final String RELNOTEDELETE = "/relnotedelete/:id/";
        @Getter public static final String RELATIONSHIPEDIT = "/relationshipedit/:id/";


        


    }

    // Declare here the location of template files
    public static class Template {
        public final static String INDEX = "/template/helloworld.vm";
        public final static String LOGIN = "/template/login.vm";
        public final static String DASHBOARD = "/template/dashboard.vm";
        public final static String REGISTER = "/template/register.vm";
        public final static String FORGOT = "/template/forgot.vm";
        public final static String RESET = "/template/reset.vm";

        public final static String FEEDBACK = "/template/feedback.vm";
        public final static String ABOUT = "/template/dashboard.vm";

        public final static String LEARN = "/template/learning/learn.vm";
        public final static String QUESTION = "/template/learning/question.vm";

        public final static String GOAL = "/template/goalList.vm";
        public final static String GOALFORM = "/template/goalForm.vm";
        public final static String GOALADD = "/template/goalAdd.vm";
        public final static String GOALEDIT = "/template/goalEdit.vm";
        public final static String BEHAVIORADD = "/template/behaviorAdd.vm";
        public final static String BEHAVIORVIEW = "/template/behaviorView.vm";
        public final static String NOTEADD = "/template/noteAdd.vm";
        public final static String NOTEVIEW = "/template/noteView.vm";
        public final static String FEEDBACKPROVIDER = "/template/contactFeedbackProvider.vm";
        public final static String RELATIONSHIPLIST = "/template/relationshipList.vm";
        public final static String RELATIONSHIPADD = "/template/relationshipAdd.vm";
        public final static String RELATIONSHIPVIEW = "/template/relationshipView.vm";
        public final static String RELNOTEADD = "/template/relNoteAdd.vm";
        public final static String RELNOTEEDIT= "/template/relNoteEdit.vm";
        public final static String RELATIONSHIPEDIT = "/template/relationshipEdit.vm";
        


        public static final String NOT_FOUND = "/template/notFound.vm";
    }

}
