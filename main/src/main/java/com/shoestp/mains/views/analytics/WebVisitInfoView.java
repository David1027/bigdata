package com.shoestp.mains.views.analytics;

import com.shoestp.mains.views.analytics.pojo.MouseClickPojo;
import com.shoestp.mains.views.analytics.pojo.PageLoadInfoPojo;
import com.shoestp.mains.views.analytics.pojo.UserInfoPojo;
import com.shoestp.mains.views.analytics.pojo.WindowsPojo;
import java.util.List;
import lombok.Data;

/** * 前端js统计数据回传接受DTO */
@Data
public class WebVisitInfoView {
  /** * 标题 */
  private String title;
  /** * url */
  private String url;
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
}
