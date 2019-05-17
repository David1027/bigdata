package com.shoestp.mains.dao.metaData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.shoestp.mains.dao.BaseDao;
import com.shoestp.mains.entitys.MetaData.GoogleBrowseInfo;
import com.shoestp.mains.entitys.MetaData.QGoogleBrowseInfo;
import com.shoestp.mains.repositorys.metaData.GoogleBrowseInfoRepository;
import com.shoestp.mains.schedulers.googleApi.PageRankingView;
import org.springframework.stereotype.Component;

@Component
public class GoogleBrowseInfoDao extends BaseDao<GoogleBrowseInfo> {

    @Resource
    private GoogleBrowseInfoRepository googleBrowseInfoRepository;


    public GoogleBrowseInfo save(GoogleBrowseInfo t) {
        return googleBrowseInfoRepository.save(t);
    }

    @Override
    public GoogleBrowseInfo find(GoogleBrowseInfo t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GoogleBrowseInfo findById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int update(GoogleBrowseInfo t) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int updateByList(List<GoogleBrowseInfo> list) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int remove(GoogleBrowseInfo t) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int removeByIds(Integer... id) {
        // TODO Auto-generated method stub
        return 0;
    }

    public Optional<GoogleBrowseInfo> findTopByOrderByCreateTimeDesc() {
        return googleBrowseInfoRepository.findTopByOrderByCreateTimeDesc();
    }

    //    ELECT page_path as pagePath,sum(page_views) as taotalPageViews,SUM(sessions) as taotalSession,sum(time_on_page) as totalTime FROM `google_browse_info` "
//            + "GROUP BY pagePath ORDER BY taotalPageViews desc LIMIT ?1
    public List<PageRankingView> queryPageRanking(Integer limit) {
        List result = new ArrayList();
        for (Object o : googleBrowseInfoRepository.queryPageRanking(limit)) {
            Object[] objects = (Object[]) o;
            result.add(new PageRankingView(String.valueOf(objects[0]), BigDecimal.valueOf(Double.valueOf(String.valueOf(objects[1]))), BigDecimal.valueOf(Double.valueOf(String.valueOf(objects[2]))), BigDecimal.valueOf(Double.valueOf(String.valueOf(objects[3])))));
        }
        return result;
    }
}
