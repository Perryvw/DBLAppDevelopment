package com.dblappdev.hitch.app;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.dblappdev.hitch.network.API;
import org.json.JSONObject;

import java.util.concurrent.Callable;

/**
 * Created by guusleijsten on 30/03/15.
 */
public class LoginDialogFragment extends DialogFragment {
    int mNum;
    //Preferences
    private SharedPreferences prefs;
    private static BirthActivity birthActivity;

    static LoginDialogFragment newInstance(int num, BirthActivity ba) {
        LoginDialogFragment f = new LoginDialogFragment();
        birthActivity = ba;
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");

        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        switch ((mNum-1)%6) {
            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
            case 3: style = DialogFragment.STYLE_NO_INPUT; break;
            case 4: style = DialogFragment.STYLE_NORMAL; break;
            case 5: style = DialogFragment.STYLE_NORMAL; break;
            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
            case 8: style = DialogFragment.STYLE_NORMAL; break;
        }
        switch ((mNum-1)%6) {
            case 4: theme = android.R.style.Theme_Holo; break;
            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
            case 6: theme = android.R.style.Theme_Holo_Light; break;
            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
            case 8: theme = android.R.style.Theme_Holo_Light; break;
        }
        setStyle(style, theme);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        prefs = getActivity().getSharedPreferences(MainActivity.SHARED_PREF, 0);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_login, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...

                        final EditText phoneText = (EditText) view.findViewById(R.id.phone);
                        String str = phoneText.getText().toString();
                        Log.d("phone", str);
                        API.LoginUser(str, new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {
                                callback();
                                return null;
                            }
                        });
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void callback() {
        int id = -1;
        JSONObject json = API.getResponse();
        try {
            id = json.getInt("userID");
        } catch(Exception e) {
            e.printStackTrace();
        }
        if (id == -1) {
            return;
        }
        //Log.e("callback login", Integer.toString(id));
        // update shared preferences, birthed and user
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(MainActivity.USER_KEY, id);
        editor.commit();
        // navigate to main
        birthActivity.startRootActivity();
    }
}
