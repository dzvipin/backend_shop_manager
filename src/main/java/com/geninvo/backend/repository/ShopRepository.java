package com.geninvo.backend.repository;

import com.geninvo.backend.modal.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    @Query(value = "select * from shop s where address IN " +
            "(SELECT id FROM address a where 6371 *acos(cos(radians(:lat))*cos(radians(a.latitude))*cos(radians(a.longitude)-" +
            "radians(:lng))+sin(radians(:lat))*sin(radians(a.latitude))) < :distance) or s.name like %:shopName%", nativeQuery = true)
    Page<Shop> findShopsByNameAndLocation(
            @Param("shopName") String shopName,
            @Param("lat") Double lat,
            @Param("lng") Double lng,
            @Param("distance") Integer distance, Pageable pageable);

}
