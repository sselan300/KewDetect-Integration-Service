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
package com.kewdetect.integration.services.koperasi;

import com.kewdetect.integration.model.payload.request.TaskModelRequest;
import com.kewdetect.integration.services.utils.ModelConverter;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class KoperasiServiceTask implements JavaDelegate {

    /**
     * @param execution
     */
    public void execute(DelegateExecution execution) {
        TaskModelRequest request=  ModelConverter.convert(execution);
//        Optional<Object> kop = Optional.ofNullable(execution.getVariable("kop"));
//        kop.ifPresent(x -> {
//            taskModel.setKop(x.toString());
//        });
//        new TaskExecutorWorker(taskModel);

        System.out.println("kop api is called");


    }


}
