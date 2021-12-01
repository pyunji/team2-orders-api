package com.mycompany.webapp.dto.orderlist;

import lombok.Data;

@Data
public class OrderItemListMap {
	String pstockid;
	Integer ocount;
	String bname;
	String pname;
	String scode;
	String ccode;
	String img1;
	String ostatus;
	Integer totalPrice;
}
