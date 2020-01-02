package cn.appsys.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ad_promotion
 * @author 
 */
@Data
@TableName("ad_Promotion")
public class AdPromotion implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * appId（来源于：app_info表的主键id）
     */
    private Long appid;

    /**
     * 广告图片存储路径
     */
    private String adpicpath;

    /**
     * 广告点击量
     */
    private Long adpv;

    /**
     * 轮播位（1-n）
     */
    private Integer carouselposition;

    /**
     * 起效时间
     */
    private Date starttime;

    /**
     * 失效时间
     */
    private Date endtime;

    /**
     * 创建者（来源于backend_user用户表的用户id）
     */
    private Long createdby;

    /**
     * 创建时间
     */
    private Date creationdate;

    /**
     * 更新者（来源于backend_user用户表的用户id）
     */
    private Long modifyby;

    /**
     * 最新更新时间
     */
    private Date modifydate;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppid() {
        return appid;
    }

    public void setAppid(Long appid) {
        this.appid = appid;
    }

    public String getAdpicpath() {
        return adpicpath;
    }

    public void setAdpicpath(String adpicpath) {
        this.adpicpath = adpicpath;
    }

    public Long getAdpv() {
        return adpv;
    }

    public void setAdpv(Long adpv) {
        this.adpv = adpv;
    }

    public Integer getCarouselposition() {
        return carouselposition;
    }

    public void setCarouselposition(Integer carouselposition) {
        this.carouselposition = carouselposition;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Long getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Long createdby) {
        this.createdby = createdby;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public Long getModifyby() {
        return modifyby;
    }

    public void setModifyby(Long modifyby) {
        this.modifyby = modifyby;
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }
}