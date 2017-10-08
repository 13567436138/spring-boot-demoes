package com.mark.demo.security.constant;

import com.mark.demo.security.entity.EnumDescribable;

public enum ClientConst implements EnumDescribable
{
    // 状态码范围：121000-125000
    SUCCESS(126000, "成功"),
    FAILURE(121008, "失败"),
    ILLEGAL_REQUEST(121000, "非法请求"),
    DESERROR(121001, "DES校验失败"),
    LENERROR(121002, "数据长度校验失败"),
    KEYERROR(121003, "身份认证失败"),
    DBERROR(121004, "数据访问失败"),
    NULERROR(121005, "请求参数为空"),
    AREAEXIT(121006, "地区编码已经存在"),
    WRITECACHEERROR(121007, "写缓存失败"),
    // DAOERROR(121008, "数据操作失败"),
    PRODUCTUNITEXIST(121009, "产品单位信息已存在"),
    WEBTEMPLATEEXIST(121010, "网站模板信息已存在"),
    SERVICECATEEXIST(121011, "网站服务分类已存在"),
    SERVICEEXIST(121012, "该网站服务已存在"),
    OBJECTEXIST(121013, "请求数据不存在"),
    SCHOOLCATEEXIST(121014, "该资讯类别已存在"),
    HASSONS(121015, "存在子类不能操作"),
    PARAMERROR(121016, "请求参数异常"),
    WEBMODULE_NOT_EXITS(121016, "WebModule数据不存在"),
    WEBMODULE_EXITS_CHILDREN(121017, "WebModule存在下层数据"),
    WEBMODULE_PARENT_NOT_EXITS(121018, "WebModule上层数据不存在"),
    WEBTEMPLATE_NOT_EXITS(121019, "WebTemplate数据不存在"),
    WEBTEMPLATE_EXITS_CHILDREN(121020, "WebTemplate存在下层数据"),
    WEBTEMPLATE_PARENT_NOT_EXITS(121021, "WebTemplate上层数据不存在"),
    WEBADDRESS_NOT_EXITS(121022, "WebAddress数据不存在"),
    WEBADDRESS_EXITS_CHILDREN(121023, "WebAddress存在下层数据"),
    WEBADDRESS_PARENT_NOT_EXITS(121024, "WebAddress上层数据不存在"),
    WEBDOCUMENT_NOT_EXITS(121025, "WebDocument数据不存在"),
    WEBDOCUMENT_PARENT_NOT_EXITS(121026, "WebDocument上层数据不存在"),
    WEBMODULE_EXITS(121027, "参数对应的WebModule数据已经存在"),
    WEBADDRESS_EXITS(121028, "参数对应的WebAddress数据已经在"),
    WEBDOCUMENT_EXITS(121029, "参数对应的WebDocument数据已经在"),
    NEED_PARAMERROR(121030, "资料编号不存在"),
    NEED_USERMOBILE(121031, "手机号码不存在"),
    NEED_USERPASSWORD(121032, "密码不存在"),
    NEED_ACCOUNTTYPE(121033, "帐号类型错误"),
    NEED_USERNAME(121034, "用户名称不存在"),
    NEED_AREANO(121035, "所在地区错误"),
    NEED_REFRENCEID(121036, "资料编号不存在"),
    IO_ERROR(121037, "读写错误"),
    ERROR_HANDLE(121038, "非法操作"),
    AUDITWHY_NOTNULL(121039, "审核不通过原因不能为空"),
    ADVERTKEY_EXITS(121040, "广告位key已经存在！"),
    MODELKEY_EXITS(121041, "模块Key已经存在！"),
    ARTICLETEXT_NOT_NULL(121042, "文章内容不能为空"),
    MEETMARK_NOT_NULL(121043, "详细描述不能为空"),
    CLIENT_PARAM_ERROR(121044, "未传指定请求参数"),
    CLIENT_AREADY_SUCCESS(121045, "已审核通过,不用再次审核"),
    CLIENT_TYPE_OVERTOP(121046, "超出指定类型"),
    ADD_INDEX_ERROR(121047, "添加索引失败"),
    DEAL_LEVEL_ERROR(121048, "类目的级别已经是最后一级了"),
    NOT_PASS_REASON(121049, "审核不通过原因不能为空"),
    MAIL_SEND_ERROR(121050, "邮件发送失败"),
    RECHARGE_ERROR(121051, "该用户已经存在此交易类型的金额记录"),
    NOT_AREA_LIST(121052, "没有授权区域");

    /**
     * 根据枚举值 返回 对应的 EnumDescribable 实现
     * @param code 待查找的 code 值
     * @return EnumDescribable
     * @author 郭小亮
     */
    public static EnumDescribable getEnumByCode(Integer code)
    {
        ClientConst[] allConst = ClientConst.values();
        ClientConst cc;
        for (int i = 0; i < allConst.length; i++)
        {
            cc = allConst[i];
            if (cc.code.intValue() == code) { return cc; }
        }
        return null;
    }
    
    private ClientConst(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }
    
    public static String getMessage(Integer code)
    {
        String result = null;
        for (ClientConst c : ClientConst.values())
        {
            if (c.code.equals(code))
            {
                result = c.message;
            }
        }
        return result;
    }
    
    public Integer             code;
    
    public String              message;
    
    // 客户端类型：交易平台
    public final static Short  CUS_TYPE_TRADE = 0x01;
    
    // 客户端类型：支撑系统
    public final static Short  CUS_TYPE_INNER = 0x02;
    
    // 客户端类型：支付平台
    public final static Short  CUS_TYPE_PAY   = 0x03;
    
    // 支撑平台文本编辑器传递时约定的变量
    public final static String HTML           = "html";
    
    // 支撑平台文本编辑器图片上传时约定的变量
    public final static String IMG_PATH       = "img";
    
    /*
     * (non-Javadoc)
     * @see com.zttx.web.bean.EnumDescribable#getCode()
     */
    @Override
    public Integer getCode()
    {
        return this.code;
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
}
