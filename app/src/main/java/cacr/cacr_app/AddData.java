package cacr.cacr_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


public class AddData extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;
    EditText name,email,phone,college,location,year,area,amount,project;
    String names,emails,phones,colleges,locations,years,areas,projects;
    Spinner spinner;
    Button b1;
    int amounts;
    ProgressDialog progressDialog;
    Boolean isInserted;
    InputStream is = null;
    public static String url = "https://neerajjethnani01.000webhostapp.com/cacr/add_volunteers.php";
    public static String url1 = "https://neerajjethnani01.000webhostapp.com/cacr/add_donors.php";
    public static String url2 = "https://neerajjethnani01.000webhostapp.com/cacr/add_partners.php";
    String exceptionMessage = "There seems to be some problem connecting to database. " +
            "Please check your Internet Connection and try again.";
    String successMessage = "Data has been entered successfully";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);
        tv5 = (TextView) findViewById(R.id.textView41);
        tv6 = (TextView) findViewById(R.id.textView42);
        tv7 = (TextView) findViewById(R.id.textView5);
        tv8 = (TextView) findViewById(R.id.textView51);
        tv9 = (TextView) findViewById(R.id.textView52);
        name = (EditText) findViewById(R.id.editText);
        email = (EditText) findViewById(R.id.editText3);
        phone = (EditText) findViewById(R.id.editText4);
        project = (EditText) findViewById(R.id.editText5);
        college = (EditText) findViewById(R.id.editText5);
        year = (EditText) findViewById(R.id.editText5);
        location = (EditText) findViewById(R.id.editText62);
        amount = (EditText) findViewById(R.id.editText61);
        area = (EditText) findViewById(R.id.editText6);
        b1 = (Button) findViewById(R.id.button);
        String[] tables = {"Volunteers", "Donors", "Partners"};
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = spinner.getSelectedItemPosition();
                switch (selected) {
                    case 0:
                    names = name.getText().toString();
                        emails = email.getText().toString();
                        phones = phone.getText().toString();
                        locations = location.getText().toString();
                        colleges = college.getText().toString();
                        if (names.equals("") || emails.equals("") || phones.equals("") || locations.equals("") || colleges.equals("")) {
                            String msg = "One or more fields are empty";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                        else
                            new addvolunteer().execute();
                            break;
                    case 1:
                        names = name.getText().toString();
                        emails = email.getText().toString();
                        phones = phone.getText().toString();
                        amounts = Integer.parseInt(amount.getText().toString());
                        projects = project.getText().toString();
                        if (names.equals("") || emails.equals("") || phones.equals("") || amounts==0 || projects.equals("")) {
                            String msg = "One or more fields are empty";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                        else
                            new adddonor().execute();
                        break;
                    case 2:
                        names = name.getText().toString();
                        emails = email.getText().toString();
                        phones = phone.getText().toString();
                        years = year.getText().toString();
                        areas = area.getText().toString();
                        if (names.equals("") || emails.equals("") || phones.equals("") || years.equals("") || areas.equals("")) {
                            String msg = "One or more fields are empty";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                        else
                            new addpartner().execute();
                        break;

                }
            }
        });
    }






    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int selected=spinner.getSelectedItemPosition();
        switch (position)
        {
            case 0:
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                tv3.setVisibility(View.VISIBLE);
                tv6.setVisibility(View.VISIBLE);
                tv9.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
                phone.setVisibility(View.VISIBLE);
                location.setVisibility(View.VISIBLE);
                college.setVisibility(View.VISIBLE);
                b1.setVisibility(View.VISIBLE);
                tv5.setVisibility(View.INVISIBLE);
                tv8.setVisibility(View.INVISIBLE);
                tv4.setVisibility(View.INVISIBLE);
                tv7.setVisibility(View.INVISIBLE);
                amount.setVisibility(View.INVISIBLE);
                area.setVisibility(View.INVISIBLE);
                break;
            case 1:
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                tv3.setVisibility(View.VISIBLE);
                tv5.setVisibility(View.VISIBLE);
                tv8.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
                phone.setVisibility(View.VISIBLE);
                project.setVisibility(View.VISIBLE);
                amount.setVisibility(View.VISIBLE);
                b1.setVisibility(View.VISIBLE);
                tv4.setVisibility(View.INVISIBLE);
                tv7.setVisibility(View.INVISIBLE);
                area.setVisibility(View.INVISIBLE);
                tv6.setVisibility(View.INVISIBLE);
                tv9.setVisibility(View.INVISIBLE);
                location.setVisibility(View.INVISIBLE);

                break;
            case 2:
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                tv3.setVisibility(View.VISIBLE);
                tv4.setVisibility(View.VISIBLE);
                tv7.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                email.setVisibility(View.VISIBLE);
                phone.setVisibility(View.VISIBLE);
                year.setVisibility(View.VISIBLE);
                area.setVisibility(View.VISIBLE);
                b1.setVisibility(View.VISIBLE);
                tv6.setVisibility(View.INVISIBLE);
                tv9.setVisibility(View.INVISIBLE);
                location.setVisibility(View.INVISIBLE);
                tv5.setVisibility(View.INVISIBLE);
                tv8.setVisibility(View.INVISIBLE);
                amount.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class addvolunteer extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            nameValuePairList.add(new BasicNameValuePair("name", names));
            nameValuePairList.add(new BasicNameValuePair("email", emails));
            nameValuePairList.add(new BasicNameValuePair("contact", phones));
            nameValuePairList.add(new BasicNameValuePair("location", locations));
            nameValuePairList.add(new BasicNameValuePair("institute", colleges));


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                is = httpEntity.getContent();
                isInserted=true;
                is.close();
            }catch(IOException e){
                isInserted=false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {

            progressDialog.dismiss();

            if (isInserted)
                Toast.makeText(getApplicationContext(), successMessage, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), exceptionMessage, Toast.LENGTH_LONG).show();
            name.setText("");
            email.setText("");
            phone.setText("");
            location.setText("");
            college.setText("");

        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(AddData.this);
            progressDialog.setTitle("Loading...");
            progressDialog.setMessage("Please Wait ... ");

            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }
    private class adddonor extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            nameValuePairList.add(new BasicNameValuePair("name", names));
            nameValuePairList.add(new BasicNameValuePair("email", emails));
            nameValuePairList.add(new BasicNameValuePair("contact", phones));
            nameValuePairList.add(new BasicNameValuePair("amount", String.valueOf(amounts)));
            nameValuePairList.add(new BasicNameValuePair("project", projects));


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url1);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                is = httpEntity.getContent();
                isInserted=true;
                is.close();
            }catch(IOException e){
                isInserted=false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {

            progressDialog.dismiss();

            if (isInserted)
                Toast.makeText(getApplicationContext(), successMessage, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), exceptionMessage, Toast.LENGTH_LONG).show();
            name.setText("");
            email.setText("");
            phone.setText("");
            amount.setText("");
            project.setText("");

        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(AddData.this);
            progressDialog.setTitle("Loading...");
            progressDialog.setMessage("Please Wait ... ");

            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }
    private class addpartner extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            nameValuePairList.add(new BasicNameValuePair("name", names));
            nameValuePairList.add(new BasicNameValuePair("email", emails));
            nameValuePairList.add(new BasicNameValuePair("contact", phones));
            nameValuePairList.add(new BasicNameValuePair("area", areas));
            nameValuePairList.add(new BasicNameValuePair("year", years));


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url2);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                is = httpEntity.getContent();
                isInserted=true;
                is.close();
            }catch(IOException e){
                isInserted=false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {

            progressDialog.dismiss();

            if (isInserted)
                Toast.makeText(getApplicationContext(), successMessage, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), exceptionMessage, Toast.LENGTH_LONG).show();
            name.setText("");
            email.setText("");
            phone.setText("");
            year.setText("");
            area.setText("");

        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(AddData.this);
            progressDialog.setTitle("Loading...");
            progressDialog.setMessage("Please Wait ... ");

            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }
}
