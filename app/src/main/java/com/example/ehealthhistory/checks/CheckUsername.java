package com.example.ehealthhistory.checks;

import android.widget.EditText;

public class CheckUsername {

    public static boolean check(EditText username) {
        if(username.getText().toString().length()>0)
        {
            return true;
        }
        return false;
    }
}
