package com.example.recyclerview;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AdapterFragment extends Fragment {
    private int Quantity;
    ChosenOne co;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            co = (ChosenOne) activity;
        }catch (ClassCastException e){
            Log.w("ClassCastException","Activity must implement ChosenOne interface");
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Length",Quantity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null) Quantity=100;
        else Quantity=savedInstanceState.getInt("Length");
        Log.d("OnCreateQuantity",Quantity+"");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final CustomAdapter ca;
        View v = inflater.inflate(R.layout.fragment_layout,container,false);
        RecyclerView rv = v.findViewById(R.id.myRecyclerView);
        LinearLayoutManager layout = new LinearLayoutManager(v.getContext());
        Log.d("Quantity:",Quantity+"");
        ca = new CustomAdapter(Quantity);
        rv.setAdapter(ca);
        rv.setLayoutManager(layout);
        Button bt = v.findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quantity++;
                ca.quantity=Quantity;
                ca.notifyDataSetChanged();
            }
        });
        return v;
    }


    //---------------------------------------IMPLEMENT ME section--------------------------------------------------------

    @FunctionalInterface
    interface ChosenOne{
        void InitSecondFragment(int num);
    }

    //---------------------------------------Private class section------------------------------------------------------------
    private class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView[] v = new TextView[4];
        Integer[] id = new Integer[]{R.id.First,R.id.Second,R.id.Third,R.id.Fourth};
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            for(int i=0;i<4;i++){
                v[i]=itemView.findViewById(id[i]);
            }
        }
    }
    private class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>{
        private int quantity;
        CustomAdapter(int quantity){
            this.quantity = quantity;
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View v = inflater.inflate(R.layout.w_reclayout,viewGroup,false);
            return new CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder cvh, int i) {
            for(int j=0;j<3;j++){
                //Эта строка нужна,тк при Bind происходит переиспользование старого ViewGroup,в котором были записаны старые элементы=>их нужно убрать с виду,чтобы они не показывались,и лишь потом переиспользовать
                cvh.v[j].setVisibility(View.GONE);
            }
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                for(int j=0;j<3 && 3*i+j<quantity;j++){
                    Log.d("Adapter",3*i+j+"");
                    cvh.v[j].setVisibility(View.VISIBLE);
                    cvh.v[j].setText(String.valueOf(3*i+j+1));
                    if((3*i+j+1)%2==0) cvh.v[j].setTextColor(getResources().getColor(R.color.colorRed));
                    else cvh.v[j].setTextColor(getResources().getColor(R.color.colorBlue));
                    cvh.v[j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView tv = (TextView) v;
                            co.InitSecondFragment(Integer.parseInt((String) tv.getText()));
                        }
                    });
                }
            }else {
                for(int j=0;j<4;j++){
                    cvh.v[j].setVisibility(View.GONE);
                }
                for(int j=0;j<4 && 4*i+j<quantity;j++){
                    Log.d("Adapter",4*i+j+"");
                    cvh.v[j].setVisibility(View.VISIBLE);
                    cvh.v[j].setText(String.valueOf(4*i+j+1));
                    if((j+1)%2==0) cvh.v[j].setTextColor(getResources().getColor(R.color.colorRed));
                    else cvh.v[j].setTextColor(getResources().getColor(R.color.colorBlue));
                    cvh.v[j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView tv = (TextView) v;
                            co.InitSecondFragment(Integer.parseInt((String) tv.getText()));
                        }
                    });
                }
                }
            }

        @Override
        public int getItemCount() {
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) return (quantity+2)/3;
            else return  (quantity+3)/4;
        }
    }
}
