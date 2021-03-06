package com.javamqr.productsService.command.interceptor;

import com.javamqr.productsService.command.CreateProductCommand;
import com.javamqr.productsService.core.data.ProductLookupEntity;
import com.javamqr.productsService.core.data.ProductLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);
    private final ProductLookupRepository productLookupRepository;

    public CreateProductCommandInterceptor(ProductLookupRepository productLookupRepository) {
        this.productLookupRepository = productLookupRepository;
    }


    @Override
    public CommandMessage<?> handle(CommandMessage<?> message) {
        return MessageDispatchInterceptor.super.handle(message);
    }

    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(List<? extends CommandMessage<?>> list) {
        return (index, command) -> {

            LOGGER.info("Interceptor command " + command.getPayloadType());

            if (CreateProductCommand.class.equals(command.getPayloadType())) {

                CreateProductCommand createProductCommand = (CreateProductCommand) command.getPayload();

                ProductLookupEntity productLookupEntity = productLookupRepository.findByProductIdOrTitle(createProductCommand.getProductId(), createProductCommand.getTitle());

                if (productLookupEntity != null) {
                    throw new IllegalStateException(
                            String.format("Product with productId %s or title %s already exist", createProductCommand.getProductId(), createProductCommand.getTitle())
                    );
                }
            }
            return command;
        };
    }
}
