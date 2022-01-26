package com.payoneer.evaluation_task;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.payoneer.evaluation_task.models.ApplicableNetwork;
import com.payoneer.evaluation_task.models.InputElement;
import com.payoneer.evaluation_task.models.Networks;

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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private static final String TAG = "GetApplicableNetworks";

    private static Repository instance;

    private final String apiUrl = "https://raw.githubusercontent.com/optile/checkout-android/develop/shared-test/lists/listresult.json";

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public void getApplicableNetworksFromUrl(NetworkCallback networkCallback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            String serverResponse = null;
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(apiUrl);
                urlConnection = (HttpURLConnection) url.openConnection();

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    serverResponse = readStream(urlConnection.getInputStream());
                    Log.v(TAG, serverResponse);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String finalServerResponse = serverResponse;
            handler.post(() -> {
                if (finalServerResponse != null) {
                    networkCallback.onSuccess(parseJson(finalServerResponse));
                } else {
                    networkCallback.onFailure();
                }
            });
        });
    }

    private Networks parseJson(String serverResponse) {
        Networks networks = new Networks();
        try {
            JSONObject responseObject = new JSONObject(serverResponse);
            JSONObject networkObject = responseObject.getJSONObject("networks");
            JSONArray applicableArray = networkObject.getJSONArray("applicable");
            List<ApplicableNetwork> listApplicableNetworks = new ArrayList<>();
            for (int i = 0; i < applicableArray.length(); i++) {
                JSONObject item = applicableArray.getJSONObject(i);
                ApplicableNetwork applicableNetwork = new ApplicableNetwork();
                applicableNetwork.setCode(item.getString("code"));
                applicableNetwork.setLabel(item.getString("label"));
                if(item.has("inputElements")) {
                    JSONArray inputElementArray = item.getJSONArray("inputElements");
                    List<InputElement> listOfElements = new ArrayList<>();
                    for (int j = 0; j < inputElementArray.length(); j++) {
                        JSONObject inputElementItem = inputElementArray.getJSONObject(j);
                        InputElement element = new InputElement();
                        element.setName(inputElementItem.getString("name"));
                        element.setType(inputElementItem.getString("type"));
                        listOfElements.add(element);
                    }
                    applicableNetwork.setInputElements(listOfElements);
                }
                listApplicableNetworks.add(applicableNetwork);
            }
            networks.setApplicable(listApplicableNetworks);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return networks;
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
