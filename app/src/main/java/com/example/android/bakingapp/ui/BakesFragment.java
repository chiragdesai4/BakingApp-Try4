package com.example.android.bakingapp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.BakesAdapter;
import com.example.android.bakingapp.model.Bake;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.android.bakingapp.ui.MainActivity.isTablet;

/**
 * Created by Chirag Desai on 12-06-2017.
 */

public class BakesFragment extends Fragment implements BakesAdapter.ListItemClickListener {

    public static ArrayList<Bake> bakes = new ArrayList<>();
    private RecyclerView recyclerView;
    private BakesAdapter adapter;
    private View rootView;

    public BakesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_fragment, container, false);
        new FetchBakesTask(getActivity()).execute();
        return rootView;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(getActivity(), StepsActivity.class);
        intent.putExtra("item", clickedItemIndex);
        startActivity(intent);
    }

    void onPostExcute(ArrayList<Bake> bakes) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.bakeslist);
        RecyclerView.LayoutManager layoutManager;
        if (isTablet) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        } else {
            layoutManager = new LinearLayoutManager(getActivity());
        }
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BakesAdapter(this, bakes);
        recyclerView.setAdapter(adapter);
    }

    public class FetchBakesTask extends AsyncTask<Void, Void, ArrayList<Bake>> {

        private ProgressDialog dialog;

        public FetchBakesTask(Activity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage(getString(R.string.dialog));
            dialog.show();
        }

        @Override
        protected ArrayList<Bake> doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                Uri builtUri = Uri.parse(getString(R.string.URL))
                        .buildUpon()
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod(getString(R.string.GET));
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                JSONArray movieArray = new JSONArray(buffer.toString());
                bakes = new ArrayList<>();
                for (int i = 0; i < movieArray.length(); i++) {
                    bakes.add(new Bake(movieArray.getJSONObject(i)));
                    Log.e("name: ", bakes.get(i).getName());
                }
                return bakes;
            } catch (Exception e) {
                e.printStackTrace();
                return bakes;
            } finally {
                try {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        reader.close();
                    }
                } catch (Exception e) {
                }
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Bake> bakes) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            onPostExcute(bakes);
        }
    }
}
