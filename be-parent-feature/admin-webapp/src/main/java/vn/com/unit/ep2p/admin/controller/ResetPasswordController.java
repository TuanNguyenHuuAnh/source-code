
package vn.com.unit.ep2p.admin.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Case;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.core.security.UserProfileUtils;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.constant.*;
import vn.com.unit.ep2p.admin.service.ResetPasswordService;
import vn.com.unit.ep2p.admin.service.impl.ResetPasswordServiceImpl;
import vn.com.unit.ep2p.admin.validators.ResetPasswordValidator;
import vn.com.unit.ep2p.constant.UrlConst;
import vn.com.unit.ep2p.dto.ResetPasswordDto;

@RequestMapping(UrlConst.ROOT + UrlConst.ACCOUNT)
@Controller
public class ResetPasswordController {

	@Autowired
	private ResetPasswordService resetPasswordService;

	@Autowired
	private ResetPasswordValidator resetPasswordValidator;

	@Autowired
	private MessageSource msg;

	private static final String VIEW_LIST = "/views/reset-password/reset-password.html";

	/** SCREEN_FUNCTION_CODE */
	private static final String SCREEN_FUNCTION_CODE = RoleConstant.RESET_PASSWORD;

	private Logger logger = LoggerFactory.getLogger(ResetPasswordServiceImpl.class);

	@RequestMapping(value = UrlConst.ACCOUNT_RESET_PASSWORD, method = RequestMethod.GET)
	public ModelAndView doView(@RequestParam(name = "team-id", required = false) Long teamId, Model model,
			Locale locale) {

		// Security for this page.
		if (!UserProfileUtils.hasRole(SCREEN_FUNCTION_CODE)) {
			return new ModelAndView(ViewConstant.ACCESS_DENIED_MODELANDVIEW);
		}

		ModelAndView mav = new ModelAndView(VIEW_LIST);
		mav.addObject("editDto", new ResetPasswordDto());
		return mav;
	}

	@RequestMapping(value = UrlConst.ACCOUNT_RESET_PASSWORD, method = RequestMethod.POST)
	@ResponseBody
	public ResetPasswordDto checkSubmit(@ModelAttribute(value = "editDto") ResetPasswordDto editDto,
			BindingResult bindingResult, Locale locale, HttpServletRequest request) {

		editDto.setLanguageCode(locale.toString());
		resetPasswordValidator.validate(editDto, bindingResult);
		if (!bindingResult.hasErrors()) {
			editDto.setResult("");
		}

		return editDto;

	}

	@RequestMapping(value = UrlConst.ACCOUNT_RESET_PASSWORD
			+ UrlConst.AJAX_CHANGE_PASSWORD, method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView resetPass(@ModelAttribute(value = "editDto") ResetPasswordDto editDto,
			BindingResult bindingResult, Locale locale, HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws DetailException {
		ModelAndView mav = new ModelAndView(VIEW_LIST);

		// Init message list
		MessageList messageList = new MessageList();
		String msgInfo = msg.getMessage(ConstantCore.MSG_RESET_PASSWORD_SUCCESS, null, locale);
		// send
		try {
			ResetPasswordDto active = resetPasswordService.activeSend(editDto);
			if (ObjectUtils.isEmpty(active.getResult())) {
				// reset password
				ResetPasswordDto newPassword = resetPasswordService.resetPassword(editDto);
				// choose password reset
				String result = resetPasswordService.setPasswordReset(editDto, locale);
				if (StringUtils.isNotBlank(result)) {
					messageList.setStatus(Message.ERROR);
					if(StringUtils.endsWithIgnoreCase(result,"AGENTNOTGA")){
						msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_RESET_PASSWORD, null, locale);

					}
					else{
						msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_RESET_PASSWORD, null, locale);

					}
					messageList.add(msgInfo);
				}

			} else {
				return returnError(editDto, locale);
			}
		} catch (Exception e) {
			messageList.setStatus(Message.ERROR);
			msgInfo = msg.getMessage(ConstantCore.MSG_ERROR_RESET_PASSWORD, null, locale);
			messageList.add(msgInfo);
		}
		
		if (StringUtils.isBlank(messageList.getStatus())) {
			editDto.setMessage(Message.SUCCESS);
		}
			
		mav.addObject("editDto", editDto);
		mav.addObject(ConstantCore.MSG_LIST, messageList);
		return mav;
	}

	protected ModelAndView returnError(ResetPasswordDto editDto, Locale locale) {
		ModelAndView mav = new ModelAndView(VIEW_LIST);
		MessageList messageList = editDto.getMessageList();
		if (StringUtils.isNotBlank(editDto.getResult())) {

			if (StringUtils.equalsAnyIgnoreCase(editDto.getResult(), "NOT_SEND_EMAIL")) {
				messageList = new MessageList(Message.ERROR);
				String INFO = msg.getMessage(ConstantCore.NOT_SEND_EMAIL, null, locale);
				messageList.add(INFO);
			}
			if (StringUtils.equalsAnyIgnoreCase(editDto.getResult(), "NOT_SEND_EMAIL_DLVN")) {
				messageList = new MessageList(Message.ERROR);
				String INFO = msg.getMessage(ConstantCore.NOT_SEND_EMAIL_DLVN, null, locale);
				messageList.add(INFO);
			}
			if (StringUtils.equalsAnyIgnoreCase(editDto.getResult(), "NOT_SEND_PHONE")) {
				messageList = new MessageList(Message.ERROR);
				String INFO = msg.getMessage(ConstantCore.NOT_SEND_PHONE, null, locale);
				messageList.add(INFO);
			}
		}else {

			if (messageList == null) {
				messageList = new MessageList(Message.ERROR);
				String INFO = msg.getMessage(ConstantCore.MSG_NO_SEND, null, locale);
				String msgError = StringUtils.replaceOnce(INFO, "{0}", editDto.getAgent());
				messageList.add(msgError);
			}
		}

		mav.addObject("messageList", messageList);
		mav.addObject("editDto", editDto);

		return mav;
	}

}
