package com.aone.menurandomchoice.repository.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.aone.menurandomchoice.GlobalApplication;
import com.aone.menurandomchoice.repository.model.MenuDetail;
import com.aone.menurandomchoice.repository.model.StoreDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import androidx.annotation.NonNull;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class SqliteDatabaseRepository extends SQLiteOpenHelper implements SqliteDatabaseHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "randommenu";

    private Context mContext;

    private static SqliteDatabaseRepository sqliteDatabaseRepository;

    public static SqliteDatabaseRepository getInstance() {
        if(sqliteDatabaseRepository == null) {
            sqliteDatabaseRepository = new SqliteDatabaseRepository(GlobalApplication.getGlobalApplicationContext());
        }

        return sqliteDatabaseRepository;
    }

    private final String SQL_CREATE_STORES_TABLE = "CREATE TABLE " +
            StoreTable.TABLE_NAME.getColumnName() + " (" +
            StoreTable.STORES_IDX.getColumnName() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            StoreTable.STORES_NAME.getColumnName() + " TEXT NULL, " +
            StoreTable.STORES_TIME.getColumnName() + " TEXT NULL, " +
            StoreTable.STORES_ADDRESS.getColumnName() + " TEXT NULL, " +
            StoreTable.STORES_DESCRIPTION.getColumnName() + " TEXT NULL, " +
            StoreTable.STORES_LATITUDE.getColumnName() + " REAL NOT NULL DEFAULT 0, " +
            StoreTable.STORES_LONGITUDE.getColumnName() + " REAL NOT NULL DEFAULT 0, " +
            StoreTable.STORES_UPDATETIME.getColumnName() + " TEXT NOT NULL DEFAULT (datetime('now', 'localtime')))";


    private final String SQL_CREATE_MENU_TABLE = "CREATE TABLE " +
            MenuTable.TABLE_NAME.getColumnName() + " (" +
            MenuTable.MENU_IDX.getColumnName() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MenuTable.MENU_NAME.getColumnName() + " TEXT NULL, " +
            MenuTable.MENU_PHOTO_URL.getColumnName() + " TEXT NULL, " +
            MenuTable.MENU_PRICE.getColumnName() + " INTEGER NOT NULL DEFAULT 0, " +
            MenuTable.MENU_DESCRIPTION.getColumnName() + " TEXT NULL, " +
            MenuTable.MENU_CATEGORY.getColumnName() + " TEXT NULL, " +
            MenuTable.MENU_SEQUENCE.getColumnName() + " INTEGER NOT NULL)" ;


    public SqliteDatabaseRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_STORES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MENU_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StoreTable.TABLE_NAME.getColumnName());
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MenuTable.TABLE_NAME.getColumnName());
        onCreate(sqLiteDatabase);
    }


    @Override
    public void addStoreDetail() {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            db.insertOrThrow(StoreTable.TABLE_NAME.getColumnName(), null, null);

            for(int sequence = 1; sequence <= 3; sequence++) {
                addMenuDetail(db, sequence);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error adding Store detail");
        } finally {
            db.endTransaction();
        }

    }


    public void addMenuDetail(@NonNull SQLiteDatabase db, final int sequence) {
        try {
            ContentValues values = new ContentValues();
            values.put(MenuTable.MENU_SEQUENCE.getColumnName(), sequence);

            db.insertOrThrow(MenuTable.TABLE_NAME.getColumnName(), null, values);
        } catch (Exception e) {
            Log.d(TAG, "Error adding Menu detail");
        }
    }

    @Override
    public StoreDetail getStoreDetail() {
        StoreDetail storeDetail = new StoreDetail();

        String STOREDETAIL_SELECT_QUERY = String.format("SELECT * FROM %s", StoreTable.TABLE_NAME.getColumnName());

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(STOREDETAIL_SELECT_QUERY, null);
        
        try {
            while(cursor.moveToNext()) {
                storeDetail.setName(cursor.getString(cursor.getColumnIndex(StoreTable.STORES_NAME.getColumnName())));
                storeDetail.setTime(cursor.getString(cursor.getColumnIndex(StoreTable.STORES_TIME.getColumnName())));
                storeDetail.setAddress(cursor.getString(cursor.getColumnIndex(StoreTable.STORES_ADDRESS.getColumnName())));
                storeDetail.setDescription(cursor.getString(cursor.getColumnIndex(StoreTable.STORES_DESCRIPTION.getColumnName())));
                storeDetail.setLatitude(cursor.getDouble(cursor.getColumnIndex(StoreTable.STORES_LATITUDE.getColumnName())));
                storeDetail.setLongitude(cursor.getDouble(cursor.getColumnIndex(StoreTable.STORES_LONGITUDE.getColumnName())));
                storeDetail.setMenuDetailList(getMenuDetailList());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error Get Store Detail");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return storeDetail;
    }


    public List<MenuDetail> getMenuDetailList() {
        List<MenuDetail> menuDetails = new ArrayList<>();

        String MenuDetails_SELECT_QUERY =
                String.format("SELECT * FROM %s", MenuTable.TABLE_NAME.getColumnName());

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(MenuDetails_SELECT_QUERY, null);

        try {
            while(cursor.moveToNext()) {
                MenuDetail menuDetail = new MenuDetail();
                menuDetail.setName(cursor.getString(cursor.getColumnIndex(MenuTable.MENU_NAME.getColumnName())));
                menuDetail.setPhotoUrl(cursor.getString(cursor.getColumnIndex(MenuTable.MENU_PHOTO_URL.getColumnName())));
                menuDetail.setPrice(cursor.getInt(cursor.getColumnIndex(MenuTable.MENU_PRICE.getColumnName())));
                menuDetail.setCategory(cursor.getString(cursor.getColumnIndex(MenuTable.MENU_CATEGORY.getColumnName())));
                menuDetail.setDescription(cursor.getString(cursor.getColumnIndex(MenuTable.MENU_DESCRIPTION.getColumnName())));
                menuDetail.setSequence(cursor.getInt(cursor.getColumnIndex(MenuTable.MENU_SEQUENCE.getColumnName())));
                menuDetails.add(menuDetail);
            }
        } catch (Exception e) {
            Log.d(TAG, "Error Get Menu Detail");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return menuDetails;
    }

    @Override
    public void updateStoreDetail(@NonNull final StoreDetail storeDetail) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(StoreTable.STORES_NAME.getColumnName(), storeDetail.getName());
            values.put(StoreTable.STORES_TIME.getColumnName(), storeDetail.getTime());
            values.put(StoreTable.STORES_ADDRESS.getColumnName(), storeDetail.getAddress());
            values.put(StoreTable.STORES_DESCRIPTION.getColumnName(), storeDetail.getDescription());
            values.put(StoreTable.STORES_LATITUDE.getColumnName(), storeDetail.getLatitude());
            values.put(StoreTable.STORES_LONGITUDE.getColumnName(), storeDetail.getLongitude());
            values.put(StoreTable.STORES_UPDATETIME.getColumnName(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            for(int i = 0; i < storeDetail.getMenuDetailList().size(); i++) {
                updateMenuDetail(db, storeDetail.getMenuDetailList().get(i));
            }

            db.update(StoreTable.TABLE_NAME.getColumnName(), values, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error Updating StoreDetail");
        } finally {
            db.endTransaction();
        }
    }

    public void updateMenuDetail(SQLiteDatabase db, @NonNull final MenuDetail menuDetail) {
        try {
            ContentValues values = new ContentValues();
            values.put(MenuTable.MENU_NAME.getColumnName(), menuDetail.getName());
            values.put(MenuTable.MENU_PHOTO_URL.getColumnName(), menuDetail.getPhotoUrl());
            values.put(MenuTable.MENU_PRICE.getColumnName(), menuDetail.getPrice());
            values.put(MenuTable.MENU_DESCRIPTION.getColumnName(), menuDetail.getDescription());
            values.put(MenuTable.MENU_CATEGORY.getColumnName(), menuDetail.getCategory());
            values.put(MenuTable.MENU_SEQUENCE.getColumnName(), menuDetail.getSequence());

            db.update(MenuTable.TABLE_NAME.getColumnName(), values,
                    MenuTable.MENU_SEQUENCE.getColumnName() + " = ?",
                    new String[] { String.valueOf(menuDetail.getSequence()) });
        } catch (Exception e) {
            Log.d(TAG, "Error Updating MenuDetail");
        }
    }
}
