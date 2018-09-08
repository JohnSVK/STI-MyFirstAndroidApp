package expert.sti.myfirstapp.presenters;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import expert.sti.myfirstapp.entities.Actor;
import expert.sti.myfirstapp.ui.adapters.ActorAdapter;
import expert.sti.myfirstapp.ui.fragments.ActorFragment;

public class ActorFragmentPresenter implements IActorFragmentPresenter{

    private int mPosition;
    private ActorFragment mView;

    private static final String URL = "http://www.infotech.sk/sti/actors";
    private ArrayList<Actor> actors;

    public ActorFragmentPresenter(ActorFragment view, int position) {
        this.mView = view;
        this.mPosition = position;
    }

    @Override
    public void start() {
        new JSONAsyncTask().execute();
    }

    private class JSONAsyncTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                HttpGet httppost = new HttpGet(URL);
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse response = httpClient.execute(httppost);

                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity httpEntity = response.getEntity();
                    String data = EntityUtils.toString(httpEntity);

                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("actors");

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Actor>>(){}.getType();
                    actors = gson.fromJson(jsonArray.toString(), type);

                    return true;
                } else {
                    mView.makeToast("Internet connection failed");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result){
            if (result) {
               mView.onWSResponse(createSeparateArrayList());
            } else {
                mView.makeToast("Unable fetch data from server!");
            }
        }
    }

    private ArrayList<Actor> createSeparateArrayList() {
        ArrayList<Actor> ar = new ArrayList<>();

        if (mPosition == 0) {
            return actors;
        } else if (mPosition == 1) {

            for (Actor actor : actors) {
                if (actor.getGender().equals("male")) {
                    ar.add(actor);
                }
            }

            return ar;

        } else if (mPosition == 2) {
            for (Actor actor : actors) {
                if (actor.getGender().equals("female")) {
                    ar.add(actor);
                }
            }
        }

        return ar;
    }
}
