package com.kewdetect.integration.services;

import com.kewdetect.integration.model.payload.request.TaskModelRequest;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import java.util.Optional;

public class IC implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        TaskModelRequest taskModel = new TaskModelRequest();
        Optional<Object> caseID = Optional.ofNullable(execution.getVariable("case_id"));
        caseID.ifPresent(x -> {
            taskModel.setIpID(x.toString());
        });
        Optional<Object> rfiID = Optional.ofNullable(execution.getVariable("rfi_id"));
        rfiID.ifPresent(x -> {
            taskModel.setRfiID(x.toString());
        });
        Optional<Object> groupType = Optional.ofNullable(execution.getVariable("keyword"));
        groupType.ifPresent(x -> {
            taskModel.setKeywordType(x.toString());
        });
        Optional<Object> keyword = Optional.ofNullable(execution.getVariable("jo_id"));
        keyword.ifPresent(x -> {
            taskModel.setJoID(x.toString());
        });

//        new TaskExecutorWorker(taskModel);
        System.out.println("Sample IC: 980916105301");

    }
}
