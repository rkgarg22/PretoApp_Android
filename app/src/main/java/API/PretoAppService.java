package API;


import APIEntity.GetResturantListEntity;
import APIEntity.Login_Entity;
import APIEntity.UserInformation_Entity;
import APIResponse.CommonStringResponse;
import APIResponse.GetResturantListResponse;
import APIResponse.LoginResponse;
import APIResponse.RegistrationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PretoAppService {


    @POST("login.php")
    Call<LoginResponse> userLogin(
            @Body Login_Entity loginEntity
    );

    @POST("registration.php")
    Call<RegistrationResponse> userRegistration(
            @Body UserInformation_Entity userEntity
    );

    @POST("getRestuarantList.php")
    Call<GetResturantListResponse> getResturantList(
            @Body GetResturantListEntity resturantListEntity
    );

    @GET("markLike.php")
    Call<CommonStringResponse> markLike(
            @Query("userID") String userID,
            @Query("restID") String restID
    );

}
