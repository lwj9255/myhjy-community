package com.hhu.easy_poi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.hhu.easy_poi.pojo.Card;
import com.hhu.easy_poi.pojo.Emp;
import com.hhu.easy_poi.pojo.Order;
import com.hhu.easy_poi.pojo.User;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


class EasyPoiApplicationTests {

    public List<User> getUsers(){
        List<User> users = new ArrayList<>();
        for(int i =0;i<10;i++){
            User user=new User();
            user.setAge(10+i);
            user.setBir(new Date());
            user.setName("a"+i);
            user.setId(String.valueOf(i));
            user.setStatus(String.valueOf(i%2));
            user.setHobby(Arrays.asList("游戏","吃饭"));
            user.setCard(new Card("334455200100001111"+i*4,"北京市朝阳区"+i+"号"));
            user.setOrders(Arrays.asList(new Order("0"+i,"游戏机"),new Order("1"+i,"电脑"),new Order("2"+i,"手机")));
            user.setPhoto("C:\\Users\\Administrator\\Desktop\\img\\girl.png");
            users.add(user);
        }
        return users;
    }

    @Test
    public void testExport() throws Exception {
        //1 配置对象
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户列表", "测试"), User.class, getUsers());
        //2 导出设置
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\Master\\Java\\user.xls");
        //3 导出
        workbook.write(fileOutputStream);
        //4 关闭
        fileOutputStream.close();
        workbook.close();
    }

    @Test
    public void testInport() throws Exception {
        ImportParams params=new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        params.setNeedSave(true);
        params.setSaveUrl("D:\\Master\\Java\\myhjy-community\\easy_poi\\src\\main\\resources\\static\\img");

        List<Emp> list = ExcelImportUtil.importExcel(new FileInputStream("D:\\Master\\Java\\emp.xls"), Emp.class, params);
        list.forEach(System.out::println);
    }

}
