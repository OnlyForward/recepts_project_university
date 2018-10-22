package com.example.a123.recepts_project_university.database;

public class ReceptsDbSchema {

    public static final class ReceptsTable{
        public static final String Name = "recepts";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String STEPS_COUNT = "steps_count";
            public static final String STEPS_DESCRIPTION = "steps_description";
            public static final String SAVED = "saved";
        }
    }

}
