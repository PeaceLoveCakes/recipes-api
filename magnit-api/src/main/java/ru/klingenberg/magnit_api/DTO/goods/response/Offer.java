package ru.klingenberg.magnit_api.DTO.goods.response;

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
