package app.util;

import lombok.*;

public class Path {

    // The @Getter methods are needed in order to access
    // the variables from Velocity Templates
    //
    // Declare the actual routes here
    public static class Web {
        @Getter public static final String INDEX = "/index/";
        @Getter public static final String LOGIN = "/login/";
        @Getter public static final String LOGOUT = "/logout/";

    }

    // Declare here the location of template files
    public static class Template {
        public final static String INDEX = "/template/helloworld.vm";

        public static final String NOT_FOUND = "/template/notFound.vm";
    }

}
