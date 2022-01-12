package com.javamqr.productsService.query;

import com.javamqr.productsService.core.data.ProductEntity;
import com.javamqr.productsService.core.data.ProductsRepository;
import com.javamqr.productsService.query.rest.ProductRestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductsQueryHandler {

    private final ProductsRepository productsRepository;

    public ProductsQueryHandler(ProductsRepository productsRepository){
        this.productsRepository = productsRepository;
    }

    @QueryHandler
    public List<ProductRestModel> findProducts(FindProductsQuery findProductsQuery){

        List<ProductRestModel> productRest = new ArrayList<>();

        List<ProductEntity> storedProducts = productsRepository.findAll();

        for(ProductEntity productEntity: storedProducts){
            ProductRestModel productRestModel = new ProductRestModel();
            BeanUtils.copyProperties(productEntity, productRestModel);
            productRest.add(productRestModel);
        }

        return productRest;

    }
}
