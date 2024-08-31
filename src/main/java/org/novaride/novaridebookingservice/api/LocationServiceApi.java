package org.novaride.novaridebookingservice.api;


import org.novaride.novaridebookingservice.dto.DriverLocationDto;
import org.novaride.novaridebookingservice.dto.NearByDriverRequestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LocationServiceApi {
    @POST("/api/location/nearby/drivers")
    Call<DriverLocationDto[]> getNearByDriver(@Body NearByDriverRequestDto nearByDriverRequestDto);
}
