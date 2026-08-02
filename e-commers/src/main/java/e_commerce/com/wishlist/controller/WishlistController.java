package e_commerce.com.wishlist.controller;

import e_commerce.com.wishlist.dto.WishlistRequest;
import e_commerce.com.wishlist.dto.WishlistResponse;
import e_commerce.com.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;


    @PostMapping
    public ResponseEntity<WishlistResponse> addToWishlist(
            @RequestBody WishlistRequest request
    ) {

        return ResponseEntity.ok(
                wishlistService.addToWishlist(request)
        );

    }


    @GetMapping
    public ResponseEntity<List<WishlistResponse>> getWishlist() {

        return ResponseEntity.ok(
                wishlistService.getWishlist()
        );

    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFromWishlist(
            @PathVariable Long productId
    ) {

        wishlistService.removeFromWishlist(productId);

        return ResponseEntity.ok(
                "Product removed from wishlist successfully."
        );

    }

}