package com.mycompany.webapp.dto.ordercomplete;

import lombok.Data;

@Data
public class OrderCompleteItem {
	private String pstockid;
	private String pcolorid;
	private String bname;
	private String pname;
	private String scode;
	private String ccode;
	private String img1;
	private Integer ocount;
	private Integer totalPrice;
}
