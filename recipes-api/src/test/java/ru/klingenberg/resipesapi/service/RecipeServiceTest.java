package ru.klingenberg.resipesapi.service;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RecipeServiceTest {

//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private RecipeService recipeService;
//
//    @Test
//    void saveAndGetTest() {
//        Double price = 249.99;
//        Double amount = 1d;
//        ProductDto productDto = new ProductDto()
//                .setName("Помидор")
//                .setAmount(amount)
//                .setMeasurementUnit(MeasurementUnit.kg)
//                .setPrice(price);
//        Product product = productService.save(productDto);
//        RecipeProductDto recipeProductDto = new RecipeProductDto()
//                .setProductId(product.getId())
//                .setAmount(0.3);
//        RecipeDtoPost recipeDtoPost = new RecipeDtoPost()
//                .setDescription("test desc")
//                        .setName("test name")
//                                .setProductIds(List.of(recipeProductDto));
//        String recipeId = recipeService.save(recipeDtoPost).getId();
//        System.out.println("recipe saved with id = " + recipeId);
//        RecipeDtoGet recipeDtoGet = recipeService.getById(recipeId);
//    }
}