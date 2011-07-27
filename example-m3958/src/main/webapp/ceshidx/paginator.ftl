<#-- variable
paginator

	public Paginator getPaginator(int totalPage,int pageWindow,int currentPage,String url){
		Paginator p = new Paginator(totalPage, pageWindow, currentPage,url);
		return p;
	}

.item-list .pager{clear:both;text-align:center;}
.item-list .pager li{background-image:none;display:inline;list-style-type:none;padding:0.5em;}
.pager-current{font-weight:bold;}

.item-list .pager{padding:1em 0 0;text-align:left;}
.item-list ul.pager li{margin:0;padding:0 5px;}
.pager li.first{padding:0;}
ul.pager a{color:#2E6AB1;}
.pager li.pager-current{background-color:#2E6AB1;color:#FFF;}
-->
<#if (paginator.totalPage > 1)>
<div class="item-list">
	<ul class="pager">
		<#if paginator.showFirst>
		<li class="pager-first first"><a href="${paginator.firstUrl}" title="Go to first page" class="active">« first</a></li>
		</#if>

		<#if paginator.showPrevious>
		<li class="pager-previous"><a href="${paginator.url + (paginator.currentPage - 1)}" title="Go to previous page" class="active">‹ previous</a></li>
		</#if>
		 
		<#if paginator.showFrontEllipse>
		<li class="pager-ellipsis">…</li>
		</#if>

		<#list paginator.start..paginator.end as pageNum>
			<#if paginator.currentPage == pageNum>
				<li class="pager-current">${pageNum}</li>
			<#else>
				<li class="pager-item"><a href="${paginator.url + pageNum}" title="Go to page 3" class="active">${pageNum}</a></li>	
			</#if>
		</#list>

		<#if paginator.showEndEllipse>
		<li class="pager-ellipsis">…</li>
		</#if> 
		<#if paginator.showNext>
		<li class="pager-next"><a href="${paginator.url + (paginator.currentPage + 1)}" title="Go to next page" class="active">next ›</a></li>
		</#if>
		<#if paginator.showLast> 
		<li class="pager-last last"><a href="${paginator.url + (paginator.totalPage)}" title="Go to last page" class="active">last »</a></li>
		</#if> 
	</ul>
</div>
</#if>

