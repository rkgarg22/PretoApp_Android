package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import APIResponse.OperatingHour;
import APIResponse.ResturantObject;
import infrastructure.AppCommon;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Preto.db";
    public static final String TABLE_NAME = "Restuarant";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_REST_ID = "restID";
    public static final String COLUMN_REST_NAME = "restName";
    public static final String COLUMN_REST_DESCRIPTION = "description";
    public static final String COLUMN_REST_IS_HOME_DELIVERY = "isHomeDeliveryAvailable";
    public static final String COLUMN_REST_PAYMENT_METHOD = "paymentMethod";
    public static final String COLUMN_REST_PHONENUMBER = "phoneNumber";
    public static final String COLUMN_REST_ADDRESS = "address";
    public static final String COLUMN_REST_CATEGORY = "category";
    public static final String COLUMN_REST_IMAGES = "images";
    public static final String COLUMN_REST_IS_FAVOURITES = "isFavourite";
    public static final String COLUMN_REST_LIKES_COUNT = "likesCount";
    public static final String COLUMN_REST_MENU = "menu";
    public static final String COLUMN_REST_LATTITUDE = "lattitude";
    public static final String COLUMN_REST_LONGITUDE = "longitude";
    public static final String COLUMN_REST_ISACTIVE = "isActive";
    public static final String COLUMN_REST_DISTANCE = "distance";
    public static final String COLUMN_REST_HISTORY = "histroy";
    public static final String COLUMN_REST_PRICE_FROM = "priceFrom";

    public static final String COLUMN_REST_PRICE_TO = "priceTo";
    public static final String COLUMN_REST_REGISTERED_DATE = "registered_date";
    public static final String COLUMN_REST_FAV_COUNT = "favCount";
    public static final String COLUMN_REST_IS_LIKED = "isLiked";
    public static final String COLUMN_REST_COLOR = "color";

    public static final String COLUMN_REST_WEB_URL = "web_url";
    public static final String COLUMN_REST_INSTAGRAM_ACCOUNT = "instagram_account";
    public static final String COLUMN_REST_SERVICEPHONE = "servicePhone";
    public static final String COLUMN_REST_SERVICE_STATUS = "serviceStatus";

    public static final String COLUMN_REST_OPERATING_HOURS = "operatingHours";
    public static final String COLUMN_REST_TYPE_OF_FOOD = "typeOfFood";
    public static final String COLUMN_REST_OTHER = "other";
    public static final String COLUMN_REST_LANGUAGE = "language";

    public static DbHelper mInstance = null;
    static Context mContext;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static DbHelper getInstance(Context _Context) {
        if (mInstance == null) {
            mInstance = new DbHelper(_Context);
        }
        mContext = _Context;
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TABLE_NAME +
                        " (id integer primary key," +
                        COLUMN_REST_ID + " text," +
                        COLUMN_REST_NAME + " text," +
                        COLUMN_REST_DESCRIPTION + " text," +
                        COLUMN_REST_IS_HOME_DELIVERY + " text," +
                        COLUMN_REST_PAYMENT_METHOD + " text," +
                        COLUMN_REST_PHONENUMBER + " text," +
                        COLUMN_REST_ADDRESS + " text," +
                        COLUMN_REST_CATEGORY + " text," +
                        COLUMN_REST_IMAGES + " text," +
                        COLUMN_REST_IS_FAVOURITES + " text," +
                        COLUMN_REST_LIKES_COUNT + " text," +
                        COLUMN_REST_MENU + " text," +

                        COLUMN_REST_LATTITUDE + " text," +
                        COLUMN_REST_LONGITUDE + " text," +
                        COLUMN_REST_ISACTIVE + " text," +
                        COLUMN_REST_DISTANCE + " text," +
                        COLUMN_REST_HISTORY + " text," +
                        COLUMN_REST_PRICE_FROM + " text," +
                        COLUMN_REST_PRICE_TO + " text," +
                        COLUMN_REST_REGISTERED_DATE + " text," +
                        COLUMN_REST_FAV_COUNT + " text," +
                        COLUMN_REST_IS_LIKED + " text," +
                        COLUMN_REST_WEB_URL + " text," +
                        COLUMN_REST_COLOR + " text," +
                        COLUMN_REST_INSTAGRAM_ACCOUNT + " text," +
                        COLUMN_REST_SERVICEPHONE + " text," +
                        COLUMN_REST_SERVICE_STATUS + " text," +
                        COLUMN_REST_OPERATING_HOURS + " text," +
                        COLUMN_REST_TYPE_OF_FOOD + " text," +
                        COLUMN_REST_OTHER + " text," +
                        COLUMN_REST_LANGUAGE + " text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertResturant(ResturantObject obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_REST_ID, obj.getRestID());
        contentValues.put(COLUMN_REST_NAME, obj.getRestName());
        contentValues.put(COLUMN_REST_DESCRIPTION, obj.getDescription());
        contentValues.put(COLUMN_REST_IS_HOME_DELIVERY, obj.getIsHomeDeliveryAvailable());
        contentValues.put(COLUMN_REST_PAYMENT_METHOD, obj.getPaymentMethod());
        contentValues.put(COLUMN_REST_PHONENUMBER, obj.getPhoneNumber());
        contentValues.put(COLUMN_REST_ADDRESS, obj.getAddress());
        contentValues.put(COLUMN_REST_CATEGORY, obj.getCategory());
        contentValues.put(COLUMN_REST_IMAGES, obj.getImages());
        contentValues.put(COLUMN_REST_IS_FAVOURITES, obj.getIsFavourite());
        contentValues.put(COLUMN_REST_LIKES_COUNT, obj.getLikesCount());
        contentValues.put(COLUMN_REST_MENU, obj.getMenu());
        contentValues.put(COLUMN_REST_LATTITUDE, obj.getLattitude());
        contentValues.put(COLUMN_REST_LONGITUDE, obj.getLongitude());

        contentValues.put(COLUMN_REST_ISACTIVE, obj.getIsActive());
        contentValues.put(COLUMN_REST_DISTANCE, obj.getDistance());
        contentValues.put(COLUMN_REST_HISTORY, obj.getHistroy());
        contentValues.put(COLUMN_REST_PRICE_FROM, obj.getPriceFrom());
        contentValues.put(COLUMN_REST_PRICE_TO, obj.getPriceTo());
        contentValues.put(COLUMN_REST_REGISTERED_DATE, obj.getRegistered_date());
        contentValues.put(COLUMN_REST_FAV_COUNT, obj.getFavCount());
        contentValues.put(COLUMN_REST_IS_LIKED, obj.getIsLiked());
        contentValues.put(COLUMN_REST_COLOR, obj.getColor());
        contentValues.put(COLUMN_REST_WEB_URL, obj.getWebUrl());

        contentValues.put(COLUMN_REST_INSTAGRAM_ACCOUNT, obj.getInstagramAccount());
        contentValues.put(COLUMN_REST_SERVICEPHONE, obj.getServicePhone());
        contentValues.put(COLUMN_REST_SERVICE_STATUS, obj.getServiceStatus());

        Gson gson = new Gson();
        String operatingHours = gson.toJson(obj.getOperatingHourArrayList());
        String typeOFFood = gson.toJson(obj.getTypeOfFood());
        String other = gson.toJson(obj.getOther());

        contentValues.put(COLUMN_REST_OPERATING_HOURS, operatingHours);
        contentValues.put(COLUMN_REST_TYPE_OF_FOOD, typeOFFood);
        contentValues.put(COLUMN_REST_OTHER, other);
        contentValues.put(COLUMN_REST_LANGUAGE, AppCommon.getInstance(mContext).getSelectedLanguage());
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateRest(ResturantObject obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_REST_ID, obj.getRestID());
        contentValues.put(COLUMN_REST_NAME, obj.getRestName());
        contentValues.put(COLUMN_REST_DESCRIPTION, obj.getDescription());
        contentValues.put(COLUMN_REST_IS_HOME_DELIVERY, obj.getIsHomeDeliveryAvailable());
        contentValues.put(COLUMN_REST_PAYMENT_METHOD, obj.getPaymentMethod());
        contentValues.put(COLUMN_REST_PHONENUMBER, obj.getPhoneNumber());
        contentValues.put(COLUMN_REST_ADDRESS, obj.getAddress());
        contentValues.put(COLUMN_REST_CATEGORY, obj.getCategory());
        contentValues.put(COLUMN_REST_IMAGES, obj.getImages());
        contentValues.put(COLUMN_REST_IS_FAVOURITES, obj.getIsFavourite());
        contentValues.put(COLUMN_REST_LIKES_COUNT, obj.getLikesCount());
        contentValues.put(COLUMN_REST_MENU, obj.getMenu());
        contentValues.put(COLUMN_REST_LATTITUDE, obj.getLattitude());
        contentValues.put(COLUMN_REST_LONGITUDE, obj.getLongitude());

        contentValues.put(COLUMN_REST_ISACTIVE, obj.getIsActive());
        contentValues.put(COLUMN_REST_DISTANCE, obj.getDistance());
        contentValues.put(COLUMN_REST_HISTORY, obj.getHistroy());
        contentValues.put(COLUMN_REST_PRICE_FROM, obj.getPriceFrom());
        contentValues.put(COLUMN_REST_PRICE_TO, obj.getPriceTo());
        contentValues.put(COLUMN_REST_REGISTERED_DATE, obj.getRegistered_date());
        contentValues.put(COLUMN_REST_FAV_COUNT, obj.getFavCount());
        contentValues.put(COLUMN_REST_IS_LIKED, obj.getIsLiked());
        contentValues.put(COLUMN_REST_COLOR, obj.getColor());
        contentValues.put(COLUMN_REST_WEB_URL, obj.getWebUrl());

        contentValues.put(COLUMN_REST_INSTAGRAM_ACCOUNT, obj.getInstagramAccount());
        contentValues.put(COLUMN_REST_SERVICEPHONE, obj.getServicePhone());
        contentValues.put(COLUMN_REST_SERVICE_STATUS, obj.getServiceStatus());

        Gson gson = new Gson();
        String operatingHours = gson.toJson(obj.getOperatingHourArrayList());
        String typeOFFood = gson.toJson(obj.getTypeOfFood());
        String other = gson.toJson(obj.getOther());

        contentValues.put(COLUMN_REST_OPERATING_HOURS, operatingHours);
        contentValues.put(COLUMN_REST_TYPE_OF_FOOD, typeOFFood);
        contentValues.put(COLUMN_REST_OTHER, other);
        contentValues.put(COLUMN_REST_LANGUAGE, AppCommon.getInstance(mContext).getSelectedLanguage());
        db.update(TABLE_NAME, contentValues, COLUMN_REST_ID + " =  ? ", new String[]{obj.getRestID()});
        return true;
    }

    public Integer deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public boolean isResturantExist(String resID) {
        boolean isExist = true;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c = db.query(TABLE_NAME, null, COLUMN_REST_ID + "=?", new String[]{String.valueOf(resID)}, null, null, null);
            isExist = c.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExist;
    }

    public ArrayList<ResturantObject> getAllResturants() {
        ArrayList<ResturantObject> resturantObjectArrayList = new ArrayList<ResturantObject>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        if (res.moveToFirst()) {
            do {
                ResturantObject rest = new ResturantObject();
                rest.setRestID(res.getString(res.getColumnIndex(COLUMN_ID)));
                rest.setRestName(res.getString(res.getColumnIndex(COLUMN_REST_NAME)));
                rest.setDescription(res.getString(res.getColumnIndex(COLUMN_REST_DESCRIPTION)));
                rest.setIsHomeDeliveryAvailable(res.getString(res.getColumnIndex(COLUMN_REST_IS_HOME_DELIVERY)));
                rest.setPaymentMethod(res.getString(res.getColumnIndex(COLUMN_REST_PAYMENT_METHOD)));
                rest.setPhoneNumber(res.getString(res.getColumnIndex(COLUMN_REST_PHONENUMBER)));
                rest.setAddress(res.getString(res.getColumnIndex(COLUMN_REST_ADDRESS)));
                rest.setCategory(res.getString(res.getColumnIndex(COLUMN_REST_CATEGORY)));
                rest.setImages(res.getString(res.getColumnIndex(COLUMN_REST_IMAGES)));
                rest.setIsFavourite(res.getString(res.getColumnIndex(COLUMN_REST_IS_FAVOURITES)));
                rest.setLikesCount(res.getString(res.getColumnIndex(COLUMN_REST_LIKES_COUNT)));
                rest.setMenu(res.getString(res.getColumnIndex(COLUMN_REST_MENU)));
                rest.setLattitude(res.getString(res.getColumnIndex(COLUMN_REST_LATTITUDE)));
                rest.setLongitude(res.getString(res.getColumnIndex(COLUMN_REST_LONGITUDE)));
                rest.setIsActive(res.getString(res.getColumnIndex(COLUMN_REST_ISACTIVE)));
                rest.setDistance(res.getString(res.getColumnIndex(COLUMN_REST_DISTANCE)));
                rest.setHistroy(res.getString(res.getColumnIndex(COLUMN_REST_HISTORY)));
                rest.setPriceFrom(res.getString(res.getColumnIndex(COLUMN_REST_PRICE_FROM)));
                rest.setPriceTo(res.getString(res.getColumnIndex(COLUMN_REST_PRICE_TO)));
                rest.setRegistered_date(res.getString(res.getColumnIndex(COLUMN_REST_REGISTERED_DATE)));
                rest.setFavCount(res.getString(res.getColumnIndex(COLUMN_REST_FAV_COUNT)));

                rest.setIsLiked(res.getString(res.getColumnIndex(COLUMN_REST_IS_LIKED)));
                rest.setColor(res.getString(res.getColumnIndex(COLUMN_REST_COLOR)));
                rest.setWebUrl(res.getString(res.getColumnIndex(COLUMN_REST_WEB_URL)));
                rest.setInstagramAccount(res.getString(res.getColumnIndex(COLUMN_REST_INSTAGRAM_ACCOUNT)));
                rest.setServicePhone(res.getString(res.getColumnIndex(COLUMN_REST_SERVICEPHONE)));
                rest.setServiceStatus(res.getString(res.getColumnIndex(COLUMN_REST_SERVICE_STATUS)));

                Gson gson = new Gson();

                String operatingHours = res.getString(res.getColumnIndex(COLUMN_REST_OPERATING_HOURS));
                Type type = new TypeToken<ArrayList<OperatingHour>>() {
                }.getType();
                ArrayList<OperatingHour> operatingHourArrayList = gson.fromJson(operatingHours, type);
                rest.setOperatingHourArrayList(operatingHourArrayList);

                String typeOFFood = res.getString(res.getColumnIndex(COLUMN_REST_TYPE_OF_FOOD));
                Type typeOFFoodType = new TypeToken<ArrayList<String>>() {
                }.getType();
                ArrayList<String> typeOFFoodArrayList = gson.fromJson(typeOFFood, typeOFFoodType);
                rest.setTypeOfFood(typeOFFoodArrayList);

                String otherStr = res.getString(res.getColumnIndex(COLUMN_REST_OTHER));
                Type otherType = new TypeToken<ArrayList<String>>() {
                }.getType();
                ArrayList<String> otherArrayList = gson.fromJson(otherStr, otherType);
                rest.setOther(otherArrayList);

                resturantObjectArrayList.add(rest);
            } while (res.moveToNext());
        }
        return resturantObjectArrayList;
    }

    public ArrayList<ResturantObject> getResturantsListForCategory(String category) {
        ArrayList<ResturantObject> resturantObjectArrayList = new ArrayList<ResturantObject>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " where " + COLUMN_REST_CATEGORY + " LIKE '%" + category + "%' AND " + COLUMN_REST_LANGUAGE + " ='" + AppCommon.getInstance(mContext).getSelectedLanguage() + "'";
        Cursor res = db.rawQuery(sql, null);
        if (res.moveToFirst()) {
            do {
                ResturantObject rest = new ResturantObject();
                rest.setRestID(res.getString(res.getColumnIndex(COLUMN_ID)));
                rest.setRestName(res.getString(res.getColumnIndex(COLUMN_REST_NAME)));
                rest.setDescription(res.getString(res.getColumnIndex(COLUMN_REST_DESCRIPTION)));
                rest.setIsHomeDeliveryAvailable(res.getString(res.getColumnIndex(COLUMN_REST_IS_HOME_DELIVERY)));
                rest.setPaymentMethod(res.getString(res.getColumnIndex(COLUMN_REST_PAYMENT_METHOD)));
                rest.setPhoneNumber(res.getString(res.getColumnIndex(COLUMN_REST_PHONENUMBER)));
                rest.setAddress(res.getString(res.getColumnIndex(COLUMN_REST_ADDRESS)));
                rest.setCategory(res.getString(res.getColumnIndex(COLUMN_REST_CATEGORY)));
                rest.setImages(res.getString(res.getColumnIndex(COLUMN_REST_IMAGES)));
                rest.setIsFavourite(res.getString(res.getColumnIndex(COLUMN_REST_IS_FAVOURITES)));
                rest.setLikesCount(res.getString(res.getColumnIndex(COLUMN_REST_LIKES_COUNT)));
                rest.setMenu(res.getString(res.getColumnIndex(COLUMN_REST_MENU)));
                rest.setLattitude(res.getString(res.getColumnIndex(COLUMN_REST_LATTITUDE)));
                rest.setLongitude(res.getString(res.getColumnIndex(COLUMN_REST_LONGITUDE)));
                rest.setIsActive(res.getString(res.getColumnIndex(COLUMN_REST_ISACTIVE)));
                rest.setDistance(res.getString(res.getColumnIndex(COLUMN_REST_DISTANCE)));
                rest.setHistroy(res.getString(res.getColumnIndex(COLUMN_REST_HISTORY)));
                rest.setPriceFrom(res.getString(res.getColumnIndex(COLUMN_REST_PRICE_FROM)));
                rest.setPriceTo(res.getString(res.getColumnIndex(COLUMN_REST_PRICE_TO)));
                rest.setRegistered_date(res.getString(res.getColumnIndex(COLUMN_REST_REGISTERED_DATE)));
                rest.setFavCount(res.getString(res.getColumnIndex(COLUMN_REST_FAV_COUNT)));

                rest.setIsLiked(res.getString(res.getColumnIndex(COLUMN_REST_IS_LIKED)));
                rest.setColor(res.getString(res.getColumnIndex(COLUMN_REST_COLOR)));
                rest.setWebUrl(res.getString(res.getColumnIndex(COLUMN_REST_WEB_URL)));
                rest.setInstagramAccount(res.getString(res.getColumnIndex(COLUMN_REST_INSTAGRAM_ACCOUNT)));
                rest.setServicePhone(res.getString(res.getColumnIndex(COLUMN_REST_SERVICEPHONE)));
                rest.setServiceStatus(res.getString(res.getColumnIndex(COLUMN_REST_SERVICE_STATUS)));

                Gson gson = new Gson();

                String operatingHours = res.getString(res.getColumnIndex(COLUMN_REST_OPERATING_HOURS));
                Type type = new TypeToken<ArrayList<OperatingHour>>() {
                }.getType();
                ArrayList<OperatingHour> operatingHourArrayList = gson.fromJson(operatingHours, type);
                rest.setOperatingHourArrayList(operatingHourArrayList);

                String typeOFFood = res.getString(res.getColumnIndex(COLUMN_REST_TYPE_OF_FOOD));
                Type typeOFFoodType = new TypeToken<ArrayList<String>>() {
                }.getType();
                ArrayList<String> typeOFFoodArrayList = gson.fromJson(typeOFFood, typeOFFoodType);
                rest.setTypeOfFood(typeOFFoodArrayList);

                String otherStr = res.getString(res.getColumnIndex(COLUMN_REST_OTHER));
                Type otherType = new TypeToken<ArrayList<String>>() {
                }.getType();
                ArrayList<String> otherArrayList = gson.fromJson(otherStr, otherType);
                rest.setOther(otherArrayList);

                resturantObjectArrayList.add(rest);
            } while (res.moveToNext());
        }
        return resturantObjectArrayList;
    }

    public ArrayList<ResturantObject> getResturantsListForFavourite() {
        ArrayList<ResturantObject> resturantObjectArrayList = new ArrayList<ResturantObject>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " where " + COLUMN_REST_IS_FAVOURITES + " = 1";
        Cursor res = db.rawQuery(sql, null);
        if (res.moveToFirst()) {
            do {
                ResturantObject rest = new ResturantObject();
                rest.setRestID(res.getString(res.getColumnIndex(COLUMN_ID)));
                rest.setRestName(res.getString(res.getColumnIndex(COLUMN_REST_NAME)));
                rest.setDescription(res.getString(res.getColumnIndex(COLUMN_REST_DESCRIPTION)));
                rest.setIsHomeDeliveryAvailable(res.getString(res.getColumnIndex(COLUMN_REST_IS_HOME_DELIVERY)));
                rest.setPaymentMethod(res.getString(res.getColumnIndex(COLUMN_REST_PAYMENT_METHOD)));
                rest.setPhoneNumber(res.getString(res.getColumnIndex(COLUMN_REST_PHONENUMBER)));
                rest.setAddress(res.getString(res.getColumnIndex(COLUMN_REST_ADDRESS)));
                rest.setCategory(res.getString(res.getColumnIndex(COLUMN_REST_CATEGORY)));
                rest.setImages(res.getString(res.getColumnIndex(COLUMN_REST_IMAGES)));
                rest.setIsFavourite(res.getString(res.getColumnIndex(COLUMN_REST_IS_FAVOURITES)));
                rest.setLikesCount(res.getString(res.getColumnIndex(COLUMN_REST_LIKES_COUNT)));
                rest.setMenu(res.getString(res.getColumnIndex(COLUMN_REST_MENU)));
                rest.setLattitude(res.getString(res.getColumnIndex(COLUMN_REST_LATTITUDE)));
                rest.setLongitude(res.getString(res.getColumnIndex(COLUMN_REST_LONGITUDE)));
                rest.setIsActive(res.getString(res.getColumnIndex(COLUMN_REST_ISACTIVE)));
                rest.setDistance(res.getString(res.getColumnIndex(COLUMN_REST_DISTANCE)));
                rest.setHistroy(res.getString(res.getColumnIndex(COLUMN_REST_HISTORY)));
                rest.setPriceFrom(res.getString(res.getColumnIndex(COLUMN_REST_PRICE_FROM)));
                rest.setPriceTo(res.getString(res.getColumnIndex(COLUMN_REST_PRICE_TO)));
                rest.setRegistered_date(res.getString(res.getColumnIndex(COLUMN_REST_REGISTERED_DATE)));
                rest.setFavCount(res.getString(res.getColumnIndex(COLUMN_REST_FAV_COUNT)));

                rest.setIsLiked(res.getString(res.getColumnIndex(COLUMN_REST_IS_LIKED)));
                rest.setColor(res.getString(res.getColumnIndex(COLUMN_REST_COLOR)));
                rest.setWebUrl(res.getString(res.getColumnIndex(COLUMN_REST_WEB_URL)));
                rest.setInstagramAccount(res.getString(res.getColumnIndex(COLUMN_REST_INSTAGRAM_ACCOUNT)));
                rest.setServicePhone(res.getString(res.getColumnIndex(COLUMN_REST_SERVICEPHONE)));
                rest.setServiceStatus(res.getString(res.getColumnIndex(COLUMN_REST_SERVICE_STATUS)));

                Gson gson = new Gson();

                String operatingHours = res.getString(res.getColumnIndex(COLUMN_REST_OPERATING_HOURS));
                Type type = new TypeToken<ArrayList<OperatingHour>>() {
                }.getType();
                ArrayList<OperatingHour> operatingHourArrayList = gson.fromJson(operatingHours, type);
                rest.setOperatingHourArrayList(operatingHourArrayList);

                String typeOFFood = res.getString(res.getColumnIndex(COLUMN_REST_TYPE_OF_FOOD));
                Type typeOFFoodType = new TypeToken<ArrayList<String>>() {
                }.getType();
                ArrayList<String> typeOFFoodArrayList = gson.fromJson(typeOFFood, typeOFFoodType);
                rest.setTypeOfFood(typeOFFoodArrayList);

                String otherStr = res.getString(res.getColumnIndex(COLUMN_REST_OTHER));
                Type otherType = new TypeToken<ArrayList<String>>() {
                }.getType();
                ArrayList<String> otherArrayList = gson.fromJson(otherStr, otherType);
                rest.setOther(otherArrayList);

                resturantObjectArrayList.add(rest);
            } while (res.moveToNext());
        }
        return resturantObjectArrayList;
    }

    public ResturantObject getResturantDetail(String restID) {
        ResturantObject rest = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " where " + COLUMN_REST_ID + " ='" + restID + "'";
        Cursor res = db.rawQuery(sql, null);
        if (res.moveToFirst()) {
            rest = new ResturantObject();
            rest.setRestID(res.getString(res.getColumnIndex(COLUMN_ID)));
            rest.setRestName(res.getString(res.getColumnIndex(COLUMN_REST_NAME)));
            rest.setDescription(res.getString(res.getColumnIndex(COLUMN_REST_DESCRIPTION)));
            rest.setIsHomeDeliveryAvailable(res.getString(res.getColumnIndex(COLUMN_REST_IS_HOME_DELIVERY)));
            rest.setPaymentMethod(res.getString(res.getColumnIndex(COLUMN_REST_PAYMENT_METHOD)));
            rest.setPhoneNumber(res.getString(res.getColumnIndex(COLUMN_REST_PHONENUMBER)));
            rest.setAddress(res.getString(res.getColumnIndex(COLUMN_REST_ADDRESS)));
            rest.setCategory(res.getString(res.getColumnIndex(COLUMN_REST_CATEGORY)));
            rest.setImages(res.getString(res.getColumnIndex(COLUMN_REST_IMAGES)));
            rest.setIsFavourite(res.getString(res.getColumnIndex(COLUMN_REST_IS_FAVOURITES)));
            rest.setLikesCount(res.getString(res.getColumnIndex(COLUMN_REST_LIKES_COUNT)));
            rest.setMenu(res.getString(res.getColumnIndex(COLUMN_REST_MENU)));
            rest.setLattitude(res.getString(res.getColumnIndex(COLUMN_REST_LATTITUDE)));
            rest.setLongitude(res.getString(res.getColumnIndex(COLUMN_REST_LONGITUDE)));
            rest.setIsActive(res.getString(res.getColumnIndex(COLUMN_REST_ISACTIVE)));
            rest.setDistance(res.getString(res.getColumnIndex(COLUMN_REST_DISTANCE)));
            rest.setHistroy(res.getString(res.getColumnIndex(COLUMN_REST_HISTORY)));
            rest.setPriceFrom(res.getString(res.getColumnIndex(COLUMN_REST_PRICE_FROM)));
            rest.setPriceTo(res.getString(res.getColumnIndex(COLUMN_REST_PRICE_TO)));
            rest.setRegistered_date(res.getString(res.getColumnIndex(COLUMN_REST_REGISTERED_DATE)));
            rest.setFavCount(res.getString(res.getColumnIndex(COLUMN_REST_FAV_COUNT)));

            rest.setIsLiked(res.getString(res.getColumnIndex(COLUMN_REST_IS_LIKED)));
            rest.setColor(res.getString(res.getColumnIndex(COLUMN_REST_COLOR)));
            rest.setWebUrl(res.getString(res.getColumnIndex(COLUMN_REST_WEB_URL)));
            rest.setInstagramAccount(res.getString(res.getColumnIndex(COLUMN_REST_INSTAGRAM_ACCOUNT)));
            rest.setServicePhone(res.getString(res.getColumnIndex(COLUMN_REST_SERVICEPHONE)));
            rest.setServiceStatus(res.getString(res.getColumnIndex(COLUMN_REST_SERVICE_STATUS)));

            Gson gson = new Gson();

            String operatingHours = res.getString(res.getColumnIndex(COLUMN_REST_OPERATING_HOURS));
            Type type = new TypeToken<ArrayList<OperatingHour>>() {
            }.getType();
            ArrayList<OperatingHour> operatingHourArrayList = gson.fromJson(operatingHours, type);
            rest.setOperatingHourArrayList(operatingHourArrayList);

            String typeOFFood = res.getString(res.getColumnIndex(COLUMN_REST_TYPE_OF_FOOD));
            Type typeOFFoodType = new TypeToken<ArrayList<String>>() {
            }.getType();
            ArrayList<String> typeOFFoodArrayList = gson.fromJson(typeOFFood, typeOFFoodType);
            rest.setTypeOfFood(typeOFFoodArrayList);

            String otherStr = res.getString(res.getColumnIndex(COLUMN_REST_OTHER));
            Type otherType = new TypeToken<ArrayList<String>>() {
            }.getType();
            ArrayList<String> otherArrayList = gson.fromJson(otherStr, otherType);
            rest.setOther(otherArrayList);
        }
        return rest;
    }


}