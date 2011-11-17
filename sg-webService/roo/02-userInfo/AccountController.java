package com.mycompany.userinfo.web;

import com.mycompany.userinfo.domain.Account;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RooWebScaffold(path = "accounts", formBackingObject = Account.class)
@RequestMapping("/accounts")
@Controller
public class AccountController {

    @RequestMapping(value = "/{id}",params = "reset", method = RequestMethod.GET)
    public String resetPassword(@PathVariable("id") Long id, Model uiModel, HttpServletRequest httpServletRequest) {
        uiModel.asMap().clear();
        Account account = Account.findAccount(id);
        account.setPassword("111111");
        account.merge();
        return "redirect:/accounts/" + encodeUrlPathSegment(account.getId().toString(), httpServletRequest);
    }




}
