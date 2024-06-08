package ru.klingenberg.magnitgetgoods.DTO.response;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Data
@ToString
@ResponseBody
public class GoodsResponse {
    private List<Good> goods;

    private Pagination pagination;
}
