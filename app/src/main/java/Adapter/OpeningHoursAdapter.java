package Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tucan.pretoapp.R;

import java.util.ArrayList;

import APIResponse.OperatingHour;
import CustomControl.LatoLightTextView;
import butterknife.Bind;
import butterknife.ButterKnife;


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
