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

import com.mycompany.webapp.dto.OrderCompleteMap;
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
	
	
	
	@RequestMapping("/orderform")
	public List<Map> orderform(HttpServletRequest request, @RequestBody List<Map> cartItems) {
		/*
		 * cartItems = [{pstockid=..., quantity=..., appliedPrice=...}]
		 */
		
		/* 요청 user의 id를 가져오는 부분 시작 */
		mid = JwtUtil.getMidFromRequest(request);
		/* //요청 user의 id를 가져오는 부분 끝 */
		this.cartItems = cartItems;
		
		/* 카트로부터 넘겨받은 데이터 그대로 다시 리턴 */
		return cartItems;
	}

	@PostMapping("/ordercomplete")
	/*
	 * insert: orders테이블, orderItems테이블에 데이터 저장
	 * delete: cart에서 해당 상품 삭제
	 * update: product_stock 테이블에서 해당 상품 재고 업데이트
	 */
	public String orderComplete(
			@RequestBody Orders orders
			) {
			log.info("실행");
			log.info("mid = {}", mid);
			log.info("cartItems = {}", cartItems);
			String madeOrderId = orderService.makeOrder(mid, cartItems, orders);
			log.info("madeOrderId = {}", madeOrderId);
			
			return madeOrderId;
		
	}
	
	// 주문 완료 페이지. 주문 완료 후 주문 번호를 넘겨받아서 데이터 가져옴
	@GetMapping("/ordercomplete")
	public OrderCompleteMap showOrder(HttpServletRequest request, @RequestParam String oid) {
		mid = JwtUtil.getMidFromRequest(request);
		return orderService.selectOrderByOid(mid, oid);
	}

}
