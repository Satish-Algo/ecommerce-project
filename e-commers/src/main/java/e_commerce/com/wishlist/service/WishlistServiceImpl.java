package e_commerce.com.wishlist.service;

import e_commerce.com.common.exception.BadRequestException;
import e_commerce.com.common.exception.ResourceNotFoundException;
import e_commerce.com.product.entity.Product;
import e_commerce.com.product.repository.ProductRepository;
import e_commerce.com.user.entity.User;
import e_commerce.com.user.repository.UserRepository;
import e_commerce.com.wishlist.dto.WishlistRequest;
import e_commerce.com.wishlist.dto.WishlistResponse;
import e_commerce.com.wishlist.entity.Wishlist;
import e_commerce.com.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    @Override
    public WishlistResponse addToWishlist(WishlistRequest request) {

        User user = getCurrentUser();

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found")
                );


        if (wishlistRepository.existsByUserAndProduct(user, product)) {
            throw new BadRequestException(
                    "Product already exists in wishlist"
            );
        }


        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .product(product)
                .build();


        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        return mapToResponse(savedWishlist);
    }



    @Override
    public List<WishlistResponse> getWishlist() {

        User user = getCurrentUser();

        return wishlistRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }



    @Override
    public void removeFromWishlist(Long productId) {

        User user = getCurrentUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found")
                );


        Wishlist wishlist = wishlistRepository
                .findByUserAndProduct(user, product)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Wishlist item not found"
                        )
                );


        wishlistRepository.delete(wishlist);

    }



    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found")
                );
    }



    private WishlistResponse mapToResponse(Wishlist wishlist) {

        Product product = wishlist.getProduct();

        return WishlistResponse.builder()
                .wishlistId(wishlist.getId())
                .productId(product.getId())
                .productName(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();

    }

}