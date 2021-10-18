package com.kewdetect.integration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * https://cmcts.com.vn/.
 *
 * @author long
 * @version 1.0
 * @created_by long
 * @created_date 9/22/21 1:44 PM
 * @updated_by long
 * @updated_date 9/22/21 1:44 PM
 * @since 9/22/21 1:44 PM
 */
@Getter
@Setter
public class TaskModel implements Serializable {

    @SerializedName("id")
    @ApiModelProperty(name = "id", example = "id", required = true)
    @JsonProperty("id")
    private String id;

    @SerializedName("identity_no")
    @ApiModelProperty(name = "identity_no", example = "identity_no", required = true)
    @JsonProperty("identity_no")
    private String identityNo;
}
