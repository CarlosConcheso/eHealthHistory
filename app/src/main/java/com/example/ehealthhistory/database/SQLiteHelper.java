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
        inicializateAllValues(db);
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

    private void inicializateAllValues(SQLiteDatabase sql)
    {
        inicializateUserRoles(sql);
    }

    private void createTableUser(SQLiteDatabase sql)
    {
        String sentencia = "CREATE TABLE " + USERTABLE +
                "(" +
                "username TEXT PRIMARY KEY, " +
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
                "username TEXT NOT NULL, " +

                "CHECK(name IN('club' , 'careteam', 'footballer')), " +
                "FOREIGN KEY(id_user) REFERENCES USER(username)" +
                ")";

        sql.execSQL(sentencia);
    }

    private void inicializateUserRoles(SQLiteDatabase sql){

        String sentencia = "INSERT INTO " + USERTABLE + " VALUES('realoviedo','ROV')";
        sql.execSQL(sentencia);

        sentencia = "INSERT INTO " + USERTABLE + " VALUES('careteamrealoviedo','careteamROV')";
        sql.execSQL(sentencia);
        sentencia = "INSERT INTO " + USERTABLE + " VALUES('rodas','rodas')";
        sql.execSQL(sentencia);
        sentencia = "INSERT INTO " + USERTABLE + " VALUES('clinicasturias','clinicaasturiasoviedo')";
        sql.execSQL(sentencia);

        sentencia = "INSERT INTO " + USERTABLE + " VALUES('joanfemenias','joanfemeniasROV')";
        sql.execSQL(sentencia);
        sentencia = "INSERT INTO " + USERTABLE + " VALUES('tomeunadal','tomeunadalROV')";
        sql.execSQL(sentencia);
        sentencia = "INSERT INTO " + USERTABLE + " VALUES('carlosisaac','calosisaacROV')";
        sql.execSQL(sentencia);
        sentencia = "INSERT INTO " + USERTABLE + " VALUES('lucasahijado','lucasahijadoROV')";
        sql.execSQL(sentencia);
        sentencia = "INSERT INTO " + USERTABLE + " VALUES('davidcostas','davidcostasROV')";
        sql.execSQL(sentencia);
        sentencia = "INSERT INTO " + USERTABLE + " VALUES('danicalvo','danicalvoROV')";
        sql.execSQL(sentencia);

        sentencia = "INSERT INTO " + ROLTABLE + " VALUES(1,'club','realoviedo')";
        sql.execSQL(sentencia);
        sentencia = "INSERT INTO " + ROLTABLE + " VALUES(2,'careteam','careteamrealoviedo')";
        sql.execSQL(sentencia);
        sentencia = "INSERT INTO " + ROLTABLE + " VALUES(3,'careteam','rodas')";
        sql.execSQL(sentencia);
        sentencia = "INSERT INTO " + ROLTABLE + " VALUES(4,'careteam','clinicasturias')";
        sql.execSQL(sentencia);

        sentencia = "INSERT INTO " + ROLTABLE + " VALUES(5,'footballer','joanfemenias')";
        sql.execSQL(sentencia);
        sentencia = "INSERT INTO " + ROLTABLE + " VALUES(6,'footballer','tomeunadal')";
        sql.execSQL(sentencia);
        sentencia = "INSERT INTO " + ROLTABLE + " VALUES(7,'footballer','carlosisaac')";
        sql.execSQL(sentencia);
        sentencia = "INSERT INTO " + ROLTABLE + " VALUES(8,'footballer','lucasahijado')";
        sql.execSQL(sentencia);
        sentencia = "INSERT INTO " + ROLTABLE + " VALUES(9,'footballer','davidcostas')";
        sql.execSQL(sentencia);
        sentencia = "INSERT INTO " + ROLTABLE + " VALUES(10,'footballer','danicalvo')";
        sql.execSQL(sentencia);

    }

    private void createTableClub(SQLiteDatabase sql)
    {
        String sentencia = "CREATE TABLE " + CLUBTABLE +
                "(" +
                "username TEXT NOT NULL, " +
                "active TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "president TEXT NOT NULL, " +
                "alias TEXT NOT NULL, " +
                "contact_name TEXT NOT NULL, " +

                "CHECK(active IN('yes' , 'no')), " +
                "PRIMARY KEY(username), " +
                "FOREIGN KEY(username) REFERENCES USER(username)" +
                ")";

        sql.execSQL(sentencia);
    }

    private void createTableFootballer(SQLiteDatabase sql)
    {
        String sentencia = "CREATE TABLE " + FOOTBALLERTABLE +
                "(" +
                "username TEXT NOT NULL, " +
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
                "PRIMARY KEY(username), " +
                "FOREIGN KEY(username) REFERENCES USER(username), " +
                "FOREIGN KEY(id_club) REFERENCES " + CLUBTABLE + "(id)" +
                ")";

        sql.execSQL(sentencia);
    }

    private void createTableCareTeam(SQLiteDatabase sql)
    {
        String sentencia = "CREATE TABLE " + CARETEAMTABLE +
                "(" +
                "username TEXT NOT NULL, " +
                "status TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "telecom TEXT NOT NULL, " +
                "note TEXT NOT NULL, " +

                "CHECK(status IN('active' , 'suspended', 'inactive', 'entered_in_error')), " +
                "PRIMARY KEY(username), " +
                "FOREIGN KEY(username) REFERENCES USER(username)" +
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
                "username TEXT NOT NULL, " +


                "CHECK(active IN('yes' , 'no')), " +
                "CHECK(all_day IN('yes' , 'no')), " +
                "FOREIGN KEY(username) REFERENCES " + FOOTBALLERTABLE + "(username)," +
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