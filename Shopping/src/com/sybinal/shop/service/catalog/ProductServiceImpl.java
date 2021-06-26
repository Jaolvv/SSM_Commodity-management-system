package com.sybinal.shop.service.catalog;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.mapper.ProductCategoryMapper;
import com.sybinal.shop.mapper.ProductImageMapper;
import com.sybinal.shop.mapper.ProductMapper;
import com.sybinal.shop.mapper.ProductOptionInfoMapper;
import com.sybinal.shop.mapper.SkuMapper;
import com.sybinal.shop.mapper.UserMapper;
import com.sybinal.shop.model.Product;
import com.sybinal.shop.model.ProductExample;
import com.sybinal.shop.model.ProductImage;
import com.sybinal.shop.model.ProductImageExample;
import com.sybinal.shop.model.ProductOptionInfo;
import com.sybinal.shop.model.ProductOptionInfoExample;
import com.sybinal.shop.model.ProductRelation;
import com.sybinal.shop.model.Sku;
import com.sybinal.shop.model.SkuExample;
import com.sybinal.shop.model.User;
import com.sybinal.shop.model.UserExample;
import com.sybinal.shop.utils.Page;
import com.sybinal.shop.utils.PagingTool;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductCategoryMapper ProductCategoryMapper;

	@Autowired
	ProductMapper productMapper;

	@Autowired
	ProductImageMapper productImageMapper;

	@Autowired
	UserMapper userMapper;

	@Autowired
	PagingTool pagingTool;

	@Autowired
	ProductOptionInfoMapper productOptionInfoMapper;
	
	@Autowired
	SkuMapper skuMapper;

	@Override
	public Map<String, Object> getProductByCategory(Map<String, Object> categoryId) {
		return this.getProductRelation(categoryId);
	}

	@Override
	public Map<String, Object> getProductRelation(Map<String, Object> conditions) {
		ProductExample productExample = new ProductExample();
		ProductExample.Criteria criteria = productExample.createCriteria();
		Object condition = null;
		criteria.andProductStatusEqualTo(0);
		/* 指定分类ID */
		if ((condition = conditions.get("categoryId")) != null)
			criteria.andCategoryIdEqualTo((int) condition);
		/* 指定商品名称 */
		if ((condition = conditions.get("productName")) != null)
			criteria.andNameLike(String.format("%%%s%%", (String) condition));
		/* 指定商品价格范围下限 */
		if ((condition = conditions.get("shopPriceFrom")) != null)
			criteria.andShopPriceGreaterThanOrEqualTo((int) condition);
		/* 指定热销商品 */
		if ((condition = conditions.get("hot")) != null)
			criteria.andHotEqualTo((int) condition);
		/* 指定商品价格范围上限 */
		if ((condition = conditions.get("shopPriceTo")) != null)
			criteria.andShopPriceLessThanOrEqualTo((int) condition);
		/* 指定排序条件 */
		if ((condition = conditions.get("order")) != null)
			switch ((String) condition) {
			case "ASC":
				condition = "`p`.`shop_price` ASC, ";
				break;
			case "DESC":
				condition = "`p`.`shop_price` DESC, ";
				break;
			default:
				condition = "";
				break;
			}
		productExample.setOrderByClause(
				String.format("%s`p`.`hot` DESC, `p`.`id` DESC", condition != null ? (String) condition : ""));
		/* 当前页 */
		int currentPage = 1;
		if ((condition = conditions.get("currentPage")) != null)
			currentPage = (int) condition;
		else
			conditions.put("currentPage", 1);
		/* 分页大小 */
		int pageSize = 8;
		if ((condition = conditions.get("pageSize")) != null)
			pageSize = (int) condition;
		else
			conditions.put("pageSize", pageSize);
		long totalCount = productMapper.countByExample(productExample);
		conditions.put("totalPage", Page.confirmPage(totalCount, pageSize));
		conditions.put("totalCount", totalCount);
		conditions.put("productList",
				pagingTool.PagingData("com.sybinal.shop.mapper.ProductMapper.selectByExampleWithBLOBs", productExample,
						currentPage, pageSize));
		return conditions;
	}

	@Override
	public ProductRelation getProductDetailsById(Map<String, Object> reqMap) {
		ProductExample productExample = new ProductExample();
		ProductExample.Criteria criteria = productExample.createCriteria();
		criteria.andIdEqualTo((int) reqMap.get("productId"));
		List<ProductRelation> list = productMapper.selectProductRelationByExample(productExample);
		if (list.size() == 1)
			return productMapper.selectProductRelationByExample(productExample).get(0);
		return null;
	}

	@Override
	public List<Product> getProduct(Map<String, Object> conditions) {
		ProductExample productExample = new ProductExample();
		ProductExample.Criteria criteria = productExample.createCriteria();
		Object condition = null;
		/* 指定上架商品 */
		criteria.andProductStatusEqualTo(0);
		/* 指定热销商品 */
		criteria.andHotEqualTo(1);
		if ((condition = conditions.get("productId")) != null)
			criteria.andCategoryIdInSelect((int) condition);
		productExample.setOrderByClause("`p`.`hot` DESC, `p`.`id` DESC");
		/* 当前页 */
		int currentPage = 1;
		if ((condition = conditions.get("currentPage")) != null)
			currentPage = (int) condition;
		/* 分页大小 */
		int pageSize = 8;
		if ((condition = conditions.get("pageSize")) != null)
			pageSize = (int) condition;
		List<Product> product = pagingTool.PagingData("com.sybinal.shop.mapper.ProductMapper.selectByExample",
				productExample, currentPage, pageSize);
		return product;
	}

	@Override
	public Map<String, Object> getProductInfoByCondition(PageInformation pageInfo, Product product) {
		int pageCount = productMapper.selectCountByCondition(product);
//		System.err.println("=================================================");
//		System.err.println("product:"+pageCount);
		List<Product> listProduct = pagingTool.PagingData(
				"com.sybinal.shop.mapper.ProductMapper.selectProductByCondition", product,
				(Integer.parseInt(pageInfo.getiDisplayStart())/Integer.parseInt(pageInfo.getiDisplayLength()) +1),Integer.parseInt(pageInfo.getiDisplayLength()));
//				Integer.parseInt(pageInfo.getiDisplayStart()) / Integer.parseInt(pageInfo.getiDisplayLength()) + 1,
//				Integer.parseInt(pageInfo.getiDisplayLength()));

		HashMap<String, Object> datas = new HashMap<String, Object>();
		datas.put("sEcho", pageInfo.getsEcho());
		datas.put("iTotalRecords", pageCount);
		datas.put("iTotalDisplayRecords", pageCount);
		datas.put("aaData", listProduct);

		return datas;
	}

	public Product getProductByid(int productId) {
		ProductExample productExample = new ProductExample();
		ProductExample.Criteria criteria = productExample.createCriteria();
		criteria.andIdEqualTo(productId);
		List<Product> productList = productMapper.selectByExample(productExample);
		if (productList != null) {
			return productList.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public Integer saveProductInfo(ProductRelation product) {
		int cnt = 0;
		Date date = new Date();
		Integer userId = this.getUserId(product.getUpdateUserName());
		product.setUpdateTime(date);
		product.setUpdateUserId(userId);
		if (product.getId() != null) {
			cnt = productMapper.updateByPrimaryKeySelective(product);
		} else {
			product.setCreateTime(date);
			product.setCreateUserId(userId);
			cnt = productMapper.insert(product);
		}
		List<ProductImage> productImageList = product.getProductImageList();
		if (productImageList != null) {
			ProductImageExample example = new ProductImageExample();
			ProductImageExample.Criteria criteria = example.createCriteria();
			criteria.andProductIdEqualTo(product.getId());
			productImageMapper.deleteByExample(example);
			for (ProductImage productImage : productImageList) {
				productImage.setProductId(product.getId());
				productImage.setUpdateUserId(userId);
				productImage.setUpdateTime(date);
				cnt += productImageMapper.insert(productImage);
			}
		}
		return cnt;
	}

	@Override
	public ProductRelation getProductInfoById(Integer productId) {
		ProductExample productExample = new ProductExample();
		ProductExample.Criteria criteria = productExample.createCriteria();
		criteria.andIdEqualTo(productId);
		return productMapper.selectProductRelationByExample(productExample).get(0);
	}

	private Integer getUserId(String userName) {
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(userName);
		List<User> userList = userMapper.selectByExample(example);
		if (userList != null && userList.size() > 0) {
			return userList.get(0).getId();
		}
		return null;
	}

	@Override
	public List<Sku> getProductOptionInfoByProductId(Integer productId) {
		SkuExample example = new SkuExample();
		SkuExample.Criteria criteria = example.createCriteria();
		criteria.andProductIdEqualTo(productId);
		return skuMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public List<ProductOptionInfo> getOptionByProductId(Integer productId) {
		ProductOptionInfoExample example = new ProductOptionInfoExample();
		ProductOptionInfoExample.Criteria criteria = example.createCriteria();
		criteria.andProductIdEqualTo(productId);
		return productOptionInfoMapper.selectByExample(example);
	}

}
