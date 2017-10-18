package example.com.retrofit20;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by muhammad.sohail on 10/18/2017.
 */

public class Recycleview extends Activity {

    APIInterface apiInterface;
    ImageView userPic;
    TextView nameTv;
    private AnswersAdapter mAdapter = null;
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<UserList.Datum> arrayList;
    UserList userInfo = new UserList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        initUI();
        arrayList = new ArrayList<>();
        if (isNetworkAvailable()) {
            apiInterface = APIClient.getClient().create(APIInterface.class);
            getUserData();
        }
    }

    private void initUI() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void getUserData() {
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Please wait", "Loading...");

        /**
         GET List Users
         **/
        Call call2 = apiInterface.doGetUserList("2");
        call2.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                String displayResponse = "";
                UserList userList = (UserList) response.body();
                Integer text = userList.getPage();
                Integer total = userList.getTotal();
                Integer totalPages = userList.getTotalPages();
                List<UserList.Datum> datumList = userList.getData();


                for (UserList.Datum datum : datumList) {
                    //  userInfo.data = (List<UserList.Datum>) datum;
                    arrayList.add(datum);
                    // Toast.makeText(getApplicationContext(), "id : " + datum.getId() + " name: " + datum.getFirstName() + " " + datum.getLastName() + " avatar: " + datum.getAvatar(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

                displayData();


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                call.cancel();
                progressDialog.dismiss();
            }
        });
    }

    private void displayData() {
        mAdapter = new AnswersAdapter(this, arrayList, new AnswersAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
                Toast.makeText(Recycleview.this, "Post id is" + id, Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setAdapter(mAdapter);


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}