package com.mymart.ecommerce_backend.service.Imp;

import com.mymart.ecommerce_backend.constants.AppConstants;
import com.mymart.ecommerce_backend.dto.ProductDto;
import com.mymart.ecommerce_backend.entities.ProductCategoryEntity;
import com.mymart.ecommerce_backend.entities.ProductEntity;
import com.mymart.ecommerce_backend.mapper.ProductMapper;
import com.mymart.ecommerce_backend.repos.ProductCategoryRepo;
import com.mymart.ecommerce_backend.repos.ProductRepo;
import com.mymart.ecommerce_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImp implements ProductService {

    @Value("${app.images.upload-dir}")
    private String imageUploadDir;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductCategoryRepo productCategoryRepo;

    @Override
    public ProductDto createProduct(
            Integer categoryId,
            ProductDto productDto,
            MultipartFile productImage) throws Exception {

        UUID uuid = UUID.randomUUID();

        String originalFilename =
                uuid.toString() + "_"
                        + productImage.getOriginalFilename();

        Path filePath =
                Paths.get(imageUploadDir, originalFilename);

        // create uploading dir if not available
        if (!Files.exists(filePath.getParent())) {

            try {
                Files.createDirectories(filePath.getParent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // save image
        Files.copy(
                productImage.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        productDto.setImageUrl(filePath.toString());

        ProductEntity productEntity =
                ProductMapper.toEntity(productDto);

        ProductCategoryEntity category =
                productCategoryRepo.findById(categoryId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product category not found with id: "
                                                + categoryId
                                )
                        );

        productEntity.setCategory(category);

        ProductEntity savedEntity =
                productRepo.save(productEntity);

        return ProductMapper.toDto(savedEntity);
    }

    @Override
    public ProductDto updateProduct(
            Integer productId,
            ProductDto productDto,
            MultipartFile productImage) throws Exception {

        ProductEntity productEntity =
                productRepo.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found with id: "
                                                + productId
                                )
                        );

        UUID uuid = UUID.randomUUID();

        String originalFilename =
                uuid.toString() + "_"
                        + productImage.getOriginalFilename();

        Path filePath =
                Paths.get(imageUploadDir, originalFilename);


        if (!Files.exists(filePath.getParent())) {

            try {
                Files.createDirectories(filePath.getParent());
            } catch (IOException e) {
                throw new RuntimeException(
                        "Failed to save product image",
                        e
                );
            }
        }

        // save image
        Files.copy(
                productImage.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        productEntity.setImageUrl(filePath.toString());
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setUnitsStock(productDto.getUnitsStock());
        productEntity.setUnitPrice(productDto.getUnitPrice());

        ProductEntity updatedEntity =
                productRepo.save(productEntity);

        return ProductMapper.toDto(updatedEntity);
    }

    @Override
    public List<ProductDto> getAllProductsByCategoryId(Integer categoryId) {
        return productRepo.findByCategoryCategoryId(categoryId).stream().map(ProductMapper::toDto).toList();
    }

    @Override
    public ProductDto getProductById(Integer productId) {
        ProductEntity productEntity = productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        return ProductMapper.toDto(productEntity);
    }

    @Override
    public List<ProductDto> getProductsByName(String productName) {

        return productRepo
                .findByNameContainingIgnoreCase(productName)
                .stream()
                .map(ProductMapper::toDto)
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  .toList();
    }

    @Override
    public ProductDto deleteProduct(Integer productId) {

        ProductEntity productEntity =
                productRepo.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found with id: "
                                                + productId)
                        );

        productEntity.setActive(AppConstants.NO);

        ProductEntity savedEntity =
                productRepo.save(productEntity);

        return ProductMapper.toDto(savedEntity);
    }
}
