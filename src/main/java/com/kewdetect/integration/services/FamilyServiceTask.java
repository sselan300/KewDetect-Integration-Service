/*
 * Copyright (c) 2019, 2020 kewmann.com and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kewdetect.integration.services;

import com.kewdetect.integration.model.payload.request.TaskModelRequest;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import java.util.Optional;

public class FamilyServiceTask implements JavaDelegate {
    /**
     * @param execution
     */
    public void execute(DelegateExecution execution) {
        TaskModelRequest taskModel = new TaskModelRequest();
        Optional<Object> caseID = Optional.ofNullable(execution.getVariable("case_id"));
        caseID.ifPresent(x -> {
            taskModel.setCaseID(x.toString());
        });
        Optional<Object> rfiID = Optional.ofNullable(execution.getVariable("rfi_id"));
        rfiID.ifPresent(x -> {
            taskModel.setRfiID(x.toString());
        });
        Optional<Object> groupType = Optional.ofNullable(execution.getVariable("group_type"));
        groupType.ifPresent(x -> {
            taskModel.setGroupType(x.toString());
        });
        Optional<Object> keyword = Optional.ofNullable(execution.getVariable("keyword"));
        keyword.ifPresent(x -> {
            taskModel.setKeyword(x.toString());
        });
        new TaskExecutorWorker(taskModel);
    }
}
