package com.mycompany.webapp.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.fromCartToOrder.OrderAllInfo;
import com.mycompany.webapp.dto.ordercomplete.OrderCompleteMap;
import com.mycompany.webapp.dto.orderlist.OrderHistoryMap;
import com.mycompany.webapp.security.JwtUtil;
import com.mycompany.webapp.service.OrderService;
import com.mycompany.webapp.vo.Orders;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
	private static String mid;
	private static List<Map> cartItems;
	
	@Resource OrderService orderService;
	
	
	
//	@RequestMapping("/orderform")
//	public List<Map> orderform(HttpServletRequest request, @RequestBody List<Map> cartItems) {
//		/*
//		 * cartItems = [{pstockid=..., quantity=..., appliedPrice=...}]
//		 */
//		
//		/* 요청 user의 id를 가져오는 부분 시작 */
//		mid = JwtUtil.getMidFromRequest(request);
//		/* //요청 user의 id를 가져오는 부분 끝 */
//		this.cartItems = cartItems;
//		
//		/* 카트로부터 넘겨받은 데이터 그대로 다시 리턴 */
//		return cartItems;
//	}

	@PostMapping("/ordercomplete")
	/*
	 * insert: orders테이블, orderItems테이블에 데이터 저장
	 * delete: cart에서 해당 상품 삭제
	 * update: product_stock 테이블에서 해당 상품 재고 업데이트
	 */
	public String orderComplete(HttpServletRequest request, @RequestBody OrderAllInfo orderAllInfo) {
			/* 요청 user의 id를 가져오는 부분 시작 */
			mid = JwtUtil.getMidFromRequest(request);
			/* //요청 user의 id를 가져오는 부분 끝 */
			log.info("실행");
			log.info("mid = {}", mid);
			log.info("orderAllInfo = {}", orderAllInfo);
			String madeOrderId = orderService.makeOrder(mid, orderAllInfo.getCartItems(), orderAllInfo.getOrders());
			log.info("madeOrderId = {}", madeOrderId);
			
			return madeOrderId;
	}
	
	// 주문 완료 페이지. 주문 완료 후 주문 번호를 넘겨받아서 데이터 가져옴
	@GetMapping("/ordercomplete")
	public OrderCompleteMap showOrder(HttpServletRequest request, @RequestParam String oid) {
		mid = JwtUtil.getMidFromRequest(request);
		log.info("mid = {}", mid);
		log.info("oid = {}", oid);
		OrderCompleteMap orderItems = orderService.selectOrderByOid(mid, oid);
		log.info("orderItems = {}", orderItems);
		return orderItems;
	}
	
	@GetMapping("/orderlist")
	public List<OrderHistoryMap> showAllOrder(HttpServletRequest request) {
		mid = JwtUtil.getMidFromRequest(request);
		List<OrderHistoryMap> orderHistory = orderService.getOrderHistory(mid);
		log.info("orderHistory = " + orderHistory);
		return orderHistory;
	}

}
