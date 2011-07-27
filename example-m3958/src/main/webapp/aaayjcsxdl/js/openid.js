/**
 * 
 */


YUI({combine:true,charset:'utf-8',
    modules:  {
        'message-box-module':{
            fullpath: '/aaayjcsxdl/js/webmastertools/messagediv.js',
            requires: ['node','stylesheet','align-plugin']
        }
    }
	}).use('node','io-base','io-form','event-delegate','dump','json','history','tabview','message-box-module', function(Y) {
	
//	Y.JSON.parse('{"a":0}');
//	Y.JSON.parse("{status:-1,message:'登录失败！请检查OpenId是否正确！'}");
	
	var user = null,
		loginStatus = Y.one('#login-status'),
		loginStatusTpl = loginStatus.getContent(),
		logined = false;
	
	  var history = new Y.HistoryHash(),
      tabview = new Y.TabView({srcNode: '#openid-tabview'});
	  tabview.render();
	  
	  tabview.selectChild(history.get('tab') || 0);
	  
	  tabview.get('srcNode').get("ownerDocument").get("documentElement").removeClass("yui3-loading");
	  
	  tabview.on('selectionChange',function(e){
		  ;
	  });
	  
	  tabview.after('selectionChange', function (e) {
		  Y.log(e.newVal.get('index'));
		  if(e.newVal.get('index') == 3 && user){
			  setUserDetail();
		  }
		  history.addValue('tab', e.newVal.get('index') || null);
	  });
	
	  Y.on('history:change', function (e) {
	    if (e.src === Y.HistoryHash.SRC_HASH) {
	
	      if (e.changed.tab) {

	        tabview.selectChild(e.changed.tab.newVal);
	      } else if (e.removed.tab) {

	        tabview.selectChild(0);
	      }
	    }
	  });
	  
	  function setUserDetail(){
		  edit_login_form.one('#loginNamee').set('value',user.loginName);
		  edit_login_form.one('#emaile').set('value',user.email);
		  edit_login_form.one('#nicknamee').set('value',user.nickname);
		  edit_login_form.one('#mobilee').set('value',user.mobile);
		  if(user.fcUser && user.loginName == user.email && user.email == user.mobile){
			  edit_login_form.one('#prePassword').set('value',user.loginName);
		  }
	  }


	var discs = {'google-disc':'www.google.com/accounts/o8/id','yahoo-disc':'yahoo.com','qq-disc':'/qqauthlogin'};
	
	var signupwrap = Y.one('#signup-wrap'),
		siteloginwrap = Y.one('#sitelogin-wrap'),
		openidwrap = Y.one('#openid-wrap'),
		edit_login_form = Y.one('#edit-login-form'),
		site_signup_form = Y.one('#site-signup-form'),
		site_login_form = Y.one('#site-login-form'),
		openid_form = Y.one('#openid-form'),
		ajaxloading = Y.one('#ajaxloading');
	
	
	var wheretogo="";
	var pattern = new RegExp( "wheretogo=([^&]+)", "i" );
	if(matches = pattern.exec( window.location.href )){
		wheretogo = matches[1];
	}
	
	var GlobalEventHandler = {
			  start: function(id, args) {
				  ajaxloading.show();
			  },
			  end: function(id, args) {
				  ajaxloading.hide();
			  }
	};
	Y.on('io:start', GlobalEventHandler.start, Y);
	Y.on('io:end', GlobalEventHandler.end, Y);
	
	Y.on('click',function(e){
		this.set('src','/simpleImg?' + Math.random());
	},'.captchaImg');
	
	Y.delegate('click',function(e){
		e.preventDefault();
		if(logined){
			Y.messageBox.prompt("您已经登录，要继续操作请先退出登录！");
			return;
		}
		var durl = discs[e.currentTarget.get("id")];
		
		if('/qqauthlogin' == durl){
			openid_form.set('action',durl);
		}else{
			openid_form.set('action','/openid');
		}
		
//		var data;
		Y.one('#openid_identifier').set('value',durl);
		
		if(wheretogo){
//			data = "wheretogo=" + encodeURIComponent(wheretogo) + "&openid_identifier=" + encodeURIComponent(durl);
			Y.one('#openid-wheretogo').set('value',wheretogo);
		}else{
//			data = "wheretogo=" + encodeURIComponent("/openid.html#tab=4") + "&openid_identifier=" + encodeURIComponent(durl);
			Y.one('#openid-wheretogo').set('value',"/openid.html#tab=4");
		}
		openid_form.submit();
//		Y.log(data);
//		(function(){
//			var tH = {
//					success: function(id, o, args) {
//							Y.log(o.responseText);
//							var res = Y.JSON.parse(o.responseText);
//								if(res.status == 0){
//								   window.location.href = res.message;
//								   logined = true;
//								}else{
//									Y.messageBox.warn(res.message);
//								}
//							 },
//					failure: function(id, o, args) {
//							  alert(o.responseText);
//							 }
//
//				};
//			
//			var cfg = {
//					method:'GET',
//					data:data,
//					on: {
//						success: tH.success,
//						failure: tH.failure
//					},
//					context: tH
//				};
//			
//			Y.io('/openid', cfg);		
//		})();
		
	},'#openid-discs','a');
	
	
	
	site_signup_form.on('submit',function(e){
		 e.preventDefault();
		 
		 var stop = false;
		 var fs = {loginName:this.one('#loginName'),
				 	answer:this.one('#answer'),
				 	password:this.one('#password'),
				 	confirmPassword:this.one('#confirmPassword'),
				 	email:this.one('#email'),
				 	mobile:this.one('#mobile')};

		 Y.each(fs,function(n){
			 if(!n.get('value')){
				 n.setStyle('backgroundColor','red');
				 stop = true;
			 }
		 });
		 if(stop)return; 
		 
		 if(fs.loginName.get('value').indexOf('@') != -1){
			 fs['loginName'].setStyle('backgroundColor','red');
			 Y.messageBox.warn('用户名不能包含@符号！');
			 return;
		 }
		 
		 if(fs.email.get('value').indexOf('@') == -1){
			 fs['email'].setStyle('backgroundColor','red');
			 Y.messageBox.warn('非法的电子邮件！');
			 return;
		 }
		 
		 if(fs.password.get('value') != fs.confirmPassword.get('value')){
			 fs['password'].setStyle('backgroundColor','red');
			 fs['confirmPassword'].setStyle('backgroundColor','red');
			 Y.messageBox.warn('两次密码不相符！');
			 return;
		 }
		 
		(function(){
			var tH = {
					success: function(id, o, args) {
							var res = Y.JSON.parse(o.responseText);
							if(res.response.status == 0){
								Y.messageBox.prompt('注册成功！');
								tabview.selectChild(0);
							}else{
								var err = getError(res.response);
								if(err){
									Y.messageBox.warn(err.message);
								}else{
									Y.messageBox.warn('发生未知错误，注册失败！');
								}
								Y.one('#site-signup-form img.captchaImg').set('src','/simpleImg?' + Math.random());
							}
						},
					failure: function(id, o, args) {
								Y.log(o);
								alert("发生未知故障！");
							 }

				};
			
			var cfg = {
					method:'GET',
					form: {
						id: 'site-signup-form',
						useDisabled: true
					},
					on: {
						success: tH.success,
						failure: tH.failure
					},
					context: tH
				};
			
			Y.io('/jqfprocessor', cfg);
		})();
	});
	
	edit_login_form.on('submit',function(e){
		 e.preventDefault();
		 
		 var stop = false;
		 var fs = {loginName:this.one('#loginNamee'),
				 	answer:this.one('#answere'),
				 	password:this.one('#passworde'),
				 	confirmPassword:this.one('#confirmPassworde'),
				 	email:this.one('#emaile'),
				 	mobile:this.one('#mobilee'),
				 	prePassword:this.one('#prePassword')};
		 Y.each(fs,function(v,k){
			 if('password' == k || 'confirmPassword' == k)return;
			 if(!v.get('value')){
				 v.setStyle('backgroundColor','red');
				 stop = true;
			 }
		 });
		 
		 if(stop)return; 
		 
		 if(fs.loginName.get('value').indexOf('@') != -1){
			 Y.messageBox.warn('用户名不能包含@符号！');
			 return;
		 }
		 
		 if(fs.email.get('value').indexOf('@') == -1){
			 Y.messageBox.warn('非法的电子邮件！');
			 return;
		 }
		 
		 if(fs.password.get('value') != fs.confirmPassword.get('value')){
			 fs['password'].setStyle('backgroundColor','red');
			 fs['confirmPassword'].setStyle('backgroundColor','red');
			 Y.messageBox.warn('两次密码不相符！');
			 return;
		 }
		 
		 if(fs.prePassword.get('value').length == 36 && (!fs.password.get('value') || fs.password.get('value').length < 6)){
			 fs['password'].setStyle('backgroundColor','red');
			 fs['confirmPassword'].setStyle('backgroundColor','red');
			 Y.messageBox.warn('openId绑定时必须提供新密码，至少6位！');
			 return;
		 }
		 
		(function(){
			var tH = {
					success: function(id, o, args) {
							var res = Y.JSON.parse(o.responseText);
							if(res.response.status == 0){
								Y.messageBox.prompt('修改成功！');
								tabview.selectChild(0);
							}else{
								var err = getError(res.response);
								if(err){
									Y.messageBox.warn(err.message);
								}else{
									Y.messageBox.warn('发生未知错误，修改失败！');
								}
								Y.one('#edit-login-form img.captchaImg').set('src','/simpleImg?' + Math.random());
							}
						},
					failure: function(id, o, args) {
								Y.log(o);
								alert("发生未知故障！");
							 }

				};
			
			var cfg = {
					method:'GET',
					form: {
						id: 'edit-login-form',
						useDisabled: true
					},
					on: {
						success: tH.success,
						failure: tH.failure
					},
					context: tH
				};
			
			Y.io('/jqfprocessor', cfg);
		})();
	});
	
	
	site_login_form.on('submit',function(e){
		 e.preventDefault();
		 var stop = false;
		 var fs = [this.one('#username'),this.one('#answerl'),this.one('#passwordl')];
		 Y.each(fs,function(n){
			 if(!n.get('value')){
				 n.setStyle('backgroundColor','red');
				 stop = true;
			 }
		 });
		 if(stop)return;
		(function(){
			var tH = {
					success: function(id, o, args) {
								var res = Y.JSON.parse(o.responseText);
								var status = res.response.status;
								var data = res.response.data;
								if(status == 0){
									   if(Y.Lang.isArray(data) && data.length == 1){
										   user = res.response.data[0];
										   logined = true;
										   Y.log(wheretogo);
										   if(wheretogo)
											   window.location.href = wheretogo;
										   else{
											   tabview.selectChild(4);
										   }
										   changeLoginStatus(true);
										   return;
									   }
								}else{
									Y.messageBox.warn(data[0].message);
									Y.one('#site-login-form img.captchaImg').set('src','/simpleImg?' + Math.random());
								}
							 },
					failure: function(id, o, args) {
							  Y.log(o);
							  alert("发生未知故障！");
							 }

				};
			
			var cfg = {
					method:'GET',
					form: {
						id: 'site-login-form',
						useDisabled: true
					},
					on: {
						success: tH.success,
						failure: tH.failure
					},
					context: tH
				};
			
			Y.io('/notrpclogin', cfg);
		})();
	});
	
	
	openid_form.on('submit',function(e){
		 var f = Y.one('#openid_identifier');
		 if(!f.get('value')){
			 f.setStyle('backgroundColor','red');
			 e.preventDefault();
			 return;
		 }
//		 var data = "";
		 if(wheretogo){
//			 data = "wheretogo=" + encodeURIComponent(wheretogo);
			 Y.one('#openid-wheretogo').set('value',wheretogo);
		 }else{
//			 data = "wheretogo=" + encodeURIComponent('/openid.html#tab=4');
			 Y.one('#openid-wheretogo').set('value','/openid.html#tab=4');
		 }
		 
//		(function(){
//			var tH = {
//					success: function(id, o, args) {
//							   window.location.href = o.responseText;
//							   logined = true;
//							 },
//					failure: function(id, o, args) {
//							  ;
//							 }
//
//				};
//			
//			var cfg = {
//					method:'GET',
//					form: {
//						id: 'openid-form',
//						useDisabled: true
//					},
//					data:data,
//					on: {
//						success: tH.success,
//						failure: tH.failure
//					},
//					context: tH
//				};
//			
//			Y.io('/openid', cfg);		
//		})();
	});
	
	
	
	(function(){
		var tH = {
				success: function(id, o, args) {
						   var res = Y.JSON.parse(o.responseText);
						   Y.log(res);
						   if(res && res.response && res.response.data && res.response.data.length == 1){
							   user = res.response.data[0];
							   if(tabview.get('selection').get('index') == 3){
								   setUserDetail();
							   }
							   if(user.loginName == user.email && user.email == user.mobile && user.fcUser){
								   tabview.selectChild(3);
								   Y.messageBox.warn('您通过openId登录，请修改资料绑定本地账号，以防万一OpenId不可用！',5000);
							   }
							   changeLoginStatus(true);
							   logined = true;
						   }else{
							   tabview.item(3).set('disabled',true);
							   changeLoginStatus(false);
							   logined = false;
						   }
						 },
				failure: function(id, o, args) {
							Y.log(o);
							alert("发生未知故障！");
						 }

			};
		
		var cfg = {
				on: {
					success: tH.success,
					failure: tH.failure
				},
				context: tH
			};
		
		Y.io('/openid?loginstatus=true', cfg);		
	})();
	
	function changeLoginStatus(login){
		if(login){
				if(!user)user = {};
			   loginStatus.setContent(Y.Lang.sub(loginStatusTpl,user));
			   loginStatus.show();
			   site_login_form.all('input').set('disabled',true);
			   site_signup_form.all('input').set('disabled',true);
//			   if(user && user.fcUser){
//				   edit_login_form.all('input').set('disabled',true);
//			   }else{
				   edit_login_form.all('input').set('disabled',false);
//			   }
			   
			   openidwrap.all('input').set('disabled',true);
		}else{
			   site_login_form.all('input').set('disabled',false);
			   site_signup_form.all('input').set('disabled',false);
			   edit_login_form.all('input').set('disabled',true);
			   openidwrap.all('a,input').set('disabled',false);
			   loginStatus.hide();
		}
	}
	
	function getError(res){
		if(Y.Lang.isArray(res.data)){
			var ec = res.data[0];
/*			if(ec.code == -1){
				
			}*/
			return ec;
		}else{
			return null;
		}
	}
});

//https://www.google.com/accounts/o8/ud