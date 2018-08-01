package android.myapp.mydiary.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "notes")
public class NoteEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    @ColumnInfo(name = "created_at")
    private Date createdAt;

    //Room can have only on constructor so we have to use ignore for first one
    @Ignore
    public NoteEntry(String title, String description, Date createdAt)
    {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public NoteEntry(int id, String title, String description, Date createdAt)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    public void  setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
    }
}
