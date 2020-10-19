package com.example.realestate.controller;

import com.example.realestate.model.CurrentUser;
import com.example.realestate.model.Listing;
import com.example.realestate.model.ListingFeatures;
import com.example.realestate.service.ListingFeatureService;
import com.example.realestate.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;
    private final ListingFeatureService listingFeatureService;

    @GetMapping("/listing/add")
    public String addListingGet(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser){
        if (currentUser != null) {
            modelMap.addAttribute("currentUser", currentUser.getUser());
        }
        List<ListingFeatures> featureList = listingFeatureService.findAll();
        modelMap.addAttribute("features", featureList);
        return "addListing";
    }

    @PostMapping("/listing/add")
    public String addListingPost(@ModelAttribute Listing listing,
                                 @RequestParam("img")MultipartFile multipartFile,
                                 @RequestParam("features") List<Integer> features) throws IOException {

        List<ListingFeatures> allById = listingFeatureService.findAllById(features);
        listing.setFeatureList(allById);
        String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        File file = new File("D:\\upload\\" + picName);
        multipartFile.transferTo(file);
        listing.setPicUrl(picName);
        listingService.save(listing);
        return "redirect:/listing/add";
    }

    @GetMapping("/listing/image")
    public @ResponseBody byte[] userImage (@RequestParam("picUrl")String picUrl) throws IOException {
        InputStream in = new FileInputStream("D:\\upload\\" + picUrl);
        return IOUtils.toByteArray(in);
    }


}
