package app.util;

import lombok.Getter;

import javax.annotation.PostConstruct;

public class Path {

    // The @Getter methods are needed in order to access
    // the variables from Velocity Templates
    public static class Web {
        @Getter
        public static final String INDEX = "/";
        @Getter
        public static final String LOGIN = "/login/";

        @Getter
        public static final String STUDENTSCHEDULE = "/api/student/schedule";

        @Getter
        public static final String LOGOUT = "/logout/";
        @Getter
        public static final String BOOKS = "/books/";
        @Getter
        public static final String ENTRY = "/entry/";
        @Getter
        public static final String BLOCK = "/block/";
        @Getter
        public static final String COURSES = "/course/";
        @Getter
        public static final String STUDENT = "/student/";

        @Getter
        public static final String COURSE = "/api/course/course?id=";

        @Getter
        public static final String CHANGE = "/api/course/change";

        @Getter
        public static final String LIST = "/api/course/list";

        @Getter
        public static final String ADD = "/api/course/add";

        @Getter
        public static final String DELETECOURSE = "/api/course/remove";

       @Getter
        public static final String ONE_BOOK = "/books/:isbn/";


    }

    public static class Template {
        public final static String INDEX = "/velocity/index.vm";
        public final static String LOGIN = "/velocity/login/login.vm";
        public static final String NOT_FOUND = "/velocity/notFound.vm";
        public static final String WELCOME = "/velocity/welcome.vm";
        public static final String PROFILE = "/velocity/profile/profile.vm";
        public static final String ALL_COURSES = "/velocity/course/list.vm";
        public static final String ALL_ENTRIES = "/velocity/entry/list.vm";
        public static final String ADD_COURSES = "/velocity/course/add.vm";

        public static final String STUDENT_SCHEDULE = "/velocity/student/schedule.vm";
    }

}
