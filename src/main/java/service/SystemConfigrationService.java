package service;

import common.JsonResult;
import entity.Sys_Configration_Data;
import entity.Sys_Configration_Type;

import javax.xml.crypto.Data;
import java.util.List;

public interface SystemConfigrationService {
    /**
     * 获取全部系统配置类型信息
     * @return
     */
    List<Sys_Configration_Type> getConfigTypeInfo();

    /**
     * 根据类型编号获取参数数据信息
     * @param typeId
     * @return
     */
    List<Sys_Configration_Data> getConfigDataInfoByID(String typeId);

    /**
     * 系统配置类型保存（包含新增和更新）
     * @param list
     * @return
     */
    JsonResult saveConfigType(List<Sys_Configration_Type> list);

    /**
     * 删除选中行数据（系统类型）
     * @param selectData
     * @return
     */
    JsonResult deleteConfigInfo(Sys_Configration_Type selectData);

    /**
     * 系统配置参数保存（包含新增和更新）
     * @param list
     * @return
     */
    JsonResult saveConfigData(String typeId, List<Sys_Configration_Data> list);

    /**
     * 删除选中行数据（系统参数数据）
     * @param selectData
     * @return
     */
    JsonResult deleteConfigData(Sys_Configration_Data selectData);
}
