package com.example.realestate.repository;

import com.example.realestate.model.ListingFeatures;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingFeatureRepository extends JpaRepository<ListingFeatures, Integer> {

    
}
