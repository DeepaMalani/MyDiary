package android.myapp.mydiary;

import android.content.Context;
import android.myapp.mydiary.database.NoteEntry;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>{

    private Context mContext;
    private List<NoteEntry> mListNotes;
    private ItemClickListener mItemClickListener;

    public NotesAdapter(Context context,ItemClickListener itemClickListener)
    {
        mContext = context;
        mItemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view= layoutInflater.inflate(R.layout.notes_list_item,parent,false);

        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

       holder.textViewTitle.setText("Title: " + mListNotes.get(position).getTitle());
       holder.textViewDescription.setText("Detail: " + mListNotes.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        if(mListNotes == null)
        return 0;
        else
            return mListNotes.size();
    }

    public List<NoteEntry> getNotes()
    {
        return mListNotes;
    }



    public class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView textViewTitle;
        TextView textViewDescription;
        public NotesViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
         int itemId = mListNotes.get(getAdapterPosition()).getId();
         mItemClickListener.onItemClick(itemId);
        }
    }
    public void setNotes(List<NoteEntry> listNotes)
    {
        if(listNotes !=null)
        {
            mListNotes = listNotes;
            notifyDataSetChanged();
        }
    }
    public interface ItemClickListener{
        void onItemClick(int itemId);
    }

}
