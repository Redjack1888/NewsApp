package com.example.android.newsapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QueryUtils {

    static String createStringUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("order-by", "newest")
                .appendQueryParameter("show-references", "author")
                .appendQueryParameter("show-tags", "contributor")
                .appendQueryParameter("q", "Music")
                .appendQueryParameter("api-key", "test");
        String url = builder.build().toString();
        return url;
    }

    static URL createUrl() {
        String stringUrl = createStringUrl();
        try {
            return new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("Queryutils", "Error creating URL: ", e);
            return null;
        }
    }

    private static String formatDate(String rawDate) {
        String jsonDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat jsonFormatter = new SimpleDateFormat(jsonDatePattern, Locale.US);
        try {
            Date parsedJsonDate = jsonFormatter.parse(rawDate);
            String finalDatePattern = "MMM d, yyy";
            SimpleDateFormat finalDateFormatter = new SimpleDateFormat(finalDatePattern, Locale.US);
            return finalDateFormatter.format(parsedJsonDate);
        } catch (ParseException e) {
            Log.e("QueryUtils", "Error parsing JSON date: ", e);
            return "";
        }
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("mainActivity", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("Queryutils", "Error making HTTP request: ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            // Closing the input stream could throw an IOException, which is why
            // the makeHttpRequest(URL url) method signature specifies than an IOException
            // could be thrown.
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();    }

    static List<News> parseJson(String response) {
        // Create an empty ArrayList that we can start adding News to
        ArrayList<News> listOfNews = new ArrayList<>();
        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject jsonResults = jsonResponse.getJSONObject("response");
            JSONArray resultsArray = jsonResults.getJSONArray("results");

            // For each news in the resultsArray, create an {@link News} object
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject oneResult = resultsArray.getJSONObject(i);
                // Extract the value for the key called "mag"
                String webTitle = oneResult.getString("webTitle");
                // Extract the value for the key called "mag"
                String url = oneResult.getString("webUrl");
                // Extract the value for the key called "mag"
                String date = oneResult.getString("webPublicationDate");
                // Extract the value for the key called "mag"
                date = formatDate(date);
                // Extract the value for the key called "mag"
                String category = oneResult.getString("sectionName");
                // Extract the value for the key called "mag"
                JSONArray tagsArray = oneResult.getJSONArray("tags");
                String author = "";

                if (tagsArray.length() == 0) {
                    author = null;
                } else {
                    for (int j = 0; j < tagsArray.length(); j++) {
                        JSONObject firstObject = tagsArray.getJSONObject(j);
                        author += firstObject.getString("webTitle") + ". ";
                    }
                }
                // Create a new {@link News} object with the webTitle, author, url, date, category
                // from the JSON response.
                // Add the new {@link News} to the list of earthquakes.
                listOfNews.add(new News(webTitle, author, url, date, category));
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("Queryutils", "Error parsing JSON response", e);
        }
        // Return the list of news
        return listOfNews;
    }
}
