package com.example.androidprojectpocs.activity.Reminder.activity.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidprojectpocs.R;
import com.example.androidprojectpocs.activity.Reminder.activity.ReminderActivity;
import com.example.androidprojectpocs.activity.Reminder.activity.item.RecyclerViewClickListener;
import com.example.androidprojectpocs.activity.Reminder.activity.utils.Constants;
import com.example.androidprojectpocs.activity.Reminder.activity.utils.Events;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;



public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Events> objects;
    public static Context mContext;
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private static RecyclerViewClickListener itemListener;

    public RecyclerAdapter() {
    }

    public RecyclerAdapter(Context context, List<Events> cur, RecyclerViewClickListener listener) {
        mContext = context;
        objects = cur;
        itemListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTitleName;
        TextView txtTitleCount;
        ImageView imgTitleEvent;
        ImageView imgTitleArrow;
        View imgTitleBlur;

        TextView txtContentName;
        TextView txtContentDate;
        TextView txtContentDiffDate;
        TextView txtContentCategory;
        ImageView imgContentEvent;
        Button bttnContentModify;
        Button bttnContentDelete;
        TextView txtContentAnnual;

        public ViewHolder(View v, Context context) {
            super(v);
            this.txtTitleName = (TextView) v.findViewById(R.id.title_txt_name);
            this.txtTitleCount = (TextView) v.findViewById(R.id.title_txt_count);
            this.imgTitleEvent = (ImageView) v.findViewById(R.id.title_image_event);
//            this.imgTitleBlur = v.findViewById(R.id.title_view_blur);
            this.imgTitleArrow = (ImageView) v.findViewById(R.id.title_image_arrow);
            this.bttnContentDelete = (Button) v.findViewById(R.id.content_button_delete);
            this.bttnContentModify = (Button) v.findViewById(R.id.content_button_modify);
            this.txtContentAnnual = (TextView) v.findViewById(R.id.content_annual);

            this.txtContentName = (TextView) v.findViewById(R.id.content_txt_name);
            this.txtContentDate = (TextView) v.findViewById(R.id.content_txt_date);
            this.txtContentDiffDate = (TextView) v.findViewById(R.id.content_txt_diff_date);
            this.txtContentCategory = (TextView) v.findViewById(R.id.content_txt_category);
            this.imgContentEvent = (ImageView) v.findViewById(R.id.content_img_event);

            this.bttnContentDelete.setOnClickListener(this);
            this.bttnContentModify.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            if (v == this.bttnContentDelete) {
                itemListener.recyclerViewListClicked(2, v, this.getLayoutPosition());
            }

            if (v == this.bttnContentModify) {
                itemListener.recyclerViewListClicked(1, v, this.getLayoutPosition());
            }
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Events listViewItem = objects.get(position);
//         ViewHolder.listViewItem = objects.get(position);
        if (unfoldedIndexes.contains(position)) {
            ((FoldingCell) viewHolder.itemView).unfold(true);
        } else {
            ((FoldingCell) viewHolder.itemView).fold(true);
        }
        viewHolder.txtTitleName.setText(listViewItem.getName());

        if (listViewItem.getDiff() > 0) {
            if (ReminderActivity.sharedPreferences.getBoolean(ReminderActivity.DISPLAY_DAY, true))
                viewHolder.txtTitleCount.setText(String.valueOf(listViewItem.getDiff()));
            else {
                try {
                    viewHolder.txtTitleCount.setText(listViewItem.getDiffString(0));
                } catch (Exception e) {
                }
            }
            viewHolder.imgTitleArrow.setImageResource(R.drawable.arrow_right);
        } else if (listViewItem.getDiff() == 0) {
            viewHolder.imgTitleArrow.setVisibility(View.GONE);
            viewHolder.txtTitleCount.setText(String.valueOf(0));
        } else {
            if (ReminderActivity.sharedPreferences.getBoolean(ReminderActivity.DISPLAY_DAY, true))
                viewHolder.txtTitleCount.setText(String.valueOf(-listViewItem.getDiff()));
            else {
                try {
                    viewHolder.txtTitleCount.setText(listViewItem.getDiffString(0));
                } catch (Exception e) {
                }
            }
        }

        viewHolder.txtContentDate.setText(listViewItem.getDate());
        viewHolder.txtContentName.setText(listViewItem.getName());
        try {
            viewHolder.txtContentDiffDate.setText(listViewItem.getDiffString(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewHolder.imgTitleEvent.setImageResource(Constants.background[listViewItem.getImg()]);
        viewHolder.imgContentEvent.setImageResource(Constants.background[listViewItem.getImg()]);
        if (listViewItem.getLoop() != 1)
            viewHolder.txtContentAnnual.setVisibility(View.INVISIBLE);
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell, parent, false);
        return new ViewHolder(v, mContext);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public List<Events> removeAt(int position) {
        objects.remove(position);
        notifyItemRemoved(position);
        return objects;
        // notifyItemRangeRemoved(position, objects.size());
    }

    public void updateData(List<Events> events) {
        objects.clear();
        objects.addAll(events);
        notifyDataSetChanged();
    }

}


