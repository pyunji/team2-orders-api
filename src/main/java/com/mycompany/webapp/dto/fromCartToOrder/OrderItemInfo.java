package com.mycompany.webapp.dto.fromCartToOrder;

import lombok.Data;

@Data
public class OrderItemInfo {
	private String pstockid;
	private String scode;
	private String ccode;
	private String img1;
	private String pname;
	private String bname;
	private Integer appliedPrice;
	private Integer pprice;
	private Integer quantity;
}
