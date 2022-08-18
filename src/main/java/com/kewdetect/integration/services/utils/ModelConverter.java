package com.kewdetect.integration.services.utils;

import com.kewdetect.integration.model.payload.request.TaskModelRequest;
import org.flowable.engine.delegate.DelegateExecution;

import java.util.Optional;

public class ModelConverter {

    public static TaskModelRequest convert(DelegateExecution execution){
        TaskModelRequest taskModel = new TaskModelRequest();
        Optional<Object> ipID = Optional.ofNullable(execution.getVariable("ip_id"));
        ipID.ifPresent(x -> {
            taskModel.setIpID(x.toString());
        });
        Optional<Object> rfiID = Optional.ofNullable(execution.getVariable("rfi_id"));
        rfiID.ifPresent(x -> {
            taskModel.setRfiID(x.toString());
        });
        Optional<Object> keyword = Optional.ofNullable(execution.getVariable("keyword"));
        keyword.ifPresent(x -> {
            taskModel.setKeyword(x.toString());
        });

        Optional<Object> keywordType = Optional.ofNullable(execution.getVariable("keyword_type"));
        keywordType.ifPresent(x -> {
            taskModel.setKeywordType(x.toString());
        });

        Optional<Object> joID = Optional.ofNullable(execution.getVariable("jo_id"));
        joID.ifPresent(x -> {
            taskModel.setJoID(x.toString());
        });
    return taskModel;
    }
}
