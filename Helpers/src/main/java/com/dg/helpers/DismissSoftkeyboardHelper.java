package com.dg.helpers;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by Daniel Cohen Gindi (danielgindi@gmail.com)
 */
public class DismissSoftkeyboardHelper
{

    public static void setupUI(final Context context, View view)
    {
        if (view == null) return;

        String viewPackageName = view.getClass().getPackage().getName();
        String viewClassName = view.getClass().getSimpleName();

        if ((view instanceof EditText) ||
                (view instanceof Button) ||
                (view instanceof ImageButton) ||
                (view instanceof Checkable) ||
                (view instanceof DatePicker) ||
                (view instanceof AdapterView) ||
                (Build.VERSION.SDK_INT >= 11 && view instanceof android.widget.NumberPicker) ||
                (view instanceof android.widget.RadioGroup) ||
                (view instanceof android.widget.TimePicker) ||
                (Build.VERSION.SDK_INT >= 21 && view instanceof android.widget.Toolbar) ||
                (viewPackageName.equals("android.widget") && viewClassName.equals("Toolbar")) ||
                (viewClassName.equals("TextInputLayout")))
        {
            return;
        }

        view.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View v, MotionEvent event)
            {
                hideSoftKeyboard(context);
                return false;
            }
        });

        if (view instanceof ViewGroup)
        {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(context, innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Context context)
    {
        try
        {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception ex)
        {

        }
    }
}