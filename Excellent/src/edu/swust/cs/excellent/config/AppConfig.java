package edu.swust.cs.excellent.config;

import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.spring.SpringPlugin;
import com.jfinal.render.ViewType;

import edu.swust.cs.excellent.controller.ClassController;
import edu.swust.cs.excellent.controller.FileController;
import edu.swust.cs.excellent.controller.IndexController;
import edu.swust.cs.excellent.controller.NewsController;
import edu.swust.cs.excellent.controller.NoteController;
import edu.swust.cs.excellent.controller.StudentController;
import edu.swust.cs.excellent.model.*;
import edu.swust.cs.excellent.model.Class;
import edu.swust.cs.excellent.util.QuartzPlugin;


public class AppConfig extends JFinalConfig{

    /**
     * 配置常量.
     */
    @Override
    public void configConstant(Constants me) {
        // TODO Auto-generated method stub
    	//加载配置项
    	Constant.init("constant.txt");
        me.setDevMode(Constant.DEVMODE);
        me.setViewType(ViewType.JSP);
        me.setBaseViewPath(Constant.BASE_PATH);
       
    }

    /**
     * 配置路由.
     */
    @Override
    public void configRoute(Routes me) {
        // TODO Auto-generated method stub
        me.add("/",IndexController.class);
        me.add("/stu",StudentController.class);
        me.add("/news",NewsController.class);
        me.add("/class",ClassController.class);
        me.add("/note",NoteController.class);
        me.add("/file",FileController.class);
    }

    /**
     * 配置插件.
     */
    @Override
    public void configPlugin(Plugins me) {

    	me.add(new QuartzPlugin());
    	
        C3p0Plugin c3p0Plugin = new C3p0Plugin(Constant.DB_URL,
        		                               Constant.DB_USER,
                                               Constant.DB_PSWD);
        me.add(c3p0Plugin);
        
        me.add(new SpringPlugin(new FileSystemXmlApplicationContext(PathKit.getRootClassPath() + "/applicationContext.xml")));
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        me.add(arp);
        arp.addMapping("admin", Admin.class);
        arp.addMapping("award", Award.class);
        arp.addMapping("class", Class.class);
        arp.addMapping("comment", Comment.class);
        arp.addMapping("news", News.class);
        arp.addMapping("notes", Note.class);
        arp.addMapping("student", Student.class);
        arp.addMapping("teacher", Teacher.class);
        arp.addMapping("group", Group.class);
        arp.addMapping("news_extend", News_extend.class);
    }

    /**
     * 编辑拦截器
     * ee com.jfinal.config.JFinalConfig#configInterceptor(com.jfinal.config.Interceptors)
     */
    @Override
    public void configInterceptor(Interceptors me) {
        // TODO Auto-generated method stub
        
        
    }

    @Override
    public void configHandler(Handlers me) {
        // TODO Auto-generated method stub
        me.add(new SessionHandler());
    }

}
