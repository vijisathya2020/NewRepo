import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { CartItem } from '../models/cart-item.model';
import { Product } from "../models/product.model";

// const baseUrl = "http://localhost:8080/cart";
@Injectable({
  providedIn: 'root'
})

/*
  EVERY FUNCTION MUST ASSIGN
  NEXT VALUE TO THE WATCHER SUBJECT
  TO CONTINUOUSLY UPDATE THE CART VIEW
*/

export class CartService {
  private watcher = new Subject();
  length:number = this.cartLength();

  cartLength(){
    const cart = localStorage.getItem("cart");
    let cartLength = 0;
    if (cart){
      const cartJSON = JSON.parse(cart);
      cartLength = cartJSON.length;
    }

    return cartLength;
  }

  constructor(private http: HttpClient) {}

  activateWatcher(): Observable<any> {
    return this.watcher.asObservable();
  }

  addProduct(data: Product) {
    const cartItem = { product: data, amt: 1 };
    const cart = localStorage.getItem('cart');
    if (cart) {
      let cartJSON = JSON.parse(cart);
      let index = -1;
      for (let i = 0; i < cartJSON.length; i++) {
        if (cartJSON[i].product.productId === data.productId) {
          index = i;
        }
      };
      //This runs if the item already exists in the cartItem array.
      if (index > -1) {
        //cartItem.amt += 1;
        cartJSON[index].amt += 1;
      }
      else {
        cartJSON.push(cartItem);
        this.length+=1;
      }
      this.watcher.next(cartJSON)

      const cartString = JSON.stringify(cartJSON);
      localStorage.setItem("cart", cartString);
    }
    else {
      const startCart = [cartItem];
      this.watcher.next(startCart);
      this.length+=1;

      localStorage.setItem('cart', JSON.stringify(startCart));
    }
  }

  updateCart(data: CartItem[]) {
    if (data) {
      localStorage.setItem("cart", JSON.stringify(data));
      this.watcher.next(data)
    }
  }

  removeProduct(data: Product) {
    const cart = localStorage.getItem('cart');
    if (cart) {
      let cartJSON = JSON.parse(cart);
      cartJSON = cartJSON.filter((item: { product: Product, amt: number }) => item.product.productId !== data.productId);
      this.watcher.next(cartJSON)

      this.length-=1;

      const cartString = JSON.stringify(cartJSON);
      localStorage.setItem("cart", cartString);
    } else {
      console.log("cart is empty!")
    }

  }

  clearCart() {
    localStorage.removeItem('cart');
    console.log("cart cleared!");
    this.length=0;
  }

  // updateProductAmount(amount:number){
  //   const cart = localStorage.getItem('cart');
  //   if(cart){
  //     let cartJSON = JSON.parse(cart);
  //     cartJSON = cartJSON.filter((item: { product: Product }) => item.product.productId !== data.productId);
  //     const cartString = JSON.stringify(cartJSON);
  //     localStorage.setItem("cart", cartString);
  //   }
  // }

  viewItems() {
    let cartString = localStorage.getItem('cart')
    let cartList: CartItem[] = [];

    if (cartString) {
      let cartJSON: CartItem[] = JSON.parse(cartString);
      cartList = cartJSON;
    }
    // return cartObj;
    this.watcher.next(cartList);
  }
}
