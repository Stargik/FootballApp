package com.example.footballapp.ui.footballclubs;

import android.provider.BaseColumns;

public final class FootballClubsContract {
    public FootballClubsContract(){}

    public static abstract class FootballClubEntry implements BaseColumns {
        public static final String TABLE_NAME = "footballclub";
        public static final String COLUMN_NAME_CLUB_ID = "clubid";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_CITY = "city";
        public static final String COLUMN_NAME_COUNTRY = "country";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_RANK = "rank";
        public static final String TEXT_TYPE = " TEXT";
        public static final String INTEGER_TYPE = " INTEGER";
        public static final String REAL_TYPE = " REAL";
        public static final String COMMA_SEP = ",";
        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FootballClubEntry.TABLE_NAME + " (" +
                        FootballClubEntry._ID + " INTEGER PRIMARY KEY," +
                        FootballClubEntry.COLUMN_NAME_CLUB_ID + TEXT_TYPE + COMMA_SEP +
                        FootballClubEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                        FootballClubEntry.COLUMN_NAME_CITY + TEXT_TYPE + COMMA_SEP +
                        FootballClubEntry.COLUMN_NAME_COUNTRY + TEXT_TYPE + COMMA_SEP +
                        FootballClubEntry.COLUMN_NAME_YEAR + INTEGER_TYPE + COMMA_SEP +
                        FootballClubEntry.COLUMN_NAME_RANK + REAL_TYPE +
                " )";
        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FootballClubEntry.TABLE_NAME;
    }
}
