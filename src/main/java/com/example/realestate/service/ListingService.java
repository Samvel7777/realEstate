package com.example.realestate.service;

import com.example.realestate.model.Listing;
import com.example.realestate.model.ListingStatus;
import com.example.realestate.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;

    public Listing save(Listing listing) {
        return listingRepository.save(listing);
    }

    public List<Listing> findAllByListingStatus(ListingStatus listingStatus) {
        return listingRepository.findAllByListingStatus(listingStatus.FEATURED);
    }

    public List<Listing> findTop3ByOrderByIdDesc() {
        return listingRepository.findTop3ByOrderByIdDesc();
    }

    public List<Listing> findAllByTitleIgnoreCaseContaining(String keyword) {
        return listingRepository.findAllByTitleIgnoreCaseContaining(keyword);
    }

}
