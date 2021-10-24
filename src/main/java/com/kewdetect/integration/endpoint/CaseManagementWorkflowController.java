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
package com.kewdetect.integration.endpoint;

import com.kewdetect.integration.model.TaskModel;
import com.kewdetect.integration.model.payload.request.TaskModelRequest;
import com.kewdetect.integration.services.CaseManagementService;
import com.kwm.common.endpoint.AbstractEndpoint;
import com.kwm.common.logging.SystemGeneralLog;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/flowable/bpmn")
@Api(value = "CaseManagementWorkflowController")
public class CaseManagementWorkflowController extends AbstractEndpoint {

    @Autowired
    private CaseManagementService service;

    @PostMapping(value = "/integration/agency")
    @ApiOperation(
            value = "Integration Agency.",
            notes = "Integration Agency.",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", reference = "The server successfully processed the request.", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Bad Request", reference = "Requested action could not be understood by the system.", response = ResponseEntity.class),
            @ApiResponse(code = 401, message = "Unauthorized", reference = "Requested action requires authentication.", response = ResponseEntity.class),
            @ApiResponse(code = 403, message = "Forbidden", reference = "System refuses to fulfill the requested action.", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error", reference = "A generic error has occurred on the system.", response = ResponseEntity.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
    })
    public DeferredResult<ResponseEntity> agency(
            @Validated @RequestBody TaskModelRequest body,
            BindingResult bindingResult,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        String accessToken = getAccessToken(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
        return toDeferredResult(
                new DeferredResult<>(),
                service.agency(body).subscribeOn(Schedulers.io()),
                Locale.getDefault()
        );
    }

    @PostMapping(value = "/integration/flow")
    @ApiOperation(
            value = "Flowable BPMN.",
            notes = "Flowable BPMN.",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", reference = "The server successfully processed the request.", response = ResponseEntity.class),
            @ApiResponse(code = 400, message = "Bad Request", reference = "Requested action could not be understood by the system.", response = ResponseEntity.class),
            @ApiResponse(code = 401, message = "Unauthorized", reference = "Requested action requires authentication.", response = ResponseEntity.class),
            @ApiResponse(code = 403, message = "Forbidden", reference = "System refuses to fulfill the requested action.", response = ResponseEntity.class),
            @ApiResponse(code = 500, message = "Internal Server Error", reference = "A generic error has occurred on the system.", response = ResponseEntity.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
    })
    public DeferredResult<ResponseEntity> flowableBPMN(
            @Validated @RequestBody TaskModelRequest body,
            BindingResult bindingResult,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        String accessToken = getAccessToken(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
        return toDeferredResult(
                new DeferredResult<>(),
                service.flowableBPMN(body).subscribeOn(Schedulers.io()),
                Locale.getDefault()
        );
    }

}
