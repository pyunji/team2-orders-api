package com.mycompany.webapp.dto.orderlist;

import lombok.Data;

@Data
public class OrderHistoryItem {
	String pstockid;
	String pcolorid;
	Integer ocount;
	String bname;
	String pname;
	String scode;
	String ccode;
	String img1;
	String odate;
	Integer totalPrice;
}
