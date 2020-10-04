package com.example.realestate.controller;

import com.example.realestate.model.CurrentUser;
import com.example.realestate.model.Listing;
import com.example.realestate.model.ListingFeatures;
import com.example.realestate.repository.ListingFeatureRepository;
import com.example.realestate.repository.ListingRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Controller
public class ListingController {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private ListingFeatureRepository listingFeatureRepository;

    @GetMapping("/listing/add")
    public String addListingGet(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser){
        if (currentUser != null) {
            modelMap.addAttribute("currentUser", currentUser.getUser());
        }
        List<ListingFeatures> featureList = listingFeatureRepository.findAll();
        modelMap.addAttribute("features", featureList);
        return "addListing";
    }

    @PostMapping("/listing/add")
    public String addListingPost(@ModelAttribute Listing listing,
                                 @RequestParam("img")MultipartFile multipartFile,
                                 @RequestParam("features") List<Integer> features) throws IOException {

        List<ListingFeatures> allById = listingFeatureRepository.findAllById(features);
        listing.setFeatureList(allById);
        String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        File file = new File("D:\\upload\\" + picName);
        multipartFile.transferTo(file);
        listing.setPicUrl(picName);
        listingRepository.save(listing);
        return "redirect:/listing/add";
    }

    @GetMapping("/listing/image")
    public @ResponseBody byte[] userImage (@RequestParam("picUrl")String picUrl) throws IOException {
        InputStream in = new FileInputStream("D:\\upload\\" + picUrl);
        return IOUtils.toByteArray(in);
    }


}
