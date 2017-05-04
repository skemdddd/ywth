package com.example.dddkj.ypth.Entity;


import java.io.Serializable;

public class ProductInfo extends BaseInfo implements Serializable
{
	public String getGoodsVal() {
		return goodsVal;
	}

	public void setGoodsVal(String goodsVal) {
		this.goodsVal = goodsVal;
	}

	public String getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(String marketPrice) {
		this.marketPrice = marketPrice;
	}

	private String marketPrice;
	private String goodsVal;
	private String imageUrl;
	private String desc;
	private String stock;


	private double price;
	private int count;

	private int position;// 绝对位置，只在ListView构造的购物车中，在删除时有效

	public ProductInfo()
	{
		super();
	}

	public ProductInfo(String id, String name, String imageUrl, String goodsVal, String marketPrice, String desc, double price, int count,String stock)
	{

		super.Id = id;
		super.name = name;
		this.imageUrl = imageUrl;
		this.desc = desc;
		this.price = price;
		this.count = count;
		this.goodsVal =goodsVal;
		this.marketPrice= marketPrice;
		this.stock =stock;



	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getImageUrl()
	{
		return imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public int getPosition()
	{
		return position;
	}

	public void setPosition(int position)
	{
		this.position = position;
	}

}
