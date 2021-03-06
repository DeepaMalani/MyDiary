package android.myapp.mydiary.utilities;

import android.content.Context;
import android.myapp.mydiary.ReminderFirebaseJobService;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class ReminderUtilities {
    private static final int REMINDER_INTERVAL_MINUTES = 60;
    private static final int REMINDER_INTERVAL_SECONDS =(int) TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES);
    private static final int SYNC_SECONDS = REMINDER_INTERVAL_SECONDS;
    private static final String REMINDER_TAG = "reminder_tag";
    //Variable to keep track is job has started
    private static boolean sInitialized;

    synchronized public static void scheduleReminder(final Context context)
    {
        if(sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        //Create the job with all constraints
        Job jobReminder = dispatcher.newJobBuilder()
                          .setService(ReminderFirebaseJobService.class)
                          .setTag(REMINDER_TAG)
                          .setConstraints(Constraint.ON_UNMETERED_NETWORK)
                          .setLifetime(Lifetime.FOREVER)
                          .setRecurring(true)
                           .setTrigger(Trigger.executionWindow(
                                   REMINDER_INTERVAL_SECONDS,
                                   REMINDER_INTERVAL_SECONDS + SYNC_SECONDS
                                   ))
                          .setReplaceCurrent(true)
                          .build();
        //Schedule the job using FirebaseJobDispatcher
        dispatcher.schedule(jobReminder);
        sInitialized = true;
    }

}
