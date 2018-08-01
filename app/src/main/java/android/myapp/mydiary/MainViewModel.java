package android.myapp.mydiary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.myapp.mydiary.database.AppDatabase;
import android.myapp.mydiary.database.NoteEntry;
import android.support.annotation.NonNull;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

   private LiveData<List<NoteEntry>> mNotes;
    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getsInstance(this.getApplication());
        mNotes = db.notesDao().loadAllNotes();
    }

    public LiveData<List<NoteEntry>> getNotes() {
        return mNotes;
    }
}
