package android.myapp.mydiary;

import android.content.Intent;
import android.myapp.mydiary.database.AppDatabase;
import android.myapp.mydiary.database.NoteEntry;
import android.myapp.mydiary.utilities.ReminderUtilities;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.widget.GridLayout.VERTICAL;

public class MainActivity extends AppCompatActivity implements NotesAdapter.ItemClickListener{

    private NotesAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private AppDatabase mDb;
    public static String ITEM_ID = "item_id";
    private static String LOG_TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view_notes);
        mAdapter = new NotesAdapter(this,this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDb = AppDatabase.getsInstance(this);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<NoteEntry> list = mAdapter.getNotes();
                        mDb.notesDao().delete(list.get(position));

                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);


        FloatingActionButton floatingActionButton = findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddMyNotesActivity.class);
                startActivity(intent);
            }
        });
        setUpViewModel();

        //Schedule the reminder job
        ReminderUtilities.scheduleReminder(MainActivity.this);
    }

    @Override
    public void onItemClick(int itemId) {

        Intent intent = new Intent(MainActivity.this,AddMyNotesActivity.class);
        intent.putExtra(ITEM_ID,itemId);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        }

    public void setUpViewModel()
    {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getNotes().observe(this, new Observer<List<NoteEntry>>() {
            @Override
            public void onChanged(@Nullable List<NoteEntry> noteEntries) {
                mAdapter.setNotes(noteEntries);
            }
        });
        }

}
