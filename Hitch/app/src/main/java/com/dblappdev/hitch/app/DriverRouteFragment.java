package com.dblappdev.hitch.app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.dblappdev.hitch.network.API;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.dblappdev.hitch.app.DriverFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by s128232 on 10-3-2015.
 */
public class DriverRouteFragment extends Fragment implements View.OnClickListener {

    private Spinner spinner;
    private Button btnOK, btnCancel, btnNow;
    private EditText start, destination;
    private String startValue, destinationValue;
    List<String> spinnerList;
    private TimePicker timePicker;
    private int dateTime;
    private SharedPreferences prefs;
    private int userID;

    // Variables for Time and Date fields
    private EditText travelDate, travelTime;

    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_driver_route, container, false);

        prefs = this.getActivity().getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE);

        getItems(rootView);

        travelDate.setOnClickListener(this);
        travelTime.setOnClickListener(this);
        buildSpinnerList();
        addItemsToSpinner(rootView);
        addListenerOnButtons(rootView);
        addSpinnerListener();
        getCurrentTime();
        return rootView;
    }

    public void getItems(View rootView) {
        start = (EditText) rootView.findViewById(R.id.startPoint);
        destination = (EditText) rootView.findViewById(R.id.destinationPoint);
        spinner = (Spinner) rootView.findViewById(R.id.routes_spinner);
        travelDate = (EditText) rootView.findViewById(R.id.travelDate);
        travelTime = (EditText) rootView.findViewById(R.id.travelTime);
    }

    public void addItemsToSpinner(View rootView) {
        spinnerList = new ArrayList<String>();

        spinnerList.add("Choose an existing route");

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, spinnerList);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void buildSpinnerList() {
        //get the user id
        userID = prefs.getInt(MainActivity.USER_KEY, -1);
        if (userID == -1) return;

        final API api = new API();

        api.getUserRoutes(userID, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                JSONObject json = api.getResponse();
                try {
                    JSONArray arr = json.getJSONArray("routes");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject route = arr.getJSONObject(i);
                        int routeID = route.getInt("routeID");
                        String startPoint = route.getString("startPoint");
                        String endPoint = route.getString("endPoint");
                        String timestamp = route.getString("timestamp");
                        String routeName = startPoint + " -> " + endPoint;
                        spinnerList.add(routeName);
                    }

                } catch(JSONException e) {
                    e.printStackTrace();
                    spinnerList.add("exception");
                }
                return null;
            }
        });
    }

    // get the selected dropdown list value
    public void addListenerOnButtons(View rootView) {
        btnOK = (Button) rootView.findViewById(R.id.buttonOK);
        btnNow = (Button) rootView.findViewById(R.id.buttonNow);

        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final API api = new API();
                startValue = start.getText().toString().trim();
                destinationValue = destination.getText().toString().trim();
                try {
                    startValue = java.net.URLEncoder.encode(startValue, "UTF-8").replace("+", "%20");
                    destinationValue = java.net.URLEncoder.encode(destinationValue, "UTF-8").replace("+", "%20");
                }
                catch (Exception e) {

                }
                dateTime = toTimestamp(mYear, mMonth, mDay, mHour, mMinute);

                api.addUserRoute(userID, startValue, destinationValue, dateTime, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        return null;
                    }
                });
            }
        });

        btnNow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getCurrentTime();
                setTimeText(mHour, mMinute);
                setDateText(mYear, mMonth, mDay);
            }

        });
    }

    public void addSpinnerListener () {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selected = parentView.getItemAtPosition(position).toString();
                if (selected == "Choose an existing route") {
                    start.setText("");
                    destination.setText("");
                    start.setEnabled(true);
                    destination.setEnabled(true);
                }
                else {
                    start.setText(selected.split("->")[0]);
                    destination.setText(selected.split("->")[1]);
                    start.setEnabled(false);
                    destination.setEnabled(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    public void getCurrentTime () {
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
    }

    public void setTimeText (int hour, int minute) {
        if (minute < 10) {
            travelTime.setText(hour + ":0" + minute);
        }
        else {
            travelTime.setText(hour + ":" + minute);
        }
    }

    public void setDateText (int year, int month, int day) {
        String monthString = Integer.toString(month+1);
        String dayString = Integer.toString(day);
        if (month < 10) {
            monthString = "0" + monthString;
        }
        if (day < 10) {
            dayString = "0" + dayString;
        }
        travelDate.setText(year + "-" + (monthString) + "-" + dayString);
    }

    @Override
    public void onClick(View v) {
        if (v == travelDate) {
            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            setDateText(year, monthOfYear, dayOfMonth);
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                        }

                    }, mYear, mMonth, mDay);
            dpd.show();
        }

        if (v == travelTime) {

            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                   setTimeText(hourOfDay, minute);
                   mHour = hourOfDay;
                   mMinute = minute;
                }
            }, mHour, mMinute, false);
            tpd.show();
        }
    }

    int toTimestamp(int year, int month, int day, int hour, int minute) {

        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000L);
    }
}
