package com.mycompany.webapp.vo;

import java.util.Date;

import lombok.Data;

@Data
public class OrderItem {
	private String pstockid;
	private String oid;
	private int ocount;
	private int totalPrice;
	private Date odate;
	private Date odelDate;
}
