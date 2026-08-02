package e_commerce.com.wishlist.service;

import e_commerce.com.wishlist.dto.WishlistRequest;
import e_commerce.com.wishlist.dto.WishlistResponse;

import java.util.List;

public interface WishlistService {

    WishlistResponse addToWishlist(
            WishlistRequest request
    );

    List<WishlistResponse> getWishlist();

    void removeFromWishlist(
            Long productId
    );

}