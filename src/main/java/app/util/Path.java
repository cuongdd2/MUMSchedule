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
        public static final String STUDENTSCHEDULE = "/student/schedule";

        @Getter
        public static final String LOGOUT = "/logout/";
        @Getter
        public static final String BOOKS = "/books/";
        @Getter
        public static final String ENTRY = "/entry/";
        @Getter
        public static final String ENTRY_ADD = "/entry/add";
        @Getter
        public static final String ENTRY_DELETE = "/entry/remove";
        @Getter
        public static final String ENTRY_UPDATE = "/entry/update";
        @Getter
        public static final String ENTRY_LIST = "/entry/list";
        @Getter
        public static final String COURSES = "/course/";

        @Getter
        public static final String SECTION = "/section/";

        @Getter
        public static final String STUDENT = "/student/";

        @Getter
        public static final String COURSE = "/course/";

        @Getter
        public static final String CHANGE = "/course/change";

        @Getter
        public static final String LIST = "/course/";

        @Getter
        public static final String ADD = "/course/add";

        @Getter
        public static final String DELETECOURSE = "/course/remove";

        @Getter
        public static final String ADDSECTION = "/section/add";
        @Getter
        public static final String DELETESECTION = "/section/remove";
        @Getter
        public static final String CHANGESECTION = "/section/change";

        //BLOCK
        @Getter
        public static final String BLOCK = "/block/";
        @Getter
        public static final String CHANGEBLOCK = "/block/change";
        @Getter
        public static final String BLOCKLIST = "/block/list";
        @Getter
        public static final String ADDBLOCK = "/block/add";
        @Getter
        public static final String DELETEBLOCK = "/block/remove";

        //Professor
        @Getter
        public static final String ALLPROFESSORS = "/professor/list";
        @Getter
        public static final String ADDPROFESSORS = "/professor/add";
        @Getter
        public static final String UPDATEPROFESSOR = "/professor/change";
        @Getter
        public static final String DELETEPROFESSOR = "/professor/remove";
        @Getter
        public static final String PROFESSOR = "/professor/";



        @Getter public static final String USER_LIST = "/user/";
        @Getter public static final String USER_ADD = "/user/add";
        @Getter public static final String USER_CHANGE = "/user/change";
        @Getter public static final String USER_REMOVE = "/user/remove";
        @Getter public static final String USER = "/user/";

    }

    public static class Template {
        public final static String INDEX = "/velocity/index.vm";
        public final static String LOGIN = "/velocity/login/login.vm";
        public static final String NOT_FOUND = "/velocity/notFound.vm";
        public static final String WELCOME = "/velocity/welcome.vm";
        public static final String PROFILE = "/velocity/profile/profile.vm";
        public static final String ALL_COURSES = "/velocity/course/list.vm";
        public static final String ALL_BLOCKS = "/velocity/block/list.vm";
        public static final String ALL_ENTRIES = "/velocity/entry/list.vm";
        public static final String ADD_COURSES = "/velocity/course/add.vm";
        public static final String ALL_PROFESSORS="/velocity/professor/list.vm";

        public static final String ALL_SECTIONS = "/velocity/section/list.vm";

        public static final String STUDENT_SCHEDULE = "/velocity/student/schedule.vm";

        public static final String ALL_USERS = "/velocity/user/list.vm";
        public static final String ADD_USER = "/velocity/user/add.vm";
    }

}
