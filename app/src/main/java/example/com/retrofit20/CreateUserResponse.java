package example.com.retrofit20;

/**
 * Created by muhammad.sohail on 10/16/2017.
 */

import com.google.gson.annotations.SerializedName;
public class CreateUserResponse {
    @SerializedName("name")
    public String name;
    @SerializedName("job")
    public String job;
    @SerializedName("id")
    public String id;
    @SerializedName("createdAt")
    public String createdAt;
}
