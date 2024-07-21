package ru.klingenberg.resipesapi.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import ru.klingenberg.resipesapi.dto.ProductDto;
import ru.klingenberg.resipesapi.model.MeasurementUnit;

import java.time.LocalDateTime;

@Entity(name = "product")
@Getter
@Setter
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

//    private Double amount;
//
//    private Double price;

    private MeasurementUnit measurementUnit;

    public static Product from(ProductDto productDto){
        return new Product()
                .setId(productDto.getId())
                .setName(productDto.getName())
//                .setPrice(productDto.getPrice())
//                .setAmount(productDto.getAmount())
                .setMeasurementUnit(productDto.getMeasurementUnit());
    }
//
//    public void normalizeAmount() {
//        Double amount = this.getAmount();
//        if (amount != 1d) {
//            Double multiply = 1 / amount;
//            this.setAmount(amount * multiply);
//            this.setPrice(Math.floor(this.getPrice() * multiply * 100) / 100);
//        }
//    }

}
