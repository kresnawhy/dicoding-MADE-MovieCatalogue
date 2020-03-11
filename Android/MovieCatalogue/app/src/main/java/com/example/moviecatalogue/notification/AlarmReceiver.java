package com.example.moviecatalogue.notification;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.moviecatalogue.BuildConfig;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.model.Item;
import com.example.moviecatalogue.view.activity.DetailActivity;
import com.example.moviecatalogue.view.activity.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String EXTRA_TYPE = "type";
    public static final int ID_REMINDER = 100;
    public static final int ID_NEWS = 101;
    private ArrayList<Item> listItem;

    public AlarmReceiver() {

    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final int alarm = intent.getIntExtra(EXTRA_TYPE, ID_NEWS);
        if (alarm == ID_NEWS) {
            Date c = Calendar.getInstance().getTime();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String today = df.format(c);
            AsyncHttpClient client = new AsyncHttpClient();
            listItem = new ArrayList<>();
            String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + BuildConfig.API_KEY + "&primary_release_date.gte=" + today + "&primary_release_date.lte=" + today;
            client.get(url, new JsonHttpResponseHandler() {
                @SuppressLint("ResourceType")
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        JSONArray list = response.getJSONArray("results");
                        for (int i = 0; i < list.length(); i++) {
                            JSONObject movies = list.getJSONObject(i);
                            Item item = new Item();

                            item.setId(movies.getString("id"));
                            item.setPoster("http://image.tmdb.org/t/p/w185" + movies.getString("poster_path"));
                            item.setBackdrop("http://image.tmdb.org/t/p/w780" + movies.getString("backdrop_path"));
                            item.setTitle(movies.getString("title"));
                            item.setDescription(movies.getString("overview"));
                            item.setCategory("movie");

                            listItem.add(item);
                            showAlarmNotification(context, context.getResources().getString(R.string.release), null, alarm, item);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        } else {
            showAlarmNotification(context, context.getResources().getString(R.string.daily_reminder_notification), context.getResources().getString(R.string.reminder_message), alarm, null);
        }
    }

    public void setAlarm(Context context, int alarm, int hour) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, alarm);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_NEWS, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), android.app.AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelAlarm(Context context, int alarmId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        pendingIntent.cancel();
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private PendingIntent getPendingIntent(Context context, int notificationId, Item item) {
        Intent intent;
        if (notificationId == ID_REMINDER) {
            intent = new Intent(context, MainActivity.class);
        } else if (notificationId == ID_NEWS) {
            intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_ITEM, (Parcelable) item);
        } else {
            return null;
        }
        return PendingIntent.getActivity(context, notificationId, intent, 0);
    }

    private void showAlarmNotification(Context context, String title, @Nullable String message, int notifId, @Nullable Item item) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager chanel";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        if (listItem.size() != 0) {
            for (int i = 0; i < listItem.size(); i++) {
                inboxStyle.addLine(listItem.get(i).getTitle());
            }
        }
        NotificationCompat.Builder builder;
        if (message == null) {
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_today_black_24dp)
                    .setContentTitle(title)
                    .setStyle(inboxStyle)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                    .setVibrate(new long[]{1000, 1000, 1000, 1000})
                    .setSound(alarmSound);
        } else {
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_today_black_24dp)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                    .setVibrate(new long[]{1000, 1000, 1000, 1000})
                    .setSound(alarmSound);

        }

        PendingIntent pendingIntent = getPendingIntent(context, notifId, item);
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        Notification notification = builder.build();

        if (notificationManager != null) {
            notificationManager.notify(notifId, notification);
        }
    }
}

