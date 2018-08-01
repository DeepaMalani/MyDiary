package android.myapp.mydiary.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes ORDER BY created_at")
    LiveData<List<NoteEntry>> loadAllNotes();

    @Insert
    void insert(NoteEntry noteEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNote(NoteEntry noteEntry);

    @Delete
    void delete(NoteEntry noteEntry);

    @Query("SELECT * FROM notes WHERE id = :id")
    NoteEntry loadNoteById(int id);
}
