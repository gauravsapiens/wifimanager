package com.wifihandler;

/**
 * Callbacks for wifi operations
 *
 * @author gauravarora
 * @since 21/05/16
 */
public interface WifiRequestCallbacks {

    void onSuccess(String processId, boolean isWifiEnabled);

    void onFailure(String processId, boolean isWifiEnabled, String reason);

}
