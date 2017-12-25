package weather.khdanapp.com.weatherapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataSourse {

    //bd work

    static private DatabaseHelper dbHelper;
    static private SQLiteDatabase database;

    public WeatherDataSourse(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addWeatherToDB(String city, String temp, String date) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CITY, city);
        values.put(DatabaseHelper.COLUMN_TEMP,temp);
        values.put(DatabaseHelper.COLUMN_DATE,date);
        long insertId = database.insert(DatabaseHelper.TABLE_NOTES, null,
                values);
//        Note newNote = new Note();
//        newNote.setNote(note);
//        newNote.setId(insertId);
//        return newNote;
    }

    public void deleteNote(long id) {
        //long id = note.getId();
        database.delete(DatabaseHelper.TABLE_NOTES, DatabaseHelper.COLUMN_ID
                + " = " + id, null);
    }
    private String[] notesAllColumn = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_CITY,
            DatabaseHelper.COLUMN_TEMP,
            DatabaseHelper.COLUMN_DATE
    };

    public List<WeatherInHistory> getAllWeatherHistory() {
        database = dbHelper.getWritableDatabase();
        List<WeatherInHistory> notes = new ArrayList<WeatherInHistory>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_NOTES,
                notesAllColumn, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            WeatherInHistory note = cursorToNote(cursor);
            notes.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return notes;
    }

    private WeatherInHistory cursorToNote(Cursor cursor) {
        WeatherInHistory note = new WeatherInHistory();
        note.setId(cursor.getLong(0));
        note.setCity(cursor.getString(1));
        note.setTemp(cursor.getString(2));
        note.setDate(cursor.getString(3));
        return note;
    }


}
