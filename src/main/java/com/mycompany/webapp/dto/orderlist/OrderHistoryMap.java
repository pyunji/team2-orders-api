package com.mycompany.webapp.dto.orderlist;

import java.util.List;

import lombok.Data;

@Data
public class OrderHistoryMap {
	String oid;
	String ostatus;
	List<OrderHistoryItem> orderHistoryItems;
}
