package ru.klingenberg.resipesapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.klingenberg.resipesapi.db.entity.Ingredient;
import ru.klingenberg.resipesapi.db.repository.IngredientRepository;
import ru.klingenberg.resipesapi.dto.IngredientDto;
import ru.klingenberg.resipesapi.dto.PageableResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public Ingredient save(IngredientDto ingredientDto) {
        return ingredientRepository.findByNameIgnoreCase(ingredientDto.getName().trim())
                        .orElseGet(() -> ingredientRepository.save(new Ingredient()
                        .setName(ingredientDto.getName().trim())));
    }

    public Optional<Ingredient> findById(String id) {
        return ingredientRepository.findById(id);
    }

    public Optional<Ingredient> findByIdOrName(String id, String name){
        return ingredientRepository.findFirstByIdOrNameIgnoreCase(id, name);
    }

    public PageableResponse<Ingredient> findAll(int pageNo, int pageSize) {
        Page<Ingredient> page = ingredientRepository.findAll(PageRequest.of(pageNo, pageSize));
        return new PageableResponse<Ingredient>()
                .setElements(page.getContent())
                .setPageNo(pageNo)
                .setPageSize(pageSize)
                .setTotalElements(page.getTotalElements())
                .setTotalPages(page.getTotalPages());
    }

    public List<Ingredient> save(List<IngredientDto> products) {
        List<Ingredient> response = new ArrayList<>();
        for (IngredientDto ingredientDto : products) {
            response.add(save(ingredientDto));
        }
        return response;
    }

    public Page<Ingredient> searchByName(String name, int pageNo, int pageSize) {
        return ingredientRepository.findByNameContainingIgnoreCase(name.trim(), PageRequest.of(pageNo, pageSize));
    }

    public Optional<Ingredient> findByName(String name){
        return ingredientRepository.findByNameIgnoreCase(name.trim());
    }
}
