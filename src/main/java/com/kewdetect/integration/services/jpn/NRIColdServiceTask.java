package com.kewdetect.integration.services.jpn;

import com.kewdetect.integration.model.payload.request.TaskModelRequest;
import com.kewdetect.integration.services.utils.ModelConverter;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import java.util.Optional;

public class NRIColdServiceTask implements JavaDelegate {

    /**
     * @param execution
     */
    public void execute(DelegateExecution execution) {

        TaskModelRequest request=  ModelConverter.convert(execution);
//        new TaskExecutorWorker(taskModel);
        System.out.println("Old Ic api is called");
    }
}
