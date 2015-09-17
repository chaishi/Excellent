package edu.swust.cs.excellent.config;


import java.io.File;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.jfinal.core.Const;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Logger;

/**
 * 
 * 常量定义集
 *
 */
public class Constant {

	/**
	 * 新闻列表预览信息的最大长度
	 */
	public static int NEWS_PREVIEW_LENGTH=100;
	
	/**
	 * 是否将数据库备份发送到管理员邮箱
	 */
	public static boolean DB_IS_TOSENDMAIL=false;
	
	/**
	 * 邮件提醒管理员系统异常的警告错误日志的上限阈值(M)
	 */
	public static long WARN_ERROR_LOG_LIMIT=50;
	
	/**
	 * 保留的数据库备份份数
	 */
	public static int DB_BACKUP_COPIES=3;
	
	/**
	 * 默认数据库文件备份路径
	 */
	public static final String DEFAULT_DB_BACKUP_PATH="DBBackUp";
	
	/**
	 * 数据库文件备份路径
	 */
	public static String DB_BACKUP_PATH="DBBackUp";
	
	/**
	 * 警告错误日志存储时间(天)
	 */
	public static int WARN_ERROR_LOG_LIFETIME=30;
	
	/**
	 * 普通日志存储时间(天)
	 */
	public static int INFO_LOG_LIFETIME=10;
	
	/**
	 * 日志路径系统变量Key
	 */
	public static String LOG4JDIRKEY="log4jDir";
	
	/**
	 * 日志存放目录
	 */
	public static String LOG4JDIR="log";
	
	
	public static int PUPPET_CLASS_ID = 1;
	
	/**
	 * 上传File路径
	 */
	public static  String UPLOAD_FILE_PATH = "file";

	/**
	 * 上传Image路径
	 */
	public static  String UPLOAD_IMGAGE_PATH = "img";
	
	/**
	 * Image文件的后缀名
	 */
	public  static String[] UPLOAD_IMGAE_EXTENSION = {".jpg",".jepg",".bmp",".gif",".jpeg2000",".tiff",".psd",".png",".svg"};
	
	/**
	 * Excel文件的后缀名
	 */
	public  static String[] UPLOAD_FILE_EXTENSION = {".xml",".xls",".xmls",".h"};
	
    /**
     * 文件的暂存路径
     */
   // public  static String FILE_TEMPORARY_SVAE_DIR = "webRoot/upload";
	public  static String FILE_TEMPORARY_SVAE_DIR = "webRoot"+File.separator+"upload";
    
    /**
     * 从excel导入数据时的数据有效列数
     */
    public static int EXCEL_GROUP_VALID_COLUMNS = 5;
    
    /**
     * 数据库url
     */
    public  static String DB_URL="jdbc:mysql://127.0.0.1:3306/excellent?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
    
    /**
     * 数据库用户名
     */
    public static  String  DB_USER = "root";

    /**数据库密码**/
    public static String  DB_PSWD = "";

    /**开发模式**/
    public static boolean DEVMODE = true;
    
    /**返回值起始路径**/
  //public static String BASE_PATH = "/excellent"+File.separator;
    public static String BASE_PATH = File.separator+"excellent";

    /** 定时器配置    */
	public static String JOB_PROPERTY="job.properties";
    
  
    public static final String TEACHER = "教师";
    
    public static final String STUDENT = "学生";
    
    public static final String ADMIN = "管理员";
    
    
    /**
	 * 权限登记
	 * 教师，班长，团支书，学委，文体委员,生活委员，普通同学,游客
	 */
    private final static String[] AUTHORITY = {"教师","班长","团支书","学习委员","文体委员","生活委员","普通同学","游客"};
    
    public final static String AUTHORITY_ADMIN = "管理员";
    
    public final static String AUTHORITY_TEACHER = "教师";

    public final static String AUTHORITY_MONITOR =  "班长";
     
    public final static String AUTHORITY_SECRETARY  = "团支书";
    
    public final static String AUTHORITY_ACADEMIC_REP  = "学习委员";
    
    public final static String AUTHORITY_SPORTS_REP  = "文体委员";

    public final static String AUTHORITY_LIFE_REP  = "生活委员";
    
    public final static String AUTHORITY_NORMAL_STUDENT  = "普通同学";
    
    public final static String AUTHORITY_STUDENT  = "所有学生";
    
    public final static String AUTHORITY_TOURIST  = "游客";
    
  
    
    private Constant(){
    	
    }
 
    /**
     * @param 常量配置文件路径
     */
	public  static  void init(String fileName){
		Prop prop = PropKit.use(fileName, Const.DEFAULT_ENCODING);
		Properties properties = prop.getProperties();
		
	
		 Field[] fields = Constant.class.getFields();
		 for (Field p:fields){
			String r = properties.getProperty(p.getName());
			if (r!=null){
				try {
					if (p.getName()=="UPLOAD_FILE_EXTENSION" || p.getName()=="UPLOAD_IMGAE_EXTENSION"){
						p.set(p.getName(), r.split(","));
					}else if (p.getName()=="EXCEL_GROUP_VALID_COLUMNS"){
						p.set(p.getName(),Integer.parseInt(r));
					}else  if (p.getName()=="DEVMODE"){
						p.set(p.getName(), Boolean.getBoolean(r));
					}else if (p.getName()=="PUPPET_CLASS_ID"){
						p.set(p.getName(), Integer.parseInt(r));
					}else if (p.getName()=="WARN_ERROR_LOG_LIFETIME"){
						p.set(p.getName(), Integer.parseInt(r));
					}else if (p.getName()=="INFO_LOG_LIFETIME"){
						p.set(p.getName(),Integer.parseInt(r));
					}else if (p.getName()=="DB_BACKUP_COPIES"){
						p.set(p.getName(), Integer.parseInt(r));
					}else if (p.getName()=="DB_IS_TOSENDMAIL"){
						p.set(p.getName(),Boolean.valueOf(r));
					}else if (p.getName()=="NEWS_PREVIEW_LENGTH"){
						p.set(p.getName(), Integer.parseInt(r));
					}else{
					   p.set(p.getName(),r);
					   if (p.getName().equals("DB_BACKUP_PATH")){
						   p.set(p.getName(),initDBBackUp(r));
					   }
					}
				} catch (Exception e) {
					System.out.println("======================================");
					e.printStackTrace();
					System.out.println("!--建议您使用默认字段--!");
					System.out.println("!--字段"+p.getName()+"无法设置,请检查字段名是否正"
							+ "确或者不允许您修改--!");
					System.out.println("======================================");
					continue;
				}
			}
		 }
	}

	private static String initDBBackUp(String r){
		File file=new File(r);
		if (!file.exists()){
			file.mkdir();
		}else if (!file.isDirectory()){
			Logger.getLogger("DiskWE").warn("数据库备份文件路径不是一个文件夹,系统将使用默认路径");
			Logger.getLogger("MAIL").error("数据库备份文件路径不是一个文件夹,系统将使用默认路径");
			return initDBBackUp(Constant.DEFAULT_DB_BACKUP_PATH);
		}
		return r;
	}
	

}
