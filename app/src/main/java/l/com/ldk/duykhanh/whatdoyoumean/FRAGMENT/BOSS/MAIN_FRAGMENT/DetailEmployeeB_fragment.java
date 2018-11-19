package l.com.ldk.duykhanh.whatdoyoumean.FRAGMENT.BOSS.MAIN_FRAGMENT;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import l.com.ldk.duykhanh.whatdoyoumean.R;

public class DetailEmployeeB_fragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_boss_detail_employee,container,false);
        return view;
    }
}
