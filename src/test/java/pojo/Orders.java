package pojo;

import java.util.List;

public class Orders {

	private List<OrderDetail> orders;
	public List<OrderDetail> getOrder()
	{
		return orders;
	}
	public void setOrders(List<OrderDetail> orders)
	{
		this.orders=orders;
	}
}
