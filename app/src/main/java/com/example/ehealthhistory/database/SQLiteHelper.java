package com.example.ehealthhistory.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "eHealthHistory";
    private static final int VERSION = 1;

    private static final String USERTABLE = "USER";
    private static final String ROLTABLE = "ROL";
    private static final String FOOTBALLERTABLE = "FOOTBALLER";
    private static final String FOOTBALLERCARETEAMTABLE = "FOOTBALLER_CARETEAM";
    private static final String CARETEAMTABLE = "CARETEAM";
    private static final String CLUBTABLE = "CLUB";
    private static final String HEALTHCARETABLE = "HEALTHCARE";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableUser(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropAllTables(db);
        createAllTables(db);
    }

    protected void createAllTables(SQLiteDatabase sql)
    {
        createTableUser(sql);
        createTableRol(sql);
        createTableClub(sql);
        createTableCareTeam(sql);
        createTableFootballer(sql);
        createTableHealthCare(sql);
        createTableFootballerCareTeam(sql);
    }

    protected void dropAllTables(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE " + FOOTBALLERCARETEAMTABLE);
        db.execSQL("DROP TABLE " + HEALTHCARETABLE);
        db.execSQL("DROP TABLE " + FOOTBALLERTABLE);
        db.execSQL("DROP TABLE " + CLUBTABLE);
        db.execSQL("DROP TABLE " + CARETEAMTABLE);
        db.execSQL("DROP TABLE " + USERTABLE);
        db.execSQL("DROP TABLE " + ROLTABLE);
    }

    private void createTableUser(SQLiteDatabase sql)
    {
        String sentencia = "CREATE TABLE " + USERTABLE +
                "(" +
                "id INTEGER PRIMARY KEY, " +
                "username TEXT NOT NULL, " +
                "pass TEXT NOT NULL" +
                ")";

        sql.execSQL(sentencia);
    }

    private void createTableRol(SQLiteDatabase sql)
    {
        String sentencia = "CREATE TABLE " + ROLTABLE +
                "(" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "id_user INTEGER NOT NULL, " +
                "FOREIGN KEY(id_user) REFERENCES USER(id)" +
                ")";

        sql.execSQL(sentencia);
    }

    private void createTableClub(SQLiteDatabase sql)
    {
        String sentencia = "CREATE TABLE " + CLUBTABLE +
                "(" +
                "id INTEGER, " +
                "active TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "president TEXT NOT NULL, " +
                "alias TEXT NOT NULL, " +
                "contact_name TEXT NOT NULL, " +

                "CHECK(active IN('yes' , 'no')), " +
                "PRIMARY KEY(id), " +
                "FOREIGN KEY(id_user) REFERENCES USER(id)" +
                ")";

        sql.execSQL(sentencia);
    }

    private void createTableFootballer(SQLiteDatabase sql)
    {
        String sentencia = "CREATE TABLE " + FOOTBALLERTABLE +
                "(" +
                "id INTEGER, " +
                "active TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "username TEXT NOT NULL, " +
                "telecom TEXT NOT NULL, " +
                "birthday TEXT NOT NULL, " +
                "gender TEXT NOT NULL, " +
                "contact_name TEXT NOT NULL, " +
                "contact_address TEXT NOT NULL, " +
                "contact_telecom TEXT NOT NULL, " +
                "comunication_lenguaje TEXT NOT NULL, " +
                "comunication_prefered TEXT NOT NULL, " +
                "id_club INTEGER NOT NULL, " +


                "CHECK(active IN('yes' , 'no')), " +
                "CHECK(gender IN('male','female','other','unknow')), " +
                "PRIMARY KEY(id), " +
                "FOREIGN KEY(id_user) REFERENCES USER(id), " +
                "FOREIGN KEY(id_club) REFERENCES " + CLUBTABLE + "(id)" +
                ")";

        sql.execSQL(sentencia);
    }

    private void createTableCareTeam(SQLiteDatabase sql)
    {
        String sentencia = "CREATE TABLE " + CARETEAMTABLE +
                "(" +
                "id INTEGER, " +
                "status TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "telecom TEXT NOT NULL, " +
                "note TEXT NOT NULL, " +

                "CHECK(status IN('active' , 'suspended', 'inactive', 'entered_in_error')), " +
                "PRIMARY KEY(id), " +
                "FOREIGN KEY(id_user) REFERENCES USER(id)" +
                ")";

        sql.execSQL(sentencia);
    }

    private void createTableHealthCare(SQLiteDatabase sql)
    {
        String sentencia = "CREATE TABLE " + HEALTHCARETABLE +
                "(" +
                "id INTEGER, " +
                "active TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "category TEXT NOT NULL, " +
                "extradetails TEXT NOT NULL, " +
                "days_of_week TEXT NOT NULL, " +
                "all_day TEXT NOT NULL, " +
                "start_time TEXT NOT NULL, " +
                "end_time TEXT NOT NULL, " +
                "id_footballer INTEGER NOT NULL, " +


                "CHECK(active IN('yes' , 'no')), " +
                "CHECK(all_day IN('yes' , 'no')), " +
                "FOREIGN KEY(id_footballer) REFERENCES " + FOOTBALLERTABLE + "(id)," +
                "PRIMARY KEY(id) " +
                ")";

        sql.execSQL(sentencia);
    }

    private void createTableFootballerCareTeam(SQLiteDatabase sql)
    {
        String sentencia = "CREATE TABLE " + FOOTBALLERCARETEAMTABLE +
                "(" +
                "id_footballer INTEGER NOT NULL, " +
                "id_careteam INTEGER NOT NULL, " +

                "PRIMARY KEY(id_footballer, id_careteam), " +
                "FOREIGN KEY(id_footballer) REFERENCES " + FOOTBALLERTABLE + "(id) " +
                ")";

        sql.execSQL(sentencia);
    }

    private void initDatabaseData(SQLiteDatabase sql)
    {

    }
}