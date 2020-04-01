package cn.imhtb.ar.travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.imhtb.ar.travel.R;
import cn.imhtb.ar.travel.entity.Campus;

public class CampusAdapter extends ArrayAdapter<Campus> {

    private List<Campus> mList;

    private Context mContext;

    private int resourceId;

    public CampusAdapter(Context context, int resourceId,List<Campus> mList) {
        super(context, resourceId,mList);
        this.mList = mList;
        this.mContext = context;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Campus campus = getItem(position);
        View view;

        view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

        TextView textView = (TextView) view.findViewById(R.id.tv);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv);

        textView.setText(campus.getName());
        imageView.setImageResource(campus.getImageId());
        return view;
    }
}
