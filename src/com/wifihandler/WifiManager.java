package com.wifihandler;

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

    public static void enableWifi(String processId, WifiRequestCallbacks callback) {
        wifiHandler.operationController.addOperation(processId, WifiOperationType.ENABLE_WIFI, callback);
    }

    public static void disableWifi(String processId, WifiRequestCallbacks callback){
        wifiHandler.operationController.addOperation(processId, WifiOperationType.DISABLE_WIFI, callback);
    }

}
