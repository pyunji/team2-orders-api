package com.mycompany.webapp.dto.fromCartToOrder;

import java.util.List;

import com.mycompany.webapp.vo.Orders;

import lombok.Data;

@Data
public class OrderAllInfo {
	Orders orders;
	List<OrderItemInfo> cartItems;
}
