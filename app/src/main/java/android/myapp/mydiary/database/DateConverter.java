package android.myapp.mydiary.database;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * This class convert date so that it can save in database.
 * SQLite does not have a storage class set aside for storing dates and/or times.
 */
public class DateConverter {


    /**
     * Room will use this method to read data
     *
     * @param timestamp
     * @return
     */
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    /**
     * Room will use this method to save data
     *
     * @param date
     * @return
     */
    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}

