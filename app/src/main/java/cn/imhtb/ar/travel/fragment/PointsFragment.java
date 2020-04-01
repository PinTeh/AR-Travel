package cn.imhtb.ar.travel.fragment;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.imhtb.ar.travel.ArApplication;
import cn.imhtb.ar.travel.R;
import cn.imhtb.ar.travel.adapter.CampusAdapter;
import cn.imhtb.ar.travel.entity.Campus;
import map.baidu.ar.model.ArLatLng;
import map.baidu.ar.model.ArPoiInfo;
import map.baidu.ar.model.PoiInfoImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class PointsFragment extends Fragment {

    private List<Campus> list = new ArrayList<>();

    public PointsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_points, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();

        CampusAdapter adapter = new CampusAdapter(getActivity(),R.layout.item_campus,list);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Campus campus = list.get(i);
                //ArApplication.defaultSelect = campus.getName();
                setPoints(campus.getName());
                Toast.makeText(getContext(),"Select Success",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPoints(String defaultSelect) {
        List<PoiInfoImpl> poiInfos = new ArrayList<>();
        String[] tags = null,latitudes = null,longitudes = null;
        if ("桂林电子科技大学".equals(defaultSelect)){
            tags = getResources().getStringArray(R.array.guet_tag);
            latitudes = getResources().getStringArray(R.array.guet_latitude);
            longitudes = getResources().getStringArray(R.array.guet_longitude);
        }else if ("广西师范大学".equals(defaultSelect)){
            tags = getResources().getStringArray(R.array.gxsf_tag);
            latitudes = getResources().getStringArray(R.array.gxsf_latitude);
            longitudes = getResources().getStringArray(R.array.gxsf_longitude);
        }
        for (int i = 0; i < tags.length; i++) {
            ArPoiInfo pTest = new ArPoiInfo();
            pTest.name = tags[i];
            pTest.location = new ArLatLng(Double.parseDouble(longitudes[i]), Double.parseDouble(latitudes[i]));
            PoiInfoImpl poiImplT = new PoiInfoImpl();
            poiImplT.setPoiInfo(pTest);
            poiInfos.add(poiImplT);
        }
        ArApplication.setPoiInfos(poiInfos);
    }

    private void initData(){

        String[] campusNames = getResources().getStringArray(R.array.campusName);
        TypedArray campusImages = getResources().obtainTypedArray(R.array.campusImage);

        for (int i = 0; i < campusNames.length; i++) {
            Campus campus = new Campus();
            campus.setName(campusNames[i]);
            campus.setImageId(campusImages.getResourceId(i,0));
            list.add(campus);
        }
    }
}
