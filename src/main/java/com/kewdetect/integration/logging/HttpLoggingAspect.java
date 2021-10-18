package com.kewdetect.integration.logging;///*
// * Copyright (c) 2019, 2020 kewmann.com and/or its affiliates. All rights reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.kwm.authorization.logging;
//
//import com.kwm.common.analytics.ClientRequest;
//import com.kwm.common.analytics.IPUtils;
//import com.kwm.common.logging.AbstractHttpLoggingAspect;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//import java.util.Date;
//
///**
// * KAI.
// * https://all-record.tistory.com/168
// * https://www.leafcats.com/35
// *
// * @author longtran
// * @version 1.0
// * @created_by longtran
// * @created_date 9/29/19 12:02 PM
// * @updated_by longtran
// * @updated_date 9/29/19 12:02 PM
// * @since 9/29/19
// */
//@Aspect
//@Component
//public class HttpLoggingAspect extends AbstractHttpLoggingAspect {
//
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private Environment environment;
//
//    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
//    public void pointcut() {
//
//    }
//
//    /**
//     * Pointcut that matches all repositories, services and Web REST endpoints.
//     */
//    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
//            " || within(@org.springframework.stereotype.Service *)" +
//            " || within(@org.springframework.web.bind.annotation.RestController *)")
//    public void springBeanPointcut() {
//        // Method is empty as this is just a Pointcut, the implementations are in the advices.
//    }
//
//    /**
//     * Pointcut that matches all Spring beans in the application's main packages.
//     */
//    @Pointcut("within(com.kwm.authorization.repository..*)" +
//            " || within(com.kwm.authorization.services..*)" +
//            " || within(com.kwm.authorization.endpoint..*)")
//    public void applicationPackagePointcut() {
//        // Method is empty as this is just a Pointcut, the implementations are in the advices.
//    }
//
//    /**
//     * Advice that logs when a method is entered and exited.
//     *
//     * @param joinPoint join point for advice
//     * @return result
//     * @throws Throwable throws IllegalArgumentException
//     */
//    @Around("applicationPackagePointcut() && springBeanPointcut()")
//    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        return log2Cloud(log, joinPoint, environment, "ROLE-ACCESS-SERVICE");
//    }
//
//    /**
//     * @param e
//     */
//    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
//    public void afterThrowable(Throwable e) {
//        log.error("An abnormality occurred in the cut surface：", e);
//    }
//}
