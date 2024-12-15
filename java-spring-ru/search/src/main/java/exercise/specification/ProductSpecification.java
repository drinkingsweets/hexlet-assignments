package exercise.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;

import java.util.ArrayList;
import java.util.List;

// BEGIN
@Component
public class ProductSpecification {
    public Specification<Product> build(ProductParamsDTO dto) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (dto.getTitleCont() != null && !dto.getTitleCont().isEmpty()) {
                predicates.add(
                    criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + dto.getTitleCont().toLowerCase() + "%"
                    )
                );
            }

            if (dto.getCategoryId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), dto.getCategoryId()));
            }

            if (dto.getPriceLt() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), dto.getPriceLt()));
            }

            if (dto.getPriceGt() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), dto.getPriceGt()));
            }

            if (dto.getRatingGt() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), dto.getRatingGt()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

    }
}
// END
