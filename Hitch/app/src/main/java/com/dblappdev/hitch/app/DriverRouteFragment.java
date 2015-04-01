package com.dblappdev.hitch.app;

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
public class DriverRouteFragment extends Fragment {

    private Spinner spinner;
    private Button btnOK, btnCancel, btnNow;
    private EditText start, destination;
    private String startValue, destinationValue;
    List<String> spinnerList;
    private TimePicker timePicker;
    private String strDateTime;
    private API api;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_driver_route, container, false);

        start = (EditText) rootView.findViewById(R.id.startPoint);
        destination = (EditText) rootView.findViewById(R.id.destinationPoint);
        spinner = (Spinner) rootView.findViewById(R.id.routes_spinner);
        timePicker = (TimePicker) rootView.findViewById(R.id.driverRouteTimePicker);

        buildSpinnerList();
        addItemsToSpinner(rootView);
        addListenerOnButtons(rootView);
        addSpinnerListener();
        return rootView;
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
        final API api = new API();

        api.getUserRoutes(2, new Callable<Void>() {
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
        btnCancel = (Button) rootView.findViewById(R.id.buttonCancel);
        btnNow = (Button) rootView.findViewById(R.id.buttonNow);

        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startValue = start.getText().toString();
                destinationValue = destination.getText().toString();
                strDateTime = /*dp.getYear() + "-" + (dp.getMonth() + 1) + "-" +
                        dp.getDayOfMonth() + " "+ */timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();

                if  (String.valueOf(spinner.getSelectedItem()) == "Choose an existing route") {
                    Toast.makeText(getActivity().getBaseContext(),
                            "Selected route : " + startValue + " -> " + destinationValue + " at" + strDateTime,
                            Toast.LENGTH_SHORT).show();
                    /*api.addUserRoute(2, startValue, destinationValue, strDateTime, new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            return null;
                        }
                    });*/

                }

            }

        });

        btnNow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                timePicker.setCurrentHour(c.get(Calendar.HOUR));
                timePicker.setCurrentMinute(c.get(Calendar.MINUTE));
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
}
