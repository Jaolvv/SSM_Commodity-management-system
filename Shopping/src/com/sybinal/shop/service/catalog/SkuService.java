package com.sybinal.shop.service.catalog;

import java.util.List;
import java.util.Map;

import com.sybinal.shop.common.AjaxResult;
import com.sybinal.shop.model.ProductRelation;
import com.sybinal.shop.model.Sku;

public interface SkuService {

	List<Sku> getSkuDetailsList(Map<String, Object> reqMap);

	Sku getSkyById(int skuId);

	AjaxResult saveSku(ProductRelation productRelation);
}
