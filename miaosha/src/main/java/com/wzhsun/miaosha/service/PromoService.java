package com.wzhsun.miaosha.service;

import com.wzhsun.miaosha.service.model.PromoModel;

public interface PromoService {

    //根据itemid获取即将进行或者正在进行的秒杀活动
    PromoModel getPromoByItemId(Integer itemId);

}
