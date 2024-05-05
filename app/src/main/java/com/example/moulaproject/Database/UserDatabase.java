package com.example.moulaproject.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.moulaproject.Database.entities.Currency;
import com.example.moulaproject.Database.entities.User;
import com.example.moulaproject.MainActivity;

import org.jetbrains.annotations.NonNls;

import java.time.LocalDateTime;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Currency.class}, version = 4, exportSchema = false)

public abstract class UserDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "User_database";

    public static final String USER_TABLE = "userTable";
    public static final String CURRENCY_TABLE = "currencyTable";

    private static volatile UserDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UserDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (UserDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class,DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addMigrations(MIGRATION_1_4)
                            .addCallback(addDefaultValues).build();

                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //TODO: add databaseWriteExecutor.execute() -> {...};
            databaseWriteExecutor.execute(() -> {
                try {
                    UserDAO dao = INSTANCE.UserDAO();

                    // Delete existing users
                    dao.deleteALL();

                    // Insert admin user
                    User admin = new User("admin2", "admin2", true);
                    dao.insert(admin);

                    // Insert test user
                    User testUser1 = new User("testUser2", "testUser2", false);
                    dao.insert(testUser1);

                    // Insert currency
                    Currency dollar = new Currency("Dollar", 1.0);
                    dao.insertCurrency(dollar);

                    // Commit the transaction
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // End the transaction
                    db.endTransaction();
                }
            });
        }
    };

    private static final Migration MIGRATION_1_4 = new Migration(1, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Create the Currency table
            database.execSQL("CREATE TABLE IF NOT EXISTS `Currency` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT, `rate` REAL)");

            // Add the new column to the UserTable (if it hasn't been added yet)
            database.execSQL("ALTER TABLE " + UserDatabase.USER_TABLE + " ADD COLUMN balance INTEGER DEFAULT 0");
        }
    };


    public abstract UserDAO UserDAO();
}