package l.com.ldk.duykhanh.whatdoyoumean.ACTIVITY.LOGIN;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import l.com.ldk.duykhanh.whatdoyoumean.DAO.LOGIN.checkLogin;
import l.com.ldk.duykhanh.whatdoyoumean.R;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText edtUser, edtPass;
    private CheckBox chkRemember;
    private TextView tvForgetPass;
    private Button btnLogin;
    private SharedPreferences sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
    }

    private void addControls() {
        sf = getSharedPreferences("LOGIN", MODE_PRIVATE);
        edtPass = findViewById(R.id.edtPassword);
        edtPass.setText(sf.getString("PASS", ""));
        edtUser = findViewById(R.id.edtUser);
        edtUser.setText(sf.getString("USER", ""));
        chkRemember = findViewById(R.id.chkRemember);
        chkRemember.setChecked(sf.getBoolean("CHECK", false));
        tvForgetPass = findViewById(R.id.tvForgetPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    BroadcastReceiver checkInternet = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            if (connectivityManager.getActiveNetworkInfo() != null) {
                btnLogin.setEnabled(true);
                btnLogin.setTextColor(Color.WHITE);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnLogin.setTextColor(Color.rgb(137, 137, 137));
                        btnLogin.setEnabled(false);
                        turnOnInternet();
                    }
                }, 1000);

            }
        }
    };

    private void turnOnInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No internet! Do you want turn on?");
        builder.setTitle("Message");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                wifi.setWifiEnabled(true);

            }
        });
        builder.show();
    }

    public void ButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                String u = edtUser.getText().toString(), p = edtPass.getText().toString();
                if (u.isEmpty()) {
                    Toast.makeText(this, "Please input user name!", Toast.LENGTH_SHORT).show();
                } else if (p.isEmpty()) {
                    Toast.makeText(this, "Please input password!", Toast.LENGTH_SHORT).show();
                } else {
                    checkLogin check = new checkLogin(this, u, p, chkRemember.isChecked());
                    check.checkLogin(u, p);
                }
                break;
            case R.id.btnCancelLogin:
                finish();
                break;
        }
    }

    // đăng ký
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(checkInternet, filter);
    }
    //hủy bỏ

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(checkInternet);
    }
}
