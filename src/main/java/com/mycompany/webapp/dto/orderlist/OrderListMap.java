package com.mycompany.webapp.dto.orderlist;

import java.util.List;

import lombok.Data;

@Data
public class OrderListMap {
	String oid;
	List<OrderItemListMap> orderItemListMap;
}
