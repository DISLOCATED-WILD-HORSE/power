package mapper;

import entity.Sys_Configration_Data;
import entity.Sys_Configration_Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统配置
 */
public interface SystemConfigrationMapper {
    /**
     * 获取全部系统配置类型信息
     * @return
     */
    List<Sys_Configration_Type> getConfigTypeInfo();

    /**
     * 根据编号获取系统配置参数信息
     * @param typeId
     * @return
     */
    List<Sys_Configration_Data> getConfigDataInfoByID(@Param("typeId") String typeId);

    /**
     * 批量新增系统类型表
     * @param list
     * @return
     */
    int insertConfigTypeData(List<Sys_Configration_Type> list);

    /**
     * 批量更新系统类型表
     * @param list
     * @return
     */
    int updateConifgTypeData(List<Sys_Configration_Type> list);

    /**
     * 根据id查看数据是否存在
     * @param id
     * @return
     */
    int getCountByID(Integer id);

    //根据ID删除系统类型表数据
    int deleteConfigType(Integer id);

    //根据ID删除系统参数表数据
    int deleteConfigData(@Param("typeId") String typeId,@Param("id") String id);

    /**
     * 批量新增系统参数表
     * @param list
     * @return
     */
    int insertConfigDataInfo(List<Sys_Configration_Data> list);

    /**
     * 批量更新系统参数表
     * @param list
     * @return
     */
    int updateConifgDataInfo(List<Sys_Configration_Data> list);

    /**
     * 根据id查看系统参数表数据是否存在
     * @param id
     * @param typeId
     * @return
     */
    int getDataCountByID(@Param("id") String id,@Param("typeId") String typeId);
}
