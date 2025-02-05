package com.easyfitness.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.easyfitness.DAO.bodymeasures.BodyMeasure;
import com.easyfitness.DAO.bodymeasures.DAOBodyMeasure;
import com.easyfitness.DAO.record.DAORecord;
import com.easyfitness.DAO.record.Record;
import com.easyfitness.utils.DateConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAOProfile extends DAOBase {

    // Contacts table name
    public static final String TABLE_NAME = "EFprofil";

    public static final String KEY = "_id";
    public static final String NAME = "name";
    public static final String CREATIONDATE = "creationdate";
    public static final String SIZE = "size";
    public static final String BIRTHDAY = "birthday";
    public static final String PHOTO = "photo";
    public static final String GENDER = "gender";

    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CREATIONDATE + " DATE, " + NAME + " TEXT, " + SIZE + " INTEGER, " + BIRTHDAY + " DATE, " + PHOTO + " TEXT, " + GENDER + " INTEGER);";

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    private Cursor mCursor = null;

    //DAOFonte mDAOFonte = null;


    public DAOProfile(Context context) {
        super(context);
    }

    /**
     * @param m DBOProfil Profile a ajouter a la base
     */
    public void addProfile(Profile m) {
        // Check if profile already exists
        Profile check = getProfile(m.getName());
        if (check != null) return;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();

        value.put(DAOProfile.CREATIONDATE, DateConverter.dateToDBDateStr(new Date()));
        value.put(DAOProfile.NAME, m.getName());
        value.put(DAOProfile.BIRTHDAY, DateConverter.dateToDBDateStr(m.getBirthday()));
        value.put(DAOProfile.SIZE, 0);
        value.put(DAOProfile.PHOTO, m.getPhoto());
        value.put(DAOProfile.GENDER, m.getGender());

        db.insert(DAOProfile.TABLE_NAME, null, value);

        close();
    }

    /**
     * @param pName String Nom du profil a ajouter a la base
     */
    public void addProfile(String pName) {
        // Check if profil already exists
        Profile check = getProfile(pName);
        if (check != null) return;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();

        value.put(DAOProfile.CREATIONDATE, DateConverter.dateToDBDateStr(new Date()));
        value.put(DAOProfile.NAME, pName);

        db.insert(DAOProfile.TABLE_NAME, null, value);

        close();
    }

    /**
     * @param id long id of the Profile
     */
    public Profile getProfile(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (mCursor != null) mCursor.close();
        mCursor = null;
        mCursor = db.query(TABLE_NAME,
                new String[]{KEY, CREATIONDATE, NAME, SIZE, BIRTHDAY, PHOTO, GENDER},
                KEY + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);
        if (mCursor != null && mCursor.getCount() > 0) {
            mCursor.moveToFirst();

            Profile value = new Profile(mCursor.getLong(mCursor.getColumnIndex(DAOProfile.KEY)),
                    DateConverter.DBDateStrToDate(mCursor.getString(mCursor.getColumnIndex(DAOProfile.CREATIONDATE))),
                    mCursor.getString(mCursor.getColumnIndex(DAOProfile.NAME)),
                    mCursor.getInt(mCursor.getColumnIndex(DAOProfile.SIZE)),
                    mCursor.getString(mCursor.getColumnIndex(DAOProfile.BIRTHDAY)) != null ? DateConverter.DBDateStrToDate(mCursor.getString(mCursor.getColumnIndex(DAOProfile.BIRTHDAY))) : new Date(0),
                    mCursor.getString(mCursor.getColumnIndex(DAOProfile.PHOTO)),
                    mCursor.getInt(mCursor.getColumnIndex(DAOProfile.GENDER))
            );
            mCursor.close();
            close();

            // return value
            return value;
        } else {
            mCursor.close();
            close();
            return null;
        }

    }

    /**
     * @param name String name of the Profile
     */
    public Profile getProfile(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (mCursor != null) mCursor.close();
        mCursor = null;
        mCursor = db.query(TABLE_NAME,
                new String[]{KEY, CREATIONDATE, NAME, SIZE, BIRTHDAY, PHOTO, GENDER},
                NAME + "=?",
                new String[]{name},
                null, null, null, null);
        if (mCursor != null && mCursor.getCount() > 0) {
            mCursor.moveToFirst();

            Profile value = new Profile(mCursor.getLong(mCursor.getColumnIndex(DAOProfile.KEY)),
                    DateConverter.DBDateStrToDate(mCursor.getString(mCursor.getColumnIndex(DAOProfile.CREATIONDATE))),
                    mCursor.getString(mCursor.getColumnIndex(DAOProfile.NAME)),
                    mCursor.getInt(mCursor.getColumnIndex(DAOProfile.SIZE)),
                    mCursor.getString(mCursor.getColumnIndex(DAOProfile.BIRTHDAY)) != null ? DateConverter.DBDateStrToDate(mCursor.getString(mCursor.getColumnIndex(DAOProfile.BIRTHDAY))) : new Date(0),
                    mCursor.getString(mCursor.getColumnIndex(DAOProfile.PHOTO)),
                    mCursor.getInt(mCursor.getColumnIndex(DAOProfile.GENDER))
            );

            mCursor.close();
            close();

            // return value
            return value;
        } else {
            close();
            return null;
        }

    }

    // Getting All Profils
    public List<Profile> getProfilesList(SQLiteDatabase db, String pRequest) {
        List<Profile> valueList = new ArrayList<>();
        // Select All Query

        mCursor = null;
        mCursor = db.rawQuery(pRequest, null);

        // looping through all rows and adding to list
        if (mCursor.moveToFirst()) {
            do {
                Profile value = new Profile(mCursor.getLong(mCursor.getColumnIndex(DAOProfile.KEY)),
                        DateConverter.DBDateStrToDate(mCursor.getString(mCursor.getColumnIndex(DAOProfile.CREATIONDATE))),
                        mCursor.getString(mCursor.getColumnIndex(DAOProfile.NAME)),
                        mCursor.getInt(mCursor.getColumnIndex(DAOProfile.SIZE)),
                        mCursor.getString(mCursor.getColumnIndex(DAOProfile.BIRTHDAY)) != null ? DateConverter.DBDateStrToDate(mCursor.getString(mCursor.getColumnIndex(DAOProfile.BIRTHDAY))) : new Date(0),
                        mCursor.getString(mCursor.getColumnIndex(DAOProfile.PHOTO)),
                        mCursor.getInt(mCursor.getColumnIndex(DAOProfile.GENDER))
                );

                // Adding value to list
                valueList.add(value);
            } while (mCursor.moveToNext());
        }

        //close();

        // return value list
        return valueList;
    }

    // Getting All Profils
    public List<Profile> getAllProfiles(SQLiteDatabase db) {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + KEY + " DESC";

        // return value list
        return getProfilesList(db, selectQuery);
    }

    // Getting All Machines
    public String[] getAllProfile() {

        SQLiteDatabase db = this.getReadableDatabase();
        mCursor = null;

        // Select All Machines
        String selectQuery = "SELECT DISTINCT  " + NAME + " FROM " + TABLE_NAME + " ORDER BY " + NAME + " ASC";
        mCursor = db.rawQuery(selectQuery, null);

        int size = mCursor.getCount();

        String[] valueList = new String[size];

        // looping through all rows and adding to list
        if (mCursor.moveToFirst()) {
            int i = 0;
            do {
                String value = mCursor.getString(0);
                valueList[i] = value;
                i++;
            } while (mCursor.moveToNext());
        }

        //close();

        // return value list
        return valueList;
    }

    // Getting last record
    public Profile getLastProfile() {

        SQLiteDatabase db = this.getReadableDatabase();
        mCursor = null;

        // Select All Machines
        String selectQuery = "SELECT MAX(" + KEY + ") FROM " + TABLE_NAME;
        mCursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        mCursor.moveToFirst();
        long value = Long.parseLong(mCursor.getString(0));

        Profile prof = this.getProfile(value);
        mCursor.close();
        close();

        // return value list
        return prof;
    }

    // Updating single value
    public int updateProfile(Profile m) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(DAOProfile.NAME, m.getName());
        value.put(DAOProfile.BIRTHDAY, DateConverter.dateToDBDateStr(m.getBirthday()));
        value.put(DAOProfile.SIZE, 0);
        value.put(DAOProfile.PHOTO, m.getPhoto());
        value.put(DAOProfile.GENDER, m.getGender());

        // updating row
        return db.update(TABLE_NAME, value, KEY + " = ?",
                new String[]{String.valueOf(m.getId())});
    }

    // Deleting single Profile
    public void deleteProfile(Profile m) {
        deleteProfile(m.getId());
    }

    // Deleting single Profile
    public void deleteProfile(long id) {
        open();

        // Supprime les enregistrements de poids
        DAOProfileWeight mWeightDb = new DAOProfileWeight(null); // null car a ce moment le DatabaseHelper est cree depuis bien longtemps.
        List<ProfileWeight> valueList = mWeightDb.getWeightList(getProfile(id));
        for (int i = 0; i < valueList.size(); i++) {
            mWeightDb.deleteMeasure(valueList.get(i).getId());
        }

        // Supprime les enregistrements de measure de body
        DAOBodyMeasure mBodyDb = new DAOBodyMeasure(null); // null car a ce moment le DatabaseHelper est cree depuis bien longtemps.
        List<BodyMeasure> bodyMeasuresList = mBodyDb.getBodyMeasuresList(getProfile(id));
        for (int i = 0; i < bodyMeasuresList.size(); i++) {
            mBodyDb.deleteMeasure(bodyMeasuresList.get(i).getId());
        }

        DAORecord mDbRecords = new DAORecord(null);
        List<Record> recordList = mDbRecords.getAllRecordsByProfileList(getProfile(id));
        for (int i = 0; i < recordList.size(); i++) {
            mDbRecords.deleteRecord(recordList.get(i).getId());
        }

        // Supprime le profile
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY + " = ?",
                new String[]{String.valueOf(id)});

        close();
    }


    // Getting Profils Count
    public int getCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        open();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int value = cursor.getCount();
        cursor.close();
        close();

        // return count
        return value;
    }

    /* DEBUG ONLY */
    public void populate() {
        Date date = new Date();
        Date dateBirthday = DateConverter.getNewDate();
        Profile m = new Profile(0, date, "Champignon", 120, dateBirthday, null, 0);
        this.addProfile(m);
        m = new Profile(0, date, "Musclor", 150, dateBirthday, null, 0);
        this.addProfile(m);
    }
}
