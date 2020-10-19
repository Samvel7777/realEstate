package com.example.realestate.service;

import com.example.realestate.model.ListingFeatures;
import com.example.realestate.repository.ListingFeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingFeatureService {

    private final ListingFeatureRepository listingFeatureRepository;

    public List<ListingFeatures> findAll() {
        return listingFeatureRepository.findAll();
    }

    public List<ListingFeatures> findAllById(List<Integer> features) {
        return listingFeatureRepository.findAllById(features);
    }
}
