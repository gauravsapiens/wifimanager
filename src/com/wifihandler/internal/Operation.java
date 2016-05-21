package com.wifihandler.internal;

import com.wifihandler.WifiOperationType;
import com.wifihandler.WifiRequestCallbacks;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Defines a basic wifi operation
 *
 * @author gauravarora
 * @since 21/05/16
 */
public class Operation {

    private String processId;
    private WifiOperationType operationType;
    private Collection<WifiRequestCallbacks> callbacks;

    public Operation() {
    }

    public Operation(String processId, WifiOperationType operationType, WifiRequestCallbacks callback) {
        this.processId = processId;
        this.operationType = operationType;
        addCallbacks(callback);
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public WifiOperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(WifiOperationType operationType) {
        this.operationType = operationType;
    }

    public Collection<WifiRequestCallbacks> getCallbacks() {
        return callbacks;
    }

    public void setCallbacks(Collection<WifiRequestCallbacks> callbacks) {
        this.callbacks = callbacks;
    }

    public void addCallbacks(WifiRequestCallbacks callback) {
        if (callbacks == null) {
            callbacks = new ArrayList<WifiRequestCallbacks>();
        }
        callbacks.add(callback);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Operation)) {
            return false;
        }
        Operation operation = (Operation) obj;
        return operation.processId.equals(processId) && operation.getOperationType() == operationType;
    }
}
