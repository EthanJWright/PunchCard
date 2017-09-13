package com.example.ethanwright.javapunchcard;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import java.util.Calendar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.ethanwright.javapunchcard.R;
import com.example.ethanwright.javapunchcard.UserChosenTime;

public class PickTime extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        UserChosenTime.Hours = hourOfDay;
        UserChosenTime.Minutes = minute;
        TextView temp = (TextView) getActivity().findViewById(R.id.card_active_duration);
        temp.setText(hourOfDay + ":" + minute);
    }
    @Override
    public void onStop(){

    }
}
