package com.st.dianping.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.st.dianping.dto.CategoryDto;
import com.st.dianping.dto.SellerDto;
import com.st.dianping.dto.ShopDto;
import com.st.dianping.eu.ErrorEnum;
import com.st.dianping.exception.SocketException;
import com.st.dianping.repository.ShopDtoMapper;
import com.st.dianping.service.CategoryService;
import com.st.dianping.service.SellerService;
import com.st.dianping.service.ShopService;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ShaoTian
 * @date 2020/6/24 15:22
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDtoMapper shopDtoMapper;

    @Autowired
    private RestHighLevelClient highLevelClient;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private CategoryService categoryService;

    @Override
    @Transactional
    public ShopDto create(ShopDto shopDto) throws SocketException {
        SellerDto sellerDto = sellerService.get(shopDto.getSellerId());

        if (sellerDto == null) {
            throw new SocketException(ErrorEnum.PARAMETER_VALIDATION_ERROR, "商户不存在");
        }

        if (sellerDto.getDisabledFlag() == 1) {
            throw new SocketException(ErrorEnum.PARAMETER_VALIDATION_ERROR, "商户已被禁用");
        }

        CategoryDto categoryDto = categoryService.get(shopDto.getCategoryId());
        if (categoryDto == null) {
            throw new SocketException(ErrorEnum.PARAMETER_VALIDATION_ERROR, "品类不存在");
        }
        shopDtoMapper.insertSelective(shopDto);

        return get(shopDto.getId());
    }

    @Override
    public ShopDto get(Integer id) {
        ShopDto shopDto = shopDtoMapper.selectByPrimaryKey(id);
        shopDto.setCategoryDto(categoryService.get(shopDto.getCategoryId()));
        shopDto.setSellerDto(sellerService.get(shopDto.getSellerId()));

        return shopDto;
    }

    @Override
    public List<ShopDto> selectAllShop() {
        List<ShopDto> shopDtos = shopDtoMapper.selectAllShop();
        shopDtos.forEach(shopDto -> {
            shopDto.setCategoryDto(categoryService.get(shopDto.getCategoryId()));
            shopDto.setSellerDto(sellerService.get(shopDto.getSellerId()));
        });

        return shopDtos;
    }

    @Override
    public List<ShopDto> recommendShop(BigDecimal longitude, BigDecimal latitude) {

        List<ShopDto> shopDtos = shopDtoMapper.recommendShop(longitude, latitude);
        shopDtos.forEach(shopDto -> {
            shopDto.setCategoryDto(categoryService.get(shopDto.getCategoryId()));
            shopDto.setSellerDto(sellerService.get(shopDto.getSellerId()));
        });

        return shopDtos;
    }

    @Override
    public List<ShopDto> search(BigDecimal longitude, BigDecimal latitude, String keyword, Integer categoryId, Integer pricePerMan, String tags) {
        List<ShopDto> shopDtos = shopDtoMapper.searchShop(longitude, latitude, keyword, categoryId, pricePerMan, tags);
        shopDtos.forEach(shopDto -> {
            shopDto.setCategoryDto(categoryService.get(shopDto.getCategoryId()));
            shopDto.setSellerDto(sellerService.get(shopDto.getSellerId()));
            System.out.println(shopDto.toString());
        });

        return shopDtos;
    }

    @Override
    public Map<String, Object> searchEs(BigDecimal longitude, BigDecimal latitude, String keyword, Integer categoryId, Integer pricePerMan, String tags) throws IOException {
        Map<String, Object> result = new HashMap<>();

//        SearchRequest searchRequest = new SearchRequest("shop");
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.matchQuery("name", keyword));
//        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        searchRequest.source(searchSourceBuilder);
//
//        try {
//            SearchResponse searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//            List<Integer> shopIpList = new ArrayList<>();
//            SearchHit[] searchHits = searchResponse.getHits().getHits();
//
//            for (SearchHit searchHit : searchHits) {
//                shopIpList.add(new Integer(searchHit.getSourceAsMap().get("id").toString()));
//            }
//
//            List<ShopDto> shopDtoList = shopIpList.stream().map(id -> {
//                return get(id);
//            }).collect(Collectors.toList());
//
//            result.put("shop", shopDtoList);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }

        Request request = new Request("GET", "/shop/_search");
        String reqSql = "{\n" +
            "  \"_source\": \"*\", \n" +
            "  \"script_fields\": {\n" +
            "    \"distance\": {\n" +
            "      \"script\": {\n" +
            "      \"source\":\"haversin(lat,lon,doc['location'].lat,doc['location'].lon)\",\n" +
            "      \"lang\": \"expression\",\n" +
            "      \"params\": {\"lat\":" + longitude + ",\"lon\":" + latitude + "}\n" +
            "        }\n" +
            "      }\n" +
            "    }, \n" +
            "  \"query\": {\n" +
            "    \"function_score\": {\n" +
            "      \"query\": {\n" +
            "         \"bool\": {\n" +
            "           \"must\": [\n" +
            "            {\"match\": {\"name\": {\"query\": \"" + keyword + "\",\"boost\": 0.1}}},\n" +
            "            {\"term\": {\"seller_disabled_flag\": 0}}\n" +
            "          ]\n" +
            "      }\n" +
            "    },\n" +
            "    \"functions\": [\n" +
            "      {\n" +
            "        \"gauss\":{\n" +
            "          \"location\":{\n" +
            "            \"origin\":\"" + longitude + "," + latitude + "\",\n" +
            "            \"scale\":\"100km\",\n" +
            "            \"offset\":\"0km\",\n" +
            "            \"decay\": 0.5\n" +
            "          }\n" +
            "        }, \n" +
            "        \"weight\": 9\n" +
            "      },\n" +
            "      {\n" +
            "        \"field_value_factor\": {\n" +
            "          \"field\": \"remark_score\"\n" +
            "         }, \n" +
            "        \"weight\": 0.2\n" +
            "      },\n" +
            "      {\n" +
            "        \"field_value_factor\": {\n" +
            "          \"field\": \"seller_remark_score\"\n" +
            "         },\n" +
            "        \"weight\": 0.1\n" +
            "      },\n" +
            "      {\n" +
            "        \"field_value_factor\": {\n" +
            "          \"field\": \"price_per_man\"\n" +
            "         },\n" +
            "        \"weight\": 0.01\n" +
            "      }\n" +
            "    ]\n" +
            "    , \"score_mode\": \"sum\"\n" +
            "    , \"boost_mode\": \"sum\"\n" +
            "  }\n" +
            " }\n" +
            "}";

        request.setJsonEntity(reqSql);
        Response response = highLevelClient.getLowLevelClient().performRequest(request);
        String responseStr = EntityUtils.toString(response.getEntity());
        JSONObject jsonObject = JSONObject.parseObject(responseStr);
        List<ShopDto> shopDtoList = new ArrayList<>();

        JSONArray array = jsonObject.getJSONObject("hits").getJSONArray("hits");
        for (int i = 0; i < array.size(); i++) {
            JSONObject arrayObject = array.getJSONObject(i);
            Integer id = new Integer(arrayObject.get("_id").toString());
            BigDecimal distance = new BigDecimal(arrayObject.getJSONObject("fields").getJSONArray("distance").get(0).toString());
            ShopDto shopDto = get(id);
            shopDto.setDistance(distance.multiply(new BigDecimal(1000).setScale(0, BigDecimal.ROUND_CEILING)).intValue());
            shopDtoList.add(shopDto);
        }
        result.put("shop", shopDtoList);

        return result;
    }

    @Override
    public List<Map<String, Object>> searchGroupByTags(Integer categoryId, String keyword, String tags) {
        return shopDtoMapper.searchGroupByTags(categoryId, keyword, tags);
    }

    @Override
    public Integer countAllShop() {
        return shopDtoMapper.countAllShop();
    }
}
