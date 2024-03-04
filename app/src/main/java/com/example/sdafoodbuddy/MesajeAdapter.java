package com.example.sdafoodbuddy;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


public class MesajeAdapter extends RecyclerView.Adapter<MesajeAdapter.ViewHolder>{
    private List<Msj>msjList;

    MesajeAdapter() {

    this.msjList =msjList;
}

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mesaj_individual, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Msj msj = msjList.get(position);
        holder.msgTextView.setText(msj.getText());

    }




    @Override
    public int getItemCount() {
        return msjList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView msgTextView;
        public ConstraintLayout constraintLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            msgTextView = itemView.findViewById(R.id.textView);

        }
    }



}
