<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>诗篇建站系统OpenId登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 
<script src="http://code.jquery.com/jquery-latest.min.js"></script> 
<script type="text/javascript" src="/aaayjcsxdl/popuplib.js"></script> 
<script type="text/javascript"> 
  var upgradeToken = function() {
    window.location = '';
  };
  var extensions = {"openid.ns.ext1":"http:\/\/openid.net\/srv\/ax\/1.0","openid.ext1.mode":"fetch_request","openid.ext1.type.email":"http:\/\/axschema.org\/contact\/email","openid.ext1.type.first":"http:\/\/axschema.org\/namePerson\/first","openid.ext1.type.last":"http:\/\/axschema.org\/namePerson\/last","openid.ext1.type.country":"http:\/\/axschema.org\/contact\/country\/home","openid.ext1.type.lang":"http:\/\/axschema.org\/pref\/language","openid.ext1.required":"email,first,last,country,lang","openid.ns.oauth":"http:\/\/specs.openid.net\/extensions\/oauth\/1.0","openid.oauth.consumer":"googlecodesamples.com","openid.oauth.scope":"http:\/\/docs.google.com\/feeds\/ http:\/\/spreadsheets.google.com\/feeds\/ http:\/\/www-opensocial.googleusercontent.com\/api\/people\/","openid.ui.icon":"true"};
  var googleOpener = popupManager.createPopupOpener({
    'realm' : 'http://googlecodesamples.com',
    'opEndpoint' : 'https://www.google.com/accounts/o8/ud',
    'returnToUrl' : 'http://googlecodesamples.com/hybrid/index.php?popup=true',
    'onCloseHandler' : upgradeToken,
    'shouldEncodeUrls' : true,
    'extensions' : extensions
  });
  $(document).ready(function () {
    jQuery('#LoginWithGoogleLink').click(function() {
      googleOpener.popup(450, 500);
      return false;
    });
  });
  
</script> 
<script type="text/javascript"> 
function toggle(id, type) {
  if (type === 'list') {
    $('pre.' + id).hide();
    $('div.' + id).show();
  } else {
    $('div.' + id).hide();
    $('pre.' + id).show();
  }
}
</script>
<style type="text/css">
body {
  color:#666666;
  margin:10px;
}

fieldset {
  border:1px solid #ccc;
  width:450px;
}

h3 {
  margin-bottom:1.5em;
}

form {
  margin:0;
}

ul {
  list-style:none;
  margin-left:0;
  padding-left:0;
}

li {
  margin-left:0;
  padding-left:0;
}

a {
  color:#2F58A4;
  text-decoration:none;
}

a:hover {
  text-decoration:underline;
}

#openid_identifier {
  background: transparent url('/aaayjcsxdl/images/login-bg.gif') no-repeat scroll 0pt 50%;
  padding-left: 18px;
  margin:3px 0 5px 0;
}

.errors {
  margin:10px 0 10px;
  padding:10px;
  text-align:center;
  border: 1px solid red;
  color:red;
  font-weight:bold;
  background-color:#ffffcc;
}

.data_area {
  color:#fff;
  background-color:#000;
  height:350px;
  width:80%;
  border:1px solid #000;
  overflow:auto;
}

.google {
  font-weight:bold;
}

.google :first-child,
.google :first-child + span + span + span {
  color:#1f47b2; /*blue*/
}

.google :first-child + span,
.google :first-child + span + span + span + span + span {
  color:#c61800; /*red*/
}

.google :first-child + span + span {
  color:#bc9200; /*yellow*/
}

.google :first-child + span + span + span + span {
  color:#18a221;
}

</style>
</head> 
<body> 
<div>尚未启用，即将启用</div>
<div> 
<form method="POST" action="/hybrid/index.php"> 
<fieldset><legend><small><b>Enter an OpenID:</b></small></legend> 
  <input type="hidden" name="openid_mode" value="checkid_setup"> 
  <input type="text" name="openid_identifier" id="openid_identifier" size="40" value="google.com/accounts/o8/id" /> <input type="submit" value="login" />
  <br> 
  Sign in with a
  <a href="/hybrid/index.php?openid_mode=checkid_setup&openid_identifier=google.com/accounts/o8/id" id="LoginWithGoogleLink">
  	<img height="16" width="16" align="absmiddle" style="margin-right: 3px;" src="/aaayjcsxdl/images/gfavicon.gif" border="0"/>
  	<span class="google"><span>G</span><span>o</span><span>o</span><span>g</span><span>l</span><span>e</span> Account</span>
  </a> (popup)
</fieldset> 
</form> 
</div>
</body> 
</html> 