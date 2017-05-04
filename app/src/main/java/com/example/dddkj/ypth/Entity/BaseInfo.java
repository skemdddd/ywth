package com.example.dddkj.ypth.Entity;


import java.io.Serializable;

public class BaseInfo implements Serializable
{
	String Id;
	protected String name;
	private boolean isChoosed;

	public String getDeliveryMoney() {
		return deliveryMoney;
	}

	public void setDeliveryMoney(String deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}

	private String   deliveryMoney;

	public String getDeliveryFreeMoney() {
		return deliveryFreeMoney;
	}

	public void setDeliveryFreeMoney(String deliveryFreeMoney) {
		this.deliveryFreeMoney = deliveryFreeMoney;
	}

	private  String deliveryFreeMoney;

	BaseInfo()
	{
		super();
	}

	BaseInfo(String id, String name, String deliveryFreeMoney, String deliveryMoney)
	{
		super();
		Id = id;
		this.name = name;
		this.deliveryFreeMoney=deliveryFreeMoney;
		this.deliveryMoney =deliveryMoney;

	}

	public String getId()
	{
		return Id;
	}

	public void setId(String id)
	{
		Id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isChoosed()
	{
		return isChoosed;
	}

	public void setChoosed(boolean isChoosed)
	{
		this.isChoosed = isChoosed;
	}

}
