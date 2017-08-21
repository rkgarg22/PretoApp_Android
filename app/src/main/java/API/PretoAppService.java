package API;


import APIEntity.GetResturantListEntity;
import APIEntity.Login_Entity;
import APIEntity.UserInformation_Entity;
import APIResponse.GetResturantListResponse;
import APIResponse.LoginResponse;
import APIResponse.RegistrationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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

}
