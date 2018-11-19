package l.com.ldk.duykhanh.whatdoyoumean.FRAGMENT.BOSS.BOTTOMSHEET;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import l.com.ldk.duykhanh.whatdoyoumean.DAO.BOSS.DAOboss;
import l.com.ldk.duykhanh.whatdoyoumean.FRAGMENT.BOSS.MAIN_FRAGMENT.EmployeeB_fragment;
import l.com.ldk.duykhanh.whatdoyoumean.MODEL.Employee;
import l.com.ldk.duykhanh.whatdoyoumean.R;

@SuppressLint("ValidFragment")
public class ExampleBottomSheetDialog extends BottomSheetDialogFragment implements  DAOboss.OnCallBack{

    private View view;
    public EditText edFullnameE, edUserE, edPassE, edAddressE, edBirthdayE, edPhoneE, edOfficeE, edMailE;
    private ImageView btnDone, btnClose;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-DD-mm");
    DAOboss daOboss;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);
        init();
        daOboss = new DAOboss(this);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (validayFrom() > 0) {
                        Employee employee = new Employee(edFullnameE.getText().toString(),
                                edUserE.getText().toString(),
                                edPassE.getText().toString(),
                                edAddressE.getText().toString(),
                                sdf.parse(edBirthdayE.getText().toString()),
                                edPhoneE.getText().toString(),
                                edOfficeE.getText().toString(),
                                edMailE.getText().toString());
                        try {
                            daOboss.insertEmployee(employee);
                            Toast.makeText(getContext(), "Insert complete!", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d("lol", "" + e.toString());
                            Toast.makeText(getContext(), "Insert fail!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Please input full information", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    Log.d("lol", "onClick: \n" + e.toString());
                    Toast.makeText(getContext(), "Fail \n" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

    public void init() {
        edFullnameE = view.findViewById(R.id.edFullnameE);
        edUserE = view.findViewById(R.id.edUsernameE);
        edPassE = view.findViewById(R.id.edPasswordE);
        edAddressE = view.findViewById(R.id.edAddressE);
        edBirthdayE = view.findViewById(R.id.edBirthdayE);
        edPhoneE = view.findViewById(R.id.edPhoneE);
        edOfficeE = view.findViewById(R.id.edOfficeE);
        edMailE = view.findViewById(R.id.edMailE);
        btnDone = view.findViewById(R.id.ic_done);
        btnClose = view.findViewById(R.id.ic_close);
    }

    public int validayFrom() {
        int check = 1;
        if (edFullnameE.getText().length() == 0 || edUserE.getText().length() == 0 ||
                edPassE.getText().length() == 0 || edAddressE.getText().length() == 0 ||
                edPhoneE.getText().length() == 0 || edOfficeE.getText().length() == 0 ||
                edMailE.getText().length() == 0) {
            Toast.makeText(getContext(), "Please input full infomation!", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container, new EmployeeB_fragment())
                .commit();
    }

    @Override
    public void onItemClicked(String i) {
        Toast.makeText(getContext(), "ok"+i, Toast.LENGTH_SHORT).show();
        dismiss();
    }
}
