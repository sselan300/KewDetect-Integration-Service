package com.kewdetect.integration.services;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class RejectProcessEnd implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.err.println("Reject process action for ...");
    }
}
