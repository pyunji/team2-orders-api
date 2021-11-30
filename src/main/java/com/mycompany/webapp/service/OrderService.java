package com.mycompany.webapp.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.webapp.dao.OrderCompleteDao;
import com.mycompany.webapp.dto.ordercomplete.OrderCompleteMap;
import com.mycompany.webapp.dto.ordercomplete.OrderItemInfo;
import com.mycompany.webapp.dto.orderlist.OrderListMap;
import com.mycompany.webapp.vo.OrderItem;
import com.mycompany.webapp.vo.Orders;

@Service
public class OrderService {
	@Resource private OrderCompleteDao orderCompleteDao;
//
//	@Resource CartDao cartDao;
//
//	@Resource private StockDao stockDao;
//
//	@Resource private MileageDao mileageDao;
//
//	@Resource private OrderItemDao orderItemDao;
//	
//	@Resource private OrdersDao ordersDao;
//	
	
//	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
//
//	
//	public List<OrderComplete> selectProductByorderId(String mid, String ordersId) {
//		return orderCompleteDao.selectProductByorderId(mid, ordersId);
//
//	}
//
//	public List<OrderComplete> selectpaymentByorderId(String mid, String ordersId) {
//		return orderCompleteDao.selectpaymentByorderId(mid, ordersId);
//	}
//
//	public List<OrderComplete> selectaddressByorderId(String mid, String ordersId) {
//		return orderCompleteDao.selectaddressByorderId(mid, ordersId);
//	}

	@Transactional(rollbackFor = Exception.class)
	public int orderProducts(Orders order) {
		return orderCompleteDao.insertOrders(order);
	}

	@Transactional(rollbackFor = Exception.class)
	public int specificOrder(OrderItem orderitem) {
		return orderCompleteDao.insertOrderitem(orderitem);
	}

	@Transactional
	public String makeOrder(String mid, List<OrderItemInfo> cartItems, Orders order) {
		// orderid 생성 -> orderid = yyyyMMddHHmmss + userid
		String madeOrderId = makeOrderId(mid, order);
		order.setOid(madeOrderId);
		order.setMid(mid);
		// Orders 테이블에 주문번호로 주문 데이터 입력
		orderCompleteDao.insertOrders(order);
		Date odate = new Date();
		for (int i = 0; i < cartItems.size(); i++) {
			OrderItemInfo product = cartItems.get(i);

			/*			// 재고 업데이트
						String productStockId = product.getString("pcolorId") + "_" + product.getString("sizeCode");
						Stock stock = new Stock(productStockId, product.getInt("quantity"));
						updateStock(stock, "-");
			*/
			OrderItem orderItem = new OrderItem();
			orderItem.setOid(madeOrderId);
			orderItem.setOdate(odate);
			orderItem.setOcount(product.getQuantity());
			orderItem.setPstockid(product.getPstockid());
			orderItem.setTotalPrice(product.getAppliedPrice());
			// orderitem table에 각 주문상품 데이터를 입력
			orderCompleteDao.insertOrderitem(orderItem);

			/*			// 주문한 상품 장바구니에서 삭제
						Cart cart = new Cart();
						cart.setMemberId(principal.getName());
						cart.setProductStockId(product.getString("pcolorId") + "_" + product.getString("sizeCode"));
						cartDao.deleteByMemberIdAndProductStockId(cart);*/

		}
		/*		if(order.getUsedMileage()!= null || order.getUsedMileage()!= 0) {
					updateMileageHistory(principal, order);
				}*/
		return madeOrderId;
	}
	
	public OrderCompleteMap selectOrderByOid(String mid, String oid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mid", mid);
		map.put("oid", oid);
		return orderCompleteDao.selectOrderByOid(map);
	}
	
	public List<OrderListMap> getAllOrderList(String mid) {
		return orderCompleteDao.selectAllOrderList(mid);
	}

//	@Transactional
//	public void cancelOrder(String hidden_ordersId) {
//		List<Stock> orderCancelStocks = ordersDao.selectByOrdersId(hidden_ordersId);
//		logger.info("orderCancelStocks =" + orderCancelStocks);
//		orderItemDao.updateByOrdersId(hidden_ordersId, new Date());
//		ordersDao.updateByOrdersId(hidden_ordersId);
//		for(Stock orderCancelStock : orderCancelStocks) {
//			logger.info("orderCancelStock = " + orderCancelStock);
//			updateStock(orderCancelStock, "+");
//		}
//	}
	
	//===== methods =====
	
	private String makeOrderId(String mid, Orders order) {
		Date today = new Date();
		SimpleDateFormat orderIdDateformat = new SimpleDateFormat("yyyyMMddHHmmss");
		String madeOrderId = orderIdDateformat.format(today) + mid;

		return madeOrderId;
	}
//
//	@Transactional
//	public void updateStock(Stock stock, String operator) {
//		logger.info("실행");
//		logger.info("stock = " + stock);
//		Stock oldStock = stockDao.select(stock);
//		logger.info("oldStock: " +  oldStock);
//		int oldQuantity = oldStock.getQuantity();
//		logger.info("oldQuantity: " + oldQuantity);
//		int newQuantity = 0;
//		if (operator == "-") {
//			newQuantity = oldQuantity - stock.getQuantity();
//			if (newQuantity < 0) {
//				throw new OutOfStockException("outOfStock");
//			}
//		} else if (operator == "+") {
//			newQuantity = oldQuantity + stock.getQuantity();
//		}
//		logger.info("newQuantity: " + newQuantity);
//		Stock newStock = new Stock(stock.getProductStockId(), newQuantity);
//		stockDao.update(newStock);
//	}
//
//	/**
//	 * @param principal
//	 * @param order
//	 */
//	private void updateMileageHistory(Principal principal, Orders order) {
//		Mileage mileage = new Mileage();
//		mileage.setIssueDate(new Date());
//		mileage.setMemberId(principal.getName());
//		mileage.setAmount(order.getUsedMileage());
//		mileage.setCategory("마일리지사용");
//		mileage.setContent("상품구매로 마일리지 사용");
//
//		Calendar cal = Calendar.getInstance();
//		Date date = new Date();
//		cal.setTime(date);
//		cal.add(Calendar.YEAR, 1);
//		mileage.setExpireDate(cal.getTime());
//		mileage.setName("마일리지사용");
//		mileage.setStatus("0");
//		mileageDao.insertMileage(mileage);
//	}

}
