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

public class DonorFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner s1,s2;
    public static String url = "https://neerajjethnani01.000webhostapp.com/cacr/get_projects.php";
    public static String url2 = "https://neerajjethnani01.000webhostapp.com/cacr/get_all_donors.php";
    public static String url3 = "https://neerajjethnani01.000webhostapp.com/cacr/get_projects_data.php";
    public static String url4 = "https://neerajjethnani01.000webhostapp.com/cacr/get_amounts_data.php";
    InputStream is=null;
    String line="", result="",project;
    int amount1,amount2;
    String[] proj;
    ProgressDialog progressDialog;
    ListView list1;
    ListView list2;
    String name_arr="", email_arr="", contact_arr="", project_arr="", amount_arr="", combinedText="";
    String name_arr1[], email_arr1[], contact_arr1[], project_arr1[], amount_arr1[], combinedArray[];
    public DonorFragment() {
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
        View view=inflater.inflate(R.layout.fragment_donor,container,false);
        s1=(Spinner)view.findViewById(R.id.spinner1);
        s2=(Spinner)view.findViewById(R.id.spinner2);
        list1=(ListView)view.findViewById(R.id.listView1);
        list2=(ListView)view.findViewById(R.id.listView2);
        String[] tables={"View All","Project","Amount"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, tables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(dataAdapter);
        s1.setOnItemSelectedListener(this);
        s2.setOnItemSelectedListener(this);
        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner=(Spinner)parent;
        if(spinner.getId()== R.id.spinner1){
            switch (position){
                case 0:
                    s2.setVisibility(View.GONE);
                    list1.setVisibility(View.VISIBLE);
                    list2.setVisibility(View.GONE);
                    new RetrieveAll().execute();
                    break;
                case 1:
                    s2.setVisibility(View.VISIBLE);
                    new RetreiveDon().execute();
                    list1.setVisibility(View.GONE);
                    list2.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    s2.setVisibility(View.VISIBLE);
                    String[] am={"0 to 500","500 to 2500","2500 to 10000","10000 to 25000","25000 to 50000","more than 50000"};
                    s2.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,am));
                    list1.setVisibility(View.GONE);
                    list2.setVisibility(View.VISIBLE);
            }
        }
        else if(spinner.getId()== R.id.spinner2){
            if(s1.getSelectedItemPosition()==1) {
                project = s2.getSelectedItem().toString();
                new RetreiveProjAll().execute();
            }
           else if(s1.getSelectedItemPosition()==2) {
                switch(s2.getSelectedItemPosition())
                {
                    case 0: amount1=0;
                        amount2=500;
                        break;
                    case 1: amount1=500;
                        amount2=2500;
                        break;
                    case 2: amount1=2500;
                        amount2=10000;
                        break;
                    case 3: amount1=10000;
                        amount2=25000;
                        break;
                    case 4: amount1=25000;
                        amount2=50000;
                        break;
                    case 5: amount1=50000;
                        amount2=5000000;
                        break;
                }

                new RetreiveAmountAll().execute();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class RetreiveDon extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            project="";
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
                    project+=jsonObject.getString("project")+":";
                }
            }
            catch(Exception e){

            }
            return  null;
        }
        @Override
        protected void onPostExecute(Void a){
            proj=project.split(":");

            s2.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,proj));

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
                    project_arr +=jsonObject.getString("project")+":";
                    amount_arr +=jsonObject.getString("amount")+":";
                    combinedText += (i+1)+". Name '"+jsonObject.getString("name")+"', "+
                            "Email '"+jsonObject.getString("email")+"',"+
                            "Contact '"+jsonObject.getString("contact")+"',"+
                            "Project '"+jsonObject.getString("project")+"',"+
                            "Amount '"+jsonObject.getString("amount")+"',"+":";
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
            project_arr1 = project_arr.split(":");
            amount_arr1 = amount_arr.split(":");
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
    private class RetreiveProjAll extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try{

                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(new BasicNameValuePair("project", project));
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
                    project_arr +=jsonObject.getString("project")+":";
                    amount_arr +=jsonObject.getString("amount")+":";
                    combinedText += (i+1)+". Name '"+jsonObject.getString("name")+"', "+
                            "Email '"+jsonObject.getString("email")+"',"+
                            "Contact '"+jsonObject.getString("contact")+"',"+
                            "Project '"+jsonObject.getString("project")+"',"+
                            "Amount '"+jsonObject.getString("amount")+"',"+":";
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
            project_arr1 = project_arr.split(":");
            amount_arr1 = amount_arr.split(":");
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
    private class RetreiveAmountAll extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try{

                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(new BasicNameValuePair("amount1", String.valueOf(amount1)));
                nameValuePairList.add(new BasicNameValuePair("amount2", String.valueOf(amount2)));
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
                    project_arr +=jsonObject.getString("project")+":";
                    amount_arr +=jsonObject.getString("amount")+":";
                    combinedText += (i+1)+". Name '"+jsonObject.getString("name")+"', "+
                            "Email '"+jsonObject.getString("email")+"',"+
                            "Contact '"+jsonObject.getString("contact")+"',"+
                            "Project '"+jsonObject.getString("project")+"',"+
                            "Amount '"+jsonObject.getString("amount")+"',"+":";
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
            project_arr1 = project_arr.split(":");
            amount_arr1 = amount_arr.split(":");
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