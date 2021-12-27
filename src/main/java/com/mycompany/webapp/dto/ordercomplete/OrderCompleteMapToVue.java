package com.mycompany.webapp.dto.ordercomplete;

import java.util.List;

import lombok.Data;

@Data
public class OrderCompleteMapToVue {
	private String oid;
	private String odate; 
	private Integer usedMileage;
	private Integer beforeDcPrice;
	private Integer afterDcPrice;
	private String paymentInfo;
	private String company;
	private String paymentMethodCode;
	private String ozipcode;
	private String oaddress;
	private String oreceiver;
	private String ophone;
	private String otel;
	private String memo;
	private String ostatus;
	private List<OrderCompleteItem> orderCompleteItems;
}
