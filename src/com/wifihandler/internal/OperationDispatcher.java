package com.wifihandler.internal;

import com.wifihandler.WifiRequestCallbacks;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * Dispatcher that continuously polls {@link Operation} from queue & executes
 *
 * @author gauravarora
 * @since 21/05/16
 */
public class OperationDispatcher extends Thread{

    private final Queue<Operation> operationQueue;
    private final Set<String> activeWifiProcesses;
    private final WifiHardwareInterface wifiHardwareInterface;
    private boolean shouldExecute;

    public OperationDispatcher(Queue<Operation> operationQueue) {
        this.operationQueue = operationQueue;
        activeWifiProcesses = new HashSet<String>();
        wifiHardwareInterface = getWifiHardwareInterface();
        shouldExecute = true;
    }

    @Override
    public void run() {
        while (shouldExecute) {
            if (!operationQueue.isEmpty()) {
                try {
                    Operation operation = operationQueue.poll();
                    performOperation(operation);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void stopExecution(){
        shouldExecute = false;
    }

    private void performOperation(Operation operation) {
        if (!isValidOperation(operation)) {
            return;
        }

        boolean status = true;
        String reason = null;
        switch (operation.getOperationType()) {
            case ENABLE_WIFI: {
                if(!isWifiEnabled()) {
                    status = wifiHardwareInterface.enableWifiHardware();
                    if(status){
                        activeWifiProcesses.add(operation.getProcessId());
                    }
                }
            }
            break;

            case DISABLE_WIFI: {
                if (!activeWifiProcesses.isEmpty()) {
                    status = false;
                    reason = "WiFi in use. Can't turn off";
                } else {
                    if(isWifiEnabled()) {
                        status = wifiHardwareInterface.disableWiFiHardware();
                    }
                    activeWifiProcesses.remove(operation.getProcessId());
                }
            }
            break;
        }

        WifiRequestCallbacks callbacks = operation.getCallbacks();
        if (callbacks != null) {
            if (status) {
                callbacks.onSuccess(operation.getProcessId(), isWifiEnabled());
            } else {
                callbacks.onFailure(operation.getProcessId(), isWifiEnabled(), reason);
            }
        }
    }

    private boolean isWifiEnabled(){
        return !(activeWifiProcesses == null || activeWifiProcesses.isEmpty());
    }

    private boolean isValidOperation(Operation operation) {
        return !(operation == null || operation.getOperationType() == null);
    }

    private WifiHardwareInterface getWifiHardwareInterface(){
        return null;
    }
}
