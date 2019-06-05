package com.hitwh.vblog.service.impl;

/**
 * 李洋
 */

import com.hitwh.vblog.bean.LabelDo;
import com.hitwh.vblog.mapper.LabelDoMapper;
import com.hitwh.vblog.model.LabelModel;
import com.hitwh.vblog.outparam.LabelOutParam;
import com.hitwh.vblog.response.BusinessException;
import com.hitwh.vblog.response.EnumError;
import com.hitwh.vblog.response.PageResponse;
import com.hitwh.vblog.service.LabelService;
import com.hitwh.vblog.validator.ValidationResult;
import com.hitwh.vblog.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {
    @Autowired
    LabelDoMapper labelDoMapper;
    @Autowired
    ValidatorImpl validator;

    // 根据标签ID查询标签
    @Override
    public LabelOutParam queryLabelById(Integer id) throws BusinessException {
        if(id == null || id <=0 )
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        LabelDo labelDo = labelDoMapper.selectByPrimaryKey(id);

        if(labelDo == null)
            return null;

        LabelOutParam labelOutParam = new LabelOutParam();
        labelOutParam.setLabel_id(labelDo.getLabelId());
        labelOutParam.setLabel_name(labelDo.getLabelName());
        labelOutParam.setDiscription(labelDo.getDescription());

        return labelOutParam;
    }

    // 查询所有兴趣
    @Override
    public PageResponse queryAllInterests(Integer start, Integer end) throws BusinessException {
        Integer resultStart = start;
        Integer resultEnd = end;
        Integer resultNum = 0;
        List<LabelDo> labelDos = null;
        if(start == null && end == null){
            labelDos = labelDoMapper.selectAllInterests();
        }
        else if(start != null && end != null && start > 0 && end > start){
            labelDos = labelDoMapper.selectAllInterestsWithPage(start, end);
        }else
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        if(labelDos == null || labelDos.size() == 0)
            return null;

        List<LabelOutParam> labelOutParams = new ArrayList<>();
        for(int i=0; i<labelDos.size(); i++){
            LabelOutParam labelOutParam = new LabelOutParam();
            labelOutParam.setLabel_id(labelDos.get(i).getLabelId());
            labelOutParam.setLabel_name(labelDos.get(i).getLabelName());
            labelOutParam.setDiscription(labelDos.get(i).getDescription());
            labelOutParams.add(labelOutParam);
        }

        if(resultStart == null)
            resultStart = 0;
        if(resultEnd == null)
            resultEnd = labelDos.size();

        resultNum = labelDoMapper.interestsPageCount();

        return PageResponse.create(resultStart, resultEnd, resultNum, labelDos);
    }

    @Override
    public void insertLabel(LabelModel labelModel) throws BusinessException {
        if(labelModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        ValidationResult result = validator.validate(labelModel);
        if(result.isHasErrors()){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        LabelDo labelDo = new LabelDo();
        labelDo.setLabelName(labelModel.getLabelName());
        labelDo.setDescription(labelModel.getDescription());

        Integer column = labelDoMapper.insert(labelDo);

        if(column == null || column == 0)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);
    }

    @Override
    public void updateLabel(LabelModel labelModel) throws BusinessException {
        if(labelModel == null)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        if(labelModel.getLabelId() == null || labelModel.getLabelId() <= 0)
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR);

        ValidationResult result = validator.validate(labelModel);
        if(result.isHasErrors()){
            throw new BusinessException(EnumError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        LabelDo labelDo = new LabelDo();
        labelDo.setLabelId(labelModel.getLabelId());
        labelDo.setLabelName(labelModel.getLabelName());
        labelDo.setDescription(labelModel.getDescription());

        Integer column = labelDoMapper.updateByPrimaryKeySelective(labelDo);

        if(column == null || column == 0)
            throw new BusinessException(EnumError.DATABASE_INSERT_ERROR);
    }


}
