package android.myapp.mydiary.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

@Database(entities = {NoteEntry.class},version = 1,exportSchema = false)
@TypeConverters(DateConverter.class)

public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final String DATA_BASE_NAME = "mydiary";
    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context)
    {
        if(sInstance == null)
        {
            synchronized (LOCK)
            {
                Log.d(LOG_TAG,"Creating database");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,DATA_BASE_NAME)
                       // .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(LOG_TAG,"getting the database");
        return sInstance;
    }

    public abstract NotesDao notesDao();

}
