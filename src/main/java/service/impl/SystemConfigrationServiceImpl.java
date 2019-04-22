package service.impl;

import common.JsonResult;
import common.MyException;
import entity.Sys_Configration_Data;
import entity.Sys_Configration_Type;
import mapper.SystemConfigrationMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.SystemConfigrationService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service("SystemConfigrationService")
public class SystemConfigrationServiceImpl implements SystemConfigrationService {
    @Resource
    private SystemConfigrationMapper systemConfigrationMapper;
    @Override
    public List<Sys_Configration_Type> getConfigTypeInfo(){
        List<Sys_Configration_Type> sysConfigrationTypeList = systemConfigrationMapper.getConfigTypeInfo();
        if(sysConfigrationTypeList!=null&&sysConfigrationTypeList.size()>0){
            return sysConfigrationTypeList;
        }else{
            return null;
        }
    }

    @Override
    public List<Sys_Configration_Data> getConfigDataInfoByID(String typeId){
        return systemConfigrationMapper.getConfigDataInfoByID(typeId);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor= Exception.class,timeout = 60)
    public JsonResult saveConfigType(List<Sys_Configration_Type> typeList){
        try {
            //新增集合
            List<Sys_Configration_Type> addTypeList = new ArrayList<>();
            //更新集合
            List<Sys_Configration_Type> updateTypeList = new ArrayList<>();
            if(typeList!=null&&typeList.size()>0){
                for (Sys_Configration_Type item : typeList){
                    int result = systemConfigrationMapper.getCountByID(item.getId());
                    if(result>0){
                        updateTypeList.add(item);
                    }else{
                        addTypeList.add(item);
                    }
                }
                int result2=0;
                int result3=0;
                if(addTypeList.size()>0&&updateTypeList.size()>0){
                    result2 = systemConfigrationMapper.insertConfigTypeData(addTypeList);
                    result3 = systemConfigrationMapper.updateConifgTypeData(updateTypeList);
                    if(result2>0&&result3>0){
                        return JsonResult.success("保存成功");
                    }
                    else{
                        return JsonResult.error("保存失败");
                    }
                }else{
                    if(addTypeList.size()>0 && updateTypeList.size()<=0){
                        result2 = systemConfigrationMapper.insertConfigTypeData(addTypeList);
                        if(result2>0){
                            return JsonResult.success("保存成功");
                        }else{
                            return JsonResult.error("保存失败");
                        }
                    }else if(addTypeList.size()<=0 && updateTypeList.size()>0){
                        result3 = systemConfigrationMapper.updateConifgTypeData(updateTypeList);
                        if(result3>0){
                            return JsonResult.success("保存成功");
                        }else{
                            return JsonResult.error("保存失败");
                        }
                    }else{
                        return JsonResult.error("数据未变动，请检查！");
                    }
                }
            }else{
                return JsonResult.error("参数集合列表为空");
            }
        }catch(Exception ex){
            throw new MyException(ex,"系统配置类型更新和保存异常！");
        }
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor= Exception.class,timeout = 60)
    public JsonResult deleteConfigInfo(Sys_Configration_Type selectData){
        try {
            if(selectData!=null){
                List<Sys_Configration_Data> dataList = systemConfigrationMapper.getConfigDataInfoByID(selectData.getTypeId());
                if(dataList!=null&&dataList.size()>0){
                    int result1 = systemConfigrationMapper.deleteConfigData(selectData.getTypeId(),"");
                    int result2 = systemConfigrationMapper.deleteConfigType(selectData.getId());

                    if(result1>0&&result2>0){
                        return JsonResult.success("删除成功！");
                    }else{
                        return JsonResult.error("删除失败！");
                    }
                }else{
                    int result2 = systemConfigrationMapper.deleteConfigType(selectData.getId());
                    if(result2>0){
                        return JsonResult.success("删除成功！");
                    }else{
                        return JsonResult.error("删除失败！");
                    }
                }
            }else{
                return JsonResult.error("参数对象为空！");
            }
        } catch (Exception e) {
            throw new MyException(e,"系统配置类型删除异常！");
        }
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor= Exception.class,timeout = 60)
    public JsonResult saveConfigData(String typeId,List<Sys_Configration_Data> dataList){
        try {
            //新增集合
            List<Sys_Configration_Data> addDataList = new ArrayList<>();
            //更新集合
            List<Sys_Configration_Data> updateDataList = new ArrayList<>();
            if(dataList!=null&&dataList.size()>0){
                for (Sys_Configration_Data item : dataList){
                    item.setTypeId(typeId);
                    //通过id验证表是否存在这条数据
                    int result = systemConfigrationMapper.getDataCountByID(item.getId(),typeId);
                    if(result>0){
                        //存在即是更新
                        updateDataList.add(item);
                    }else{
                        addDataList.add(item);
                    }
                }
                int result2=0;
                int result3=0;
                if(addDataList.size()>0&&updateDataList.size()>0){
                    result2 = systemConfigrationMapper.insertConfigDataInfo(addDataList);
                    result3 = systemConfigrationMapper.updateConifgDataInfo(updateDataList);
                    if(result2>0&&result3>0){
                        return JsonResult.success("保存成功");
                    }
                    else{
                        return JsonResult.error("保存失败");
                    }
                }else{
                    if(addDataList.size()>0 && updateDataList.size()<=0){
                        result2 = systemConfigrationMapper.insertConfigDataInfo(addDataList);
                        if(result2>0){
                            return JsonResult.success("保存成功");
                        }else{
                            return JsonResult.error("保存失败");
                        }
                    }else if(addDataList.size()<=0 && updateDataList.size()>0){
                        result3 = systemConfigrationMapper.updateConifgDataInfo(updateDataList);
                        if(result3>0){
                            return JsonResult.success("保存成功");
                        }else{
                            return JsonResult.error("保存失败");
                        }
                    }else{
                        return JsonResult.error("数据未变动，请检查！");
                    }
                }
            }else{
                return JsonResult.error("参数集合列表为空");
            }
        } catch (Exception e) {
            throw new MyException(e,"系统参数保存时发生异常！");
        }
    }

    @Override
    public JsonResult deleteConfigData(Sys_Configration_Data selectData){
        try {
            if(selectData!=null){
                int result = systemConfigrationMapper.deleteConfigData(selectData.getTypeId(),selectData.getId());
                if(result>0){
                    return JsonResult.success("删除成功！");
                }else{
                    return JsonResult.error("删除失败！");
                }
            }else{
                return JsonResult.error("参数为空！");
            }
        } catch (Exception e) {
            throw new MyException(e,"系统配置参数数据删除异常！");
        }
    }
}
