package example.com.retrofit20;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    TextView responseText;
    ImageView img1, img2, img3,img4;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        responseText = (TextView) findViewById(R.id.data);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);


        apiInterface = APIClient.getClient().create(APIInterface.class);

        if (isNetworkAvailable()) {
            /**
             GET List Resources
             **/
            Call call = apiInterface.doGetListResources();
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {


                    Log.d("TAG", response.code() + "");

                    String displayResponse = "";

                    MultipleResource resource = (MultipleResource) response.body();
                    Integer text = resource.getPage();
                    Integer total = resource.getTotal();
                    Integer totalPages = resource.getTotalPages();
                    List<MultipleResource.Datum> datumList = resource.getData();

                    displayResponse += text + " Page\n" + total + " Total\n" + totalPages + " Total Pages\n";

                    for (MultipleResource.Datum datum : datumList) {
                        displayResponse += datum.getId() + " " + datum.getName() + " " + datum.getPantoneValue() + " " + datum.getYear() + "\n";
                    }

                    responseText.append(displayResponse);

                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    call.cancel();
                }
            });

            /**
             Create new user
             **/
//        User user = new User("morpheus", "leader");
//        Call call1 = apiInterface.createUser(user);
//        call1.enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                User user1 = response.body();
//
//                Toast.makeText(getApplicationContext(), user1.name + " " + user1.job + " " + user1.id + " " + user1.createdAt, Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                call.cancel();
//            }
//        });

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
                    Toast.makeText(getApplicationContext(), text + " page\n" + total + " total\n" + totalPages + " totalPages\n", Toast.LENGTH_SHORT).show();

                    //displayResponse = "\n\n\n"+text + " Page\n" + total + " Total\n" + totalPages + " Total Pages\n";

                    for (UserList.Datum datum : datumList) {
                        Toast.makeText(getApplicationContext(), "id : " + datum.getId() + " name: " + datum.getFirstName() + " " + datum.getLastName() + " avatar: " + datum.getAvatar(), Toast.LENGTH_SHORT).show();
                        //  displayResponse += datum.getId() + " " + datum.getFirstName() + " " + datum.getLastName() + " " + datum.getAvatar() + "\n";
                        if (datum.getId() == 4) {
                            Glide.with(MainActivity.this)
                                    .load(datum.getAvatar())
                                    //.load(R.mipmap.ic_launcher)
                                    .placeholder(R.mipmap.ic_launcher)
                                    .into(img1);
                        } else if (datum.getId() == 5) {
                            Glide.with(MainActivity.this)
                                    .load(datum.getAvatar())
                                    //.load(R.mipmap.ic_launcher)
                                    .placeholder(R.mipmap.ic_launcher)
                                    .into(img2);
                        } else {
                            Glide.with(MainActivity.this)
                                    .load(datum.getAvatar())
                                    //.load(R.mipmap.ic_launcher)
                                    .placeholder(R.mipmap.ic_launcher)
                                    .into(img3);
                        }
                    }

                    //responseText.append("\n\n call2 \n"+displayResponse);


                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    call.cancel();
                }
            });
        } else {
            Glide.with(MainActivity.this)
                    .load("https://s3.amazonaws.com/uifaces/faces/twitter/marcoramires/128.jpg")
                    //.load(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(img4);
        }


        /**
         POST name and job Url encoded.
         **/
//        Call call3 = apiInterface.doCreateUserWithField("morpheus", "leader");
//        call3.enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                UserList userList = (UserList) response.body();
//                Integer text = userList.getPage();
//                Integer total = userList.getTotal();
//                Integer totalPages = userList.getTotalPages();
//                List<UserList.Datum> datumList = userList.getData();
//                Toast.makeText(getApplicationContext(), text + " page\n" + total + " total\n" + totalPages + " totalPages\n", Toast.LENGTH_SHORT).show();
//
//                for (UserList.Datum datum : datumList) {
//                    Toast.makeText(getApplicationContext(), "id : " + datum.getId() + " name: " + datum.getFirstName() + " " + datum.getLastName() + " avatar: " + datum.getAvatar(), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                call.cancel();
//            }
//        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
