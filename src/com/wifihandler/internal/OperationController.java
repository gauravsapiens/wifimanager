package com.wifihandler.internal;

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
     * multiple threads. Also, it checks for consequtive duplicate requests
     *
     * @param operation
     */
    public synchronized void addOperation(Operation operation) {
        Operation lastOperation = operationQueue.getLast();
        if(lastOperation.equals(operation)){
            return;
        }
        operationQueue.add(operation);
    }
}
