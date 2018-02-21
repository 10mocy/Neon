package pw.neiro.android.neon.service;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import pw.neiro.android.neon.MainActivity;
import pw.neiro.android.neon.util.TwitterAPI;

/**
 * Created by nirot1r on 2018/02/20.
 */

public class NotificationService extends NotificationListenerService {

    private static SharedPreferences sharedPreferences;

    private static final String TAG = "NeonNP";

    private static final String FILTER_CLOUDPLAYER = "com.doubleTwist.cloudPlayer";
    private static final String FILTER_PLAYMUSIC = "com.google.android.music";
    private static final String FILTER_SPOTIFY = "com.spotify.music";

    private static final HashMap<String, String> PLAYER_LIST = new HashMap<String, String>() {
        {
            put(FILTER_CLOUDPLAYER, "CloudPlayer");
            put(FILTER_PLAYMUSIC, "Google Playミュージック");
            put(FILTER_SPOTIFY, "Spotify");
        }
    };

    String previous = "";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) { //通知が更新された際に呼び出される

        /* Toast.makeText(MainActivity.getContext(), "通知", Toast.LENGTH_SHORT).show(); */

        /* ここから */
            Log.d(TAG, "通知をレシーブしました。");

        String packageName = sbn.getPackageName();
        if(!(
                packageName.equals(FILTER_CLOUDPLAYER)
                || packageName.equals(FILTER_PLAYMUSIC)
                || packageName.equals(FILTER_SPOTIFY)
        )) return;
            Log.d(TAG, "対応した音楽プレイヤーです。");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String template = sharedPreferences.getString("tweet_template", "");

        Bundle extras = sbn.getNotification().extras;
        String songTitle = "";
        String songArtist = "";
        String songAlbum = "";
        String player = PLAYER_LIST.get(packageName);
            Log.d(TAG, "player = " + player);

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        try {
            songTitle = extras.getCharSequence(Notification.EXTRA_TITLE).toString();
            songArtist = extras.getCharSequence(Notification.EXTRA_TEXT).toString();
            songAlbum = extras.getCharSequence(Notification.EXTRA_SUB_TEXT).toString();
                Log.d(TAG,
                        "songTitle : " + songTitle + "\n"
                                + "songArtist : " + songArtist + "\n"
                                + "songAlbum : " + songAlbum
                );
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if( (songTitle == null || songTitle.isEmpty())
                        || (songAlbum == null || songAlbum.isEmpty()) ) return;
            Log.d(TAG, "タイトル及びアルバムがnullではありません。");

        String tweetText = template
                .replaceAll(":title:", songTitle)
                .replaceAll(":artist:", songArtist)
                .replaceAll(":album:", songAlbum)
                .replaceAll(":player:", player);

        if(previous.equals(tweetText)) return;
        previous = tweetText;
            Log.d(TAG, "前回ツイートされた内容ではありません。");

        tweetText = tweetText
                .replaceAll(":y:", String.format("%4d", year))
                .replaceAll(":m:", String.format("%2d", month))
                .replaceAll(":d:", String.format("%2d", day))
                .replaceAll(":h:", String.format("%02d", hour))
                .replaceAll(":i:", String.format("%02d", minute))
                .replaceAll(":s:", String.format("%02d", second));

        if(sharedPreferences.getBoolean("is_tweet_notify", true)) {
            Log.d(TAG, "ツイート時通知の設定が有効です。");
            Toast.makeText(MainActivity.getContext(), tweetText, Toast.LENGTH_SHORT).show();
        }

        TwitterAPI.statusUpdate(tweetText);
            Log.d(TAG, "ツイートを送信しました。");

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) { //通知が削除された際に呼び出される
        /* Toast.makeText(MainActivity.getContext(), "通知削除", Toast.LENGTH_SHORT).show(); */
        Log.d(TAG, "通知削除をレシーブしました。");
    }

}
