package com.melissabakker.newaccount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BranchAdapter extends ArrayAdapter<Branch> {

    String searchType;
    String day;

    public BranchAdapter(Context context, ArrayList<Branch> branches, String st) {
        super(context, 0, branches);
        searchType = st;
    }

    /**For making a schedule adapter specifically **/
    public BranchAdapter(Context context, ArrayList<Branch> branches, String st, String day) {
        super(context, 0, branches);
        searchType = st;
        this.day = day;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_branch, parent, false);
        }


        TextView branchName = (TextView) convertView.findViewById(R.id.branchName);
        TextView branchData = (TextView) convertView.findViewById(R.id.branchData);
        Branch branch = getItem(position);

        branchName.setText(branch.getBranchName());

        if (searchType.equals("Address")) {
            branchData.setText(branch.getAddress());
        }
        else if (searchType.equals("Service")) {
            branchData.setText(branch.getEmail());
        }

        /** Search type is Schedule, so we have been passed a day through the second constructor **/
        else {
            branchData.setText(branch.getScheduleForDay(day));
        }

        return convertView;
    }
}

