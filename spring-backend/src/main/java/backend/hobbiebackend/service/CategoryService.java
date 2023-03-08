package backend.hobbiebackend.service;

import backend.hobbiebackend.entities.Category;
import backend.hobbiebackend.entities.enums.CategoryNameEnum;

import java.util.List;

public interface CategoryService {
    Category findByName(CategoryNameEnum category);

    List<Category> initCategories();
}
