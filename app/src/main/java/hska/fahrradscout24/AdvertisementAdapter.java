package hska.fahrradscout24;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdvertisementAdapter extends BaseAdapter {
    Context context;
    ArrayList<Advertisement> empList;
    private static LayoutInflater inflater = null;

    public AdvertisementAdapter(Context context, ArrayList<Advertisement> empList) {
        this.context = context;
        this.empList = empList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return empList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null)
            convertView = inflater.inflate(R.layout.advertisment_item, null);

        //ImageView anzeigeImage = convertView.findViewById(R.id.);
        TextView anzeigeIdIdTextView = convertView.findViewById(R.id.tv_adv_id);
        TextView fahrradIdTextView = convertView.findViewById(R.id.tv_adv_fahrrad_id);
        TextView erstelldatumTextView = convertView.findViewById(R.id.tv_adv_erstelldatum);
        TextView ablaufdatumTextView = convertView.findViewById(R.id.tv_adv_ablaufdatum);
        TextView preisTextView = convertView.findViewById(R.id.tv_adv_preis);
        ImageView image = convertView.findViewById(R.id.image);


        Advertisement e = new Advertisement();
        e = empList.get(position);
        anzeigeIdIdTextView.setText("");
        fahrradIdTextView.setText("fast neu!! jetzt zuschlagen!");

        erstelldatumTextView.setText("erstelldatum: " + e.getErstelldatum());
        ablaufdatumTextView.setText("ablaufdatum: " + e.getAblaufdatum());
        preisTextView.setText("preis: " + e.getPreis());
        if(e.getFahrradbild()!= null){
        image.setImageBitmap(e.getFahrradbild());}

        return convertView;
    }

}