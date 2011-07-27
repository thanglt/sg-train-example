<!DOCTYPE html>
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
<title>无标题文档</title> 
 
<link href="css/index.css" rel="stylesheet" type="text/css" /> 

 	<!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="/css/simpleddie.css" media="screen" />
    <![endif]--> 

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script> 
    <link rel="stylesheet" href="http://sfj.m3958.com/css/nivo/nivo-slider.css" type="text/css" media="screen" /> 
 
    <link rel="stylesheet" href="http://sfj.m3958.com/css/nivo/style.css" type="text/css" media="screen" /> 
	 <script type="text/javascript" src="http://sfj.m3958.com//css/nivo/jquery.nivo.slider.js"></script> 
 
 
<script type="text/javascript"> 
    $(window).load(function() {
        $('#slider').nivoSlider({controlNav:false});
    });
    </script> 
 
<style type="text/css"> 
<!--
.STYLE1 {color: #FFFFFF}
.STYLE2 {font-family: "微软雅黑"}
-->
</style> 
</head> 
 
<body background="images/bg.jpg"> 
<div class="index" id="index"> 
  <div id="banner"> 
    <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="960" height="190"> 
      <param name="movie" value="images/sfj111.swf" /> 
      <param name="quality" value="high" /> 
      <embed src="images/sfj111.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="960" height="190"></embed> 
    </object> 
  </div> 
  <div id="menu-wrap"> 
    <ul class="dropdown"> 
        	<!--第二级有sub_menu class，第三级开始没有class -->
        	<#list site.topLinks as tl>
        	<li class=""><a href="${tl.url}">${tl.name}</a>
        		<#if (tl.children?size > 0)>
        		<ul class="sub_menu">
        			<#list tl.children as subtl> 
        			<li><a href="${subtl.url}">${subtl.name}</a></li> 
        			</#list> 
        		</ul>
        		</#if>
        	</li>
        	</#list>
        </ul> 
  </div> 
  <div id="content"> 
    <div id="content-top"> 
      <div id="content-top1"> 
	    <div id="slider-wrapper"> 
            <div id="slider" class="nivoSlider"> 
                <img src="images/toystory.jpg" alt="" /> 
                <a href="http://dev7studios.com"><img src="images/up.jpg" alt="" title="This is an example of a caption" /></a> 
                <img src="images/walle.jpg" alt="" /> 
                <img src="images/nemo.jpg" alt="" title="#htmlcaption" /> 
            </div> 
            <div id="htmlcaption" class="nivo-html-caption"> 
 
                <strong>This</strong> is an example of a <em>HTML</em> caption with <a href="#">a link</a>.
            </div> 
 
		</div> 
      </div> 
      <div id="content-top2"> 
        <div style="width:420px; height:240px; float: left;" class="zycx"> 
	     <#assign gzdt = site.findSection("机关建设/工作动态")>
          <div style="width:420px;" class="zycx_c1">
            <div class="zycx_c1left">最新文件</div> 
            <div class="zycx_c1right STYLE1"><a href="${gzdt.url}">更多</a></div> 
          </div> 
          <div style="height:220px;" class="zycx_main"> 
            <div style="width:400px; height:200px;" class="zycx_main2"> 
              <#list gzdt.getArticles(9) as ar>
              <div style="width:400px;" class="zycx_main2_2"> 
                <div class="zycx_main2_2_1"></div> 
                <div style="width:380px;" class="zycx_main2_2_2"><a href="${ar.url}">${ar.getTitle(20,"...")}</a></div> 
              </div>
              </#list>
            </div> 
          </div> 
        </div> 
      </div>
      <#assign gzdt = site.findSection("机关建设/工作动态")>
      <div id="content-top3"> 
        <div class="content-top3-1"><img src="images/tg.jpg" width="204" height="30" /></div> 
        <div class="content-top3-2"> 
		  <marquee height="200" onmouseout="this.start()" onmouseover="this.stop()" scrolldelay="30" scrollamount="1" direction="up" behavior="scroll" loop="infinite"> 
<span class=" "></span>
<ul>
<#list gzdt.getArticles(9) as ar>
	<li class=" ">
		<h2 class="contentheading ">
			<a title="${ar.title}" class="contentpagetitle " href="${ar.url}">${ar.getTitle(13,"...")}</a>
		</h2>
		<span class="article_separator">
		</span>
	</li> 
</#list>
</ul> 
		  </marquee> 
        </div> 
      </div> 
      <#assign gzdt = site.findSection("机关建设/工作动态")>
      <div id="content-two"> 
        <div id="content-two-right"> 
          <div id="content-two-rightbanner"><img src="images/gg3.jpg" width="734" height="89" /></div> 
          <div class="zycx"> 
            <div class="zycx_c1"> 
              <div class="zycx_c1left">最新文件</div> 
              <div class="zycx_c1right STYLE1">更多</div> 
            </div> 
            <div class="zycx_main"> 
              <div class="zycx_main2"> 
                 <#list gzdt.getArticles(9) as ar> 
                <div class="zycx_main2_2"> 
                  <div class="zycx_main2_2_1"></div> 
                  <div class="zycx_main2_2_2">此处显示  class "zycx_main2_2_2" 的内容</div> 
                </div> 
                </#list>
              </div> 
            </div> 
          </div> 
		  <div class="kongtiao"></div>
		  <#assign gzdt = site.findSection("机关建设/工作动态")>
		  <div class="zycx"> 
            <div class="zycx_c1"> 
              <div class="zycx_c1left"><a href="${gzdt.url}">最新文件<a></div> 
              <div class="zycx_c1right STYLE1"><a href="${gzdt.url}">更多</a></div> 
            </div> 
            
            <div class="zycx_main"> 
              <div class="zycx_main2"> 
                <#list gzdt.getArticles(9) as ar>
                <div class="zycx_main2_2"> 
                  <div class="zycx_main2_2_1"></div> 
                  <div class="zycx_main2_2_2">此处显示  class "zycx_main2_2_2" 的内容</div> 
                </div> 
                </#list>
              </div> 
            </div> 
          </div> 
	      <div id="content-san-rightbanner"><img src="images/gg3.jpg" width="734" height="89" /></div>
	      <#assign gzdt = site.findSection("机关建设/工作动态")>
		  <div class="zycx"> 
            <div class="zycx_c1"> 
              <div class="zycx_c1left">最新文件</div> 
              <div class="zycx_c1right STYLE1">更多</div> 
            </div> 
            <div class="zycx_main"> 
              <div class="zycx_main2"> 
                <#list gzdt.getArticles(9) as ar>
                <div class="zycx_main2_2"> 
                  <div class="zycx_main2_2_1"></div> 
                  <div class="zycx_main2_2_2">此处显示  class "zycx_main2_2_2" 的内容</div> 
                </div> 
                </#list>
              </div> 
            </div> 
          </div> 
		  <div class="kongtiao"></div> 
		  <#assign gzdt = site.findSection("机关建设/工作动态")>
		  <div class="zycx"> 
            <div class="zycx_c1"> 
              <div class="zycx_c1left">最新文件</div> 
              <div class="zycx_c1right STYLE1">更多</div> 
            </div> 
            <div class="zycx_main"> 
              <div class="zycx_main2"> 
                <#list gzdt.getArticles(9) as ar> 
                <div class="zycx_main2_2"> 
                  <div class="zycx_main2_2_1"></div> 
                  <div class="zycx_main2_2_2">此处显示  class "zycx_main2_2_2" 的内容</div> 
                </div> 
                </#list>
              </div> 
            </div> 
          </div> 
        </div> 
        <div id="content-two-left"> 
          <div class="content-two-left1"><img src="images/lm2.jpg" width="204" height="40" /></div> 
          <div class="content-two-left1"><img src="images/lm7.jpg" width="204" height="40" /></div> 
          <div class="content-two-left1"><img src="images/lm1.jpg" width="204" height="40" /></div> 
          <div class="content-two-left1"><img src="images/lm3.jpg" width="204" height="40" /></div> 
          <div class="content-two-left1"><img src="images/lm4.jpg" width="204" height="40" /></div> 
          <div class="content-two-left1"><img src="images/lm5.jpg" width="204" height="40" /></div> 
          <div class="content-two-left1"><img src="images/lm6.jpg" width="204" height="40" /></div> 
        </div> 
    </div> 
      <div class="content-san">此处显示  class "content-san" 的内容</div> 
      <div class="content-san"> 
        <div align="center"> 
          <p class="STYLE2">版权所有：奉化市司法行政网网站 Copyright ? 2001 - 2010  All Rights Reserved   |  站点承建：<a href="#">奉化诗篇网络技术有限公司</a></p> 
        </div> 
      </div> 
      <div class="content-san"><img src="images/banner3333.jpg" width="947" height="50" /></div> 
    </div> 
</div> 
</body> 
</html>
