package cn.jack.core;

import cn.jack.annotation.Jcontroller;
import cn.jack.annotation.JrequestMapping;
import cn.jack.annotation.Jservice;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

/**
 * @Auther: ZhangXing
 * @Date: 2019/1/6 * @Description: cn.jack.core * @version: 1.0
 */

public class JdispatcherServlet extends HttpServlet {

    private static Properties properties = new Properties();

    private static List classNames = new JarrayList();

    private static Map ioc = new JhashMap();

    private static Map handlerMapping = new JhashMap();

    public void init(ServletConfig config) throws ServletException {
        //1.加载配置文件
        doLoadConfig(config);
        // 2.扫描用户设定的包下面所有的类
        doScanPackage(properties.getProperty("scanPackage"));
        //3.拿到扫描到的类,通过反射机制,实例化,并且放到ioc容器中(k-v  beanName-bean)
        doInstance();
        // 4.初始化HandlerMapping(将url和method对应上)
        initHandlerMapping();
        System.out.println("handlerMapping=" + handlerMapping.toString());
        System.out.println("ioc=" + ioc.toString());
    }

    private void initHandlerMapping() {
        Object[] obj = ioc.forech();
        for (Object o : obj) {
            Class<?> clazz = o.getClass();

            if (clazz.isAnnotationPresent(Jcontroller.class)) {
                StringBuilder baseUrl = new StringBuilder();
                //看controller类上是否有mapping
                if (clazz.isAnnotationPresent(JrequestMapping.class)) {
                    JrequestMapping annotation = clazz.getAnnotation(JrequestMapping.class);
                    baseUrl.append(annotation.value());
                }
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(JrequestMapping.class)) {
                        JrequestMapping annotation = method.getAnnotation(JrequestMapping.class);
                        String url = annotation.value();

                        url = (baseUrl.toString() + url);
                        Handler handler = new Handler(o, method);
                        handlerMapping.put(url, handler);
                    } else {
                        continue;
                    }
                }
            } else {
                continue;
            }
        }
    }

    private void doInstance() {
        for (int i = 0; i < classNames.size(); i++) {
            try {
                String className = (String) classNames.get(i);
                //反射来实例化(只有加@Jcontroller或者@Jservice需要实例化)
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Jcontroller.class)) {
                    Jcontroller controller = (Jcontroller) clazz.getAnnotation(Jcontroller.class);
                    String key = controller.value();
                    if ("".equals(key)) {
                        key = clazz.getSimpleName();
                    }
                    ioc.put(key, clazz.newInstance());
                } else if (clazz.isAnnotationPresent(Jservice.class)) {
                    Jservice service = (Jservice) clazz.getAnnotation(Jservice.class);
                    String key = service.value();
                    if ("".equals(key)) {
                        key = clazz.getSimpleName();
                    }
                    ioc.put(key, clazz.newInstance());
                } else {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    private void doScanPackage(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                //递归读取包
                doScanPackage(scanPackage + "." + file.getName());
            } else {
                String className = scanPackage + "." + file.getName().replace(".class", "");
                classNames.add(className);
            }
        }

    }

    private void doLoadConfig(ServletConfig config) {
// 把web.xml中的contextConfigLocation对应value值的文件加载到流里面
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(config.getInitParameter
                ("contextConfigLocation"));
        try {
            //用Properties文件加载文件里的内容
            properties.load(resourceAsStream);
        } catch (IOException e) {
            System.out.printf("加载初始化文件异常...%s%n", e.toString());
        } finally {
            //关流
            if (null != resourceAsStream) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    System.out.printf("关闭流失败...%s%n", e.toString());
                }
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //处理请求
            doDispatch(req, resp);
        } catch (Exception e) {
            System.out.printf("服务器异常信息打印...%s%n", e.toString());
            resp.getWriter().write("服务器异常");
        }

    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        StringBuilder url = new StringBuilder(req.getRequestURI());
        int length = req.getContextPath().length();
        String substring = url.substring(length);
        if (!this.handlerMapping.containsKey(substring)) {
            resp.getWriter().write("404 未找到资源!");
            return;
        }
        Handler heander = (Handler) this.handlerMapping.get(substring);
        System.out.println("heander=" + heander);
        //获取方法的参数列表
        Class<?>[] parameterTypes = heander.getMethod().getParameterTypes();

        //获取请求的参数
        java.util.Map<String, String[]> parameterMap = req.getParameterMap();

        //保存参数值
        Object[] paramValues = new Object[parameterTypes.length];

        //方法的参数列表
        if (parameterTypes != null) {
            for (int i = 0; i < parameterTypes.length; i++) {
                //根据参数名称，做某些处理
                String requestParam = parameterTypes[i].getSimpleName();
                if (requestParam.equals("HttpServletRequest")) {
                    paramValues[i] = req;
                } else if (requestParam.equals("HttpServletResponse")) {
                    paramValues[i] = resp;
                } else if (parameterMap != null) {
                    for (java.util.Map.Entry<String, String[]> param : parameterMap.entrySet()) {
                        String value = Arrays.toString(param.getValue());
                        paramValues[i] = value;
                    }
                }
            }
        }
        //利用反射机制来调用
        try {
            Object rest = heander.getMethod().invoke(heander.getCon(), paramValues);//第一个参数是method所对应的实例 在ioc容器中
            resp.getWriter().write(rest.toString());
        } catch (Exception e) {
            System.out.println("heander.getMethod().invoke异常");
            e.printStackTrace();
        }
    }
}
