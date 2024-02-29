package com.project.SupplySpy.repositories.products;

import com.project.SupplySpy.classes.Product;

public interface ProductRepository {
    Product getProductByProductId(int productId);
}