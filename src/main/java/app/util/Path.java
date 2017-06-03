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

    }

    // Declare here the location of template files
    public static class Template {
        public final static String INDEX = "/template/helloworld.vm";
        public final static String LOGIN = "/template/login.vm";
        public final static String DASHBOARD = "/template/dashboard.vm";

        public static final String NOT_FOUND = "/template/notFound.vm";
    }

}
