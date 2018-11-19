package l.com.ldk.duykhanh.whatdoyoumean.DAO.BOSS;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import l.com.ldk.duykhanh.whatdoyoumean.FRAGMENT.BOSS.BOTTOMSHEET.ExampleBottomSheetDialog;
import l.com.ldk.duykhanh.whatdoyoumean.FRAGMENT.BOSS.MAIN_FRAGMENT.EmployeeB_fragment;
import l.com.ldk.duykhanh.whatdoyoumean.MODEL.Employee;
import l.com.ldk.duykhanh.whatdoyoumean.R;

public class DAOboss {
    public Fragment mContext;
    public static DAOboss it;
    private OnCallBack listener;
    List<Employee> employeeList;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public DAOboss(Fragment mContext) {
        this.mContext = mContext;
        employeeList = new ArrayList<>();
        it = this;
    }

    public DAOboss(OnCallBack listener,ExampleBottomSheetDialog mContext) {
        this.mContext = mContext;
        this.listener = listener;
    }


    public List<Employee> getAllEmployee() {
        String url = "http://192.168.1.3/server/GetData.php";
        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        employeeList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject ob = response.getJSONObject(i);
                                String fn, u, p, e, ad, bd, t, o;
                                int id;
                                id = ob.getInt("ID");
                                fn = ob.getString("FullName");
                                u = ob.getString("UserName");
                                p = ob.getString("Password");
                                e = ob.getString("Email");
                                bd = ob.getString("Birthday");
                                t = ob.getString("Phone");
                                o = ob.getString("Office");
                                ad = ob.getString("Address");
                                employeeList.add(new Employee(id, fn, u, p, ad, sdf.parse(bd), t, o, e));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        EmployeeB_fragment.adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERRO_GET_LIST_EMPLOYEE", error.toString());
                    }
                });
        requestQueue.add(request);
        return employeeList;
    }

    public void deleteEmplopyeeById(final int id, final String name) {
        final String url = "http://192.168.1.3/server/DeleteData.php";
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext.getContext());
        builder.setTitle("DELETE");
        builder.setIcon(android.R.drawable.ic_menu_delete);
        builder.setMessage("Do you want delete \"" + name + "\"?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mContext.getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout_container, new EmployeeB_fragment())
                        .commit();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RequestQueue requestQueue = Volley.newRequestQueue(mContext.getContext());
                StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("Success1234!")) {
                                    Toast.makeText(mContext.getContext(), "Delete Complete!", Toast.LENGTH_SHORT).show();
                                    mContext.getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.layout_container, new EmployeeB_fragment())
                                            .commit();
                                } else {
                                    Toast.makeText(mContext.getContext(), "Delete Employee \"" + name + "\" Success!", Toast.LENGTH_SHORT).show();
                                    mContext.getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.layout_container, new EmployeeB_fragment())
                                            .commit();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("ERRO_DELETE", error.toString());
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("idEmployee", String.valueOf(id));
                        return params;
                    }
                };
                requestQueue.add(request);
            }
        });
        builder.show();
    }

    public void insertEmployee(final Employee ee) {

        String url = "http://192.168.1.3/server/InsertData.php";
        RequestQueue requestQueue = Volley.newRequestQueue(mContext.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("SuccessEmployee")) {
                            mContext.getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.layout_container, new EmployeeB_fragment())
                                    .commit();
                            listener.onItemClicked("khanh");
                        } else {
                            mContext.getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.layout_container, new EmployeeB_fragment())
                                    .commit();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext.getContext(), "error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fullNameE", ee.getmFullName());
                params.put("userE", ee.getmUser());
                params.put("passE", ee.getmPass());
                params.put("addressE", ee.getmAddress());
                params.put("birthdayE", String.valueOf(sdf.format(ee.getmBirthday())));
                params.put("phoneE", ee.getmPhone());
                params.put("officeE", ee.getmOffice());
                params.put("emailE", ee.getmEmail());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public interface OnCallBack {
        void onItemClicked(String k );
    }
}
