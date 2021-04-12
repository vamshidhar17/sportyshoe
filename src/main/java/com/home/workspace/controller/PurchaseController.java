package com.home.workspace.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.home.workspace.service.*;
import com.home.workspace.model.Product;
import com.home.workspace.model.Purchase;
import com.home.workspace.model.PurchaseItem;
import com.home.workspace.service.ProductService;
import com.home.workspace.service.PurchaseItemService;
import com.home.workspace.service.PurchaseService;
import com.home.workspace.model.*;

@Controller
public class PurchaseController {

	@Autowired
	private ProductService productService; 
	
	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private PurchaseItemService purchaseItemService;

	  @RequestMapping(value = "/memberpurchases", method = RequestMethod.GET)
	    public String memberpurchases(ModelMap map, HttpSession session) 
	    {
		  if (session.getAttribute("user_id") == null) {
			  return "login";
		  }
		  long userId = (Long) session.getAttribute("user_id");
		  
		  List<Purchase> list = purchaseService.getAllItemsByUserId(userId);
		  
		  BigDecimal total = new BigDecimal(0.0);
		  // map purchase items to each purchase for display
		  HashMap<Long, String> mapItems = new HashMap<Long, String>();
		  
		  for(Purchase purchase: list) {
			  total = total.add(purchase.getTotal());
			  
			  List<PurchaseItem> itemList = purchaseItemService.getAllItemsByPurchaseId(purchase.getID());
			  StringBuilder sb = new StringBuilder("");
			  for(PurchaseItem item: itemList) {
				  Product product = productService.getProductById(item.getProductId());
				  if (product != null) {
					  sb.append(product.getName() + ", " + 
						  	item.getQty() + " units @" + item.getRate() + " = " 
						  	+ item.getPrice() + "<br>");
				  }
			  }
			  mapItems.put(purchase.getID(), sb.toString());
		  }
		  map.addAttribute("totalAmount", total);
		  map.addAttribute("list", list);
		  map.addAttribute("mapItems", mapItems);
		  map.addAttribute("pageTitle", "SPORTY SHOES - YOUR ORDERS");
		  
	       return "purchases"; 
	    }		  
}