package com.shoestp.mains.controllers.analytics.view;

import com.shoestp.mains.controllers.analytics.view.pojo.MouseClickPojo;
import com.shoestp.mains.controllers.analytics.view.pojo.PageLoadInfoPojo;
import com.shoestp.mains.controllers.analytics.view.pojo.UserInfoPojo;
import com.shoestp.mains.controllers.analytics.view.pojo.WindowsPojo;
import lombok.Data;

import java.util.List;

/**
 * * 前端js统计数据回传接受DTO
 *
 * @author lijie
 * @date 2019 /08/06
 * @since
 * @modify Lijie HelloBox@outlook.com 2019-08-06 13:34 添加字段
 */
@Data
public class WebVisitInfoView {
  /** * 标题 */
  private String title;
  /** * url */
  private String url;
  /** URI */
  private String uri;
  /** * 第一次进从哪里进来 */
  private String firstReferrer;
  /** * 当前页面进入情况 */
  private String pageReferrer;
  /** * 页面载入情况 */
  private PageLoadInfoPojo pageLoadInfo;
  /** 用户信息 */
  private UserInfoPojo userInfo;
  /** * 鼠标点击清空 */
  private List<MouseClickPojo> mouseClick;
  /** * 用户宽高情况 */
  private WindowsPojo windows;
  /** NOTE 2019-08-06 13:39 添加字段 */
  /** 会话ID */
  private String session;
  /** 会话创建时间 */
  private Long session_create_time;
  /** 页面停留时间 */

  private Long time_on_page;
  /** 页面等待时间 */
  private Long page_wait_time;
  /** 页面图片信息 */
  private String img;
}
