package com.example.recyclerview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NumberFragment extends Fragment {

    int number;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("Number",number);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState!=null) number = savedInstanceState.getInt("Number");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.big_numba,container,false);
        TextView tv = v.findViewById(R.id.textView);
        tv.setText(String.valueOf(number));
        if(number%2==0) tv.setTextColor(getResources().getColor(R.color.colorRed));
        else tv.setTextColor(getResources().getColor(R.color.colorBlue));
        return v;
    }

}
