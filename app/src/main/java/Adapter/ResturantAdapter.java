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


import java.util.List;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import infrastructure.AppCommon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ResturantAdapter extends RecyclerView.Adapter<ResturantAdapter.ViewHolder> {
    private Activity context;

    int offset;

    public ResturantAdapter(Activity context) {
        this.context = context;
        this.offset = 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restuarent_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
