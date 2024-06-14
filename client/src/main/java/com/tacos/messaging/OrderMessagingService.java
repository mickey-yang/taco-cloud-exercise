package com.tacos.messaging;


import com.tacos.domain.TacoOrder;

public interface OrderMessagingService {
    void sendOrder(TacoOrder order);
}
