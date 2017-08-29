package Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.elisa.pretoapp.R;
import com.elisa.pretoapp.ResturantDetailActivity;
import com.elisa.pretoapp.ResturantListByCategoryActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import APIResponse.OperatingHour;
import APIResponse.ResturantObject;
import CustomControl.LatoBlackTextView;
import CustomControl.LatoBoldTextView;
import CustomControl.LatoLightTextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class OpeningHoursAdapter extends RecyclerView.Adapter<OpeningHoursAdapter.ViewHolder> {
    private Activity context;
    ArrayList<OperatingHour> operatingHourArrayList;
    int offset;

    public OpeningHoursAdapter(Activity context, ArrayList<OperatingHour> operatingHourArrayList) {
        this.context = context;
        this.offset = 1;
        this.operatingHourArrayList = operatingHourArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.opening_hours_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        OperatingHour object = operatingHourArrayList.get(position);
        holder.dayTextView.setText(object.getDay()+":");
        holder.timeTextView.setText(object.getTime());
    }

    @Override
    public int getItemCount() {
        return operatingHourArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.day)
        LatoLightTextView dayTextView;

        @Bind(R.id.time)
        LatoLightTextView timeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
