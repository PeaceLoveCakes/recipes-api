package ru.klingenberg.magnit_api.DTO.goods.response;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@Data
public class Good {
    private ArrayList<String> categories;
    private String code;
    private String id;
    private String name;
    private ArrayList<Offer> offers;
    private String unitValue;
}
