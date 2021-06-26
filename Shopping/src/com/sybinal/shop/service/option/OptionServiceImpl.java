package com.sybinal.shop.service.option;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sybinal.shop.common.PageInformation;
import com.sybinal.shop.mapper.OptionMapper;
import com.sybinal.shop.mapper.OptionValueMapper;
import com.sybinal.shop.mapper.ProductMapper;
import com.sybinal.shop.mapper.SkuMapper;
import com.sybinal.shop.model.Option;
import com.sybinal.shop.model.OptionExample;
import com.sybinal.shop.model.OptionRelation;
import com.sybinal.shop.model.OptionValue;
import com.sybinal.shop.model.OrderItem;
import com.sybinal.shop.model.Product;
import com.sybinal.shop.model.ProductExample;
import com.sybinal.shop.model.ProductExample.Criteria;
import com.sybinal.shop.model.Sku;
import com.sybinal.shop.model.SkuExample;
import com.sybinal.shop.model.SkuVOInfo;
import com.sybinal.shop.utils.PagingTool;

@Service
public class OptionServiceImpl implements OptionService {

	@Autowired
	OptionValueMapper optionValueMapper;

	@Autowired
	OptionMapper optionMapper;

	@Autowired
	SkuMapper skuMapper;

	@Autowired
	PagingTool pagingTool;

	@Autowired
	ProductMapper productMapper;

	@Override
	@Transactional
	public int saveOption(Option option) {
		return optionMapper.insert(option);
	}

	@Override
	@Transactional
	public int modOption(Option option) {
		return optionMapper.updateByPrimaryKeySelective(option);
	}

	@Override
	@Transactional
	public int delOption(int optionId) {
		return optionMapper.deleteByPrimaryKey(optionId);
	}

	@Override
	public List<Option> getOption() {
		return optionMapper.selectByExample(null);
	}

	@Override
	public Option getOption(int optionId) {
		return optionMapper.selectByPrimaryKey(optionId);
	}

	@Override
	public List<OptionRelation> getOptionRelation(Map<String, Object> reqMap) {
		if (reqMap.get("productId") == null)
			return null;
		OptionExample example = new OptionExample();
		OptionExample.Criteria criteria = example.createCriteria();
		criteria.andProductIdEqualTo((int) reqMap.get("productId"));
		return optionMapper.selectOptionRelationByProductId(example);
	}

	@Override
	public Map<String, Object> getOptionList(PageInformation pageInfo, Option option) {
		HashMap<String, Object> datas = new HashMap<String, Object>();
		int pageCount = optionMapper.selectOptionCount(option);
		List<Option> list = pagingTool.PagingData("com.sybinal.shop.mapper.OptionMapper.selectOptionAll", option,
				Integer.parseInt(pageInfo.getiDisplayStart()) / Integer.parseInt(pageInfo.getiDisplayLength()) + 1,
				Integer.parseInt(pageInfo.getiDisplayLength()));

		datas.put("sEcho", pageInfo.getsEcho());
		datas.put("iTotalRecords", pageCount);
		datas.put("iTotalDisplayRecords", pageCount);
		datas.put("aaData", list);

		return datas;
	}

	@Override
	@Transactional
	public int addOptionInfo(Option option) {
		int i = 0;
		i = optionMapper.insertSelective(option);
		List<OptionValue> optionValues = option.getOptionValue();
		for (int j = 0; j < optionValues.size(); j++) {
			optionValues.get(j).setOptionId(option.getId());
			optionValueMapper.insertSelective(optionValues.get(j));
		}
		return i;
	}

	@Override
	@Transactional
	public int updateOptionInfo(Option option) {
		int i = 0;
		i = optionMapper.updateByPrimaryKeySelective(option);
		List<OptionValue> optionValues = option.getOptionValue();
		for (int j = 0; j < optionValues.size(); j++) {
			if (optionValues.get(j).getId() != null) {
				optionValueMapper.updateByPrimaryKeySelective(optionValues.get(j));
			} else {
				optionValueMapper.insertSelective(optionValues.get(j));
			}

		}
		return i;
	}

	@Override
	public Option getOptionContent(Integer id) {
		Option option = optionMapper.selectByPrimaryKey(id);
		option.setOptionValue(optionValueMapper.selectByOptionId(id));
		return option;
	}

	@Override
	@Transactional
	public int delteOption(String id) {
		boolean flag = true;
		int i = 0;
		List<Integer> ids = new ArrayList<Integer>();
		String[] optionIds = id.split("-");
		if (optionIds != null && optionIds.length != 0) {
			for (String optionId : optionIds) {
				Integer idTemp = Integer.parseInt(optionId);
				
				if (optionMapper.selectProductOptionCountByOptionId(idTemp) == 0) {
					ids.add(idTemp);
				} else {
					flag = false;
					break;
				}
				
			}
		}
		if (flag) {
			i = optionMapper.deleteOptionList(ids);
			optionValueMapper.deleteOptionValueListByOptionId(ids);
		}
		return i;
	}

	@Override
	public List<Sku> getQuantity(List<Integer> skuList) {
		SkuExample example = new SkuExample();
		SkuExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(skuList);
		return skuMapper.selectByExample(example);
	}

	@Override
	@Transactional
	public void setQuantity(List<OrderItem> Order) {
		int affectRow = 0;
		SkuExample example = new SkuExample();
		ProductExample productExample = new ProductExample();
		SkuExample.Criteria criteria = example.createCriteria();
		for (OrderItem orderItem : Order) {
			if (orderItem.getSkuId() != null) {
				Sku sku = new Sku();
				criteria.andProductIdEqualTo(orderItem.getProductId());
				criteria.andOptionValueKeyGroupEqualTo(orderItem.getOptionValueKeyGroup());
				sku.setQuantity(orderItem.getQuantity());
				affectRow += skuMapper.updateQuantityByExample(sku, example);
				productMapper.updateQuantityCalculate(orderItem.getProductId());
				example.clear();
			} else {
				Product product = new Product();
				Criteria productCriteria = productExample.createCriteria();
				productCriteria.andIdEqualTo(orderItem.getProductId());
				product.setQuantity(orderItem.getQuantity());
				productMapper.updateQuantityByExample(product, productExample);
				productExample.clear();
			}
		}
		System.err.println(String.format("----------------- Sku table. Affect Row: %d", affectRow));
	}

	@Override
	public int getOptionNameCount(String name) {
		return optionMapper.selectOptionNameCount(name);
	}

	@Override
	public int getOptionNameRows(Option option) {
		return optionMapper.selectOptionNameRows(option);
	}

	@Override
	public List<SkuVOInfo> getOptionByOptionId(List<Integer> idList) {
		OptionExample example = new OptionExample();
		example.setOrderByClause("`oi`.`id`, `ov`.`id`");
		OptionExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(idList);
		List<OptionRelation> optionList = optionMapper.selectOptionRelationById(example);
		List<SkuVOInfo> list = new ArrayList<SkuVOInfo>();
		return skuTraverse(list, optionList);
	}
	
	private List<SkuVOInfo> skuTraverse(List<SkuVOInfo> list, List<OptionRelation> optionList) {
		if (optionList.size() == 0)
			return list;
		List<SkuVOInfo> template = skuTemplate(optionList.get(0));
		if (optionList.size() == 2) {
			for (SkuVOInfo skuVOInfo : template)
				for (OptionValue optionValue : optionList.get(1).getOptionValuesList()) {
					SkuVOInfo info = new SkuVOInfo();
					info.setKey(String.format("%s_%s", skuVOInfo.getKey(), optionValue.getId()));
					info.setTitleName(String.format("%s%s:%s ", skuVOInfo.getTitleName(), optionList.get(1).getName(), optionValue.getName()));
					list.add(info);
				}
			return list;
		}
		return template;
	}
	
	private List<SkuVOInfo> skuTemplate(OptionRelation optionList) {
		List<SkuVOInfo> list = new ArrayList<SkuVOInfo>();
		for (OptionValue value : optionList.getOptionValuesList()) {
			SkuVOInfo info = new SkuVOInfo();
			info.setKey(String.valueOf(value.getId()));
			info.setTitleName(String.format("%s:%s ", optionList.getName(), value.getName()));
			list.add(info);
		}
		return list;
	}

}
