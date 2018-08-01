package android.myapp.mydiary;

import android.content.Intent;
import android.myapp.mydiary.database.AppDatabase;
import android.myapp.mydiary.database.NoteEntry;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class AddMyNotesActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddMyNotesActivity.class.getSimpleName();
    private Button mButtonSave;
    private AppDatabase mDb;
    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private int mItemId = -1;
    // Constant for default note id to be used when not in update mode
    private static final int DEFAULT_NOTE_ID = -1;
    private static String TITLE = "title";
    private static String DETAIL = "detail";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_notes);

       mEditTextTitle = findViewById(R.id.edit_title);
       mEditTextDescription = findViewById(R.id.edit_description);

       //Initialize AppDatabase variable
        mDb = AppDatabase.getsInstance(this);

        mButtonSave = findViewById(R.id.btn_save);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveData();
            }
        });
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(MainActivity.ITEM_ID))
        {
          mButtonSave.setText("Update");
          mItemId = intent.getIntExtra(MainActivity.ITEM_ID,0);
          if(savedInstanceState == null) {
              AppExecutors.getInstance().diskIO().execute(new Runnable() {
                  @Override
                  public void run() {
                      Log.d(LOG_TAG, "Update");
                      final NoteEntry noteEntry = mDb.notesDao().loadNoteById(mItemId);
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              populateUI(noteEntry);
                          }
                      });

                  }
              });
          }
          else
          {
              mEditTextTitle.setText(savedInstanceState.getString(TITLE));
              mEditTextDescription.setText(savedInstanceState.getString(DETAIL));
          }
        }

    }

    /**
     * This method will call to update UI
     * @param noteEntry
     */
    public void populateUI(NoteEntry noteEntry)
    {
        if(noteEntry == null)
            return;
        mEditTextTitle.setText(noteEntry.getTitle().toString());
        mEditTextDescription.setText(noteEntry.getDescription().toString());
    }
    /**
     * This method is called when user clicks on save button.
     * It saves
     */
    public void saveData()
    {
     String strTitle = mEditTextTitle.getText().toString();
     String strDescription = mEditTextDescription.getText().toString();
     Date date = new Date();

     final NoteEntry noteEntry = new NoteEntry(strTitle,strDescription,date);
    //Using executor to perform database operation
     AppExecutors.getInstance().diskIO().execute(new Runnable() {
         @Override
         public void run() {
             if(mItemId == DEFAULT_NOTE_ID) {
                 mDb.notesDao().insert(noteEntry);
             }
             else
             {
                 noteEntry.setId(mItemId);
                 mDb.notesDao().updateNote(noteEntry);
             }
             finish();
         }
     });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TITLE,mEditTextTitle.getText().toString());
        outState.putString(TITLE,mEditTextDescription.getText().toString());
    }
}
