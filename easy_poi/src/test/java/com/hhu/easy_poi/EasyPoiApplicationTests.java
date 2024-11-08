package com.hhu.easy_poi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.hhu.easy_poi.pojo.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;


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
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\Master\\Java\\myhjy-community\\excel\\user.xls");
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
        params.setSaveUrl("D:\\Master\\Java\\myhjy-community\\easy_poi\\src\\test\\excel");

        List<Emp> list = ExcelImportUtil.importExcel(new FileInputStream("D:\\Master\\Java\\myhjy-community\\excel\\emp.xls"), Emp.class, params);
        list.forEach(System.out::println);
    }

    /**
      * 功能描述：根据接收的Excel文件来导入多个sheet,根据索引可返回一个集合
      * @param filePath  导入文件路径
      * @param sheetIndex  导入sheet索引
      * @param titleRows  表标题的行数
      * @param headerRows 表头行数
      * @param pojoClass  Excel实体类
      * @return
      */
    public static <T> List<T> importMultiSheet(String filePath,
                                               int sheetIndex,
                                               Integer titleRows,
                                               Integer headerRows,
                                               Class<T> pojoClass){
        ImportParams params = new ImportParams();

        params.setStartSheetIndex(sheetIndex);
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);

        //是否保存本次上传的excel
        params.setNeedSave(false);
        //表示表头必须包含的字段,不包含 就报错.
        params.setImportFields(new String[]{"用户ID"});
        List<T> list = null;

        try {
            list = ExcelImportUtil.importExcel(new FileInputStream(filePath),pojoClass,params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Test
    public void testImportMultiSheet(){
        String excelPath = "D:\\Master\\Java\\myhjy-community\\excel\\login.xls";

        List<LoginUser> loginUsers0 = importMultiSheet(excelPath, 0, 1, 1, LoginUser.class);
        loginUsers0.forEach(System.out::println);

        System.out.println("------------------------------------------------");

        List<LoginUrl> loginUsers1 = importMultiSheet(excelPath, 1, 1, 1, LoginUrl.class);
        loginUsers1.forEach(System.out::println);
    }

    public void exportMultiSheet(Object... objects){
        ExportParams loginUserexportParams = new ExportParams();

        loginUserexportParams.setSheetName("登录用户");
        loginUserexportParams.setTitle("登录用户列表");

        HashMap<String, Object> sheet1Map = new HashMap<>();
        sheet1Map.put("title",loginUserexportParams);
        sheet1Map.put("entity",LoginUser.class);
        sheet1Map.put("data",objects[0]);

        ExportParams loginUrlexportParams = new ExportParams();

        loginUrlexportParams.setSheetName("Url");
        loginUrlexportParams.setTitle("查询URL");

        HashMap<String, Object> sheet2Map = new HashMap<>();
        sheet2Map.put("title",loginUrlexportParams);
        sheet2Map.put("entity",LoginUrl.class);
        sheet2Map.put("data",objects[1]);

        List<Map<String,Object>> sheetList = new ArrayList<>();
        sheetList.add(sheet1Map);
        sheetList.add(sheet2Map);

        Workbook workbook = ExcelExportUtil.exportExcel(sheetList, ExcelType.HSSF);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("D:\\Master\\Java\\myhjy-community\\excel\\exportLogin.xls");
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testexportMultiSheet(){
        List<LoginUser> sheet1 = new ArrayList<>();
        sheet1.add(new LoginUser("1001", "向阳", "123456", new Date(), "0"));
        sheet1.add(new LoginUser("1002", "文渊", "123456", new Date(), "1"));
        sheet1.add(new LoginUser("1003", "小李", "123456", new Date(), "0"));

        List<LoginUrl> sheet2 = new ArrayList<>();
        sheet2.add(new LoginUrl("1001", "get", "http://127.0.0.1:8080"));
        sheet2.add(new LoginUrl("1001", "post", "http://127.0.0.1:8080/logingout"));

        exportMultiSheet(sheet1,sheet2);
    }

}

