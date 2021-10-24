package com.kewdetect.integration.model.payload.request;

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
public class TaskModelRequest implements Serializable {

    @SerializedName("case_id")
    @ApiModelProperty(name = "case_id", example = "case_id", required = true)
    @JsonProperty("case_id")
    private String caseID;

    @SerializedName("rfi_id")
    @ApiModelProperty(name = "rfi_id", example = "rfi_id", required = true)
    @JsonProperty("rfi_id")
    private String rfiID;

    @SerializedName("agency_id")
    @ApiModelProperty(name = "agency_id", example = "agency_id", required = true)
    @JsonProperty("agency_id")
    private String agencyID;

    @SerializedName("group_type")
    @ApiModelProperty(name = "group_type", example = "group_type", required = true)
    @JsonProperty("group_type")
    private String groupType;

    @SerializedName("keyword")
    @ApiModelProperty(name = "keyword", example = "keyword", required = true)
    @JsonProperty("keyword")
    private String keyword;
}
