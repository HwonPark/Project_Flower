package project.flower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.flower.domain.admin.Business;
import project.flower.domain.order.FlowerOrder;
import project.flower.domain.order.FlowerOrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<FlowerOrderItem, Long> {

    List<FlowerOrderItem> findAllByFlowerOrder(FlowerOrder order);

    List<FlowerOrderItem> findAllByBusiness(Business business);

    @Override
    Optional<FlowerOrderItem> findById(Long aLong);

    List<FlowerOrderItem> findAllByBusinessAndFlowerOrder(Business business, FlowerOrder order);
}
