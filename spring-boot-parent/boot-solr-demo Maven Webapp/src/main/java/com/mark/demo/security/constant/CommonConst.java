package com.mark.demo.security.constant;

import com.mark.demo.security.entity.EnumDescribable;


public enum CommonConst implements EnumDescribable
{
    // 状态码范围：126000到130000
    SUCCESS(126000,"成功"),
    MAIL_EXIST(126001, "邮件地址已经存在"),
    MOBILE_EXIST(126002, "手机号码已经存在"),
    VALID_ERR(126003, "验证码错误"),
    PASS_ERR(126004, "密码格式错误"),
    INVALIDE_PRINCEPLE(126005, "用户名或密码错误"),
    DATA_NOT_EXISTS(126006, "记录已删除或不存在"),
    DATA_EXISTS(126005, "记录已经存在"),
    IMG_SAVE_FAULT(126006, "图片保存失败"),
    FORMAT_FAULT(126007, "文件格式不匹配"),
    UPLOAD_FAULT(126008, "文件上传失败"),
    DEL_DATA_FAULT(126009, "记录删除失败"),
    UP_DATA_FAULT(126010, "记录修改失败"),
    OVERTOP(126009, "超出上传的次数"),
    MOVE_FAULT(126011, "文件移动失败"),
    MAIL_FORMAT_ERR(126012, "邮件地址格式错误"),
    MOBILE_FORMAT_ERR(126013, "手机号码格式错误"),
    SOLRSERVER_INSTANCE_NOT_EXIST(126014, "solr服务实例不存在,重新获取实例并重试"),
    SOURCE_NOT_FOUND(126015, "上传的文件不存在"),
    PARAMS_VALID_ERR(126016, "参数验证错误"),
    CREATE_FILE_ERR(126017, "创建文件失败"),
    UPDATE_PASSWORD_ERR(126018, "密码修改错误"),
    COPY_FAULT(126008, "文件复制失败"),
    FAIL(126009,"失败"),
    PASS_NULL(126010, "密码为空"),
    PASS_NOT_EXIST(126011, "还未设置密码，请先设置"),
    PASS_ERROR(126012, "密码错误"),
    USER_NOT_LOGIN(126013, "用户未登录"),
    ORDER_REFRENCE_ID_NULL(126019, "订单资料编号为空"),
    ORDER_NULL(126020, "订单为空"),
    ORDER_STATUS_NOT_TO_RECEIVE(126021, "订单不是等待确认收货状态"),
    USER_TEMP_PHOTO_NULL(126022, "用户临时头像路径为空"),
    PARAM_NULL(126023, "请求参数为空"),
    DEALER_INFO_NULL(126024, "经销商信息不存在"),
    BRAND_INFO_NULL(126025, "品牌商信息不存在"),
    REG_USER_NOT_EXIST(126026, "注册用户不存在"),
    REG_USER_NOT_SUB(126027, "此帐户不是您的子帐户"),
    VERIFY_CODE_OUT(126028, "验证码超时"),
    LOGIN_LOCKED(126029, "登录尝试过多, 暂时无法登录"),
    ORDER_REFUND_STATUS_ERROR(126030, "订单退货状态错误"),
    ORDER_REFUND_RECORD_NUMBER(126031, "订单退货记录多条"),
    ORDER_REFUND_NOT_PAY(126032, "订单还未支付"),
    REFUND_AMOUNT_NOT_LARGE_PAY_AMOUNT(126033, "退款总金额不能大于已付款金额"),
    DEALER_REFUND_NOT_LARGE_AMOUNT(126034, "终端商退款金额不能大于退款总金额"),
    BRAND_REFUND_NOT_LARGE_AMOUNT(126035, "品牌商退款金额不能大于退款总金额"),
    DEALER_BRAND_AMOUNT_EQUAL_REFUND_AMOUNT(126036, "终端商和品牌商的退款总和必须等于退款总金额"),
    ATTENT_SELF_ERROR(126037, "自己不能关注自己或取消关注自己"),
    FOUND_MULTIPLE_RECORDS(126038, "存在多条记录"),
    ORDER_TRANSACTION_RECORDS_EXIST(126039, "此订单的交易记录已存在"),
    FILE_UPDATE_OUT_TIME(126040, "文件上传超时，请过段时间再传"),
    ATTENT_EXISTS_ERROR(126041, "已关注"),
    ADD_SHORTCUT_MENU_FAULT(126043, "快捷菜单添加失败"),
    SHORTCUT_MENU_EXIST(126044, "此快捷菜单已添加"),
    SHORTCUT_MENU_ADD_MAX_ERROR(126045, "快捷菜单已添加到最大数，不能再进行添加"),
    DELETE_ART_PASS(126046, "通过审核的文章不能删除"),
    FORM_PARAMS_VALID_ERR(126047, "表单参数验证错误"),
    // 只适用于表单提交，手动验证时的错误提示。
    STATE_NOT_COOPERATION(126048, "不存在合作记录"),
    VALID_CREATE_ERR(126049, "获取验证码失败"),
    VALID_EXIST_ERR(126050, "验证码已发送"),
    SENDER_NULL(126051, "不存在发送对象"),
    ADDRESS_NOT_EXIST(126052, "地址未填写"),
    VALID_MAIL_USED(126053, "已经被使用"),
    VALID_MAIL_EXPIRES(126054, "已经过期"),
    RECRUIT_CLOSE(126055, "招募书已关闭"),
    ACCOUNT_STATE_UNAPPROVE(126101, "账户正在审核中"),
    ACCOUNT_STATE_REJECT(126102, "账户审核失败"),
    ACCOUNT_STATE_FROZEN(126103, "账户已被冻结"),
    VIDEO_FORMAT_ERROR(126104, "暂不支持此视频格式"),
    VIDEO_PROCEDU_ERROR(126105, "视频上传失败"),
    IMAGE_SIZE_ERROR(126106, "超出文件上传大小，请重新选择"),
    VERIFY_CODE_FAILURE(126107, "验证码错误"),
    SEARCH_ENGINE_ERROR(126200, "搜索引擎搜索失败"),
    OPERATE_OFTEN(126201, "操作过于频繁，请稍后再试"),
    ORDER_RECEIVE_FAILURE(126108, "收货失败"),
    NOSEND(126109, "短信网关已关闭"),
    SHOPPER_PARAMS_ERROR(126110, "dealerId:终端商ID,productId:产品ID,productSkuId:具体产品skuId,count:采购数据 为必填参数"),
    EQUALS_BRANDUSERM_PWD(126111, "支付密码与用户登录密码不能相同"),
    BRAND_PAYWORD_NOT_NUMBER_OR_LETTER(126112, "支付密码不能为全数字或全字母"),
    ORDER_TIME_EXPIRED(126113, "预定产品过期,请先移除该产品"),
    BRAND_RECRUIT_CLOSE(126114, "产品招募书已经关闭"),
    Message_IS_BLANK(126115, "拒绝退款理由不能为空"),
    FILE_ADDRESS_NOT_EXIST(126116, "文件地址不存在"),
    DOWNLOAD_ERROR(126117, "下载文件失败"),
    FILE_NAME_ERROR(126118, "文件名不能为空"),
    MOBILE_MESSAGE_RTF_ERROR(126119, "短信格式错误"),
    MOBILE_MESSAGE_TEMPLATE_NULL(126120, "短信模板不存在"),
    DOWNLOAD_FILE_NOT_EXITS(126121, "下载文件不存在"),
    FILESIZE_IS_TOO_LARGE(126122, "文件大小超出上传限制"),
    USER_NOT_AGENT(126123, "该账号不是代理商用户，不能登录"),
    IS_ACTIVITY_PRODUCT(126124, "产品已加入活动，在未结束活动前，不能移除产品"),
    BALANCE_ERROR(126125, "总金额异常"),
    FROZE_ERROR(126126, "冻结金额异常"),
    QUERY_LOCKED(126127, "询价单已被锁定，客服处理中"),
    QUERY_NOT_EXIST(126128, "询价单不存在"),
    QUERY_PRODUCT_LINE_OR_QUOTED(126129, "询价单已保价或已加产品线"),
    ORDER_PAY_INTERFACE_FAIL(126130, "调用支付系统接口失败"),
    ORDER_PAY_NULL(126131, "获取支付订单失败"),
    ORDER_PAY_POINT_BALANCE(126132, "扣除的佣金金额不正确"),
    ORDER_PAY_CLOSE_ORDER(126133, "已有支付记录，不能关闭订单"),
    ORDER_PAY_PAID_NULL(126134, "没有支付记录不能退款"),
    ORDER_PAY_REFUND_NULL(126135, "获取退款订单失败"),
    FREIGHT_TEMPLATE_NAME_EXIST(126136, "运费模板的名称不能重复"),
    FREIGHT_TEMPLATE_AREANO_EXIST(126137, "运费模板的区域不能重复"),
    FREIGHT_TEMPLATE_COPYSIZE_OUT(126138, "复制模板名称过长"),
    FREIGHT_REGION_AREANAME_EXIT_NO(126139, "区域选择有误"),
    FREIGHT_TEMPLATE_COPY_NUMBER_OUT(126140, "复制模板共存达到最大"),
    FREIGHT_TEMPLATE_DELETE_APPLY(126141, "应用模板不能删"),
    FREIGHT_MONEY_ERROR(126142, "价格超出规定范围"),
    FREIGHT_WEIGHT_ERROR(126143, "重量超出规定范围"),
    TEMPLATENUMBER_NOTONLY(126144, "您已添加一套运费模板，无法继续添加"),
    SAVE_SMS_TEMPLATE_ERROR(126145, "短信KEY不能重复!"),
    ACTIVITY_FACTORY_NO(126146, "没有正在进行的工厂店活动"),
    USER_CENTER_INTERFACE_FAIL(126136, "调用用户中心接口失败"),
    USER_CENTER_NOT_FULL_INFO(126137, "用户信息不全"),
    MOBILE_EXIST_TOLOGIN(126138, "您的手机号码已被收录/注册"),
    NOT_PLATFORM_ACCESS(126138, "无平台权限"),
    UPLOAD_DATA_NULL(126139, "导入的数据不能为空"),
    SEARCH_LOGISTIC_ERROR(126239, "快递查询失败"),
    FREIGHTCOMPANY_INSERT_ERROR1(12640, "公司编码已经存在"),
    FREIGHTCOMPANY_INSERT_ERROR2(12641, "公司名称已经存在"),
    ERP_INTERFACE_FAIL(126136, "调用ERP接口失败"),
    CRM_USER_NULL(126137, "crm用户登陆交易平台失败,请检查crm到交易平台用户参数"),
    CRM_INTERFACE_FAIL(126138, "调用CRM接口失败"),
    DEALERJOIN_DEPOSIT_NULL(126201, "品牌商未设置押金"),
    DEALERJOIN_DEPOSIT_LESS(126202, "总押金小于已付的押金"),
    DEALERJOIN_DEPOSIT_NEW(126203, "设置的押金不能小于等于原来的押金"),
    DEALERJOIN_DEPOSIT_ZERO(126204, "押金不能小于0"),
    DEALERJOIN_DEPOSIT_FACTORY(126205, "必须是工厂店加盟关系才能进行操作"),
    DEALERJOIN_DEPOSIT_CLEARING_ZERO(126206, "押金的结算金额不能为空或0"),
    FACTORY_LINE_NULL(126207, "不存在工厂店产品线"),
    FACTORY_LINE_UNAPPLY(126208, "工厂店产品线未报名工厂店活动"),
    BRANDES_INFO_NULL(126209, "品牌信息不存在"),
    CANNOT_CHANGE_PRO_NO(126210, "产品货号已经在有效订单中,不能修改产品货号"),
    CANNOT_CHANGE_PRO_SKU(126211, "产品已经在有效订单中,不能修改颜色尺码"),
    FILE_INTERFACE_FAIL(126212, "调用文件服务器接口失败"),
    FAIL_BRAND_INVITED(126212, "不能邀请已终止合作过品牌的终端商"),
    FAIL_DEALER_INVITED(126213, "不能加盟已终止合作过品牌的终端商"),
    FAIL_JOIN_CREDIT(126214,"授信加盟条件欠缺,授信订单无效"),
    FAIL_CREDIT_CURRENT(126215,"授信额度不足，授信订单无效"),
    CHANGE_CREDIT_CURRENT(126216,"授信额度不一致"),
    ORDER_HAS_PAY(126217,"订单已支付，请勿重复支付"),
    ORDER_PAY_POINT(126218,"品牌扣点有误");
   


    private CommonConst(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }
    
    // 服务对象 经销商
    public static final Integer SERVICE_CATE_DEALER = 0X001;
    
    // 服务对象 品牌商
    public static final Integer SERVICE_CATE_BRAND  = 0X002;
    
    // 服务对象 代理商
    public static final Integer SERVICE_CATE_PROXY  = 0X003;
    
    // 密码长度为32位
    public static final Integer USER_PASSWORD_LEN   = 0X020;
    
    /**
     * 根据状态码获取状态码描述
     * @param code 状态码
     * @return String 状态码描述
     */
    public static String getMessage(Integer code)
    {
        String result = null;
        for (CommonConst c : CommonConst.values())
        {
            if (c.code.equals(code))
            {
                result = c.message;
            }
        }
        return result;
    }
    
    public Integer code;
    
    public String  message;
    
    /*
     * (non-Javadoc)
     * @see com.zttx.web.bean.EnumDescribable#getCode()
     */
    @Override
    public Integer getCode()
    {
        return this.code;
    }
    
    public void setCode(Integer code)
    {
        this.code = code;
    }
    
    /*
     * (non-Javadoc)
     * @see com.zttx.web.bean.EnumDescribable#getMessage()
     */
    @Override
    public String getMessage()
    {
        return this.message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
}
