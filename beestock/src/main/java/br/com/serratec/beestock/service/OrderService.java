package br.com.serratec.beestock.service;

import java.time.LocalDate;
import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.serratec.beestock.dto.OrderDTO;
import br.com.serratec.beestock.exceptions.CompletedOrderException;
import br.com.serratec.beestock.exceptions.InsufficientQuantityException;
import br.com.serratec.beestock.exceptions.NotFindException;
import br.com.serratec.beestock.model.Availability;
import br.com.serratec.beestock.model.OrderModel;
import br.com.serratec.beestock.model.OrderProduct;
import br.com.serratec.beestock.model.OrderStatus;
import br.com.serratec.beestock.model.Product;
import br.com.serratec.beestock.repository.OrderRepository;
import br.com.serratec.beestock.repository.ProductRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public List<OrderDTO> listAll() {
        List<OrderModel> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        orders.forEach((o) -> {
            orderDTOs.add(new OrderDTO(o));
        });
        return orderDTOs;
    }

    @Transactional
    public List<OrderModel> findAll() {
        return orderRepository.findAll();
    }

    @Transactional
    public OrderDTO findById(Integer id) throws NotFindException {
        Optional<OrderModel> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return new OrderDTO(order.get());
        }
        throw new NotFindException("Pedido não encontrado");
    }

    @Transactional
    public OrderDTO addOrder(List<OrderProduct> orderProducts) throws NotFindException {
        OrderModel order = new OrderModel();
        List<OrderProduct> newOrderProducts = new ArrayList<>();
        Double total = 0.0;
        order.setUser(userService.userLogged());
        order.setDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.INCONCLUIDO);
        OrderModel newOrder = orderRepository.save(order);
        for (OrderProduct orderProduct : orderProducts) {
            newOrder.setCostumer(orderProduct.getOrder().getCostumer());
            orderProduct.setOrder(newOrder);
            orderProduct.setProduct(productRepository.findByCodeSKU(orderProduct.getProduct().getCodeSKU()).get());
            orderProduct.setUnitPrice(orderProduct.getProduct().getPrice());
            orderProduct.setSubtotal((orderProduct.getUnitPrice() - orderProduct.getDiscount()) * orderProduct.getQuantity());
            orderProductService.addOrderProduct(orderProduct);
            total += orderProduct.getSubtotal();
            newOrderProducts.add(orderProduct);
        }
        newOrder = addOrderProduct(newOrder.getId());
        newOrder.setOrdersProducts(newOrderProducts);
        newOrder.setTotalPurchase(total);
        orderRepository.save(newOrder);
        return new OrderDTO(newOrder);
    }

    private OrderModel addOrderProduct(Integer id) throws NotFindException {
        Optional<OrderModel> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new NotFindException("Pedido não encontrado");
        }
        return order.get();
    }
    @Transactional
    /**
     * método que finaliza um pedido
     * @param id
     * @return
     * @throws NotFindException
     * @throws InsufficientQuantityException
     */
    public OrderDTO finalizeOrder(Integer id) throws NotFindException, InsufficientQuantityException {
        Optional<OrderModel> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new NotFindException("Pedido não encontrado");
        }
        checkCart(order.get());
        order.get().setOrderStatus(OrderStatus.CONCLUIDO);
        orderRepository.save(order.get());
        return new OrderDTO(order.get());
    }
    /**
     * método que atualiza a quantidade de produtos no estoque
     * @param order
     * @return
     * @throws InsufficientQuantityException
     */
    private void checkCart(OrderModel order) throws InsufficientQuantityException{
        List<OrderProduct> orderProducts = order.getOrdersProducts();
        for (OrderProduct orderProduct : orderProducts) {
            setQuantity(orderProduct);
        }
    }

    /**
     * método que verifica se a quantidade comprada não excede a quantidade de estoque
     * @param currentQuantity
     * @param quantityPurchased
     * @return
     * @throws InsufficientQuantityException
     */
    private void setQuantity(OrderProduct orderProduct) throws InsufficientQuantityException{
        Integer buyQuantity=orderProduct.getQuantity();
        Integer currentQuantity = orderProduct.getProduct().getCurrentQuantity();
        Integer newCurrentQuantity= currentQuantity - buyQuantity;
        if(newCurrentQuantity<0){
            throw new InsufficientQuantityException("quantidade em estoque do produto"+orderProduct.getProduct().getName()+"é insuficiente");
        } else if(newCurrentQuantity == 0){
            orderProduct.getProduct().setAvailability(Availability.INDISPONIVEL);
            orderProduct.getProduct().setCurrentQuantity(newCurrentQuantity);
            productRepository.save(orderProduct.getProduct());
        }else{
            orderProduct.getProduct().setCurrentQuantity(newCurrentQuantity);
            productRepository.save(orderProduct.getProduct());
        }
    }

    /**
     * metodo que atualiza um pedido não finalizado
     * @param order
     * @return
     * @throws CompletedOrderException
     * @throws NotFindException
     */
    public OrderDTO editOrder(List<OrderProduct> orderProducts, Integer id) throws CompletedOrderException, NotFindException {
        Optional<OrderModel> o = orderRepository.findById(id);
        Double total = 0.0;
        List<OrderProduct> newOrderProducts = new ArrayList<>();
        if (o.isPresent() && o.get().getOrderStatus() == OrderStatus.CONCLUIDO) {
            throw new CompletedOrderException("Pedido finalizado não pode ser alterado");
        } else if (o.isEmpty()) {
            throw new NotFindException("Pedido não encontrado");
        }
        OrderModel newOrder = o.get();
        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.setOrder(newOrder);
            orderProduct.setProduct(productRepository.findByCodeSKU(orderProduct.getProduct().getCodeSKU()).get());
            orderProduct.setUnitPrice(orderProduct.getProduct().getPrice());
            orderProduct.setSubtotal((orderProduct.getUnitPrice() - orderProduct.getDiscount()) * orderProduct.getQuantity());
            orderProductService.addOrderProduct(orderProduct);
            total += orderProduct.getSubtotal();
            newOrderProducts.add(orderProduct);
        }
        newOrder.setTotalPurchase(total);
        orderProductService.romoveOrderproduct(newOrder.getOrdersProducts());
        newOrder.setOrdersProducts(newOrderProducts);
        orderRepository.save(newOrder);
        return new OrderDTO(newOrder);
    }

    /**cancela o pedido */
    public void cancelOrder (Integer id) throws NotFindException, CompletedOrderException{
        Optional<OrderModel> order = orderRepository.findById(id);
        if(order.isEmpty()){
            throw new NotFindException("Pedido não encontrado");
        }else if(order.get().getOrderStatus()== OrderStatus.CONCLUIDO){
            throw new CompletedOrderException("Não é possível cancelar um pedido finalizado");
        }else{
            orderRepository.deleteById(id);
        }
    }

    /**devolve o pedido */
    public void giveBackOrder(Integer id,OrderModel order) throws NotFindException, CompletedOrderException{
        Optional<OrderModel> newOrder = orderRepository.findById(id);
        if(newOrder.isEmpty()){
            throw new NotFindException("Pedido não encontrado");
        }else if(newOrder.get().getOrderStatus()==OrderStatus.INCONCLUIDO || newOrder.get().getOrderStatus()==OrderStatus.DEVOLVIDO){
            throw new CompletedOrderException("Pedidos que não estiverem concluidos ou estiverem devolvidos não podem ser devolvidos");
        }else{
            giveBackProduct(newOrder.get());
            newOrder.get().setOrderStatus(OrderStatus.DEVOLVIDO);
            newOrder.get().setReturnDate(LocalDate.now());
            newOrder.get().setReturnObs(order.getReturnObs());
            orderRepository.save(newOrder.get());
        }
    }

    /**atualiza a quantidade em estoque com base na devolucao do pedido */
    private void  giveBackProduct(OrderModel order){
        List<OrderProduct> orderProducts= order.getOrdersProducts();
        Integer buyQuantity= 0;
        Integer currentQuantity=0;
        Integer newCurrentQuantity=0;
        Product product = new Product();
        for (OrderProduct orderProduct : orderProducts) {
            product = orderProduct.getProduct();
            buyQuantity = orderProduct.getQuantity();
            currentQuantity=product.getCurrentQuantity();
            newCurrentQuantity=currentQuantity+buyQuantity;
            product.setCurrentQuantity(newCurrentQuantity);
            productRepository.save(product);
        }
    }
}
