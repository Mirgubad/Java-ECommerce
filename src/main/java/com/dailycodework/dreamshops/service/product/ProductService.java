package com.dailycodework.dreamshops.service.product;

import com.dailycodework.dreamshops.criteria.ProductFilterCriteria;
import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.dto.ProductDto;
import com.dailycodework.dreamshops.exceptions.ConflictException;
import com.dailycodework.dreamshops.exceptions.NotFoundException;
import com.dailycodework.dreamshops.mapper.ProductMapper;
import com.dailycodework.dreamshops.model.Category;
import com.dailycodework.dreamshops.model.Image;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.repository.CategoryRepository;
import com.dailycodework.dreamshops.repository.ImageRepository;
import com.dailycodework.dreamshops.repository.ProductRepository;
import com.dailycodework.dreamshops.request.AddProductRequest;
import com.dailycodework.dreamshops.request.ProductUpdateRequest;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements  IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private  final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public ProductDto addProduct(AddProductRequest request) {
        // Check if the category exists
        // If yes, set it is the new product category
        // If no, the save it as a new category
        // The set as the new Product category
        String categoryName=request.getCategory().getName();

        if(productIsExist(request.getName(),request.getBrand())){
            throw new ConflictException(request.getBrand() + " " + request.getName() + " Product already exists, you may update this product instead!");
        }

        Category category = Optional.ofNullable(categoryRepository.findByName(categoryName))
                .orElseGet(()->
                        categoryRepository.save(new Category(categoryName)));

        request.setCategory(category);
        Product newProduct= createProduct(request,category);
        return convertToDto(productRepository.save(newProduct));
    }

    private boolean productIsExist(String name,String brand){
        return productRepository.isExistsByNameAndBrand(name,brand);
    }

    private  Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                request.getQuantity(),
                category);
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct,request))
                .map(productRepository ::save)
                .orElseThrow(()->
                        new NotFoundException("Product not found"));
    }

    private  Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setQuantity(request.getQuantity());

        Category category= categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(request.getCategory());
        return existingProduct;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Product not found"));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                ()->{throw new NotFoundException("Product not found");
        });
    }



    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products= productRepository.findAll();
        List<ProductDto> productDto=products.stream()
                .map(ProductMapper::mapToProductDto).collect(Collectors.toList());
        return productDto;
    }


    public List<ProductDto> filterProducts(ProductFilterCriteria criteria) {
        List<Product> products;

        boolean productNameIsEmpty = criteria.getProductName() == null;
        boolean brandNameIsEmpty = criteria.getBrandName() == null;
        boolean categoryNameIsEmpty = criteria.getCategoryName() == null;

        products = productRepository.findAll().stream()
                .filter(product -> (productNameIsEmpty || product.getName().toLowerCase().contains(criteria.getProductName().toLowerCase())))
                .filter(product -> (brandNameIsEmpty || product.getBrand().toLowerCase().contains(criteria.getBrandName().toLowerCase())))
                .filter(product -> (categoryNameIsEmpty || product.getCategory().getName().toLowerCase().contains(criteria.getCategoryName().toLowerCase())))
                .filter(product -> (criteria.getMinPrice() == null || product.getPrice().compareTo(criteria.getMinPrice()) >= 0))
                .filter(product -> (criteria.getMaxPrice() == null || product.getPrice().compareTo(criteria.getMaxPrice()) <= 0))
                .collect(Collectors.toList());



        return products.stream()
                .map(ProductMapper::mapToProductDto)
                .collect(Collectors.toList());
    }

    public Long countProducts(ProductFilterCriteria criteria) {
        return filterProducts(criteria).stream().count();
    }

    private  List<ProductDto> convertedProductsDto(List<Product> products){
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProductDto convertToDto(Product product){
        ProductDto productDto=modelMapper.map(product,ProductDto.class);
        List<Image> images= imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos= images.stream()
                .map(image -> modelMapper.map(image,ImageDto.class))
                .toList();

        productDto.setImages(imageDtos);
        return productDto ;
    }
}


