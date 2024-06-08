package ru.klingenberg.magnit_api.DTO.goods.response;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.klingenberg.magnit_api.DTO.goods.Pagination;

import java.util.List;

@Data
@ToString
@ResponseBody
public class GoodsResponse {
    private List<Good> goods;

    private Pagination pagination;
}
