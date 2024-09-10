package org.novaride.novaridebookingservice.api;



import org.novaride.novaridebookingservice.dto.RideRequestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SocketApi {
    @POST("/api/socket/newRide")
    Call<Boolean> raiseRideRequest(@Body RideRequestDto requestDto);

}
