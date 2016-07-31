package com.securesw.shopping.error;



public class ProductsNotFoundException extends NotFoundException {
    public ProductsNotFoundException(String msg){
        super(msg);
    }
}
