package l.com.ldk.duykhanh.whatdoyoumean.ADAPTER;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import l.com.ldk.duykhanh.whatdoyoumean.DAO.BOSS.DAOboss;
import l.com.ldk.duykhanh.whatdoyoumean.FRAGMENT.BOSS.MAIN_FRAGMENT.DetailEmployeeB_fragment;
import l.com.ldk.duykhanh.whatdoyoumean.FRAGMENT.BOSS.MAIN_FRAGMENT.EmployeeB_fragment;
import l.com.ldk.duykhanh.whatdoyoumean.MODEL.Employee;
import l.com.ldk.duykhanh.whatdoyoumean.R;

public class BossEmployeeAdapter extends RecyclerView.Adapter<BossEmployeeAdapter.ViewHolder> {

    private ItemTouchHelper itemTouchHelper;
    public static List<Employee> arrEmployee = new ArrayList<>();
    private Fragment context;
    DAOboss daOboss;

    public BossEmployeeAdapter(List<Employee> arrEmployee, Fragment context, ItemTouchHelper itemTouchHelper) {
        this.arrEmployee = arrEmployee;
        this.context = context;
        this.itemTouchHelper = itemTouchHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recyclerview_employee, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.txtNameE.setText("Name: \t" + arrEmployee.get(i).getmFullName());
        viewHolder.txtPhoneEm.setText("Phone : \t" + arrEmployee.get(i).getmPhone());
        viewHolder.txtEmailE.setText("Email: \t" + arrEmployee.get(i).getmEmail());


        // Click icon logo để di chuyển vị trí item
        viewHolder.imgLogoE.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (itemTouchHelper != null) itemTouchHelper.startDrag(viewHolder);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrEmployee.size();
    }

    //di chuyển item
    public void onItemMove(final int initialPosition, final int finalPosition) {
        if (initialPosition < arrEmployee.size() && finalPosition < arrEmployee.size()) {
            if (initialPosition < finalPosition) {
                for (int i = initialPosition; i < finalPosition; i++) {
                    Collections.swap(arrEmployee, i, i + 1);
                }
            } else {
                for (int i = initialPosition; i > finalPosition; i--) {
                    Collections.swap(arrEmployee, i, i - 1);
                }
            }
            notifyItemMoved(initialPosition, finalPosition);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Employee mProduto1 = arrEmployee.get(initialPosition);
                Employee mProduto2 = arrEmployee.get(finalPosition);

            }
        }).start();

    }

    //Xóa item
    public void removeItem(final int position) {
//        arrEmployee.remove(position);
//        notifyItemRemoved(position);
        Employee e = arrEmployee.get(position);
        DAOboss.it.deleteEmplopyeeById(e.getmID(position), e.getmFullName());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgLogoE;
        TextView txtNameE, txtPhoneEm, txtEmailE;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogoE = itemView.findViewById(R.id.imgLogoE);
            txtNameE = itemView.findViewById(R.id.txtNameE);
            txtPhoneEm = itemView.findViewById(R.id.txtPhoneE);
            txtEmailE = itemView.findViewById(R.id.txtEmailE);
        }

        @Override
        public void onClick(View view) {
            EmployeeB_fragment.employeeList = arrEmployee;
            context.getActivity().getSupportFragmentManager()
                    .beginTransaction().replace( R.id.layout_container, new DetailEmployeeB_fragment())
                    .addToBackStack( "DETAIL" )
                    .commit();
        }
    }

}
