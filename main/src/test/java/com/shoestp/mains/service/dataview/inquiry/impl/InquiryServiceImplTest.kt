package com.shoestp.mains.service.dataview.inquiry.impl

import base.BaseTest
import com.shoestp.mains.controllers.dataview.inquiry.pojo.InquiryType
import com.shoestp.mains.dao.dataview.inquirynew.InquiryNewRepository
import com.shoestp.mains.dao.metadata.PltCountryDao
import com.shoestp.mains.entitys.dataview.inquirynew.DataViewInquiryNew
import com.shoestp.mains.entitys.dataview.inquirynew.enums.InquiryTypeEnum
import com.shoestp.mains.entitys.metadata.PltCountry
import com.shoestp.mains.entitys.metadata.WebVisitInfo
import com.shoestp.mains.entitys.metadata.enums.DeviceTypeEnum
import com.shoestp.mains.enums.flow.AccessTypeEnum
import com.shoestp.mains.service.dataview.inquiry.InquiryNewService
import com.shoestp.mains.service.metadata.WebVisitInfoService
import junit.framework.Assert
import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Test
import org.start2do.utils.DateTimeUtil
import java.time.LocalDateTime
import javax.annotation.Resource

internal class InquiryServiceImplTest : BaseTest() {
    @Resource
    var inquiryService: InquiryNewService? = null
    @Resource
    var inquiryNewRepository: InquiryNewRepository? = null
    @Resource
    var pltCountryDao: PltCountryDao? = null;

    @Resource
    private val webVisitInfoService: WebVisitInfoService? = null

    fun setUp() {
        var dataViewInquiry = DataViewInquiryNew()
        dataViewInquiry.name = "test"
        dataViewInquiry.image = "test"
        dataViewInquiry.type = InquiryTypeEnum.SUPPLIER
        dataViewInquiry.pv = 0L
        dataViewInquiry.uv = 0L
        dataViewInquiry.inquiryCount = 0L
        dataViewInquiry.statisticalTime = LocalDateTime.of(2019, 9, 1, 11, 22)
        inquiryNewRepository!!.save(dataViewInquiry)
        dataViewInquiry = DataViewInquiryNew()
        dataViewInquiry.name = "test"
        dataViewInquiry.image = "test"
        dataViewInquiry.type = InquiryTypeEnum.SUPPLIER
        dataViewInquiry.pv = 0L
        dataViewInquiry.uv = 0L
        dataViewInquiry.inquiryCount = 0L
        dataViewInquiry.statisticalTime = LocalDateTime.of(2019, 9, 22, 11, 22)
        inquiryNewRepository!!.save(dataViewInquiry)
    }


    @Test
    fun getInquiryDateViewByKeyWordAndType() {
    }

    @Test
    fun findTopOrderByStatistical_timeDesc() {
        setUp()
        val dataViewInquiryNew = inquiryNewRepository!!.findTopByTypeOrderByStatisticalTimeDesc(InquiryTypeEnum.SUPPLIER).get()
        val result = DateTimeUtil.timeDifferent(
                dataViewInquiryNew.statisticalTime, LocalDateTime.of(2019, 9, 22, 11, 22))
                .toString()
        Assert.assertEquals(result, "PT0S")
    }

    @Test
    fun schedulers() {
        val build = build()
        /** 着陆页判断  */
        logger.info(webVisitInfoService!!.save(build.buildLanding("187.250.24.20", "Women's shoe manufacturers | Wholesale womens shoes | Wholesale womens boots", pltCountryDao!!.findById(153).get())).id)
        logger.info(webVisitInfoService!!.save(build.buildLanding("18.250.24.255", "Women's shoe manufacturers", pltCountryDao!!.findById(153).get())).id)
        logger.info(webVisitInfoService!!.save(build.buildLanding("18.250.24.255", "Women's shoe manufacturers", pltCountryDao!!.findById(153).get())).id)
        logger.info(webVisitInfoService!!.save(build.buildLanding("18.250.24.25", "Women's shoe manufacturers", pltCountryDao!!.findById(153).get())).id)
        /** 商家首页判断  */
        webVisitInfoService?.save(build.buildSupplier("18.250.24.25", "Women's shoe manufacturers", pltCountryDao!!.findById(153).get()))
        webVisitInfoService?.save(build.buildSupplier("18.250.24.22", "Women's shoe manufacturers", pltCountryDao!!.findById(153).get()))
        webVisitInfoService?.save(build.buildSupplier("18.250.24.22", "Women's shoe manufacturers", pltCountryDao!!.findById(153).get()))

        inquiryService!!.schedulers()
        inquiryNewRepository!!.findAll().forEach { dataViewInquiryNew: DataViewInquiryNew? ->
            logger.info("所有的:{}", dataViewInquiryNew)
        }
        var list = inquiryService!!.getInquiryDateViewByKeyWordAndType(null, null, null, InquiryType.LANDING).list;
        Assert.assertEquals(list.size, 2);
        list = inquiryService!!.getInquiryDateViewByKeyWordAndType("Women's shoe manufacturers", null, null, InquiryType.LANDING).list
        Assert.assertEquals(list.size, 1);
        list.forEach { dataViewInquiryNew: DataViewInquiryNew? ->
            Assert.assertEquals(dataViewInquiryNew?.pv, 3L)
            Assert.assertEquals(dataViewInquiryNew?.uv, 2L)
        }
        list = inquiryService!!.getInquiryDateViewByKeyWordAndType(null, null, null, InquiryType.SUPPLIER).list;
        Assert.assertEquals(list.size, 1);
        list.forEach { dataViewInquiryNew: DataViewInquiryNew? ->
            logger.info(dataViewInquiryNew)
            Assert.assertEquals(dataViewInquiryNew?.pv, 3L)
            Assert.assertEquals(dataViewInquiryNew?.uv, 2L)
        }
    }

    companion object {
        private val logger = LogManager.getLogger(InquiryServiceImplTest::class)
    }
}

internal class build {
    fun buildLanding(ip: String, title: String, pltcount: PltCountry): WebVisitInfo {
        val webVisitInfo = WebVisitInfo()
        webVisitInfo.referer = "https://www.google.com/"
        webVisitInfo.uri = "/landing/naiRui/"
        webVisitInfo.location = pltcount;
        webVisitInfo.userAgent = "Mozilla/5.0 (iPad; CPU OS 12_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.2 Mobile/15E148 Safari/604.1"
        webVisitInfo.url = "https://www.shoestp.com/landing/naiRui/?gclid=EAIaIQobChMIjdqy_YLD5AIV0MDACh11VQUaEAAYAyAAEgLCnvD_BwE"
        webVisitInfo.title = title
        webVisitInfo.sessionCreateTime = DateTimeUtil.formatStringToDate("2019-09-09 13:38:16")
        webVisitInfo.createTime = DateTimeUtil.toDate(DateTimeUtil.formatStringToDate("2019-09-09 13:38:28"))
        webVisitInfo.equipmentPlatform = DeviceTypeEnum.WAP
        webVisitInfo.timeOnPage = 11934;
        webVisitInfo.ip = ip
        webVisitInfo.pageType = AccessTypeEnum.LANDING
        return webVisitInfo
    }

    fun buildSupplier(ip: String, title: String, pltcount: PltCountry): WebVisitInfo {
        val webVisitInfo = WebVisitInfo()
        webVisitInfo.referer = "https://www.google.com/"
        webVisitInfo.uri = "/home/usr_UsrSupplier_gtSupIndex?pkey=23"
        webVisitInfo.location = pltcount;
        webVisitInfo.userAgent = "Mozilla/5.0 (iPad; CPU OS 12_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.2 Mobile/15E148 Safari/604.1"
        webVisitInfo.url = "https://www.shoestp.com/home/usr_UsrSupplier_gtSupIndex?pkey=23"
        webVisitInfo.title = title
        webVisitInfo.sessionCreateTime = DateTimeUtil.formatStringToDate("2019-09-09 13:38:16")
        webVisitInfo.createTime = DateTimeUtil.toDate(DateTimeUtil.formatStringToDate("2019-09-09 13:38:28"))
        webVisitInfo.equipmentPlatform = DeviceTypeEnum.PC
        webVisitInfo.timeOnPage = 11934;
        webVisitInfo.ip = ip
        webVisitInfo.pageType = AccessTypeEnum.SUPPLIER_PAGE
        return webVisitInfo
    }
}
