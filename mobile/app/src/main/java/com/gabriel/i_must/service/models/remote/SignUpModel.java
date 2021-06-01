package com.gabriel.i_must.service.models.remote;

import com.google.gson.annotations.SerializedName;

import static com.gabriel.i_must.service.constants.APIConstants.API_MESSAGE;
import static com.gabriel.i_must.service.constants.APIConstants.API_STATUS;
import static com.gabriel.i_must.service.constants.APIConstants.API_SUCCESS;

public class SignUpModel {
    @SerializedName(API_MESSAGE)
    public String message = "";

    @SerializedName(API_SUCCESS)
    public boolean success = false;

    @SerializedName(API_STATUS)
    public String status = "";
}
