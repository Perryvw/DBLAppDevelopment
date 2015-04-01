package com.dblappdev.hitch.app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by s128232 on 10-3-2015.
 */
public class HitchRouteFragment extends Fragment implements View.OnClickListener {

    // Variables for EditText fields
    private EditText travelDate, travelTime, startPoint, endPoint;

    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hitch_route, container, false);

        getItems(rootView);

        travelDate.setOnClickListener(this);
        travelTime.setOnClickListener(this);

        return rootView;
    }

    // get the selected dropdown list value
    public void getItems(View rootView) {
        travelDate = (EditText) rootView.findViewById(R.id.travelDate);
        travelTime = (EditText) rootView.findViewById(R.id.travelTime);
        startPoint = (EditText) rootView.findViewById(R.id.startPoint);
        endPoint = (EditText) rootView.findViewById(R.id.endPoint);
    }

    @Override
    public void onClick(View v) {
        if (v == travelDate) {

            // Process to get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // Display Selected date in travelDate
                            travelDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }

                    }, mYear, mMonth, mDay);
            dpd.show();
        }
        if (v == travelTime) {

            // Process to get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            // Display Selected time in travelTime
                            travelTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            tpd.show();
        }
    }
}
