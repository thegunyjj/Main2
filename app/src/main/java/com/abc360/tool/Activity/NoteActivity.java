package com.abc360.tool.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.APIs.MemoReviewer;
import com.abc360.tool.userdeta.UserProfileManger;
import com.abc360.tool.widgets.VoicePlayer;

import java.io.IOException;

/**
 * Created by roya on 14/11/21.
 */
public class NoteActivity extends Activity{

    TextView textViewTname ;
    TextView textViewMname ;

    TextView textViewWords;
    TextView textViewPhreses;
    TextView textViewAdvice;


    VoicePlayer player ;

    LinearLayout linearLayoutVoice;
    ImageView imageViewVoice;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_ckeck);
        LinearLayout buttonBack = (LinearLayout)findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        linearLayoutVoice = (LinearLayout)findViewById(R.id.memo_ll_voice);
        imageViewVoice = (ImageView)findViewById(R.id.memo_iv_voice);

        textViewTname = (TextView)findViewById(R.id.memo_tname);
        textViewMname = (TextView)findViewById(R.id.memo_mname);

        textViewWords = (TextView)findViewById(R.id.memo_tv_words);
        textViewPhreses = (TextView)findViewById(R.id.memo_tv_phrases);
        textViewAdvice = (TextView)findViewById(R.id.memo_tv_advice);

        final ProgressDialog progressDialog = new ProgressDialog(NoteActivity.this);
        progressDialog.setMessage("请稍候...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        MemoReviewer reviewer = new MemoReviewer(getApplicationContext());
        reviewer.getReviewMemo(
                new UserProfileManger(getApplicationContext()).getId(),
                id,
                new MemoReviewer.OnfinishedListener() {
                    @Override
                    public void onSuccess(final MemoReviewer.Data data) {
                        progressDialog.dismiss();
                        if (data.tname == null){
                            textViewTname.setText("Free talk");
                            textViewMname.setText("Learning material unselected");
                        }else {
                            textViewTname.setText(data.tname);
                            textViewMname.setText(data.mname);
                        }

                        if (data.memo_words != null){
                            textViewWords.setText(data.memo_words);
                        }else {
                            textViewWords.setText("老师未填写");
                        }

                        if (data.memo_phrases != null){
                            textViewPhreses.setText(data.memo_phrases);
                        }else {
                            textViewPhreses.setText("老师未填写");
                        }

                        if (data.memo_advice != null){
                            textViewAdvice.setText(data.memo_advice);
                        }else {
                            textViewAdvice.setText("老师未填写");
                        }

                        if (data.memo_voice != null){
                            linearLayoutVoice.setVisibility(View.VISIBLE);
                            textViewAdvice.setVisibility(View.GONE);
                            linearLayoutVoice.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (player == null){
                                        try {
                                            player = new VoicePlayer(data.memo_voice);
                                            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                @Override
                                                public void onCompletion(MediaPlayer mediaPlayer) {
                                                    imageViewVoice.setImageResource(R.drawable.ic_play_w);
                                                }
                                            });
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getApplicationContext(),"找不到音频",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    if (player != null){
                                        if (player.isPlaying()) {
                                            player.pause();
                                            imageViewVoice.setImageResource(R.drawable.ic_play_w);
                                        }else {
                                            player.play();
                                            imageViewVoice.setImageResource(R.drawable.ic_pause_w);
                                        }

                                    }else {
                                        Toast.makeText(getApplicationContext(),"找不到音频",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (player != null) {
            player.pause();
        }
    }

}
