package com.sybinal.shop.service.catalog;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sybinal.shop.common.AjaxResult;
import com.sybinal.shop.mapper.ProductOptionInfoMapper;
import com.sybinal.shop.mapper.SkuMapper;
import com.sybinal.shop.model.ProductOptionInfo;
import com.sybinal.shop.model.ProductOptionInfoExample;
import com.sybinal.shop.model.ProductRelation;
import com.sybinal.shop.model.Sku;
import com.sybinal.shop.model.SkuExample;

@Service
public class SkuServiceImpl implements SkuService {

	@Autowired
	SkuMapper skuMapper;

	@Autowired
	ProductOptionInfoMapper productOptionInfoMapper;

	@Override
	public List<Sku> getSkuDetailsList(Map<String, Object> reqMap) {
		if (reqMap.get("productId") == null)
			return null;
		SkuExample example = new SkuExample();
		SkuExample.Criteria criteria = example.createCriteria();
		criteria.andProductIdEqualTo((int) reqMap.get("productId"));
		return skuMapper.selectByExampleWithBLOBs(example);
	}

	public Sku getSkyById(int skuId) {
		SkuExample example = new SkuExample();
		SkuExample.Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(skuId);
		if (skuMapper.selectByExample(example) != null) {
			return skuMapper.selectByExampleWithBLOBs(example).get(0);
		}
		return null;
	}

	@Override
	public AjaxResult saveSku(ProductRelation productRelation) {
		int productId = productRelation.getId();
		{
			SkuExample example = new SkuExample();
			SkuExample.Criteria criteria = example.createCriteria();
			criteria.andProductIdEqualTo(productId);
			System.out.println(String.format("-----Delete sku row count %d", skuMapper.deleteByExample(example)));
		}
		{
			ProductOptionInfoExample example = new ProductOptionInfoExample();
			ProductOptionInfoExample.Criteria criteria = example.createCriteria();
			criteria.andProductIdEqualTo(productId);
			System.out.println(String.format("-----Delete product_option_info row count %d", productOptionInfoMapper.deleteByExample(example)));
		}
		List<Sku> skuList = productRelation.getSkuList();
		for (Sku sku : skuList)
			System.out.println(String.format("-----insert sku row count %d", skuMapper.insertSelective(sku)));
		List<ProductOptionInfo> productOptionInfoList = productRelation.getProductOptionInfoList();
		for (ProductOptionInfo productOptionInfo : productOptionInfoList) {
			System.out.println(String.format("-----insert product_option_info row count %d", productOptionInfoMapper.insertSelective(productOptionInfo)));
		}
		return new AjaxResult(true, "保存成功!");
	}

}
