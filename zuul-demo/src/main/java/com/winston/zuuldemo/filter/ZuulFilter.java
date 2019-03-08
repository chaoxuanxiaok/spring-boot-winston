package com.winston.zuuldemo.filter;

import com.netflix.zuul.IZuulFilter;

/**
 * ZuulFilter是过滤器的顶级父类
 * 这个类已经有了，这里只是对一些比较重要的方法做笔记
 */
public abstract class ZuulFilter implements IZuulFilter {
    /**
     *  返回字符串，代表过滤器的类型
     *      pre         请求在被路由之前 执行
     *      routing     在路由请求时     执行
     *      post        在routing和error过滤器之后调用
     *      error       处理请求时发生错误调用
     */
    abstract public String filterType();

    /**
     *  返回int值，定义过滤器的执行顺序
     *  数字越小，优先级越高
     */
    abstract public int filterOrder();

    /**
     *   boolean shouldFilter();    来自 IZuulFilter
     *  返回布尔值，判断该过滤器是否执行
     *   true执行，false不执行
     */


    /**
     *  Object run() throws ZuulException;  来自IZuulFilter
     *  过滤器的具体业务逻辑
     */

    /**
     * 正常流程：
     *      请求先到pre类型过滤器，再到routing类型过滤器进行路由，
     *      此时请求到达真正的服务提供者，返回的结果到达post类型过滤器，之后返回响应
     *
     * 异常流程：
     *      （1）pre或routing过滤器出现异常，直接进入error过滤器，error处理完毕，请求移交post类型过滤器，最后返回用户
     *      （2）error过滤器出现异常，最后也是进入post类型过滤器，再返回用户。
     *      （3）post过滤器出现异常，也会进入error类型过滤器，但之后不再进入post类型过滤器
     *
     *  使用场景：
     *      请求鉴权：               一般放在pre类型过滤器
     *      异常处理：               一般放在error类型和post类型过滤器结合处理
     *      服务调用时长统计：       一般是pre类型和post类型过滤器结合使用
     */
}
