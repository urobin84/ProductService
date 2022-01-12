package com.javamqr.productsService.command;

import com.javamqr.productsService.core.data.ProductLookupEntity;
import com.javamqr.productsService.core.data.ProductLookupRepository;
import com.javamqr.productsService.core.events.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {

    private final ProductLookupRepository productLookupRepository;

    public ProductLookupEventsHandler(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }

    @EventHandler
    private void on(ProductCreatedEvent event){
        ProductLookupEntity productLookupEntity = new ProductLookupEntity(event.getProductId(), event.getTitle());

        productLookupRepository.save(productLookupEntity);
    }
}
