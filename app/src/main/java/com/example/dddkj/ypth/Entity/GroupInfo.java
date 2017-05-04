package com.example.dddkj.ypth.Entity;

import java.io.Serializable;

public class GroupInfo extends BaseInfo implements Serializable
{
	public GroupInfo()
	{
		super();
	}

	public GroupInfo(String id, String name,String deliveryFreeMoney,String deliveryMoney)
	{
		super(id, name,deliveryFreeMoney,deliveryMoney);
	}

}
