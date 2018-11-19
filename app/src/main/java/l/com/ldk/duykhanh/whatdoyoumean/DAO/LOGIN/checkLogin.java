package l.com.ldk.duykhanh.whatdoyoumean.DAO.LOGIN;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import l.com.ldk.duykhanh.whatdoyoumean.ACTIVITY.BOSS.BossActivity;
import l.com.ldk.duykhanh.whatdoyoumean.ACTIVITY.EMPLOYEE.EmployeeActivity;
import l.com.ldk.duykhanh.whatdoyoumean.ACTIVITY.LOGIN.LoginActivity;

public class checkLogin {
    private LoginActivity mContext;
    private String mUser, mPass;
    private boolean mCheck;
    int id;
    String user, pass, address, fullname, office, phone, email, birthday;
    private String url = "http://192.168.1.4/server/CheckLogin.php";
    ;

    public checkLogin(LoginActivity mContext, String user, String pass, boolean check) {
        this.mContext = mContext;
        this.mUser = user;
        this.mPass = pass;
        this.mCheck = check;
    }

    public int checkLogin(final String u, final String p) {
        int result = 0;
        RequestQueue requestQueue = Volley.newRequestQueue( mContext );
        final StringRequest request = new StringRequest( Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray a = new JSONArray( response );
                            if (a.length() < 1) {
                                Toast.makeText( mContext, "The Account not exists!", Toast.LENGTH_SHORT ).show();
                            } else {
                                for (int i = 0; i < a.length(); i++) {
                                    JSONObject ob = a.getJSONObject( i );
                                    id = ob.getInt( "IdEmployee" );
                                    phone = ob.getString( "PhoneEmployee" );
                                    user = ob.getString( "UserEmployee" );
                                    pass = ob.getString( "PassEmployee" );
                                    address = ob.getString( "AddressEmployee" );
                                    fullname = ob.getString( "FullNameEmployee" );
                                    office = ob.getString( "OfficeEmployee" );
                                    email = ob.getString( "EmailEmployee" );
                                    birthday = ob.getString( "BirthdayEmployee" );
                                    rememberUser( mUser, mPass, mCheck );
                                    if (office.equals( "boss" )) {
                                        Intent boss = new Intent( mContext.getApplicationContext(), BossActivity.class );
                                        boss.putExtra( "ID", id );
                                        boss.putExtra( "FN", fullname );
                                        boss.putExtra( "US", user );
                                        boss.putExtra( "PA", pass );
                                        boss.putExtra( "AD", address );
                                        boss.putExtra( "O", office );
                                        boss.putExtra( "P", phone );
                                        boss.putExtra( "E", email );
                                        boss.putExtra( "BD", birthday );
                                        mContext.startActivity( boss );
                                        mContext.finish();
                                    } else if (office.equals( "employee" )) {
                                        Intent emplyoee = new Intent( mContext.getApplicationContext(), EmployeeActivity.class );
                                        mContext.startActivity( emplyoee );
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d( "ERROLOGIN", error.toString() );
                    }
                } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put( "userEmployee", u );
                params.put( "passEmployee", p );
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "Content-Type", "application/x-www-form-urlencoded" );
                return params;
            }
        };
        requestQueue.add( request );
        return result;
    }

    public int check(String u, String p) {
        SharedPreferences sf = mContext.getSharedPreferences( "LOGIN", Context.MODE_PRIVATE );
        boolean check = sf.getBoolean( "CHECK", false );
        if (check) {
            u = sf.getString( "USER", "" );
            p = sf.getString( "PASS", "" );
            return 1;
        }
        return -1;
    }

    public void rememberUser(String u, String p, boolean status) {
        SharedPreferences sf = mContext.getSharedPreferences( "LOGIN", Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sf.edit();
        if (!status) {
            editor.clear();
        } else {
            editor.putString( "USER", u );
            editor.putString( "PASS", p );
            editor.putBoolean( "CHECK", status );
        }
        editor.apply();
    }

}
