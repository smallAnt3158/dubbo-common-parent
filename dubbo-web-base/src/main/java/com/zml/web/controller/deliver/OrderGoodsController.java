package com.zml.web.controller.deliver;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dili.deliver.entity.OrderGoods;
import com.dili.deliver.service.IOrderGoodsService;
import com.zml.common.annotation.ControllerLog;
import com.zml.common.enums.OperateLogTypeEnum;
import com.zml.common.page.Page;
import com.zml.common.page.Parameter;
import com.zml.common.web.annotation.Permission;
import com.zml.common.web.base.BaseController;
import com.zml.common.web.entity.Message;


/**
 * 订单商品表
 * 
 * @author zml
 * @email zhaoml529@gmail.com
 * @date 2017-04-17 18:24:49
 */
@RestController
@RequestMapping("/api")
public class OrderGoodsController extends BaseController {

	@Autowired
	private IOrderGoodsService orderGoodsService;
	
	/**
	 * 分页获取列表
	 * @param param
	 * @return
	 */
	@Permission("ordergoods:list")
	@RequestMapping(value = "/orderGoods/list", method = RequestMethod.POST)
	public Message list(@RequestBody Parameter<OrderGoods> param) {
		Message message = new Message();
		super.setDataPermission(param.getParamMap());	// 设置数据权限
		Page page = this.orderGoodsService.getListPage(param);
		message.setMessage("获取列表成功！");
        message.setData(page.getRecordList());
        message.setTotalCount(page.getTotalCount());
        return message;
	}
	
	/**
	 * 详情
	 * @param id
	 * @return
	 */
	@ControllerLog(content = "查询详情信息", operationType = OperateLogTypeEnum.QUERYA)
	@Permission("ordergoods:detail")
	@RequestMapping(value = "/ordergoods/{id}", method = RequestMethod.GET)
	public Message detail(@PathVariable("id") Long id) {
		Message message = new Message();
		OrderGoods orderGoods = this.orderGoodsService.getById(id);
		message.setData(orderGoods);
		return message;
		
	}
	
	/**
	 * 添加
	 * @param orderGoods
	 * @param result
	 * @return
	 */
	@ControllerLog(content = "添加", operationType = OperateLogTypeEnum.ADD)
	@Permission("ordergoods:save")
	@RequestMapping(value = "/ordergoods", method = RequestMethod.POST)
	public Message save(@Valid @RequestBody OrderGoods orderGoods, BindingResult result) {
		Message message = new Message();
		if(result.hasErrors()) {
			message.setValidFail(super.loadFieldError(result));
		} else {
			this.orderGoodsService.save(orderGoods);
			message.setMessage("添加成功！");
			message.setSuc();
		}
		return message;
	}
	
	/**
	 * 更新
	 * @Valid验证前台传过来的参数是否合法
	 * @param id
	 * @param orderGoods
	 * @return
	 */
	@ControllerLog(content = "更新", operationType = OperateLogTypeEnum.UPDATE)
	@Permission("orderGoods:update")
	@RequestMapping(value = "/ordergoods", method = RequestMethod.PUT)
    public Message update(@Valid @RequestBody OrderGoods orderGoods, BindingResult result) {
		Message message = new Message();
		// 相关字段是否验证失败
		if(result.hasErrors()) {
			message.setValidFail(super.loadFieldError(result));
		} else {
			this.orderGoodsService.update(orderGoods);
			message.setMessage("更新成功！");
			message.setSuc();
		}
        return message;
    }
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	@ControllerLog(content = "删除", operationType = OperateLogTypeEnum.DELETE)
	@Permission("orderGoods:delete")
	@RequestMapping(value = "/ordergoods/{id}", method = RequestMethod.DELETE)
    public Message delete(@PathVariable("id") Long id) {
		Message message = new Message();
		this.orderGoodsService.delete(id);
		message.setMessage("删除成功！");
    	message.setSuc();
        return message;
    }
	
}
