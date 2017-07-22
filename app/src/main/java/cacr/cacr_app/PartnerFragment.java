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
import android.widget.ListView;
import android.widget.Spinner;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PartnerFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner s1,s2;
    InputStream is=null, is1=null;
    String line="", result="", result1="", line1="";
    String expertise,year;
    ListView list1;
    ListView list2;
    ProgressDialog progressDialog;
    String name_arr="", email_arr="", contact_arr="", year_arr="", expertise_arr="", combinedText="";
    String name_arr1[], email_arr1[], contact_arr1[], year_arr1[], expertise_arr1[], combinedArray[];
    public static String url = "https://neerajjethnani01.000webhostapp.com/cacr/get_expertise.php";
    public static String url1 = "https://neerajjethnani01.000webhostapp.com/cacr/get_year.php";
    public static String url2 = "https://neerajjethnani01.000webhostapp.com/cacr/get_all_partners.php";
    public static String url3 = "https://neerajjethnani01.000webhostapp.com/cacr/get_expertise_data.php";
    public static String url4 = "https://neerajjethnani01.000webhostapp.com/cacr/get_years_data.php";
    public PartnerFragment() {
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
        View view = inflater.inflate(R.layout.fragment_partner,container,false);
        s1=(Spinner)view.findViewById(R.id.spinner1);
        s2=(Spinner)view.findViewById(R.id.spinner2);
        list1=(ListView)view.findViewById(R.id.listView1);
        list2=(ListView)view.findViewById(R.id.listView2);
        String[] tables={"View All","Area of Expertise","Year"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, tables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(dataAdapter);
        s1.setOnItemSelectedListener(this);
        s2.setOnItemSelectedListener(this);
        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner)parent;
        if(spinner.getId()==R.id.spinner1){
            switch (position){
                case 0:
                    s2.setVisibility(View.GONE);
                    list1.setVisibility(View.VISIBLE);
                    list2.setVisibility(View.GONE);
                    new RetrieveAll().execute();
                    break;
                case 1:
                    s2.setVisibility(View.VISIBLE);
                    new RetreiveExp().execute();
                    list1.setVisibility(View.GONE);
                    list2.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    s2.setVisibility(View.VISIBLE);
                    new RetreiveYear().execute();
                    list1.setVisibility(View.GONE);
                    list2.setVisibility(View.VISIBLE);
                    break;
            }
        }
        else if(spinner.getId()==R.id.spinner2){
            if(s1.getSelectedItemPosition()==1){
                expertise=s2.getSelectedItem().toString();
                new RetreiveExpAll().execute();
            }
            else if(s1.getSelectedItemPosition()==2){
                year=s2.getSelectedItem().toString();
                new RetreiveYearAll().execute();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class RetreiveExp extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            expertise="";
            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity=httpResponse.getEntity();
                is=httpEntity.getContent();
            }
            catch(Exception e){

            }
            try{
                BufferedReader reader=new BufferedReader(new InputStreamReader(is,"iso-8859-1"), 8);
                StringBuilder sb= new StringBuilder();
                while((line=reader.readLine())!=null){
                    sb.append(line+"/n");
                }
                result=sb.toString();
                is.close();
            }
            catch(Exception e){

            }
            try{
                JSONArray jsonArray=new JSONArray(result);
                int totalCount=jsonArray.length();
                for(int i=0;i<totalCount;i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    expertise+=jsonObject.getString("expertise")+":";
                }
            }
            catch(Exception e){

            }
            return  null;
        }
        @Override
        protected void onPostExecute(Void a){
            expertise_arr1=expertise.split(":");

            s2.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,expertise_arr1));

            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute(){
            progressDialog=new ProgressDialog(getContext());
            progressDialog.setTitle("Loading...");
            progressDialog.setMessage("Please Wait ... ");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }
    private class RetreiveYear extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            year="";
            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url1);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity=httpResponse.getEntity();
                is=httpEntity.getContent();
            }
            catch(Exception e){

            }
            try{
                BufferedReader reader=new BufferedReader(new InputStreamReader(is,"iso-8859-1"), 8);
                StringBuilder sb= new StringBuilder();
                while((line=reader.readLine())!=null){
                    sb.append(line+"/n");
                }
                result=sb.toString();
                is.close();
            }
            catch(Exception e){

            }
            try{
                JSONArray jsonArray=new JSONArray(result);
                int totalCount=jsonArray.length();
                for(int i=0;i<totalCount;i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    year+=jsonObject.getString("year")+":";
                }
            }
            catch(Exception e){

            }
            return  null;
        }
        @Override
        protected void onPostExecute(Void a){
            year_arr1=year.split(":");

            s2.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,year_arr1));

            progressDialog.dismiss();
        }

        @Override
        protected void onPreExecute(){
            progressDialog=new ProgressDialog(getContext());
            progressDialog.setTitle("Loading...");
            progressDialog.setMessage("Please Wait ... ");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    private class RetrieveAll extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try{
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url2);
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
                combinedText="";
                JSONArray jsonArray = new JSONArray(result);
                int totalCount = jsonArray.length();
                for(int i=0; i<totalCount; i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    name_arr += jsonObject.getString("name")+":";
                    contact_arr +=jsonObject.getString("contact")+":";
                    email_arr +=jsonObject.getString("email")+":";
                    year_arr +=jsonObject.getString("year")+":";
                    expertise_arr +=jsonObject.getString("expertise")+":";
                    combinedText += (i+1)+". Name '"+jsonObject.getString("name")+"', "+
                            "Email '"+jsonObject.getString("email")+"',"+
                            "Contact '"+jsonObject.getString("contact")+"',"+
                            "Year '"+jsonObject.getString("year")+"',"+
                            "Expertise '"+jsonObject.getString("expertise")+"',"+":";
                }

            }catch (Exception e){
                //Toast.makeText(getApplicationContext(), exceptionMessage+", Ex3", Toast.LENGTH_SHORT).show();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            name_arr1 = name_arr.split(":");
            email_arr1 = email_arr.split(":");
            contact_arr1 = contact_arr.split(":");
            year_arr1 = year_arr.split(":");
            expertise_arr1 = expertise_arr.split(":");
            combinedArray = combinedText.split(":");

            list1.setAdapter(new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_list_item_1, combinedArray));


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
    private class RetreiveExpAll extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try{

                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(new BasicNameValuePair("expertise", expertise));
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url3);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
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
                combinedText="";
                JSONArray jsonArray = new JSONArray(result);
                int totalCount = jsonArray.length();
                for(int i=0; i<totalCount; i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    name_arr += jsonObject.getString("name")+":";
                    contact_arr +=jsonObject.getString("contact")+":";
                    email_arr +=jsonObject.getString("email")+":";
                    year_arr +=jsonObject.getString("year")+":";
                    expertise_arr +=jsonObject.getString("expertise")+":";
                    combinedText += (i+1)+". Name '"+jsonObject.getString("name")+"', "+
                            "Email '"+jsonObject.getString("email")+"',"+
                            "Contact '"+jsonObject.getString("contact")+"',"+
                            "Year '"+jsonObject.getString("year")+"',"+
                            "Expertise '"+jsonObject.getString("expertise")+"',"+":";
                }

            }catch (Exception e){
                //Toast.makeText(getApplicationContext(), exceptionMessage+", Ex3", Toast.LENGTH_SHORT).show();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            name_arr1 = name_arr.split(":");
            email_arr1 = email_arr.split(":");
            contact_arr1 = contact_arr.split(":");
            year_arr1 = year_arr.split(":");
            expertise_arr1 = expertise_arr.split(":");
            combinedArray = combinedText.split(":");

            list2.setAdapter(new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_list_item_1, combinedArray));


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
    private class RetreiveYearAll extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try{

                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(new BasicNameValuePair("year", year));
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url4);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
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
                combinedText="";
                JSONArray jsonArray = new JSONArray(result);
                int totalCount = jsonArray.length();
                for(int i=0; i<totalCount; i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    name_arr += jsonObject.getString("name")+":";
                    contact_arr +=jsonObject.getString("contact")+":";
                    email_arr +=jsonObject.getString("email")+":";
                    year_arr +=jsonObject.getString("year")+":";
                    expertise_arr +=jsonObject.getString("expertise")+":";
                    combinedText += (i+1)+". Name '"+jsonObject.getString("name")+"', "+
                            "Email '"+jsonObject.getString("email")+"',"+
                            "Contact '"+jsonObject.getString("contact")+"',"+
                            "Year '"+jsonObject.getString("year")+"',"+
                            "Expertise '"+jsonObject.getString("expertise")+"',"+":";
                }

            }catch (Exception e){
                //Toast.makeText(getApplicationContext(), exceptionMessage+", Ex3", Toast.LENGTH_SHORT).show();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            name_arr1 = name_arr.split(":");
            email_arr1 = email_arr.split(":");
            contact_arr1 = contact_arr.split(":");
            expertise_arr1 = expertise_arr.split(":");
            year_arr1 = year_arr.split(":");
            combinedArray = combinedText.split(":");

            list2.setAdapter(new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_list_item_1, combinedArray));


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