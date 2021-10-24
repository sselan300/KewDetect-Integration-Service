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

import com.google.gson.Gson;
import com.kewdetect.integration.model.TaskModel;
import com.kewdetect.integration.model.payload.request.TaskModelRequest;
import com.kwm.common.analytics.Analytics;
import com.kwm.common.analytics.AnalyticsHelper;
import com.kwm.common.mailer.MailUtils;
import com.kwm.common.model.KewMannAPIResponseEntity;
import io.reactivex.Observable;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class CaseManagementService {

    private Analytics analytics = AnalyticsHelper.geAnalytics(CaseManagementService.class);

    private RuntimeService runtimeService;

    private TaskService taskService;

    @Autowired
    public CaseManagementService(RuntimeService runtimeService, TaskService taskService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    public Observable<KewMannAPIResponseEntity> agency(TaskModelRequest taskModel) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() ->
                {
                    new TaskExecutorWorker(taskModel);
                }
        );
        return Observable.fromCallable(() -> new KewMannAPIResponseEntity(new HashMap<String, String>(), HttpStatus.OK));
    }

    public Observable<KewMannAPIResponseEntity> flowableBPMN(TaskModelRequest taskModel) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() ->
                {
                    Map<String, Object> variables = new HashMap<String, Object>();
                    variables.put("case_id", taskModel.getCaseID());
                    variables.put("rfi_id", taskModel.getRfiID());
                    variables.put("group_type", taskModel.getGroupType());
                    variables.put("keyword", taskModel.getKeyword());
                    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("identityNoRequest", variables);
                    List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
                    analytics.logEvent("You have " + tasks.size() + " tasks:");
                    for (int i = 0; i < tasks.size(); i++) {
                        Map<String, Object> variablesTemp = taskService.getVariables(tasks.get(i).getId());
                        analytics.logEvent((i + 1) + ") " + tasks.get(i).getId() + tasks.get(i).getName());
                        Map<String, Object> variablesConditionExpression = new HashMap<String, Object>();
                        String groupType = taskModel.getGroupType().trim();
                        System.err.println(groupType.trim());
                        variablesConditionExpression.put("group_type", groupType);
                        taskService.complete(tasks.get(i).getId(), variablesConditionExpression);
                    }
                }
        );
        return Observable.fromCallable(() -> new KewMannAPIResponseEntity(new HashMap<String, String>(), HttpStatus.OK));
    }

//    @Transactional
//    public List<TaskModel> getTasks(String assignee) {
//        List<org.flowable.task.api.Task> tasks = taskService.createTaskQuery()
//                //.taskCandidateGroup(assignee)
//                .list();
//        List<TaskModel> articles = tasks.stream()
//                .map(task -> {
//                    Map<String, Object> variables = taskService.getVariables(task.getId());
//                    System.err.println(new Gson().toJson(variables));
//                    TaskModel taskModel = new TaskModel();
//                    taskModel.setId(task.getId());
//                    taskModel.setIdentityNo(variables.get("identity_no").toString());
//                    return taskModel;
//                })
//                .collect(Collectors.toList());
//        return articles;
//    }
//
//    @Transactional
//    public void submitReview(TaskModel task) {
//        Map<String, Object> variables = new HashMap<String, Object>();
//        //variables.put("approved", task.isStatus());
//        taskService.complete(task.getId(), variables);
//    }
}
