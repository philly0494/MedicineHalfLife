package com.example.medicinehalflife.recycler;

import android.content.Context;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.medicinehalflife.R;
import com.example.medicinehalflife.data.Drug;

public class PagerListAdapter extends PagedListAdapter<Drug, PagerListAdapter.DrugViewHolder> {

    public final static int MENU_ID_EDIT = 1;
    public final static int MENU_ID_DELETE = 2;

    private final LayoutInflater mInflater;
    private PagedList<Drug> mDrugs; //Cached copy of drugs

    //interface implementation to expose the holder's onclick method
    private static OnItemClickListener clickListener;
    //interface implementation to expose the context menu's onclick method
    private static OnContextMenuClickListener contextMenuClickListener;

    protected PagerListAdapter(Context context) {
        super(DIFF_CALLBACK);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public void submitList(PagedList<Drug> pagedList) {
        super.submitList(pagedList);
        mDrugs = pagedList;
    }

    private static DiffUtil.ItemCallback<Drug> DIFF_CALLBACK = new DiffUtil.ItemCallback<Drug>() {

        // Concert details may have changed if reloaded from the database,
        // but ID is fixed.
        @Override
        public boolean areItemsTheSame(Drug oldDrug, Drug newDrug) {
            // User properties may have changed if reloaded from the DB, but ID is fixed
            return oldDrug.getName() == newDrug.getName();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Drug oldDrug, @NonNull Drug newDrug) {
            // NOTE: if you use equals, your object must properly override Object#equals()
            // Incorrectly returning false here will result in too many animations.
            return oldDrug.equals(newDrug);
        }
    };

    // a method to get the animal at a certain position
    public Drug getDrugAtPosition(int position) {
        return mDrugs.get(position);
    }

    @NonNull
    @Override
    public DrugViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item,parent, false);
        return new DrugViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrugViewHolder holder, int position) {
        Drug drug = getItem(position);
        if (drug != null){
            holder.bindTo(drug);
        } else {
            // Covers the case of data not being ready
            // Null defines a placeholder item - PagedListAdapter automatically
            // invalidates this row when the actual object is loaded from the
            // database.
            holder.clear();
        }
    }

    class DrugViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        private Drug mDrug;
        private final TextView drugNameView;
        private final TextView drugHalfLifeView;
        private final TextView drugHalfLifeUnitView;

        public DrugViewHolder(@NonNull View itemView) {
            super(itemView);
            drugNameView = itemView.findViewById(R.id.drug_name_list_item);
            drugHalfLifeView = itemView.findViewById(R.id.drug_half_life_list_item);
            drugHalfLifeUnitView = itemView.findViewById(R.id.drug_half_life_unit_list_item);
            itemView.setOnCreateContextMenuListener(this); // REGISTER ONCREATE MENU LISTENER
            itemView.setOnClickListener(view -> clickListener.onItemClick(view, getAdapterPosition()));
        }

        void clear(){
            drugNameView.setText("");
            drugHalfLifeView.setText("");
            drugHalfLifeUnitView.setText("");
        }

        void bindTo(Drug drug){
            this.mDrug = drug;
            drugNameView.setText(mDrug.getName());
            drugHalfLifeView.setText(mDrug.getHalfLife());
            drugHalfLifeUnitView.setText(mDrug.getHalfLifeUnit());
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select The Action");
            MenuItem Edit = contextMenu.add(Menu.NONE, PagerListAdapter.MENU_ID_EDIT, 1, "Edit"); //groupId, itemId, order, title
            MenuItem Delete = contextMenu.add(Menu.NONE, PagerListAdapter.MENU_ID_DELETE, 2, "Delete");

            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        //ADD AN ONMENUITEM LISTENER TO EXECUTE COMMANDS ONCLICK OF CONTEXT MENU TASK
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                contextMenuClickListener.onContextMenuClick(item, getAdapterPosition());
                return true;
            }
        };
    }

    public void setOnClickListener(OnItemClickListener clickListener) {
        PagerListAdapter.clickListener = clickListener;
    }

    public void setOnContextMenuClickListener(OnContextMenuClickListener clickListener) {
        PagerListAdapter.contextMenuClickListener = clickListener;
    }

    //Interface to handle a click
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    //Interface to handle the clicked context menu
    public interface OnContextMenuClickListener {
        void onContextMenuClick(MenuItem item, int adapterPosition);
    }

}
