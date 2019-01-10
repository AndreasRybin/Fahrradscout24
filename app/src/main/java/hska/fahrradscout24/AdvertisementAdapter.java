package hska.fahrradscout24;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

        TextView codeTextView = convertView.findViewById(R.id.tv_emp_id);
        TextView nameTextView = convertView.findViewById(R.id.tv_emp_name);
        TextView emailTextView = convertView.findViewById(R.id.tv_emp_email);
        TextView addressTextView = convertView.findViewById(R.id.tv_emp_address);

        Advertisement e = new Advertisement();
        e = empList.get(position);
        codeTextView.setText("Code: " + String.valueOf(e.getCode()));
        nameTextView.setText("Name: " + e.getName());
        emailTextView.setText("Email: " + e.getEmail());
        addressTextView.setText("Address: " + e.getAddress());
        return convertView;
    }

}