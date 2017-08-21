package Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import com.elisa.pretoapp.R;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;


import java.util.ArrayList;
import java.util.List;


import APIResponse.ResturantObject;
import CustomControl.LatoBlackTextView;
import CustomControl.LatoBoldTextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import infrastructure.AppCommon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResturantAdapter extends RecyclerView.Adapter<ResturantAdapter.ViewHolder> {
    private Activity context;
    ArrayList<ResturantObject> resturantObjectArrayList;
    int offset;

    public ResturantAdapter(Activity context, ArrayList<ResturantObject> resturantObjectArrayList ) {
        this.context = context;
        this.offset = 1;
        this.resturantObjectArrayList = resturantObjectArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restuarent_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ResturantObject object = resturantObjectArrayList.get(position);
        holder.resturantName.setText(object.getRestName());
        holder.resturantAddress.setText(object.getAddress());
        holder.typeOFFood.setText(object.getTypeOfFood().get(0));

        String priceStr = context.getResources().getString(R.string.from)+ " $"+object.getPriceFrom()+ " - "+context.getResources().getString(R.string.to)+" $"+ object.getPriceTo();
        holder.priceRange.setText(priceStr);
        holder.likeCountTextView.setText(object.getLikesCount());
        holder.distanceTextView.setText(object.getDistance());

        if(object.getColor().equals("0")){
            //red
            holder.currentStatus.setText(context.getResources().getString(R.string.closed));
        }else if(object.getColor().equals("1")){
            //green
            holder.currentStatus.setText(context.getResources().getString(R.string.open));
        }else{
            //grey
            holder.currentStatus.setText(context.getResources().getString(R.string.no_fixed_hour));
        }
    }


    @Override
    public int getItemCount() {
        return resturantObjectArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.resturantName)
        LatoBlackTextView resturantName;

        @Bind(R.id.distance)
        LatoBoldTextView distanceTextView;

        @Bind(R.id.resturantAddress)
        LatoBoldTextView resturantAddress;

        @Bind(R.id.typeOFFood)
        LatoBoldTextView typeOFFood;

        @Bind(R.id.priceRange)
        LatoBoldTextView priceRange;

        @Bind(R.id.likeCount)
        LatoBoldTextView likeCountTextView;

        @Bind(R.id.currentStatus)
        LatoBoldTextView currentStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
