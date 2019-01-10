package hska.fahrradscout24;

public class AdvertismentAdapter extends BaseAdapter {
    Context context;
    ArrayList<Employee> empList;
    private static LayoutInflater inflater = null;

    public MyAdapter(Context context, ArrayList<Employee> empList) {
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
            convertView = inflater.inflate(R.layout.layout_grid_item, null);

        TextView codeTextView = (TextView) convertView.findViewById(R.id.tv_emp_id);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.tv_emp_name);
        TextView emailTextView = (TextView) convertView.findViewById(R.id.tv_emp_email);
        TextView addressTextView = (TextView) convertView.findViewById(R.id.tv_emp_address);

        Employee e = new Employee();
        e = empList.get(position);
        codeTextView.setText("Code: " + String.valueOf(e.getCode()));
        nameTextView.setText("Name: " + e.getName());
        emailTextView.setText("Email: " + e.getEmail());
        addressTextView.setText("Address: " + e.getAddress());
        return convertView;
    }

}