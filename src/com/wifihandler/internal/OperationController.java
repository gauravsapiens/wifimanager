package com.wifihandler.internal;

import com.wifihandler.WifiOperationType;
import com.wifihandler.WifiRequestCallbacks;

import java.util.LinkedList;

/**
 * Operation controller inputs an operation & delegates the execution to {@link OperationDispatcher}. Checks for
 * consecutive duplicate requests & discards them
 *
 * @author gauravarora
 * @since 21/05/16
 */
public class OperationController {

    private LinkedList<Operation> operationQueue;
    private OperationDispatcher operationDispatcher;

    public OperationController(){
        operationQueue = new LinkedList<Operation>();
        operationDispatcher = new OperationDispatcher(operationQueue);
        operationDispatcher.start();
    }

    /**
     * This method takes care of adding operation to queue. This method is synchronized to handle calls from
     * multiple processes. Also, it checks for consecutive duplicate requests
     *
     * @param processId
     * @param operationType
     * @param callback
     */
    public synchronized void addOperation(String processId, WifiOperationType operationType, WifiRequestCallbacks callback) {
        Operation operation = new Operation(processId, operationType, callback);
        Operation lastOperation = operationQueue.getLast();
        if(lastOperation.equals(operation)){
            lastOperation.addCallbacks(callback);
            return;
        }
        operationQueue.add(operation);
    }
}
