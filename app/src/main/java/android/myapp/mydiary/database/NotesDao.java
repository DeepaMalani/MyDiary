package android.myapp.mydiary.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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
