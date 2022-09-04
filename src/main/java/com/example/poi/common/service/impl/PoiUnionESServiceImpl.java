package com.example.poi.common.service.impl;

import cn.hutool.core.util.StrUtil;
import com.example.poi.common.elasticsearch.PoiUnionES;
import com.example.poi.common.elasticsearch.PoiUnionRepository;
import com.example.poi.common.service.PoiUnionESService;
import com.example.poi.mapper.PoiUnionESMapper;
import com.example.poi.service.PoiUnionService;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PoiUnionESServiceImpl implements PoiUnionESService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoiUnionESServiceImpl.class);
    @Autowired
    private PoiUnionESMapper poiUnionESMapper;
    @Autowired
    private PoiUnionRepository poiUnionRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public int importAll() {
        List<PoiUnionES> esPoiUnionList = poiUnionESMapper.getAllPoiUnionESList();
        Iterable<PoiUnionES> esPoiUnionIterable = poiUnionRepository.saveAll(esPoiUnionList);
        Iterator<PoiUnionES> iterator = esPoiUnionIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        poiUnionRepository.deleteById(id);
    }

    @Override
    public PoiUnionES create(Long id) {
        PoiUnionES esPoiUnion = poiUnionESMapper.getSinglePoiUnionESList(id);
        return poiUnionRepository.save(esPoiUnion);
    }

    @Override
    public void delete(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<PoiUnionES> esPoiUnionList = new ArrayList<>();
            for (Long id : ids) {
                PoiUnionES esPoiUnion = new PoiUnionES();
                esPoiUnion.setId(id);
                esPoiUnionList.add(esPoiUnion);
            }
            poiUnionRepository.deleteAll(esPoiUnionList);
        }
    }

    @Override
    public long count() {
        return poiUnionRepository.count();
    }

    @Override
    public Page<PoiUnionES> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //分页
        nativeSearchQueryBuilder.withPageable(pageable);
        //搜索
        if (StrUtil.isEmpty(keyword)) {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
        } else {
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("displayname", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(10)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("address", keyword),
                    ScoreFunctionBuilders.weightFactorFunction(5)));
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
            filterFunctionBuilders.toArray(builders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                    .setMinScore(2);
            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        }
        //按相关度排序
        nativeSearchQueryBuilder.withSorts(SortBuilders.scoreSort().order(SortOrder.DESC));
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        LOGGER.info("DSL:{}", searchQuery.getQuery().toString());
        SearchHits<PoiUnionES> searchHits = elasticsearchRestTemplate.search(searchQuery, PoiUnionES.class);
        if(searchHits.getTotalHits()<=0){
            return new PageImpl<>(null,pageable,0);
        }
        List<PoiUnionES> searchPoiUnionList = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageImpl<>(searchPoiUnionList,pageable,searchHits.getTotalHits());
    }
}
