package com.mycompany.webapp.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Orders {
	private String oid;
	private String ozipcode;
	private String oaddress;
	private String oreceiver;
	private String ophone;
	private String otel;
	private String memo;
	private String oemail;
	private int usedMileage;
	private int beforeDcPrice;
	private int afterDcPrice;
	private String paymentInfo;
	private String ostatus;
	private String deliveryStatus;
	private String mid;
	private String paymentMethodCode;
	private int addMileage;
}
