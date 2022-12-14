package com.test;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.context.Context;
import org.flowable.engine.test.Deployment;
import org.flowable.spring.impl.test.FlowableSpringExtension;
import org.flowable.task.api.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

/**
 * https://cmcts.com.vn/.
 *
 * @author long
 * @version 1.0
 * @created_by long
 * @created_date 9/22/21 3:22 PM
 * @updated_by long
 * @updated_date 9/22/21 3:22 PM
 * @since 9/22/21 3:22 PM
 */

@ExtendWith(FlowableSpringExtension.class)
@ExtendWith(SpringExtension.class)
public class ArticleWorkflowUnitTest {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Test
    @Deployment(resources = { "processes/article-workflow.bpmn20.xml" })
    public void articleApprovalTest() {
//        RuntimeService runtimeService = Context.getProcessEngineConfiguration()
//                .getRuntimeService();
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("author", "test@baeldung.com");
//        variables.put("url", "http://baeldung.com/dummy");
//
//        runtimeService.startProcessInstanceByKey("articleReview", variables);
//        Task task = taskService.createTaskQuery().singleResult();
//
//        Assert.assertEquals("Review the submitted tutorial", task.getName());
//
//        variables.put("approved", true);
//        taskService.complete(task.getId(), variables);
//
//        Assert.assertEquals(0, runtimeService.createProcessInstanceQuery().count());
    }
}
