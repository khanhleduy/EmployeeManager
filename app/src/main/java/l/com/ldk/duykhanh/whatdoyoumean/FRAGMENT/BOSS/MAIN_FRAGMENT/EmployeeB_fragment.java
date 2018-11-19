package l.com.ldk.duykhanh.whatdoyoumean.FRAGMENT.BOSS.MAIN_FRAGMENT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import l.com.ldk.duykhanh.whatdoyoumean.ADAPTER.BossEmployeeAdapter;
import l.com.ldk.duykhanh.whatdoyoumean.DAO.BOSS.DAOboss;
import l.com.ldk.duykhanh.whatdoyoumean.DRAG_DROP.DynamicEventsHelper;
import l.com.ldk.duykhanh.whatdoyoumean.FRAGMENT.BOSS.BOTTOMSHEET.ExampleBottomSheetDialog;
import l.com.ldk.duykhanh.whatdoyoumean.MODEL.Employee;
import l.com.ldk.duykhanh.whatdoyoumean.R;


public class EmployeeB_fragment extends Fragment {
    private View view;
    public static BossEmployeeAdapter adapter;
    private RecyclerView recyclerView;
    public static List<Employee> employeeList;
    private DAOboss dao;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_boss_employee, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fabAddEmployee);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExampleBottomSheetDialog bottomSheet = new ExampleBottomSheetDialog();
                bottomSheet.show(getFragmentManager(), "exampleBottomSheet");
            }
        });
        getActivity().setTitle("Employee");
        addControls();

        inicializarRecyclerView();

        return view;
    }

    public void addControls(){
        employeeList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rcvEmployee);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        dao = new DAOboss(this);
    }

    private void inicializarRecyclerView(){
        DynamicEventsHelper.DynamicEventsCallback callback = new DynamicEventsHelper.DynamicEventsCallback() {
            @Override
            public void onItemMove(int initialPosition, int finalPosition) {
                //Truyền position khi di chuyển item
                adapter.onItemMove(initialPosition,finalPosition);
            }
            @Override
            public void removeItem(int position) {
                adapter.removeItem(position);
            }
        };
        //Phương thức loại bỏ, kéo thả cho recyclerview
        ItemTouchHelper androidItemTouchHelper = new ItemTouchHelper(new DynamicEventsHelper(callback));
        androidItemTouchHelper.attachToRecyclerView(recyclerView);
        adapter = new BossEmployeeAdapter(dao.getAllEmployee(),null,androidItemTouchHelper);
        recyclerView.setAdapter(adapter);
    }
}
