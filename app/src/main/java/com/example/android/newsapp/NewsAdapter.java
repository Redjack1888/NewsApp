package com.example.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context) {
        super(context, -1, new ArrayList<News>());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // If convertView is null, inflate a new list item layout.
        if (convertView == null) {
            convertView
                    = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Find the TextView with view ID title
        TextView title = (TextView) convertView.findViewById(R.id.title);
        // Find the TextView with view ID author
        TextView author = (TextView) convertView.findViewById(R.id.author);
        // Find the TextView with view ID date
        TextView date = (TextView) convertView.findViewById(R.id.date);
        // Find the TextView with view ID category
        TextView category = (TextView) convertView.findViewById(R.id.category);

        // Find the currentNews at the given position in the list of News
        News currentNews = getItem(position);
        // Display the title of the current news in that TextView
        title.setText(currentNews.getTitle());
        // Display the author of the current news in that TextView
        author.setText(currentNews.getAuthor());
        // Display the date of the current news in that TextView
        date.setText(currentNews.getDate());
        // Display the category of the current news in that TextView
        category.setText(currentNews.getCategory());

        return convertView;
    }
}
