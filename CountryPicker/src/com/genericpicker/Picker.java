package com.genericpicker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.picker.R;

public class Picker extends DialogFragment implements
		Comparator<PickerObject> {
	
	/**
	 * View components
	 */
	private EditText searchEditText;
	private ListView listView;

	/**
	 * Adapter for the listview
	 */
	private PickerListAdapter adapter;

	/**
	 * Hold all objects, sorted by value
	 */
	private List<PickerObject> objectsList;

	/**
	 * Hold objects that matched user query
	 */
	private List<PickerObject> selectedObjectsList = new ArrayList<PickerObject>();

	/**
	 * Listener to which object the user selected
	 */
	private PickerListener listener;

	/**
	 * Holds the dialog title
	 */
	private String dialogTitle;
	
	/**
	 * Set listener
	 * 
	 * @param listener
	 */
	public void setListener(PickerListener listener) {
		this.listener = listener;
	}

	public EditText getSearchEditText() {
		return searchEditText;
	}

	public ListView getListView() {
		return listView;
	}

	
	public Picker(List<PickerObject> objectsList, String dialogTitle) {
		this.objectsList = (objectsList == null) ? new ArrayList<PickerObject>() : objectsList;
		Collections.sort(objectsList, this);
		this.selectedObjectsList = (objectsList == null) ? new ArrayList<PickerObject>() : objectsList;
		this.dialogTitle = dialogTitle;
	}

	/**
	 * Create view
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate view
		View view = inflater.inflate(R.layout.picker, null);

		// Set dialog title if show as dialog
		getDialog().setTitle(dialogTitle);

		int width = getResources().getDimensionPixelSize(
				R.dimen.cp_dialog_width);
		int height = getResources().getDimensionPixelSize(
				R.dimen.cp_dialog_height);
		getDialog().getWindow().setLayout(width, height);
		

		// Get view components
		searchEditText = (EditText) view
				.findViewById(R.id.country_picker_search);
		listView = (ListView) view
				.findViewById(R.id.country_picker_listview);

		// Set adapter
		adapter = new PickerListAdapter(getActivity(), selectedObjectsList);
		listView.setAdapter(adapter);

		// Inform listener
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (listener != null) {
					PickerObject pickerObject = selectedObjectsList.get(position);
					listener.onSelect(pickerObject);
				}
			}
		});

		// Search for which objects matched user query
		searchEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				search(s.toString());
			}
		});

		return view;
	}

	/**
	 * Search objectsList contains text and put result into
	 * selectedObjectsList
	 * 
	 * @param text
	 */
	@SuppressLint("DefaultLocale")
	private void search(String text) {
		selectedObjectsList.clear();

		for (PickerObject object : objectsList) {
			if (object.getValue().toLowerCase(Locale.ENGLISH)
					.contains(text.toLowerCase())) {
				selectedObjectsList.add(object);
			}
		}

		adapter.notifyDataSetChanged();
	}

	/**
	 * Support sorting the countries list
	 */
	@Override
	public int compare(PickerObject lhs, PickerObject rhs) {
		return lhs.getValue().compareTo(rhs.getValue());
	}

}
