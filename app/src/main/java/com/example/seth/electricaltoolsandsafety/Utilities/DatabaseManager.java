package com.example.seth.electricaltoolsandsafety.Utilities;

import com.example.seth.electricaltoolsandsafety.Tools.Tool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates and Manages a Database of Tool Objects.
 */
public class DatabaseManager extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Tools";
    public static final String ID_FIELD = "_id";
    public static final String TITLE_FIELD = "title";
    public static final String DATE_FIELD = "date";
    public static final String SNAPSHOT_FIELD = "snapshot";
    public static final String DETAILS_FIELD = "details";

    /**
     * Default Constructor
     * @param context
     */
    public DatabaseManager(Context context) {
        super(context, "tool_database", null, 1);
    }

    /**
     * Creates a SQLite database of Tool Objects.
     * @param db to create
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_FIELD + " INTEGER, "
                + TITLE_FIELD + " TEXT,"
                + DATE_FIELD + " TEXT,"
                + SNAPSHOT_FIELD + " TEXT,"
                + DETAILS_FIELD + " TEXT,"
                + " PRIMARY KEY (" + ID_FIELD + "));";

        db.execSQL(sql);
    }

    /**
     * Upgrades or creates a new SQLite Database of Tool Objects.
     * @param db SQLite Database
     * @param oldVersion of the SQLite Database
     * @param newVersion of the SQLite Database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db); // re-create sql table
    }

    /**
     * Adds a tool to the database
     * @param tool to add
     * @return tool added
     */
    public Tool addTool(Tool tool) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE_FIELD, tool.getTitle());
        values.put(DATE_FIELD, tool.getDate());
        values.put(SNAPSHOT_FIELD, tool.getSnapshot());
        values.put(DETAILS_FIELD, tool.getDetails());

        long id = db.insert(TABLE_NAME, null, values);
        tool.setId(id);
        db.close();
        return tool;
    }

    /**
     * Deletes a Tool Object from the Tool database based of the id.
     *
     * @param id of the Tool Object.
     */
    public void deleteTool(long id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, ID_FIELD + " = ?",
                new String[]{String.valueOf(id)});

        db.close();
    }

    /**
     * Updates a Tool Object with modified data.
     *
     * @param tool to update.
     * @return
     */
    public int updateTool(Tool tool) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE_FIELD, tool.getTitle());
        values.put(DATE_FIELD, tool.getDate());
        values.put(SNAPSHOT_FIELD, tool.getSnapshot());
        values.put(DETAILS_FIELD, tool.getDetails());

        return db.update(TABLE_NAME, values, ID_FIELD + " = ?",
                new String[]{String.valueOf(tool.getId())});
    }

    /**
     * Return a Tool Object from the database based off the user id.
     *
     * @param id of the tool to return
     * @return tool object with the specified id
     */
    public Tool getTool(long id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{ID_FIELD, TITLE_FIELD, DATE_FIELD, SNAPSHOT_FIELD, DETAILS_FIELD},
                ID_FIELD + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();

            Tool tool = new Tool(cursor.getLong(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4));

            db.close();

            return tool;
        }

        return null;
    }

    /**
     * Returns a sorted list of Tool Objects.
     * @return
     */
    public Cursor getToolCursor() {

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + DATE_FIELD + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();

        return db.rawQuery(selectQuery, null);
    }

    /**
     * Returns a complete list of Tool objects in the database.
     * @return list of Tool Objects
     */
    public List<Tool> getAllTools() {

        List<Tool> tools = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + DATE_FIELD + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            Tool tool = new Tool();
            tool.setId(Integer.parseInt(cursor.getString(0)));
            tool.setTitle(cursor.getString(1));
            tool.setDate(cursor.getString(2));
            tool.setSnapshot(cursor.getString(3));
            tool.setDetails(cursor.getString(4));
            tools.add(tool);
        }

        db.close();

        return tools;
    }

    /**
     * Deletes the entire contents of a Tool database.
     */
    public void deleteAll() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
