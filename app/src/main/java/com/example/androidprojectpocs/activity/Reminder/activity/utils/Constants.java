package com.example.androidprojectpocs.activity.Reminder.activity.utils;


import com.example.androidprojectpocs.R;

public class Constants {

    public static final int EVENT_STATE_WRITE = 0;
    public static final int EVENT_STATE_READ = 1;

    public static final int COLOR_PINK = 1;
    public static final int COLOR_RED = 2;
    public static final int COLOR_BLUE = 3;
    public static final int COLOR_GREEN = 4;
    public static final int COLOR_YELLOW = 5;
    public static final int COLOR_GRAY = 7;
    public static final int COLOR_BLACK = 6;

    public static final String DATABASE_NAME = "afterAll.db";
    public static final String EVENT_TABLE_NAME = "event";
    public static final String KIND_TABLE_NAME = "kind";
    public static final String EVENT_COLUMN_ID = "event_id";
    public static final String EVENT_COLUMN_NAME = "event_name";
    public static final String KIND_COLUMN_ID = "kind_id";
    public static final String KIND_COLUMN_NAME = "kind_name";
    public static final String KIND_COLUMN_COLOR = "kind_color";
    public static final String EVENT_COLUMN_DATE = "event_date";
    public static final String EVENT_COLUMN_LOOP = "event_loop";
    //public static final String EVENT_COLUMN_NOTIFICATION = "event_notification";
    public static final String EVENT_COLUMN_IMAGE = "event_image";
    public static final String EVENT_COLUMN_STATE = "event_state";
    public static final String EVENT_COLUMN_ID_SYNC = "event_id_sync";
    public static final String EVENT_COLUMN_DELETED = "event_deleted";

    public static final int TASK_SYNC = 0;
    public static final int TASK_DELETE = 1;
    public static final int TASK_MODIFY = 2;
    public static final int TASK_ADD = 3;
    // references to our images
    public static Integer[] background = {
            R.drawable.header,
            R.drawable.header,
            R.drawable.header,
            R.drawable.header,
            R.drawable.header,
    };
}
