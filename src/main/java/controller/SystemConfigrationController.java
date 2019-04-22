package controller;

import common.JsonResult;
import entity.Sys_Configration_Data;
import entity.Sys_Configration_Type;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.SystemConfigrationService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/systemConfig")
public class SystemConfigrationController {
    @Resource
    private SystemConfigrationService systemConfigrationService;

    /**
     * 跳转至系统配置页面
     * @return
     */
    @RequestMapping("/toConfig")
    public String toConfig(){
        return "sysConfig";
    }

    /**
     * 获取全部系统配置类型信息
     * @return
     */
    @RequestMapping("/getConfigTypeInfo")
    @ResponseBody
    public JsonResult getConfigTypeInfo(){
        List<Sys_Configration_Type> configration_typeList = systemConfigrationService.getConfigTypeInfo();
        return JsonResult.success("S",configration_typeList);
    }

    /**
     * 根据类型编号获取参数信息
     * @param typeId
     * @return
     */
    @RequestMapping("/getConfigDataInfo")
    @ResponseBody
    public JsonResult getConfigDataInfoByID(@RequestParam("typeId") String typeId){
        List<Sys_Configration_Data> configration_dataList = systemConfigrationService.getConfigDataInfoByID(typeId);
        return JsonResult.success("S",configration_dataList);
    }

    /**
     * 系统配置类型保存(包含新增和更新)
     * @param typeList
     * @return
     */
    @PostMapping("/saveConfig")
    @ResponseBody
    public JsonResult saveConfigInfo(@RequestBody List<Sys_Configration_Type> typeList){
        return systemConfigrationService.saveConfigType(typeList);
    }

    /**
     * 系统配置类型删除
     * @param selectData
     * @return
     */
    @PostMapping("/deleteConfig")
    @ResponseBody
    public JsonResult deleteConfigInfo(@RequestBody List<Sys_Configration_Type> selectData){
        return systemConfigrationService.deleteConfigInfo(selectData.get(0));
    }

    /**
     * 系统配置参数保存(包含新增和更新)
     * @param dataList
     * @return
     */
    @PostMapping("/saveConfigData")
    @ResponseBody
    public JsonResult saveConfigDataInfo(@RequestParam("typeId") String typeId, @RequestBody List<Sys_Configration_Data> dataList){
        return systemConfigrationService.saveConfigData(typeId,dataList);
    }

    @PostMapping("/deleteConfigData")
    @ResponseBody
    public JsonResult deleteConfigData(@RequestBody List<Sys_Configration_Data> selectData){
        return systemConfigrationService.deleteConfigData(selectData.get(0));
    }
}
