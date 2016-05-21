package com.wifihandler.internal;

import com.wifihandler.WifiRequestCallbacks;

import java.util.Collection;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * Thread that continuously polls {@link Operation} from queue & executes it.
 *
 * @author gauravarora
 * @since 21/05/16
 */
public class OperationDispatcher extends Thread {

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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stopExecution() {
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
                status = isWifiEnabled() || wifiHardwareInterface.enableWifiHardware();
                if (status) {
                    activeWifiProcesses.add(operation.getProcessId());
                }
            }
            break;

            case DISABLE_WIFI: {
                if (!hasOtherActiveOperations(operation)) {
                    status = false;
                    reason = "WiFi in use. Can't turn off";
                } else {
                    if (isWifiEnabled()) {
                        status = wifiHardwareInterface.disableWiFiHardware();
                    }
                    if(status) {
                        activeWifiProcesses.remove(operation.getProcessId());
                    }
                }
            }
            break;
        }

        notifyCallbacks(operation, status, reason);
    }

    private boolean hasOtherActiveOperations(Operation operation) {
        return activeWifiProcesses.size() > 1 || !activeWifiProcesses.contains(operation.getProcessId());
    }

    private void notifyCallbacks(Operation operation, boolean status, String reason) {
        Collection<WifiRequestCallbacks> callbacks = operation.getCallbacks();
        if (callbacks == null || callbacks.isEmpty()) {
            return;
        }

        for (WifiRequestCallbacks callback : callbacks) {
            if (status) {
                callback.onSuccess(operation.getProcessId(), operation.getOperationType());
            } else {
                callback.onFailure(operation.getProcessId(), operation.getOperationType(), reason);
            }
        }
    }

    private boolean isWifiEnabled() {
        return !(activeWifiProcesses == null || activeWifiProcesses.isEmpty());
    }

    private boolean isValidOperation(Operation operation) {
        return !(operation == null || operation.getOperationType() == null);
    }

    private WifiHardwareInterface getWifiHardwareInterface() {
        return null;
    }
}
