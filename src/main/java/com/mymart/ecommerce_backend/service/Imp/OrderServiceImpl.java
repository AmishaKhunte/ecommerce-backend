package com.mymart.ecommerce_backend.service.Imp;

import com.mymart.ecommerce_backend.dto.*;
import com.mymart.ecommerce_backend.entities.OrderEntity;
import com.mymart.ecommerce_backend.entities.OrderItemsEntity;
import com.mymart.ecommerce_backend.entities.ShippingAddressEntity;
import com.mymart.ecommerce_backend.entities.UserEntity;
import com.mymart.ecommerce_backend.mapper.AddressMapper;
import com.mymart.ecommerce_backend.mapper.OrderItemsMapper;
import com.mymart.ecommerce_backend.mapper.OrderMapper;
import com.mymart.ecommerce_backend.repos.AddressRepo;
import com.mymart.ecommerce_backend.repos.OrderItemsRepo;
import com.mymart.ecommerce_backend.repos.OrderRepo;
import com.mymart.ecommerce_backend.repos.UserRepo;
import com.mymart.ecommerce_backend.service.OrderService;
import com.mymart.ecommerce_backend.service.ProductService;
import com.mymart.ecommerce_backend.service.RazorPayService;
import com.razorpay.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemsRepo orderItemsRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private RazorPayService razorPayService;

    @Autowired
    private ProductService productService;

    @Override
    public PurchaseOrderResponseDto createOrder(
            PurchaseOrderRequestDto orderRequestDto
    ) throws Exception {

        UserDto userDto = orderRequestDto.getUser();

        UserEntity userEntity =
                userRepo.findByEmail(userDto.getEmail());

        ShippingAddressEntity addressEntity = null;

        ShippingAddressDto addressDto =
                orderRequestDto.getShippingAddressDto();

        if (addressDto.getAddrId() == null) {

            addressEntity =
                    AddressMapper.doToEntity(addressDto);

            addressEntity.setUser(userEntity);

            addressRepo.save(addressEntity);

        } else {

            addressEntity =
                    addressRepo
                            .findById(addressDto.getAddrId())
                            .orElse(null);
        }

        OrderEntity orderEntity =
                OrderMapper.toEntity(
                        orderRequestDto.getOrder()
                );

        String razorpayOrderId =
                razorPayService.createRazorpayOrder(
                        orderEntity.getTotalPrice()
                );

        orderEntity.setOrderTrackingNum(
                generateOrderTrackingNumber()
        );

        orderEntity.setOrderStatus("CREATED");

        orderEntity.setPaymentStatus("PENDING");

        orderEntity.setRazorpayOrderId(
                razorpayOrderId
        );

        orderEntity.setUser(userEntity);

        orderEntity.setShippingAddress(addressEntity);

        OrderEntity savedOrder =
                orderRepo.save(orderEntity);

        List<OrderItemsDto> orderItems =
                orderRequestDto.getOrderItems();

        for (OrderItemsDto itemDto : orderItems) {

            Integer productId =
                    itemDto.getProductId();

            ProductDto productById =
                    productService.getProductById(
                            productId
                    );

            OrderItemsEntity itemsEntity =
                    OrderItemsMapper.dtoToEntity(itemDto);

            itemsEntity.setImageUrl(
                    productById.getImageUrl()
            );

            itemsEntity.setOrder(savedOrder);

            orderItemsRepo.save(itemsEntity);
        }

        PurchaseOrderResponseDto responseDto =
                new PurchaseOrderResponseDto();

        responseDto.setOrderId(
                savedOrder.getOrderId()
        );

        responseDto.setRazorpayOrderId(
                razorpayOrderId
        );

        responseDto.setOrderStatus(
                savedOrder.getOrderStatus()
        );

        responseDto.setPaymentStatus(
                savedOrder.getPaymentStatus()
        );

        responseDto.setOrderTrackingNumber(
                savedOrder.getOrderTrackingNum()
        );

        return responseDto;
    }

    @Override
    public OrderDto updateOrder(
            Integer orderId,
            OrderDto orderDto
    ) {

        Optional<OrderEntity> byId =
                orderRepo.findById(orderId);

        if (byId.isPresent()) {

            OrderEntity orderEntity =
                    byId.get();

            orderEntity.setOrderStatus(
                    orderDto.getOrderStatus()
            );

            orderEntity.setPaymentStatus(
                    orderDto.getPaymentStatus()
            );

            OrderEntity updatedOrder =
                    orderRepo.save(orderEntity);

            return OrderMapper.toDto(
                    updatedOrder
            );
        }

        return null;
    }

    @Override
    public OrderDto cancelOrder(
            Integer orderId
    ) throws Exception {

        Optional<OrderEntity> byId =
                orderRepo.findById(orderId);

        if (byId.isPresent()) {

            OrderEntity orderEntity =
                    byId.get();

            razorPayService.refundPayment(
                    orderEntity.getRazorpayPaymentId(),
                    orderEntity.getTotalPrice()
            );

            orderEntity.setOrderStatus(
                    "CANCELLED"
            );

            orderEntity.setPaymentStatus(
                    "REFUND-IN-PROGRESS"
            );

            OrderEntity cancelledOrder =
                    orderRepo.save(orderEntity);

            return OrderMapper.toDto(
                    cancelledOrder
            );
        }

        return null;
    }

    @Override
    public OrderDto trackOrder(
            Integer orderId
    ) {

        Optional<OrderEntity> byId =
                orderRepo.findById(orderId);

        if (byId.isPresent()) {

            OrderEntity orderEntity =
                    byId.get();

            return OrderMapper.toDto(
                    orderEntity
            );
        }

        return null;
    }

    @Override
    public List<OrderDto> getCustomerOrders(
            String email
    ) {

        return orderRepo
                .findByUserEmail(email)
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    private String generateOrderTrackingNumber() {

        return UUID.randomUUID().toString();
    }
}