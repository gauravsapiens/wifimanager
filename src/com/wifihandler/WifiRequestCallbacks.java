package com.wifihandler;

/**
 * Callbacks for wifi operations
 *
 * @author gauravarora
 * @since 21/05/16
 */
public interface WifiRequestCallbacks {

    void onSuccess(String processId, WifiOperationType requestedOperation);

    void onFailure(String processId, WifiOperationType requestedOperation, String reason);

}
