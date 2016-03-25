package com.example.petermartinez.tapecitry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by petermartinez on 3/24/16.
 */
public class AssetAdapter extends ArrayAdapter<Asset> {
    public AssetAdapter(Context context, ArrayList<Asset> threads) {
        super(context, 0, threads);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Asset asset = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.assets_view, parent, false);
        }

        TextView text = (TextView) view.findViewById(R.id.asset_text);
        ImageView image = (ImageView) view.findViewById(R.id.asset_image);

        if (asset.getIsText()) {
            text.setText(asset.getEssay());
        } else {
            Picasso.with(getContext())
                    .load(asset.getId())
                    .placeholder(android.R.drawable.ic_dialog_alert)
//                    .resize(300, 300) //wish I could do this dynamically, w,h of assetThumb
//                    .centerCrop()
                    .into(image);
        }

        return view;
    }
}
