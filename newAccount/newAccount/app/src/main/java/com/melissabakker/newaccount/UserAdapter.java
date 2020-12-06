package com.melissabakker.newaccount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {

        public UserAdapter(Context context, ArrayList<User> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
            }


            TextView email = (TextView) convertView.findViewById(R.id.userEmail);
            TextView role = (TextView) convertView.findViewById(R.id.userRole);
            TextView userN = (TextView) convertView.findViewById(R.id.userN);
            final User user = getItem(position);

            email.setText(user.getEmail());
            role.setText(user.getRole());
            userN.setText(user.getUsername());

            return convertView;
        }
}
