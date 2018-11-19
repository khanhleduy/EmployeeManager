package l.com.ldk.duykhanh.whatdoyoumean.ACTIVITY.BOSS;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import l.com.ldk.duykhanh.whatdoyoumean.FRAGMENT.BOSS.MAIN_FRAGMENT.EmployeeB_fragment;
import l.com.ldk.duykhanh.whatdoyoumean.FRAGMENT.BOSS.MAIN_FRAGMENT.EventB_fragment;
import l.com.ldk.duykhanh.whatdoyoumean.FRAGMENT.BOSS.MAIN_FRAGMENT.InfomationB_fragment;
import l.com.ldk.duykhanh.whatdoyoumean.R;

public class BossActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bnvBoss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss);
        bnvBoss = findViewById(R.id.bnvBoss);
        bnvBoss.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState == null){
            addFrangment(new EmployeeB_fragment());
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.mn_employee:
                addFrangment(new EmployeeB_fragment());
                break;
            case R.id.mn_event:
                addFrangment(new EventB_fragment());
                Toast.makeText(this, "ui", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mn_user:
                addFrangment(new InfomationB_fragment());
                break;
        }
        return true;
    }

    private void addFrangment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_container, fragment)
                .addToBackStack("")
                .commit();

    }
}
