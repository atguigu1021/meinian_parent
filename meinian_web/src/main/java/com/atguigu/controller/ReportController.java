package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.service.MemberService;
import com.atguigu.service.ReportService;
import com.atguigu.service.SetMealService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;
    @Reference
    private SetMealService setMealService;
    @Reference
    private ReportService reportService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        try {
            Map<String, Object> map = new HashMap<>();
            //处理月份集合
            List<String> months = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -12);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            for (int i = 0; i < 12; i++) {
                //第一个参数是月份 2018-7
                //第二个参数是月份+1个月
                calendar.add(Calendar.MONTH, 1);
                months.add(format.format(calendar.getTime()));
            }
            map.put("months", months);
            //处理会员数量集合
            List<Integer> memberCount = memberService.getMemberCountByMonth(months);
            map.put("memberCount", memberCount);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    @RequestMapping("/getSetMealReport")
    public Result getSetMealReport(){
        try {
            List<Map<String,Object>> setMealCount=setMealService.getSetMealReport();

            List<String> setMealNames=new ArrayList<>();
            for (Map<String, Object> stringObjectMap : setMealCount) {
                String name= (String) stringObjectMap.get("name");
                setMealNames.add(name);
            }
            Map<String,Object> map=new HashMap<>();
            map.put("setMealNames",setMealNames);
            map.put("setMealCount",setMealCount);
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        try {
            Map<String,Object> map=reportService.getBusinessReportData();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取数据
            Map<String, Object> result = reportService.getBusinessReportData();
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map<String,Object>> hotSetMeal = (List<Map<String, Object>>) result.get("hotSetMeal");
            //获取模板文件
            String realPath = request.getSession().getServletContext().getRealPath("template")
                    + File.separator+"report_template.xlsx";
            //工作簿
            Workbook workbook=new XSSFWorkbook(new File(realPath));
            Sheet sheet = workbook.getSheetAt(0);
            //将数据写入模板文件
            Row row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);
            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日出游数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周出游数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月出游数

            int rowNum = 12;
            for(Map map : hotSetMeal){//热门套餐
                String name = (String) map.get("name");
                Long setMeal_count = (Long) map.get("setMeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum ++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setMeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }
            //以流的形式输出文件
            // 下载的数据类型（excel类型）
            response.setContentType("application/vnd.ms-excel");
            // 设置下载形式(通过附件的形式下载)
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(response.getOutputStream());
            //关闭流
            workbook.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }




}