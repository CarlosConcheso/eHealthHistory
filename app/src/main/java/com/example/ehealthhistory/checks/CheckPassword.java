package com.example.ehealthhistory.checks;

import android.widget.EditText;

public class CheckPassword {

    public static boolean check(EditText password) {
        if(password.getText().toString().length() > 0)
            return true;
        else
            return false;
    }
}
