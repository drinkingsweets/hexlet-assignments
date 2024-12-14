package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Category;
import exercise.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

// BEGIN
@Mapper(
    uses = { JsonNullableMapper.class, ReferenceMapper.class },
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ProductMapper {
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "categoryId", source = "category.id")
    public abstract ProductDTO map(Product product);

    @Mapping(target = "category.id", source = "categoryId")
    public abstract Product map(ProductCreateDTO dto);

    @Mapping(target = "category", expression = "java(updateCategory(dto.getCategoryId(), product.getCategory()))")
    public abstract Product update(ProductUpdateDTO dto, @MappingTarget Product product);

    protected Category updateCategory(Long newCategoryId, Category currentCategory) {
        if (newCategoryId == null || (currentCategory != null && currentCategory.getId() == newCategoryId)) {
            return currentCategory; // Keep existing category if unchanged
        }
        // Create a new reference for the updated category
        var category = new Category();
        category.setId(newCategoryId);
        return category;
    }
}
// END
