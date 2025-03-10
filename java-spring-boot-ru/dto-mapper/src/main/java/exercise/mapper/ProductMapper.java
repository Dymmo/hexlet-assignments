package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.*;

import static org.mapstruct.ReportingPolicy.IGNORE;

// BEGIN
@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = IGNORE
)
public abstract class ProductMapper {
    @Mapping(target = "name", source = "title")
    @Mapping(target = "barcode", source = "vendorCode")
    @Mapping(target = "cost", source = "price")
    public abstract Product map(ProductCreateDTO dto);

    @Mapping(target = "cost", source = "price")
    public abstract Product map(ProductUpdateDTO dto, @MappingTarget Product product);

    @Mapping(target = "title", source = "name")
    @Mapping(target = "vendorCode", source = "barcode")
    @Mapping(target = "price", source = "cost")
    public abstract ProductDTO map(Product product);
}
// END
