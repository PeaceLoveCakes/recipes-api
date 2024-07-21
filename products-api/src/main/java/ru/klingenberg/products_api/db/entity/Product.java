package ru.klingenberg.products_api.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import ru.klingenberg.products_api.dto.MeasurementUnit;
import ru.klingenberg.products_api.dto.ProductDto;


import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "product",
        uniqueConstraints = { @UniqueConstraint(columnNames =
                { "shop_id", "inShopId" }) })
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    private String name;

    private Double amount;

    private Double price;

    private MeasurementUnit measurementUnit;

    @ManyToOne(fetch = FetchType.EAGER)
    private Shop shop;

    private String inShopId;

    public static Product from(ProductDto productDto){
        return new Product()
                .setId(productDto.getId())
                .setName(productDto.getName())
                .setPrice(productDto.getPrice())
                .setAmount(productDto.getAmount())
                .setMeasurementUnit(productDto.getMeasurementUnit());
    }

    public void normalizeAmount() {
        Double amount = this.getAmount();
        if (amount != 1d) {
            Double multiply = 1 / amount;
            this.setAmount(amount * multiply);
            this.setPrice(Math.floor(this.getPrice() * multiply * 100) / 100);
        }
    }

}
