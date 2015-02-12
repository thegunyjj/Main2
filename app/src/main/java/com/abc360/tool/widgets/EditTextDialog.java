package com.abc360.tool.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

/**
 * Created by roya on 14/11/6.
 */
public class EditTextDialog {
    private Context context;
    private AlertDialog.Builder builder;
    protected EditText editText;

    public EditTextDialog(Context context){
        this.context = context;
        builder = new AlertDialog.Builder(context);
        editText = new EditText(context);
        builder.setView(editText);

    }

    public EditTextDialog setPositiveButton(CharSequence text,DialogInterface.OnClickListener listener){
        builder.setPositiveButton(text, listener);
        return this;
    }

    public EditTextDialog setNegativeButton(CharSequence text,DialogInterface.OnClickListener listener){
        builder.setNegativeButton(text, listener);
        return this;
    }

    public EditTextDialog setTitle(CharSequence title){
        builder.setTitle(title);
        return this;
    }

    public void show(){
        builder.show();
    }

}
