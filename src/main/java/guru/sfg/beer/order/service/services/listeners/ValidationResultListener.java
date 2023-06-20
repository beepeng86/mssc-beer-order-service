package guru.sfg.beer.order.service.services.listeners;

import guru.sfg.beer.order.service.config.RabbitmqConfig;
import guru.sfg.beer.order.service.services.BeerOrderManager;
import guru.sfg.brewery.model.events.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by jt on 12/2/19.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ValidationResultListener implements BeerOrderListener {

    private final BeerOrderManager beerOrderManager;

    @RabbitListener(queues = RabbitmqConfig.VALIDATE_ORDER_RESPONSE_QUEUE)
    public void receiveMessage(ValidateOrderResult result){
        final UUID beerOrderId = result.getOrderId();

        log.debug("Validation Result for Order Id: " + beerOrderId);

        beerOrderManager.processValidationResult(beerOrderId, result.getIsValid());
    }
}
