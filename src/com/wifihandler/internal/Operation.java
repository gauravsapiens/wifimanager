package com.wifihandler.internal;

import com.wifihandler.WifiRequestCallbacks;

/**
 * Defines a basic wifi operation
 *
 * @author gauravarora
 * @since 21/05/16
 */
public class Operation {

    public enum OperationType {
        ENABLE_WIFI,
        DISABLE_WIFI
    }

    private String processId;
    private OperationType operationType;
    private WifiRequestCallbacks callbacks;

    public Operation(){
    }

    public Operation(String processId, OperationType operationType, WifiRequestCallbacks callbacks) {
        this.processId = processId;
        this.operationType = operationType;
        this.callbacks = callbacks;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public WifiRequestCallbacks getCallbacks() {
        return callbacks;
    }

    public void setCallbacks(WifiRequestCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Operation)){
            return false;
        }
        Operation operation = (Operation) obj;
        if(operation.processId.equals(processId) && operation.getOperationType() == operationType){
            return true;
        }
        return false;
    }
}
