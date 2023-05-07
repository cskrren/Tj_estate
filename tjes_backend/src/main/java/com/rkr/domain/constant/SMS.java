package com.rkr.domain.constant;

import lombok.Data;

/**
 * @Package com.rkr.domain.constant
 * @auhter rkr
 * @date 2023/4/30 22:27
 * @description SMS:短信模板ID
 */
@Data
public class SMS {
    /**
     *  客户维修通知模板ID
     */
    public static final String CUSTOMER_REPAIR_NOTICE_TEMPLATE_ID = "SMS_460665506";

    /**
     *  客户维修回执模板ID
     */
    public static final String CUSTOMER_REPAIR_RECEIPT_TEMPLATE_ID = "SMS_460675741";

    /**
     *  客户投诉通知模板ID
     */
    public static final String CUSTOMER_COMPLAINT_NOTICE_TEMPLATE_ID = "SMS_460680741";

    /**
     *  客户投诉回执模板ID
     */
    public static final String CUSTOMER_COMPLAINT_RECEIPT_TEMPLATE_ID = "SMS_460715756";
}
