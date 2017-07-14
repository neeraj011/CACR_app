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

public class DonorFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner s1,s2;
    public static String url = "https://neerajjethnani01.000webhostapp.com/cacr/get_projects.php";
    InputStream is=null;
    String line="", result="",project;
    String[] proj;
    ProgressDialog progressDialog;
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
        String[] tables={"View All","Project"};
        String[] tables1={"View All","Project","wassup"};
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, tables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(dataAdapter);
        s1.setOnItemSelectedListener(this);
        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                s2.setVisibility(View.GONE);
                break;
            case 1:
                s2.setVisibility(View.VISIBLE);
                new RetreiveDon().execute();
                break;
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
}