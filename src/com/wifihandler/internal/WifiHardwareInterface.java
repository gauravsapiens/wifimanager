package com.wifihandler.internal;

/**
 * Interface that communicates with hardware drivers & enable/disable WiFi
 *
 * @author gauravarora
 * @since 21/05/16
 */
public interface WifiHardwareInterface {

    boolean enableWifiHardware();

    boolean disableWiFiHardware();

}
