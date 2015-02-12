package com.abc360.tool.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abc360.tool.R;
import com.abc360.tool.userdeta.APIs.SavaTextbookSelection;
import com.abc360.tool.userdeta.APIs.TextbookIndex;
import com.abc360.tool.userdeta.SaveBookClassManager;
import com.abc360.tool.userdeta.UserProfileManger;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;


public class CourseSelectionActivity extends Activity {

    String id;
    boolean hasTextbook;
    String cname;
    String tname;
    String mname;

    List<TextbookIndex.CName> cNameList;
    List<TextbookIndex.TName> tNameList;
    List<TextbookIndex.MName> mNameList;

    List<String> cNames = new ArrayList<String>();
    List<String> tNames = new ArrayList<String>();
    List<String> mNames = new ArrayList<String>();

    TextView textViewCname;
    TextView textViewTname;
    TextView textViewMname;

    RelativeLayout relativeLayoutCname;
    RelativeLayout relativeLayoutTname;
    RelativeLayout relativeLayoutMname;

    BagForSave bagForSave;

    class BagForSave{
        String sid;
        String id;
        String cid;
        String tid;
        String mid;
        String cname;
        String tname;
        String mname;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_selection);

        LinearLayout buttonBack = (LinearLayout)findViewById(R.id.reservation_button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        hasTextbook = intent.getBooleanExtra("hasTextbook",false);
        cname = intent.getStringExtra("cname");
        tname = intent.getStringExtra("tname");
        mname = intent.getStringExtra("mname");


        bagForSave = new BagForSave();
        bagForSave.id = id;
        bagForSave.sid = new UserProfileManger(getApplicationContext()).getId();
        bagForSave.cid = "0";
        bagForSave.tid = "0";
        bagForSave.mid = "0";

        textViewCname = (TextView)findViewById(R.id.course_selection_cname);
        textViewTname = (TextView)findViewById(R.id.course_selection_tname);
        textViewMname = (TextView)findViewById(R.id.course_selection_mname);

        final TextbookIndex textbookIndex = new TextbookIndex(getApplicationContext());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("载入中...");
        progressDialog.show();
        if(hasTextbook){
            textViewCname.setText(cname);
            textViewTname.setText(tname);
            textViewMname.setText(mname);
            bagForSave.cname = intent.getStringExtra("cname");
            bagForSave.tname = intent.getStringExtra("tname");
            bagForSave.mname = intent.getStringExtra("mname");
            bagForSave.cid = intent.getStringExtra("cid");
            bagForSave.tid = intent.getStringExtra("tid");
            bagForSave.mid = intent.getStringExtra("mid");

            textbookIndex.loadCNames(new TextbookIndex.OnLoadedCNamesListner() {
                @Override
                public void onLoaded(List<TextbookIndex.CName> cNameses) {
                    progressDialog.dismiss();
                    cNameList = cNameses;
                    cNames.clear();
                    for(TextbookIndex.CName i :cNameses){
                        cNames.add(i.cname);
                    }
                }
                @Override
                public void onFailure() {
                    progressDialog.dismiss();
                }
            });
            textbookIndex.loadTNames(bagForSave.cid,new TextbookIndex.OnLoadedTNamesListner() {
                @Override
                public void onLoaded(List<TextbookIndex.TName> tNameses) {
                    tNameList = tNameses;
                    tNames.clear();
                    for(TextbookIndex.TName i :tNameses){
                        tNames.add(i.tname);
                    }
                }

                @Override
                public void onFailure() {

                }
            });
            textbookIndex.loadMNames(bagForSave.tid,new TextbookIndex.OnLoadedMNamesListner() {
                @Override
                public void onLoaded(List<TextbookIndex.MName> mNameses) {
                    mNameList = mNameses;
                    mNames.clear();
                    for(TextbookIndex.MName i :mNameses){
                        mNames.add(i.mname);
                    }
                }

                @Override
                public void onFailure() {

                }
            });
        }


        relativeLayoutCname = (RelativeLayout)findViewById(R.id.course_selection_rl_cname);
        relativeLayoutTname = (RelativeLayout)findViewById(R.id.course_selection_rl_tname);
        relativeLayoutMname = (RelativeLayout)findViewById(R.id.course_selection_rl_mname);

        textbookIndex.loadCNames(new TextbookIndex.OnLoadedCNamesListner() {
            @Override
            public void onLoaded(List<TextbookIndex.CName> cNameses) {
                progressDialog.dismiss();
                cNameList = cNameses;
                cNames.clear();
                for(TextbookIndex.CName i :cNameses){
                    cNames.add(i.cname);
                }
            }
            @Override
            public void onFailure() {
                progressDialog.dismiss();

            }
        });


        relativeLayoutCname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CourseSelectionActivity.this);
                alertDialog.setTitle("课程类型");
                alertDialog.setItems(cNames.toArray(new String[cNames.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        textViewCname.setText(cNameList.get(i).cname);
                        textViewTname.setText("未选择");
                        textViewMname.setText("未选择");
                        bagForSave.cid = cNameList.get(i).cid;
                        bagForSave.cname = cNameList.get(i).cname;
                        bagForSave.tid = "0";
                        bagForSave.mid = "0";
                        textbookIndex.loadTNames(bagForSave.cid,new TextbookIndex.OnLoadedTNamesListner() {
                            @Override
                            public void onLoaded(List<TextbookIndex.TName> tNameses) {
                                tNameList = tNameses;
                                tNames.clear();
                                for(TextbookIndex.TName i :tNameses){
                                    tNames.add(i.tname);
                                    //Log.e("ces",i.tname);
                                }
                                //Log.e("ces","完成");
                            }

                            @Override
                            public void onFailure() {
                            }
                        });
                    }
                });
                alertDialog.show();
            }
        });

        relativeLayoutTname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tNames.size() <= 1){
                    Toast.makeText(getApplicationContext(),"请先选择上级目录",Toast.LENGTH_LONG).show();
                    return;
                }
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CourseSelectionActivity.this);
                alertDialog.setTitle("教材选择");
                alertDialog.setItems(tNames.toArray(new String[tNames.size()]),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        textViewTname.setText(tNameList.get(i).tname);
                        textViewMname.setText("未选择");
                        bagForSave.tid = tNameList.get(i).tid;
                        bagForSave.tname = tNameList.get(i).tname;
                        bagForSave.mid = "0";
                        textbookIndex.loadMNames(bagForSave.tid,new TextbookIndex.OnLoadedMNamesListner() {
                            @Override
                            public void onLoaded(List<TextbookIndex.MName> mNameses) {
                                mNameList = mNameses;
                                mNames.clear();
                                for(TextbookIndex.MName i :mNameses){
                                    mNames.add(i.mname);
                                }
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                    }
                });
                alertDialog.show();
            }
        });

            relativeLayoutMname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mNames.size() <= 1){
                        Toast.makeText(getApplicationContext(),"请先选择上级目录",Toast.LENGTH_LONG).show();
                        return;
                    }
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(CourseSelectionActivity.this);
                    alertDialog.setTitle("上课章节");
                    alertDialog.setItems(mNames.toArray(new String[mNames.size()]),new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            textViewMname.setText(mNameList.get(i).mname);
                            bagForSave.mid = mNameList.get(i).mid;
                            bagForSave.mname = mNameList.get(i).mname;
                        }
                    });
                    alertDialog.show();
                }
            });



        Button buttonSave = (Button)findViewById(R.id.course_selection_btn_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bagForSave.mid.equals("0")){
                    Toast.makeText(getApplicationContext(),"请选择教材",Toast.LENGTH_SHORT).show();
                    return;
                }

                final ProgressDialog dialog = new ProgressDialog(CourseSelectionActivity.this);
                dialog.setMessage("请稍后...");
                dialog.show();

                RequestParams params = new RequestParams();
                params.put("id",bagForSave.id);
                params.put("sid",bagForSave.sid);
                params.put("cid",bagForSave.cid);
                params.put("tid",bagForSave.tid);
                params.put("mid",bagForSave.mid);
                //Log.e("sava",params.toString());
                new SavaTextbookSelection(getApplicationContext()).doSaveTextbook(params,new SavaTextbookSelection.OnfinishListner() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("cname", bagForSave.cname);
                        resultIntent.putExtra("tname", bagForSave.tname);
                        resultIntent.putExtra("mname", bagForSave.mname);
                        resultIntent.putExtra("cid",bagForSave.cid);
                        resultIntent.putExtra("tid",bagForSave.tid);
                        resultIntent.putExtra("mid",bagForSave.mid);
                        setResult(1,resultIntent);
                        finish();
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        dialog.cancel();
                        if (errorCode == 0){
                            Toast.makeText(getApplicationContext(),"网络连接失败，请稍后再试",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });




    }


}
