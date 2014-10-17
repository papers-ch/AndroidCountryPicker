package com.genericpicker;

import java.util.List;

import com.picker.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PickerListAdapter extends BaseAdapter {

	private Context context;
	List<PickerObject> objects;
	LayoutInflater inflater;

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param objects
	 */
	public PickerListAdapter(Context context, List<PickerObject> objects) {
		super();
		this.context = context;
		this.objects = objects;
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return objects.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Return row for each object
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View cellView = convertView;
		Cell cell;
		PickerObject pickerObject = objects.get(position);

		if (convertView == null) {
			cell = new Cell();
			cellView = inflater.inflate(R.layout.row, null);
			cell.textView = (TextView) cellView.findViewById(R.id.row_title);
			cell.imageView = (ImageView) cellView.findViewById(R.id.row_icon);
			cellView.setTag(cell);
		} else {
			cell = (Cell) cellView.getTag();
		}

		cell.textView.setText(pickerObject.getValue());

		if(pickerObject.getIcon() == null){
			cell.imageView.setVisibility(View.GONE);
		}else{
			cell.imageView.setImageDrawable(pickerObject.getIcon());
			cell.imageView.setVisibility(View.VISIBLE);
		}
		return cellView;
	}

	/**
	 * Holder for the cell
	 * 
	 */
	static class Cell {
		public TextView textView;
		public ImageView imageView;
	}

}