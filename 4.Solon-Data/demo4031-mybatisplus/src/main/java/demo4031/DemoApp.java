package demo4031;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import demo4031.dso.MybatisSqlSessionFactoryBuilderImpl;
import org.apache.ibatis.session.Configuration;
import org.noear.solon.SolonBuilder;
import org.noear.solon.core.Aop;

/**
 *
 * 演示用到的表结构::
 *
 * CREATE TABLE `appx` (
 *   `app_id` int NOT NULL AUTO_INCREMENT COMMENT '应用ID',
 *   `app_key` varchar(40) DEFAULT NULL COMMENT '应用访问KEY',
 *   `akey` varchar(40) DEFAULT NULL COMMENT '（用于取代app id 形成的唯一key） //一般用于推广注册之类',
 *   `ugroup_id` int DEFAULT '0' COMMENT '加入的用户组ID',
 *   `agroup_id` int DEFAULT NULL COMMENT '加入的应用组ID',
 *   `name` varchar(50) DEFAULT NULL COMMENT '应用名称',
 *   `note` varchar(50) DEFAULT NULL COMMENT '应用备注',
 *   `ar_is_setting` int NOT NULL DEFAULT '0' COMMENT '是否开放设置',
 *   `ar_is_examine` int NOT NULL DEFAULT '0' COMMENT '是否审核中(0: 没审核 ；1：审核中)',
 *   `ar_examine_ver` int NOT NULL DEFAULT '0' COMMENT '审核 中的版本号',
 *   `log_fulltime` datetime DEFAULT NULL,
 *   PRIMARY KEY (`app_id`),
 *   UNIQUE KEY `IX_akey` (`akey`) USING BTREE
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='应用表';
 * */
public class DemoApp {
    public static void main(String[] args) {

        new SolonBuilder()
                .onEvent(Configuration.class, e -> {
                    MybatisPlusInterceptor plusInterceptor = new MybatisPlusInterceptor();

                    plusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

                    e.addInterceptor(plusInterceptor);
                })
                .onPluginLoadEnd(e -> {
                    //重新定义 SqlSessionFactoryBuilder（没事儿，别用它）
                    Aop.wrapAndPut(MybatisSqlSessionFactoryBuilder.class, new MybatisSqlSessionFactoryBuilderImpl());
                })
                .start(DemoApp.class, args, (app) -> {
                    //app.beanMake(MybatisConfiguration.class);
                });
    }
}