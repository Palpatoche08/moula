package com.example.moulaproject.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.moulaproject.Database.entities.User;
import com.example.moulaproject.Database.typeConverties.LocalDateTypeConverter;
import com.example.moulaproject.MainActivity;

import org.jetbrains.annotations.NonNls;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)

public abstract class UserDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "User_database";

    public static final String USER_TABLE = "userTable";

    private static volatile UserDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static UserDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (UserDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class,DATABASE_NAME)
                            .fallbackToDestructiveMigration().addCallback(addDefaultValues).build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback()
    {
        public void onCreate(@NonNull SupportSQLiteDatabase db)
        {
            super.onCreate(db);
            //TODO: add databaseWriteExcecutor.execute() -> {...};
        }

    };

    public abstract UserDAO UserDAO();
}