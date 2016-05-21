package com.wifihandler;

import com.wifihandler.internal.Operation;
import com.wifihandler.internal.OperationController;

/**
 * Manager that exposes API to enable/disable WiFi
 *
 * @author gauravarora
 * @since 21/05/16
 */
public class WifiManager {

    private static WifiManager wifiHandler = new WifiManager();
    private OperationController operationController;

    private WifiManager(){
        operationController = new OperationController();
    }

    public static void enableWifi(String processId, WifiRequestCallbacks callbacks) {
        Operation operation = new Operation(processId, Operation.OperationType.ENABLE_WIFI, callbacks);
        wifiHandler.operationController.addOperation(operation);
    }

    public static void disableWifi(String processId, WifiRequestCallbacks callbacks){
        Operation operation = new Operation(processId, Operation.OperationType.DISABLE_WIFI, callbacks);
        wifiHandler.operationController.addOperation(operation);
    }

}
