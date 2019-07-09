package com.example.medicinehalflife.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.medicinehalflife.R;
import com.example.medicinehalflife.data.Drug;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.DrugViewHolder> {

    private final LayoutInflater mInflater;
    private List<Drug> mDrugs;// Cached copy of drugs

    RecyclerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DrugViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new DrugViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrugViewHolder holder, int position) {
        if (mDrugs != null) {
            Drug current = mDrugs.get(position);
            holder.drugNameView.setText(current.getName());
            holder.drugHalfLifeView.setText(current.getHalfLife());
            holder.drugHalfLifeUnitView.setText(current.getHalfLifeUnit());
        } else {
            holder.drugNameView.setText("No Drug");
        }

    }

    void setDrugs(List<Drug> drugs) {
        mDrugs = drugs;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDrugs != null) {
            return mDrugs.size();
        } else return 0;
    }

    // a method to get the drug at a certain position
    public Drug getDrugAtPosition(int position) {
        return mDrugs.get(position);
    }

    class DrugViewHolder extends RecyclerView.ViewHolder {

        private final TextView drugNameView;
        private final TextView drugHalfLifeView;
        private final TextView drugHalfLifeUnitView;


        public DrugViewHolder(@NonNull View itemView) {
            super(itemView);
            drugNameView = itemView.findViewById(R.id.drug_name_list_item);
            drugHalfLifeView = itemView.findViewById(R.id.drug_half_life_list_item);
            drugHalfLifeUnitView = itemView.findViewById(R.id.drug_half_life_unit_list_item);
        }
    }

}
