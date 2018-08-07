package android.myapp.mydiary;

import android.myapp.mydiary.utilities.NotificationUtils;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class ReminderFirebaseJobService extends JobService {

  private AsyncTask mBackgroundTask;
    public ReminderFirebaseJobService() {
        super();
    }

    @Override
    public boolean onStartJob(final JobParameters job) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                NotificationUtils.remindUser(ReminderFirebaseJobService.this);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job,false);
            }
        };
        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if(mBackgroundTask !=null) mBackgroundTask.cancel(true);
        return true;
    }
}
