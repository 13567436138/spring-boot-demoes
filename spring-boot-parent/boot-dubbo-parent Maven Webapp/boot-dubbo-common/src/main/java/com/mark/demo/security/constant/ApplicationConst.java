package com.mark.demo.security.constant;

public class ApplicationConst
{
    // 私有构造器，防止类的实例化
    private ApplicationConst()
    {
        super();
    }
    
    public static final String   DOMAIN_NAME            = "www.mark.com";
    
    // 项目名称定义
    public static final String   PROJECT_NAME           = "Zttx-Web";
    
    // 系统默认字符编码
    public static final String   LANGUAGE_UT            = CharsetConst.CHARSET_UT;
    
    // 主键ID长度
    public static final int      UUID_SIZE              = 32;
    
    // 系统默认每页记录数
    public static final Integer  DEFAULT_PAGE_SIZE      = 20;
    
    // 系统默认每页最大记录数
    public static final Integer  MAX_PAGE_SIZE          = DEFAULT_PAGE_SIZE * 10;
    
    // 每隔多少次提交事务
    public static final Integer  BATCH_SIZE             = 20;
    
    // 图库默认分类名称
    public static final String   PICCATE_DEFAULT_NAME   = "默认分类";
    
    // 图片域名
    public static final String   PIC_DOMAIN             = "";
    
    // 默认水印文字
    public static final String   WATER_MARK_WORD        = DOMAIN_NAME;
    
    // 品牌商图库最大容量2000MB
    public static final long     BRAND_PIC_TOTAL_SIZE   = 2000 * 1024 * 1024;
    
    // Https
    public static final String   SSL_DEFAULT_SCHEME     = "https";
    
    // Http
    public static final String   DEFAULT_SCHEME         = "http";
    
    // https协议端口
    public static final int      SSL_DEFAULT_PORT       = 443;
    
    // 系统默认当前页
    public static final Integer  DEFAULT_CURRENT_PAGE   = 1;
    
    public static final String   ENCRYPT                = "MD5";
    
    // 创建TelCode 时间
    public static final Integer  CODE_VALID_CREATE      = 60 * 1000 * 1;
    
    // 手机验证码有效时间
    public static final Integer  CODE_VALID             = 60 * 1000 * 5;
    
    // 邮箱验证有效时间
    public static final Integer  EMAIL_VALID_TIME       = 60 * 60 * 1000 * 24;
    
    // 登录有效时间
    public static final Integer  LOGIN_VALID            = 60 * 60;
    
    // 系统年月格式，如：2010-06
    public static final String   DATE_FORMAT_YM         = "yyyy-MM";
    
    // 系统年月日格式，如：2010-08-19
    public static final String   DATE_FORMAT_YMD        = "yyyy-MM-dd";
    
    // 系统年月日格式，如：2010-08-19 05:23:20
    public static final String   DATE_FORMAT_YMDHMS     = "yyyy-MM-dd HH:mm:ss";
    
    // 文件上传路径
    public static final String   UPLOAD                 = "/upload";
    
    // 图片上传时，允许的contentType
    public static final String[] ALLOW_IMAGE            = {"image/bmp", "image/gif", "image/jpeg", "image/jpg", "image/png", "image/x-png", "image/pjpeg"};
    
    // 图片上传时，允许的后缀
    public static final String[] IMAGE_SUFFIX           = {"bmp", "jpg", "gif", "jpeg", "png"};
    
    // Flash控件图片批量上传 flash上传contentType 都是 "application/octet-stream"
    public static final String[] ALLOW_FLASH_IMAGE      = {"application/octet-stream"};
    
    // 文件上传时，允许的文件格式
    public static final String[] ALLOW_FILE             = {"application/kset", "application/kswps", "text/plain", "application/pdf","application/vnd.ms-excel"};
    
    // 允许的文件后缀名
    public static final String[] FILE_SUFFIX            = {};
    
    // 文件上传时，允许的视频格式
    public static final String[] ALLOW_VIDEO            = {"video/x-ms-wmv", "application/octet-stream", "video/flv"};
    
    // 允许的视频上传后缀
    public static final String[] VIDEO_SUFFIX           = {};
    
    // 文件上传时，允许的Flash格式
    public static final String[] ALLOW_FLASH            = {"application/octet-stream", "application/x-shockwave-flash", "video/flv"};
    
    // 允许的Flash上传后缀
    public static final String[] FLASH_SUFFIX           = {};
    
    public static final String[] ALLOW_ZIP              = {"application/octet-stream", "application/zip", "application/x-zip-compressed", "application/x-rar-compressed"};
    
    // 允许的压缩文件上传后缀
    public static final String[] ZIP_SUFFIX             = {"zip", "rar", "7z"};
    
    public static final String[] FLV                    = {"flv"};
    
    // 当类型都不正确时，将启用通用匹配后缀方式
    public static final Object[] COMMON_SUFFIX          = {IMAGE_SUFFIX, VIDEO_SUFFIX, FILE_SUFFIX, ZIP_SUFFIX, FLASH_SUFFIX};
    
    // 所有视频和flash格式都转成这个
    public final static String   VIDEO_FORMAT           = ".flv";
    
    // 列表记录属性名称（主要在controller中保持对象时使用，如：model.setAttribute("result",result)）
    public static final String   RESULT                 = "result";
    
    // 数据对象属性名称（主要在controller中保持对象时使用，如：model.setAttribute("model",object)）
    public static final String   MODEL                  = "model";
    
    public static final String   DEMO                   = "/Demo";
    
    // 品牌商模块
    public static final String   BRAND                  = "/brand";
    
    // 加盟入驻模块
    public static final String   APPLY                  = "/apply";
    
    // 帮助
    public static final String   ABOUT                  = "/about";
    
    // 帮助模块
    public static final String   HELP                   = "/help";
    
    // 经销商模块
    public static final String   DEALER                 = "/dealer";
    
    // 公用模块(如：注册、取回密码)
    public static final String   COMMON                 = "/common";
    
    // 行业资讯
    public static final String   INFO                   = "/info";
    
    // 加盟入驻
    public static final String   JOIN                   = "/join";
    
    // 市场模块
    public static final String   MARKET                 = "/market";
    
    // 店铺
    public static final String   SHOP                   = "/shop";
    
    // 订货会
    public static final String   MEET                   = "/meet";
    
    // 交易会
    public static final String   TRADEMEET              = "/tradeMeeting";
    
    // 装修展示
    public static final String   DECORATION             = "/decoration";
    
    // 第三方对接
    public static final String   CLIENT                 = "/client";
    
    // 商学院
    public static final String   SCHOOL                 = "/school";
    
    // 代理商模块
    public static final String   AGENT                  = "/agent";
    
    // 代理商手机模块
    public static final String   AGENT_MOBILE           = "/agentMobile";
    
    // 新增操作标志
    public static final String   INSERT_OPRATER         = "insert";
    
    // 修改操作标志
    public static final String   UPDATE_OPRATER         = "update";
    
    // Quartz applicationContext Key
    public static final String   QUARTZ_APPLICATION_KEY = "applicationContext";
    
    public static final String   CACHE_PREFIX_NAME      = "ZTTX_";
}
