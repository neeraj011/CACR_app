package cacr.cacr_app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class VolunteerFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    Spinner s1,s2;
    InputStream is=null, is1=null;
    String result = "", line = "", result1="", line1="";
    String institute,location;
    String[] insti;
    String[] locate;
    ProgressDialog progressDialog;
    public static String url = "https://neerajjethnani01.000webhostapp.com/cacr/get_institutions.php";
    public static String url1 = "https://neerajjethnani01.000webhostapp.com/cacr/get_locations.php";
    public VolunteerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_volunteer,container,false);
        s1=(Spinner)view.findViewById(R.id.spinner1);
        s2=(Spinner)view.findViewById(R.id.spinner2);
        String[] tables = {"View All", "Location","Institute"};

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, tables);


        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        s1.setAdapter(dataAdapter);
        s1.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        switch (position){
            case 0:
                s2.setVisibility(View.GONE);
                break;
            case 1:
                s2.setVisibility(View.VISIBLE);
                new RetrieveDon().execute();
                break;
            case 2:
                s2.setVisibility(View.VISIBLE);
                new RetrieveInst().execute();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private class RetrieveInst extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            institute="";
            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }catch (Exception e){
                // Toast.makeText(getApplicationContext(), exceptionMessage+", Ex1", Toast.LENGTH_SHORT).show();
            }try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                while((line=reader.readLine())!=null){
                    sb.append(line+"\n");
                }
                result = sb.toString();
                System.out.println("-----JSON Data-----");
                System.out.println(result);
                is.close();
            }catch(Exception e){
                //Toast.makeText(getApplicationContext(), exceptionMessage+", Ex2", Toast.LENGTH_SHORT).show();
            }try{
                JSONArray jsonArray = new JSONArray(result);
                int totalCount = jsonArray.length();
                for(int i=0; i<totalCount; i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    institute += jsonObject.getString("institute")+":";
                }

            }catch (Exception e){
                //Toast.makeText(getApplicationContext(), exceptionMessage+", Ex3", Toast.LENGTH_SHORT).show();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            insti = institute.split(":");


            s2.setAdapter(new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_list_item_1, insti));


            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Loading...");
            progressDialog.setMessage("Please Wait ... ");

            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    private class RetrieveDon extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            location="";
            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url1);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is1 = httpEntity.getContent();
            }catch (Exception e){
                // Toast.makeText(getApplicationContext(), exceptionMessage+", Ex1", Toast.LENGTH_SHORT).show();
            }try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(is1, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                while((line1=reader.readLine())!=null){
                    sb.append(line1+"\n");
                }
                result1 = sb.toString();
                is1.close();
            }catch(Exception e){
                //Toast.makeText(getApplicationContext(), exceptionMessage+", Ex2", Toast.LENGTH_SHORT).show();
            }try{
                JSONArray jsonArray = new JSONArray(result1);
                int totalCount = jsonArray.length();
                for(int i=0; i<totalCount; i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    location += jsonObject.getString("location")+":";
                }

            }catch (Exception e){
                //Toast.makeText(getApplicationContext(), exceptionMessage+", Ex3", Toast.LENGTH_SHORT).show();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            locate = location.split(":");


            s2.setAdapter(new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_list_item_1, locate));


            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Loading...");
            progressDialog.setMessage("Please Wait ... ");

            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }


}
