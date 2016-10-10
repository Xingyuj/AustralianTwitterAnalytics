package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lld on 10/05/2016.
 */
public class IPAddress {

	

    public String ip;
    public String port;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
    public IPAddress(String ip, String port) {
    	this.ip = ip;
    	this.port = port;
    }
}
