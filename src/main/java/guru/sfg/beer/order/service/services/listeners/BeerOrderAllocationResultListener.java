package guru.sfg.beer.order.service.services.listeners;

import guru.sfg.beer.order.service.config.RabbitmqConfig;
import guru.sfg.beer.order.service.services.BeerOrderManager;
import guru.sfg.brewery.model.events.AllocateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 12/3/19.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderAllocationResultListener implements BeerOrderListener{
    private final BeerOrderManager beerOrderManager;

    @RabbitListener(queues = RabbitmqConfig.ALLOCATE_ORDER_RESPONSE_QUEUE)
    public void receiveMessage(AllocateOrderResult result){
        if(!result.getAllocationError() && !result.getPendingInventory()){
            //allocated normally
            beerOrderManager.beerOrderAllocationPassed(result.getBeerOrderDto());
        } else if(!result.getAllocationError() && result.getPendingInventory()) {
            //pending inventory
            beerOrderManager.beerOrderAllocationPendingInventory(result.getBeerOrderDto());
        } else if(result.getAllocationError()){
            //allocation error
            beerOrderManager.beerOrderAllocationFailed(result.getBeerOrderDto());
        }
    }

}
