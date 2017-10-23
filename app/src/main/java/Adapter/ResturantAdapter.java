package Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.tucan.pretoapp.FavouriteListActivity;
import com.tucan.pretoapp.MapActivityForDistance;
import com.tucan.pretoapp.R;
import com.tucan.pretoapp.ResturantDetailActivity;
import com.tucan.pretoapp.ResturantListByCategoryActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;


import java.util.ArrayList;


import APIResponse.ResturantObject;
import CustomControl.LatoBlackTextView;
import CustomControl.LatoBoldTextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import infrastructure.AppCommon;


public class ResturantAdapter extends RecyclerView.Adapter<ResturantAdapter.ViewHolder> {
    private Activity context;
    ArrayList<ResturantObject> resturantObjectArrayList;
    int offset;

    public ResturantAdapter(Activity context, ArrayList<ResturantObject> resturantObjectArrayList) {
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
        holder.resturantName.setText(Html.fromHtml(object.getRestName()));
        holder.resturantAddress.setText(Html.fromHtml(object.getAddress()));
        if(object.getTypeOfFood().size()>0) {
            holder.typeOFFood.setText(Html.fromHtml(object.getTypeOfFood().get(0)));
        }

        String priceStr = context.getResources().getString(R.string.from) + " $" + object.getPriceFrom() + " - " + context.getResources().getString(R.string.to) + " $" + object.getPriceTo();
        holder.priceRange.setText(Html.fromHtml(priceStr));
        holder.likeCountTextView.setText(object.getLikesCount());
        holder.distanceTextView.setText(object.getDistance() + " km");
        holder.dealImageView.setImageURI(Uri.parse(object.getImages()));
        if (object.getColor().equals("0")) {
            //red
            holder.openStatusTextView.setBackgroundResource(R.drawable.red_circle);
            holder.currentStatus.setText(context.getResources().getString(R.string.closed));
        } else if (object.getColor().equals("1")) {
            //green
            holder.openStatusTextView.setBackgroundResource(R.drawable.green_circle);
            holder.currentStatus.setText(context.getResources().getString(R.string.open));
        } else {
            //grey
            holder.openStatusTextView.setBackgroundResource(R.drawable.grey_circle);
            holder.currentStatus.setText(context.getResources().getString(R.string.no_fixed_hour));
        }

        if(object.getIsLiked().equals("1")){
            holder.likeImage.setSelected(true);
        }else{
            holder.likeImage.setSelected(false);
        }

        holder.likeImage.setTag(Integer.toString(position));
        holder.rowLayout.setTag(Integer.toString(position));
        holder.distanceLayout.setTag(Integer.toString(position));

        if (position == ((offset * 20) - 1)) {
            if (context instanceof ResturantListByCategoryActivity) {
                offset = offset + 1;
                ((ResturantListByCategoryActivity) context).getResturantList(offset);
            }
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

        @Bind(R.id.dealImage)
        SimpleDraweeView dealImageView;

        @Bind(R.id.likeImage)
        ImageView likeImage;

        @Bind(R.id.rowLayout)
        LinearLayout rowLayout;

        @Bind(R.id.distanceLayout)
        LinearLayout distanceLayout;

        @Bind(R.id.openStatusTextView)
        TextView openStatusTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.likeImage)
        public void likeClick(View view) {
            int position = Integer.parseInt(view.getTag().toString());
            if (context instanceof ResturantListByCategoryActivity) {
                ((ResturantListByCategoryActivity) context).markLike(position);
            }else if (context instanceof FavouriteListActivity) {
                ((FavouriteListActivity) context).markLike(position);
            }
        }

        @OnClick(R.id.rowLayout)
        public void rowLayoutClick(View view) {
            int position = Integer.parseInt(view.getTag().toString());
            ResturantObject object = resturantObjectArrayList.get(position);
            Intent resturantDetailIntent = new Intent(context, ResturantDetailActivity.class);
            resturantDetailIntent.putExtra("restID", object.getRestID());
            if (context instanceof ResturantListByCategoryActivity) {
                ((ResturantListByCategoryActivity) context).startActivityForResult(resturantDetailIntent, AppCommon.INTENT_FOR_RESTURANT_DETAIL);
            } else if (context instanceof FavouriteListActivity) {
                ((FavouriteListActivity) context).startActivityForResult(resturantDetailIntent, AppCommon.INTENT_FOR_RESTURANT_DETAIL);
            }
        }

        @OnClick(R.id.distanceLayout)
        public void distanceClick(View view) {
            int position = Integer.parseInt(view.getTag().toString());
            ResturantObject object = resturantObjectArrayList.get(position);
            Gson gson = new Gson();
            String objectStr = gson.toJson(object);
            Intent mapIntent = new Intent(context, MapActivityForDistance.class);
            mapIntent.putExtra("object", objectStr);
            if (context instanceof ResturantListByCategoryActivity) {
                ((ResturantListByCategoryActivity) context).startActivityForResult(mapIntent, AppCommon.INTENT_FOR_MAP_DISTANCE);
            } else if (context instanceof FavouriteListActivity) {
                ((FavouriteListActivity) context).startActivityForResult(mapIntent, AppCommon.INTENT_FOR_MAP_DISTANCE);
            }
        }
    }
}
