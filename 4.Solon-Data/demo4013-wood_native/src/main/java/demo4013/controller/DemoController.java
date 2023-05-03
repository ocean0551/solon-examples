package demo4013.controller;

import demo4013.dso.mapper.SqlMapper;
import demo4013.model.AppxModel;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;
import org.noear.wood.annotation.Db;

@Mapping("/demo/")
@Controller
public class DemoController {
    /**
     * 使用SqlMapper默认的数据库注入
     * */
    @Db
    SqlMapper sqlMapper1;


    @Mapping("/test")
    public AppxModel db1() throws Exception{
        return sqlMapper1.appx_get2(1);
    }
}
