package com.abc360.tool.widgets;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by roya on 14/12/2.
 */
public class VoicePlayer {

    private MediaPlayer player ;

    private boolean isPlaying = false;
    private boolean isPreparing = false;
    private boolean isPrepared = false;
    private boolean needPlay = false;

    public void setOnCompletionListener (MediaPlayer.OnCompletionListener listener){
        player.setOnCompletionListener(listener);
    }

    public VoicePlayer(String url) throws IOException {
        player = new MediaPlayer();
        player.setDataSource(url);
        player.prepareAsync();
        isPreparing = true;
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                isPrepared = true;
                isPreparing = false;
                if (needPlay){
                    player.start();
                    isPlaying = true;
                }
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                player.seekTo(0);
                player.pause();
                isPlaying = false;
            }
        });
    }

    public void play(){
        if (isPrepared){
            player.start();
            isPlaying = true;
        }else {
            needPlay = true;
            isPlaying = true;
        }
    }

    public void pause(){
        if (isPrepared && player.isPlaying()){
            player.seekTo(0);
            player.pause();
        }
        needPlay = false;
        isPlaying = false;
    }

    public boolean isPlaying(){
        return this.isPlaying;
    }

}
