package com.shoestp.mains.controllers.xwt.metadata;

import javax.servlet.http.HttpServletRequest;

import com.shoestp.mains.entitys.xwt.metadata.XwtMetaAccessLog;
import com.shoestp.mains.service.xwt.metadata.XwtMetaAccessLogService;
import com.shoestp.mains.utils.xwt.FieldUtils;
import com.shoestp.mains.utils.xwt.HttpRequestUtils;
import com.shoestp.mains.utils.xwt.StringFormatUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 访问日志控制层
 * @author: lingjian
 * @create: 2019/12/26 10:39
 */
@CrossOrigin
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/xwt/meta/date/access/log")
public class XwtMetaAccessLogController {

  @Autowired
  private XwtMetaAccessLogService service;

  /**
   * 保存日志信息
   *
   * @param request 客户端请求
   */
  @GetMapping("save")
  public void save(HttpServletRequest request) throws NoSuchFieldException {
    // 创建访问日志对象
    XwtMetaAccessLog accessLog = new XwtMetaAccessLog();
    // 获取访问日志对象属性名列表
    String[] filedNames = FieldUtils.getFiledName(accessLog);
    // 将请求中的访问信息设置到日志对象中
    for (String filedName : filedNames) {
      FieldUtils.setValue(
          accessLog,
          accessLog.getClass(),
          filedName,
          XwtMetaAccessLog.class.getDeclaredField(filedName).getType(),
          request.getParameter(StringFormatUtils.camelToUnderline(filedName)));
    }
    // 后台处理字段
    // 添加ip地址
    accessLog.setIp(HttpRequestUtils.getIpAddress(request));
    // 添加设备平台标识
    accessLog.setDeviceType(HttpRequestUtils.getDevice(request));
    // 保存日志对象
    service.save(accessLog);
  }
}
