package com.example.footballapp.ui.footballclubs;

import static com.example.footballapp.ui.footballclubs.FootballClubsContract.FootballClubEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FootballClubsDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FootballClubs.db";
    public FootballClubsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FootballClubEntry.SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FootballClubEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addFootballClub(FootballClub footballClub){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FootballClubEntry.COLUMN_NAME_CLUB_ID, footballClub.getId());
        values.put(FootballClubEntry.COLUMN_NAME_NAME, footballClub.getName());
        values.put(FootballClubEntry.COLUMN_NAME_CITY, footballClub.getCity());
        values.put(FootballClubEntry.COLUMN_NAME_COUNTRY, footballClub.getCountry());
        values.put(FootballClubEntry.COLUMN_NAME_YEAR, footballClub.getYear());
        values.put(FootballClubEntry.COLUMN_NAME_RANK, footballClub.getRank());

        db.insert(FootballClubEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void removeAllFootballClubs(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FootballClubEntry.TABLE_NAME, null, null);
        db.close();
    }

    public List<FootballClub> getAllFootballClubs() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<FootballClub> footballClubs = new ArrayList<>();

        String[] projection = {
                FootballClubEntry.COLUMN_NAME_NAME,
                FootballClubEntry.COLUMN_NAME_RANK,
        };

        String sortOrder = FootballClubEntry.COLUMN_NAME_RANK + " DESC";
        Cursor cursor = db.query(FootballClubEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);

        if(cursor.moveToFirst()){
            do {
                FootballClub footballClub = new FootballClub();
                footballClub.setName(cursor.getString(cursor.getColumnIndexOrThrow(FootballClubEntry.COLUMN_NAME_NAME)));
                footballClub.setRank(cursor.getDouble(cursor.getColumnIndexOrThrow(FootballClubEntry.COLUMN_NAME_RANK)));
                footballClubs.add(footballClub);
            } while(cursor.moveToNext());
        }
        return footballClubs;
    }

    public List<FootballClub> getUkrainianFootballClubs() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<FootballClub> footballClubs = new ArrayList<>();

        String[] projection = {
                FootballClubEntry.COLUMN_NAME_NAME,
                FootballClubEntry.COLUMN_NAME_RANK,
        };

        String selection = FootballClubEntry.COLUMN_NAME_COUNTRY + " = ?";
        String[] selectionArgs = { "Ukraine" };

        String sortOrder = FootballClubEntry.COLUMN_NAME_RANK + " DESC";
        Cursor cursor = db.query(FootballClubEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

        if(cursor.moveToFirst()){
            do {
                FootballClub footballClub = new FootballClub();
                footballClub.setName(cursor.getString(cursor.getColumnIndexOrThrow(FootballClubEntry.COLUMN_NAME_NAME)));
                footballClub.setRank(cursor.getDouble(cursor.getColumnIndexOrThrow(FootballClubEntry.COLUMN_NAME_RANK)));
                footballClubs.add(footballClub);
            } while(cursor.moveToNext());
        }
        return footballClubs;
    }

    public double getTotalKyivFootballClubsRank() {
        SQLiteDatabase db = this.getReadableDatabase();
        double totalRank = 0.0;

        String query = "SELECT SUM(" + FootballClubEntry.COLUMN_NAME_RANK + ") AS total_rank FROM " +
                FootballClubEntry.TABLE_NAME + " WHERE " + FootballClubEntry.COLUMN_NAME_COUNTRY + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{"Ukraine"});
        if (cursor.moveToFirst()) {
            int index = cursor.getColumnIndex("total_rank");
            if (index >= 0){
                totalRank = cursor.getDouble(index);
            }
        }
        return totalRank;
    }
}