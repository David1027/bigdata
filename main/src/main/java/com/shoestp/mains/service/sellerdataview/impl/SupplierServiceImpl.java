package com.shoestp.mains.service.sellerdataview.impl;

import java.util.*;

import javax.annotation.Resource;

import com.shoestp.mains.constant.dataview.Contants;
import com.shoestp.mains.constant.sellerdataview.SellerContants;
import com.shoestp.mains.dao.sellerdataview.supplier.SupplierDao;
import com.shoestp.mains.entitys.sellerdataview.supplier.SellerDataViewSupplier;
import com.shoestp.mains.service.sellerdataview.SupplierService;
import com.shoestp.mains.utils.dateUtils.DateTimeUtil;
import com.shoestp.mains.utils.dateUtils.KeyValueViewUtil;
import com.shoestp.mains.views.dataview.real.RealView;
import com.shoestp.mains.views.dataview.utils.KeyValue;
import com.shoestp.mains.views.sellerdataview.supplier.CountryView;
import com.shoestp.mains.views.sellerdataview.supplier.OverviewView;

import org.springframework.stereotype.Service;

/**
 * @description: 商家后台供应商-服务层实现类
 * @author: lingjian
 * @create: 2019/5/24 11:48
 */
@Service
public class SupplierServiceImpl implements SupplierService {

  @Resource private SupplierDao supplierDao;

  public OverviewView isNullTo(OverviewView overview) {
    if (overview.getSupplierId() == null) {
      overview.setVisitorCount(0);
      overview.setViewCount(0);
      overview.setInquiryCount(0);
      overview.setInquiryNumber(0);
    }
    return overview;
  }

  /**
   * 获取首页概况
   *
   * @author: lingjian @Date: 2019/5/27 10:58
   * @param startDate
   * @param endDate
   * @param supplierid
   * @return
   */
  @Override
  public OverviewView getIndexOverview(Date startDate, Date endDate, Integer supplierid) {
    return isNullTo(
        supplierDao.findSupplier(
            DateTimeUtil.getTimesOfDay(startDate),
            DateTimeUtil.getTimesOfDay(endDate, 24),
            supplierid));
  }

  /**
   * 获取数据趋势(num天)
   *
   * @author: lingjian @Date: 2019/5/27 11:27
   * @param date
   * @param supplierid
   * @param num
   * @param type
   * @return
   */
  public List getIndexTrendList(Date date, Integer supplierid, int num, String type) {
    List list = new ArrayList();
    for (int i = 0; i < num; i++) {
      if (SellerContants.VISITOR.equals(type)) {
        list.add(
            getIndexOverview(
                    DateTimeUtil.getDayFromNum(date, num - i - 1),
                    DateTimeUtil.getDayFromNum(date, num - i - 1),
                    supplierid)
                .getVisitorCount());
      } else if (SellerContants.VIEW.equals(type)) {
        list.add(
            getIndexOverview(
                    DateTimeUtil.getDayFromNum(date, num - i - 1),
                    DateTimeUtil.getDayFromNum(date, num - i - 1),
                    supplierid)
                .getViewCount());
      } else if (SellerContants.INQUIRY.equals(type)) {
        list.add(
            getIndexOverview(
                    DateTimeUtil.getDayFromNum(date, num - i - 1),
                    DateTimeUtil.getDayFromNum(date, num - i - 1),
                    supplierid)
                .getInquiryCount());
      } else if (SellerContants.INQUIRYNUM.equals(type)) {
        list.add(
            getIndexOverview(
                    DateTimeUtil.getDayFromNum(date, num - i - 1),
                    DateTimeUtil.getDayFromNum(date, num - i - 1),
                    supplierid)
                .getInquiryNumber());
      }
    }
    return list;
  }

  /**
   * 获取key-value形式的数据趋势
   *
   * @author: lingjian @Date: 2019/5/27 11:27
   * @param date
   * @param supplierid
   * @param num
   * @return
   */
  public List getIndexTrendKV(Date date, Integer supplierid, int num) {
    List<KeyValue> keyValues = new ArrayList<>();
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            SellerContants.VISITOR,
            getIndexTrendList(date, supplierid, num, SellerContants.VISITOR)));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            SellerContants.VIEW, getIndexTrendList(date, supplierid, num, SellerContants.VIEW)));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            SellerContants.INQUIRY,
            getIndexTrendList(date, supplierid, num, SellerContants.INQUIRY)));
    keyValues.add(
        KeyValueViewUtil.getFlowKeyValue(
            SellerContants.INQUIRYNUM,
            getIndexTrendList(date, supplierid, num, SellerContants.INQUIRYNUM)));
    return keyValues;
  }

  /**
   * 获取数据趋势+横坐标
   *
   * @author: lingjian @Date: 2019/5/27 11:27
   * @param num
   * @param supplierid
   * @return
   */
  @Override
  public Map getIndexTrend(Integer num, Integer supplierid) {
    Map<String, List> map = new HashMap<>();
    map.put(Contants.ABSCISSA, DateTimeUtil.getDayByNum(new Date(), num));
    map.put(Contants.LIST, getIndexTrendKV(new Date(), supplierid, num));
    return map;
  }

  /**
   * 获取国家-访客数集合
   *
   * @author: lingjian @Date: 2019/5/27 13:32
   * @param startDate
   * @param endDate
   * @param supplierid
   * @return
   */
  public List<CountryView> getIndexCountryList(Date startDate, Date endDate, Integer supplierid) {
    return supplierDao.findCountry(
        DateTimeUtil.getTimesOfDay(startDate), DateTimeUtil.getTimesOfDay(endDate, 24), supplierid);
  }

  /**
   * 获取key-value形式的国家-访客数集合
   *
   * @author: lingjian @Date: 2019/5/27 13:32
   * @param startDate
   * @param endDate
   * @param supplierid
   * @return
   */
  public List getIndexCountryKV(Date startDate, Date endDate, Integer supplierid) {
    List<KeyValue> keyValues = new ArrayList<>();
    List<CountryView> list = getIndexCountryList(startDate, endDate, supplierid);
    for (int i = 0; i < list.size(); i++) {
      keyValues.add(
          KeyValueViewUtil.getFlowKeyValue(
              list.get(i).getCountry(), list.get(i).getVisitorCount()));
    }
    return keyValues;
  }

  /**
   * 获取国家横坐标
   *
   * @author: lingjian @Date: 2019/5/27 13:31
   * @param startDate
   * @param endDate
   * @param supplierid
   * @return
   */
  public List getCountryList(Date startDate, Date endDate, Integer supplierid) {
    List country = new ArrayList<>();
    List<CountryView> list = getIndexCountryList(startDate, endDate, supplierid);
    for (int i = 0; i < list.size(); i++) {
      country.add(list.get(i).getCountry());
    }
    return country;
  }

  /**
   * 获取国家用户画像+纵坐标
   *
   * @author: lingjian @Date: 2019/5/27 13:31
   * @param startDate
   * @param endDate
   * @param supplierid
   * @return
   */
  @Override
  public Map getIndexCountry(Date startDate, Date endDate, Integer supplierid) {
    Map<String, List> map = new HashMap<>();
    map.put(SellerContants.COUNTRY, getCountryList(startDate, endDate, supplierid));
    map.put(Contants.LIST, getIndexCountryKV(startDate, endDate, supplierid));
    return map;
  }
}
