package es.upv.dadm.myquotesapp.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.upv.dadm.myquotesapp.pojo.Quotation;

@Database(entities = {Quotation.class}, version = 1, exportSchema = false)
public abstract class QuotationsDatabase extends RoomDatabase {

    private static QuotationsDatabase quotationsDatabase;
    public synchronized static QuotationsDatabase getInstance(Context context) {
        if (quotationsDatabase == null) {
            quotationsDatabase = Room
                    .databaseBuilder(context, QuotationsDatabase.class, "quotation_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return quotationsDatabase;
    }
    public abstract QuotationDao quotationDao();

}
