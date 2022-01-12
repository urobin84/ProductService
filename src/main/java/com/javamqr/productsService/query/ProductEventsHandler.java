package com.javamqr.productsService.query;

import com.javamqr.productsService.core.data.ProductEntity;
import com.javamqr.productsService.core.data.ProductsRepository;
import com.javamqr.productsService.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductEventsHandler {

    private final ProductsRepository productsRepository;

    public ProductEventsHandler(ProductsRepository productsRepository){
        this.productsRepository = productsRepository;
    }

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception exception) throws Exception{
        //log error message
        throw exception;
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException exception){
        //log error message
    }

    @EventHandler
    public void on(ProductCreatedEvent event) throws Exception{

        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);

        try {
            productsRepository.save(productEntity);
        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }

        if(true) throw new Exception("Forcing exceptionin the Event Handler class");

    }
}
