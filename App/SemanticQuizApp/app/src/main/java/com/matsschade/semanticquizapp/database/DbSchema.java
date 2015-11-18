package com.matsschade.semanticquizapp.database;

import android.provider.BaseColumns;

/**
 * Created by Mats on 11/11/15.
 */
public final class DbSchema {

    public DbSchema() {}

    public static abstract class Questions implements BaseColumns {
        public static final String TABLE_NAME = "questions";

        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TEXT = "text";
        public static final String COLUMN_NAME_CATEGORY_ID = "categoryid";
    }

    public static abstract class Categories implements BaseColumns {
        public static final String TABLE_NAME = "categories";

        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TEXT = "text";
    }
}
