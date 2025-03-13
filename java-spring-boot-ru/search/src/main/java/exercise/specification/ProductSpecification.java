package exercise.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;

import java.nio.file.Path;

// BEGIN
@Component
public class ProductSpecification {
    public Specification<Product> build(ProductParamsDTO params) {
        return titleContains(params.getTitleCont())
                .and(priceGreaterThan(params.getPriceGt()))
                .and(priceLesserThan(params.getPriceLt()))
                .and(categoryEquals(params.getCategoryId()))
                .and(ratingGreaterThan(params.getRatingGt()));
    }

    private Specification<Product> titleContains(String text) {
        return ((root, query, criteriaBuilder) ->
                text == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.like(root.get("title"), text));
    }

    private Specification<Product> categoryEquals(Long category) {
        return (root, query, criteriaBuilder) ->
                category == null ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("category").get("id"), category);
    }

    private Specification<Product> priceGreaterThan(Integer value) {
        int v = null == value ? 0 : value;
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("price"), v);
    }

    private Specification<Product> priceLesserThan(Integer value) {
        int v = null == value ? Integer.MAX_VALUE : value;
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("price"), v);
    }

    private Specification<Product> ratingGreaterThan(Double value) {
        double v = null == value ? 0 : value;
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), v);
    }
}
// END
