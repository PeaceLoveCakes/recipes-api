package ru.klingenberg.goods_collector.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class Offer {
    public boolean isAction;
    public String price;
    public Date actionDate;
    public int discountPercent;
    public String oldPrice;
}
