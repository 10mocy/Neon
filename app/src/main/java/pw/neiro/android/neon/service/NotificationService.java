package pw.neiro.android.neon.service;

import android.app.Notification;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import java.util.HashMap;

import pw.neiro.android.neon.MainActivity;
import pw.neiro.android.neon.util.TwitterAPI;

/**
 * Created by nirot1r on 2018/02/20.
 */

public class NotificationService extends NotificationListenerService {

    private static final String FILTER_CLOUDPLAYER = "com.doubleTwist.cloudPlayer";
    private static final HashMap<String, String> PLAYER_LIST = new HashMap<String, String>() {
        {
            put(FILTER_CLOUDPLAYER, "CloudPlayer");
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

        String packageName = sbn.getPackageName();
        if(!packageName.equals(FILTER_CLOUDPLAYER)) return;

        Bundle extras = sbn.getNotification().extras;
        String songTitle = "";
        String songArtist = "";
        String songAlbum = "";
        String player = PLAYER_LIST.get(packageName);

        try {
            songTitle = extras.getCharSequence(Notification.EXTRA_TITLE).toString();
            songArtist = extras.getCharSequence(Notification.EXTRA_TEXT).toString();
            songAlbum = extras.getCharSequence(Notification.EXTRA_SUB_TEXT).toString();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if(songTitle == null || songTitle.isEmpty()) return;

        String tweetText = "🎵 " + songTitle + "\n"
                + "🎤 " + songArtist + "\n"
                + "💿 " + songAlbum + "\n"
                + "🎧 " + player + "\n"
                + "#NowPlaying #NeonNP #自動";

        //Toast.makeText(MainActivity.getContext(), tweetText, Toast.LENGTH_LONG).show();

        if(previous.equals(tweetText)) return;
            previous = tweetText;
        TwitterAPI.statusUpdate(tweetText);

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) { //通知が削除された際に呼び出される
        //Toast.makeText(MainActivity.getContext(), "通知削除", Toast.LENGTH_LONG).show();
    }

}
