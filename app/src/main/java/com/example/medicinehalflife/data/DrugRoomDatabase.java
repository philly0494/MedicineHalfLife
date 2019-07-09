package com.example.medicinehalflife.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.medicinehalflife.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

@Database(entities = {Drug.class}, version = 1, exportSchema = false)
public abstract class DrugRoomDatabase extends RoomDatabase {

    public abstract DrugDao drugDao();

    private static DrugRoomDatabase INSTANCE;

    public static DrugRoomDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (DrugRoomDatabase.class) {
                // Create the database
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        DrugRoomDatabase.class, "drug_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(new RoomDatabase.Callback() {
                            @Override
                            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                super.onOpen(db);
                                new PopulateDbAsync(INSTANCE, context).execute();
                            }
                        })
                        .build();
            }
        }
        return INSTANCE;
    }

    /**
     * Populate the database in the background
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final DrugDao mDao;
        WeakReference<Context> weakContext;

        PopulateDbAsync(DrugRoomDatabase db, Context context) {
            mDao = db.drugDao();
            weakContext = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            JSONArray myDrugs = getDrugsFromJson(weakContext.get());

            //If we have no words, then create the initial list of words
            if (mDao.getAnyDrug().length < 1) {
                int i = 0;
                while (i < myDrugs.length()) {
                    try {
                        JSONObject oneDrug = myDrugs.getJSONObject(i);
                        Drug drug = new Drug(oneDrug.getString(DrugClass.DRUG_ID),
                                oneDrug.getString(DrugClass.DRUG_NAME),
                                oneDrug.getString(DrugClass.DRUG_HALF_LIFE));
                        mDao.insert(drug);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    i++;
                }

            }
            return null;
        }
    }


    private static JSONArray getDrugsFromJson(Context context) {

        String tContents = "";

        try {
            InputStream stream = context.getResources().openRawResource(R.raw.drugs);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
        }

        try {
            JSONObject jsonObject = new JSONObject(tContents);
            JSONArray animalsArray = jsonObject.getJSONArray("drugs");
            return animalsArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
