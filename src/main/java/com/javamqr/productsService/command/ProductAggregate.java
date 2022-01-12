package com.javamqr.productsService.command;

import com.javamqr.productsService.core.events.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate(){

    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) throws IllegalAccessException {
        //Validate Create Product Command

        if(createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalAccessException("Price cannot be less or equal than zero");
        }

        if(createProductCommand.getTitle() == null
        || createProductCommand.getTitle().isBlank()){
            throw new IllegalAccessException("Title cannot be empty");
        }

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();

        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

        AggregateLifecycle.apply(productCreatedEvent);

//        if(true) throw new Exception("An error tookk place in the CreateProductCommand @CommandHandler method");

    }

    @CommandHandler
    public void handle(ReserveProductCommand reserveProductCommand){

    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        this.productId = productCreatedEvent.getProductId();
        this.price = productCreatedEvent.getPrice();
        this.title = productCreatedEvent.getTitle();
        this.quantity = productCreatedEvent.getQuantity();
    }
}
