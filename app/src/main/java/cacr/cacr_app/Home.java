package cacr.cacr_app;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    DonorFragment df;
    PartnerFragment pf;
    VolunteerFragment vf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_layout);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position,false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
           // Toast.makeText(getApplicationContext(),"MEAN HAI TU",Toast.LENGTH_LONG).show();
        }
        if (id == R.id.action_add) {


        }
        return true;
    }
    private void setupViewPager(ViewPager viewPager)
    {
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        vf=new VolunteerFragment();
        df=new DonorFragment();
        pf= new PartnerFragment();
        //ViewFragment =new ViewFragment();
        adapter.addFragment(vf,"VOLUNTEERS");
        adapter.addFragment(df,"DONORS");
        adapter.addFragment(pf,"PARTNERS");
        //adapter.addFragment(DeleteFragment,"DELETE");

        viewPager.setAdapter(adapter);
    }

}
