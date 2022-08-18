package com.kewdetect.integration.model.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @SerializedName("ip_id")
    @ApiModelProperty(name = "ip_id", example = "ip_id", required = false)
    @JsonProperty("ip_id")
    private String ipID;

    @SerializedName("rfi_id")
    @ApiModelProperty(name = "rfi_id", example = "rfi_id", required = true)
    @JsonProperty("rfi_id")
    private String rfiID;

    @SerializedName("keyword")
    @ApiModelProperty(name = "keyword", example = "keyword", required = true)
    @JsonProperty("keyword")
    private String keyword;

    @Enumerated(EnumType.STRING)
    @SerializedName("keyword_type")
    @ApiModelProperty(name = "keyword_type", example = "keyword_type", required = true)
    @JsonProperty("keyword_type")
    private String keywordType;

    @SerializedName("jo_id")
    @ApiModelProperty(name = "jo_id", example = "jo_id", required = false)
    @JsonProperty("jo_id")
    private String joID;

}
